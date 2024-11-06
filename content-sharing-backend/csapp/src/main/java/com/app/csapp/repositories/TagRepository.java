package com.app.csapp.repositories;

import com.app.csapp.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    //List<Tag> findById(Long tagId);
}
