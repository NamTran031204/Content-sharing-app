package com.app.csapp.repositories;

import com.app.csapp.enums.ReactEnums;
import com.app.csapp.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {
    @Query("SELECT r.id FROM React r WHERE r.user= :user and r.picture= :picture and r.typeReact = :typeReact ")
    Long getIdByUserAndPictureAndTypeReact(@Param("user") User user,@Param("picture") Picture picture,@Param("typeReact") ReactEnums typeReact);
}
