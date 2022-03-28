package com.kang.kanglog.service;


import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private static final Logger log = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<Post> 관련게시글찾기(String name){

        List<Tag> tagEntitys = tagRepository.mFindByName(name);

        List<Post> tagPosts = new ArrayList<>();


       tagEntitys.forEach((tag)->{
           tagPosts.add(tag.getPost());
       });

       log.info(String.valueOf(tagPosts.size()));

        return tagPosts;
    }


}
