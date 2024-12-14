package com.app.csapp.repositories;

import com.app.csapp.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.csapp.models.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber); // tra ve mot user hoac mot null
    Optional<User> findByEmail(String email);
    // dung de kiem tra xem so dien thoai/email da ton tai trong csdl chua bang .isemty hoac count
    //SELECT * FROM users WHERE phoneNumber=?
    //Page<User> findUserById(long id) throws DataNotFoundException;
}
