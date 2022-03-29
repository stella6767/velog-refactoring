package com.kang.kanglog.repository.user;

import com.kang.kanglog.domain.User;

public interface UserCustomRepository {


    User mfindByUsername(String username);
}
