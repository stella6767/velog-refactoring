package com.kang.kanglog.repository.post;

import com.kang.kanglog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {


    Page<Post> mLikeList(Pageable pageable, Long principalId);

    Page<Post> mFindByKeyword(String keyword, Pageable pageable);

    Page<Post> mTrending(Pageable pageable);

    Page<Post> mfindAllByPage(Pageable pageable);
}
