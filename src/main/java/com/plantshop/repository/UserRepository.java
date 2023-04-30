package com.plantshop.repository;

import com.plantshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByEmail(String email);
    public User findByEmail(String email);
    public User findByEmailAndMobileNumber(String email, String mobileNumber);
}
