package com.pj.auth.service;

import com.pj.auth.pojo.User;

public interface AuthUserService {
    public User selectUserByEmail(String email);


}
