package com.plantshop.service;

import com.plantshop.model.User;
import com.plantshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userRepo.save(user);
    }

    @Override
    public User editProfile(User user){
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setMobileNumber(user.getMobileNumber());
        return userRepo.save(user);
    }
    @Override
    public boolean checkEmail(String email) {
        return userRepo.existsByEmail(email);
    }
    @Override
    public String deleteUser(String email){
        User user = userRepo.findByEmail(email);

        if(user!=null) {
            userRepo.delete(user);
            return "Профиль успешно удален";
        }
        return "Ошибка";
    }
}
