package com.app.csapp.repositories;

import com.app.csapp.enums.ReactEnums;
import com.app.csapp.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {
    @Query("SELECT r.id FROM React r WHERE r.user= :user and r.picture= :picture and r.typeReact = :typeReact ")
    Long getIdByUserAndPictureAndTypeReact(@Param("user") User user,@Param("picture") Picture picture,@Param("typeReact") ReactEnums typeReact);

    @Query("SELECT r.user FROM React r WHERE r.picture= :picture and r.typeReact = :typeReact ")
    Page<User> getUserByPictureAndTypeReact(
            @Param("picture") Picture picture,
            @Param("typeReact") ReactEnums typeReact,
            PageRequest pageRequest);

    @Query("SELECT r.id FROM React r WHERE r.picture= :picture and r.typeReact = :typeReact ")
    List<Long> getReactIdByPictureAndTypeReact(
            Picture picture,
            ReactEnums typeReact);

    @Query("SELECT r.user FROM React r WHERE r.id = :id")
    User getUserById(@Param("id") long id);
    @Query("SELECT r.content FROM React r WHERE r.id = :id")
    String getContentById(long id);

}
