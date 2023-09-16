package com.test.userapi.repository;

import com.test.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checking if user exist given thi email
     * @param email
     * @return true if user exists with this email, else false
     */
    boolean existsByEmail(String email);
}
