package com.kang.kanglog.repository.tag;

import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {



}
