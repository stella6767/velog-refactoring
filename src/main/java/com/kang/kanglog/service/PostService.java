package com.kang.kanglog.service;


import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Comment;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.repository.post.PostRepository;
import com.kang.kanglog.repository.tag.TagRepository;
import com.kang.kanglog.utils.component.S3Uploader;
import com.kang.kanglog.utils.util_function.UtilManager;
import com.kang.kanglog.web.dto.post.PostResDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

import static com.kang.kanglog.web.dto.post.PostReqDto.*;

@RequiredArgsConstructor
@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;



    @Transactional(readOnly = true) //1.변경감지 안하도록 하고(쓸데없는 연산제거), 2.고립성 유지
    public Page<Post> 검색하기(String keyword, Pageable pageable){
        return postRepository.mFindByKeyword(keyword,pageable);
    }

    @Transactional
    public int 삭제하기(Long id, Long userId){

        Post postEntity = postRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("id를 찾을 수 없습니다.");
        });

        if(postEntity.getUser().getId() == userId) {
            postRepository.deleteById(id);
            return 1;
        }else {
            return -1;
        }

    }



    @Transactional//서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다.
    public Long 저장하기(PostSaveReqDto postSaveReqDto, PrincipalDetails principalDetails)  {

        log.info("저장하기 요청 옴.");

        List<String> imgUrlList = new ArrayList<>();

        String imgSrc = "";

        Document doc = Jsoup.parseBodyFragment(postSaveReqDto.getContent());
        Elements imgs = doc.getElementsByTag("img");

        if(imgs.size() > 0) {
            imgSrc = imgs.get(0).attr("src"); //첫번째 이미지 썸네일
            List<String> imgSrcList = UtilManager.getImgSrcList(postSaveReqDto.getContent());
            log.info("게시글 이미지 리스트: " + String.valueOf(imgSrcList.size()));

            for (String base64Str : imgSrcList) {
                String s3Url = s3Uploader.putS3WithBase64(base64Str);
                imgUrlList.add(s3Url);
            }
        }

        postSaveReqDto.refineContent(doc, imgUrlList, imgs);
        Post post = modelMapper.map(postSaveReqDto, Post.class);
        post.add(principalDetails.getUser(), imgUrlList);

        Post postEntity = postRepository.save(post);

        if(postSaveReqDto.getTags() != null){
            List<Tag> tags =  UtilManager.parsingToTagObject(postSaveReqDto.getTags(), post);
            tagRepository.saveAll(tags);
        }

        return postEntity.getId();
    }



    @Transactional(readOnly = true) //JPA 변경감지라는 내부 기능 활성화 X, update시의 정합성을 유지해줌. inset의 유령데이터현상(팬텀현상) 못막음
    public PostResDto.PostDto 한건가져오기(Long userId, Long postId, PrincipalDetails details) {

        long id = getPrincipalId(details);

        if(userId == id){
            log.info("내 벨로그 글에 들어왔다고 판단");
        }
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!"));

        log.info("트랜잭션 경계 안 1 " + TransactionSynchronizationManager.isActualTransactionActive());
//        log.info(String.valueOf(postEntity.getTags()));
//        log.info(String.valueOf(postEntity.getComments()));

        PostResDto.PostDto postDto = new PostResDto.PostDto(id, postEntity);

        return postDto;
    }


    //todo refactoring 대상
    @Transactional(readOnly = true)
    public Page<Post> 트렌딩게시글(PrincipalDetails details, Pageable pageable){

        long id = getPrincipalId(details);
        Page<Post> posts = postRepository.mTrending(pageable);
        Page<PostResDto.PostDto> postDtos = posts.map(entity -> {
            PostResDto.PostDto dto = new PostResDto.PostDto(id, entity);
            return dto;
        });

        return posts;
    }



    @Transactional(readOnly = true)
    public Page<PostResDto.PostDto> 전체찾기(PrincipalDetails details, Pageable pageable){

        log.info("최신 순으로 찾기");
        long id = getPrincipalId(details);

        Page<Post> posts = postRepository.mfindAllByPage(pageable);
        Page<PostResDto.PostDto> postDtos = posts.map(entity -> {
            PostResDto.PostDto dto = new PostResDto.PostDto(id, entity);
            return dto;
        });

        return postDtos;
    }




    private long getPrincipalId(PrincipalDetails details) {
        long id = 0L;
        if(details != null){
            id = details.getUser().getId();
        }
        return id;
    }






//    @Transactional
//    public Post 수정하기(Long id, Post Post) {
//        //더티체킹 update치기
//        Post postEntity = postRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));// 영속화 (Post 오브젝트) -영속성 컨텍스트 보관
//
//        postEntity.setTitle(Post.getTitle());
//        postEntity.setAuthor(Post.getAuthor());
//
//        return PostEntity;
//    }//함수 종료=>트랜잭션 종료 => 영속화 되어있는 데이터를 DB로 갱신(flush) => commit ===========>더티체킹



}
