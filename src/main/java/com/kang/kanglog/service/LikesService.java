package com.kang.kanglog.service;

import com.kang.kanglog.domain.Like;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.User;
import com.kang.kanglog.repository.like.LikeRepository;
import com.kang.kanglog.web.dto.post.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikeRepository likesRepository;

    @Transactional
    public Like 좋아요(Long postId, Long principalId) {

        //Save 할 때 연관관계가 있으면 오브젝트로 만들어서 id값만 넣어주면 된다.
        //불안하긴 한데.. 도중에 DB 에서 누가 삭제하면 유령 게시글이나 유저가 insert 되는 거 아닌가?
        Post post = new Post(postId);
        User user = new User(principalId);
        Like like = Like.createLike(post, user);

        return likesRepository.save(like);
    }


    @Transactional
    public long 싫어요(Long postId, Long principalId) {

       return likesRepository.mUnLike(postId, principalId);
    }





}
