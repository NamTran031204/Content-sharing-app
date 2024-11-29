package com.app.csapp.repositories;

import com.app.csapp.models.Picture;
import com.app.csapp.models.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {

}
