package com.kang.kanglog.service;

import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Like;
import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.domain.User;
import com.kang.kanglog.repository.like.LikeRepository;
import com.kang.kanglog.repository.post.PostRepository;
import com.kang.kanglog.repository.tag.TagRepository;
import com.kang.kanglog.repository.user.UserRepository;
import com.kang.kanglog.utils.component.S3Uploader;
import com.kang.kanglog.web.dto.post.PostResDto;
import com.kang.kanglog.web.dto.user.UserRespDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    //의존성이 너무 많아지는 것 같긴 한데.. 그렇다고 인터페이스를 만들기는 싫다.

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final TagRepository tagRepository;
    private final S3Uploader s3Uploader;

    @Transactional//더티체킹 todo s3로 대체할 지 고민해봐야겠다.
    public User 회원사진변경(MultipartFile profileImageFile, PrincipalDetails principalDetails, HttpServletRequest request) {

        UUID uuid = UUID.randomUUID(); //같은 이름의 사진이 들어오면 충돌나므로 방지하기 위해
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename();
        log.info("파일명 : "+imageFileName);

        String uploadPath = "";
        try {
            uploadPath = s3Uploader.upload(profileImageFile, imageFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Path imageFilePath = Paths.get(uploadPath);
        log.info("파일패스 : "+uploadPath);

        User userEntity = userRepository.findById(principalDetails.getUser().getId()).get();
        userEntity.setPicture(uploadPath); //풀경로는 안넣어도 되는게,
        //userEntity.setProfileImgUrl(imageUrl);

        return userEntity;
    }


    @Transactional
    public int 회원탈퇴(Long id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("id를 찾을 수 없습니다.");
        });
        userRepository.deleteById(id); //여기서 자동으로 에러를 내뱉지 않을까? 위에 로직이 없어도 상관없을 것 같긴 한데
        return 1;
    }


    @Transactional(readOnly = true)
    public UserRespDto.UserVelogRespDto 회원블로그(Long userId) {

        UserRespDto.UserVelogRespDto userVelogRespDto = new UserRespDto.UserVelogRespDto();
        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            return new IllegalArgumentException();
        });
        List<Tag> tagsEntity = tagRepository.mFindUserTags(userId);
        userVelogRespDto.setPostCount((long) userEntity.getPosts().size());
        userVelogRespDto.setTags(tagsEntity);
        userVelogRespDto.setUser(userEntity);

        return userVelogRespDto;
    }


    @Transactional(readOnly = true)
    public Page<PostResDto.PostDto> 좋아요게시글목록(Long principalId, Pageable pageable) {
        // 읽기 목록, N+1 문제를 해결하기 위해.. fetch join을 썼는데, api 명세가 달라지는 걸


        Page<Like> posts = likeRepository.mLikeList(pageable, principalId);
        if (posts == null){
            return null;
        }

        //동일한 양식으로 프론트에게 돌려줘야겠당.
        Page<PostResDto.PostDto> likePosts = posts.map(like -> {
            PostResDto.PostDto postDto = PostResDto.PostDto.builder()
                    .id(like.getPost().getId())
                    .title(like.getPost().getTitle())
                    .content(like.getPost().getContent())
                    .thumbnail(like.getPost().getThumbnail())
                    .user(like.getUser())
                    .comments(like.getPost().getComments()) //이건 불가능..
                    //.likes(like.getPost().getLikes())
                    .likeCount(posts.getContent().size())
                    .likeState(true)
                    .build();

            return postDto;
        });

        return likePosts;
    }


}
