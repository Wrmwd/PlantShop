package com.plantshop.service;

import com.plantshop.model.User;

public interface UserService {
    public User createUser(User user);
    public User editProfile(User user);
    public boolean checkEmail(String email);
}
