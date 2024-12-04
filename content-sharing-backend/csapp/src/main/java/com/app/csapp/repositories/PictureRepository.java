package com.app.csapp.repositories;


import com.app.csapp.models.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    //boolean existByImageUrl(String imageUrl);

    Page<Picture> findAll(Pageable pageable);
}
