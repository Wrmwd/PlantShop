package com.plantshop.repository;

import com.plantshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByEmail(String email);
    public User findByEmail(String email);
    public User findByEmailAndMobileNumber(String email, String mobileNum);
}
