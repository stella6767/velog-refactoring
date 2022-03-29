package com.kang.kanglog.repository.tag;

import com.kang.kanglog.domain.Tag;

import java.util.List;

public interface TagCustomRepository {


    List<Tag> mFindByName(String name);

    List<Tag> mFindUserTags(Long userId);
}
