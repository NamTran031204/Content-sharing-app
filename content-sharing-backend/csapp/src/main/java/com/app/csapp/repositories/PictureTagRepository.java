package com.app.csapp.repositories;

import com.app.csapp.models.PictureTag;
import com.app.csapp.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureTagRepository extends JpaRepository<PictureTag, Long> {
}
