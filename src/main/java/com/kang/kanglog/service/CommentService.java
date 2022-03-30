package com.kang.kanglog.service;


import com.kang.kanglog.domain.Comment;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.User;

import com.kang.kanglog.repository.comment.CommentRepository;
import com.kang.kanglog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kang.kanglog.web.dto.comment.CommentReqDto.*;

@RequiredArgsConstructor
@Service
public class CommentService {


    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public int 삭제하기(Long id, Long userId) {
        Comment commentEntity = commentRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("id를 찾을 수 없습니다.");
        });

        if(commentEntity.getUser().getId() == userId) {
            commentRepository.deleteById(id);
            return 1;
        }else {
            return -1;
        }
    }



    @Transactional
    public Comment 댓글쓰기(User principal, CommentAddDto commentAddDto){

        Post post =
                postRepository.findById(commentAddDto.getPostId())
                        .orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없습니다."));

        //Save 할 때 연관관계가 있으면 오브젝트로 만들어서 id값만 넣어주면 된다. 하지만 나는 오브젝트를
        Comment comment = modelMapper.map(commentAddDto, Comment.class);
        comment.add(post, principal);

        return commentRepository.save(comment);
    }


}
