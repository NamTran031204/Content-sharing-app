package com.app.csapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.csapp.models.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber); // tra ve mot user hoac mot null
    Optional<User> findByEmail(String email);
    // dung de kiem tra xem so dien thoai/email da ton tai trong csdl chua bang .isemty hoac count
    //SELECT * FROM users WHERE phoneNumber=?
}
