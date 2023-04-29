package com.plantshop.service;

import com.plantshop.model.UserDtls;

public interface UserService {
    public UserDtls createUser(UserDtls user);
    public boolean checkEmail(String email);
}
