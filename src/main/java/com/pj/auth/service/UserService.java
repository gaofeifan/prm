package com.pj.auth.service;

import com.pj.auth.pojo.User;

public interface UserService {
    public User selectUserByEmail(String email);
}
