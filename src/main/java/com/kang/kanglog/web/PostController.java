package com.kang.kanglog.web;


import com.kang.kanglog.config.anno.LoginCheck;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.handler.custom_exception.NoLoginException;
import com.kang.kanglog.service.LikesService;
import com.kang.kanglog.service.PostService;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.web.dto.post.PostReqDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.kang.kanglog.web.dto.post.PostReqDto.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;



    @LoginCheck
    @DeleteMapping("/post/{id}")
    public CMResDto<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails details){

        return new CMResDto<>(postService.삭제하기(id, details.getUser().getId()), "게시글 삭제", null);
    }

    // 주소: /post/all?page=0   자동으로 이렇게 먹음
    @GetMapping("/post/all")
    public CMResDto<?> findAllByRecent(@AuthenticationPrincipal PrincipalDetails details,
                                       @PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 16) Pageable pageable ){
        log.info("최신게시글 페이지.");
        return new CMResDto<>(1, "게시글리스트 불러오기", postService.전체찾기(details, pageable));
    }



    @GetMapping("/post/trend")  //프론트단에서는 "/" 로 맵핑.
    public CMResDto<?> findAllByLike(@AuthenticationPrincipal PrincipalDetails details, @PageableDefault(size = 16) Pageable pageable){
        log.info("메인 페이지(트렌딩 페이지)");
        return new CMResDto<>(1, "게시글리스트 불러오기", postService.트렌딩게시글(details, pageable));
    }



    @LoginCheck
    @PostMapping("/post")
    public CMResDto<?> image(@RequestBody PostSaveReqDto postSaveReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new CMResDto<>(1,"게시글 저장", postService.저장하기(postSaveReqDto, principalDetails) );
    }


    @GetMapping("/post/search")
    public CMResDto<?> search(@RequestParam(value="keyword") String keyword,
                         @PageableDefault(size = 10) Pageable pageable) {
        return new CMResDto<>(1, keyword + " 검색결과", postService.검색하기(keyword,pageable));
    }




    @GetMapping("/post/{userId}/{postId}")
    public CMResDto<?> detail(@PathVariable Long userId, @PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails details) {

        log.info("게시글 싱세보기." + userId+" " + postId);

        return new CMResDto<>(1,"게시글 상세보기", postService.한건가져오기(userId, postId, details));
    }





    @LoginCheck
    @PutMapping("/post/{postId}")
    public CMResDto<?> updateById(@AuthenticationPrincipal PrincipalDetails details, @PathVariable long postId, @RequestBody PostSaveReqDto postSaveReqDto){

        return new CMResDto<>(1, "게시글 수정", postService.수정하기(postId, postSaveReqDto));
    }

}
