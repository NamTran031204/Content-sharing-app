package com.app.csapp.repositories;


import com.app.csapp.models.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    //boolean existByImageUrl(String imageUrl);
    Optional<Long> getPictureById(long id);
    Page<Picture> findAll(Pageable pageable);
}
