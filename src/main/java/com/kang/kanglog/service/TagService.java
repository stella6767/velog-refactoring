package com.kang.kanglog.service;


import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.repository.tag.TagRepository;
import com.kang.kanglog.web.dto.post.PostResDto;
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
    public List<PostResDto.PostDto> 관련게시글찾기(String name) {

        List<Tag> tagEntitys = tagRepository.mFindByName(name);
        List<PostResDto.PostDto> tagPosts = new ArrayList<>();
        tagEntitys.forEach((tag) -> {
            PostResDto.PostDto dto = new PostResDto.PostDto(0L, tag.getPost());
            tagPosts.add(dto);
        });
        log.info(String.valueOf(tagPosts.size()));
        return tagPosts;
    }


}
