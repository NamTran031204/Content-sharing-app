package com.app.csapp.repositories;

import com.app.csapp.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
//    boolean existByImageUrl(String imageUrl);
//
//    Page<Picture> findAll(Pageable pageable);
}
