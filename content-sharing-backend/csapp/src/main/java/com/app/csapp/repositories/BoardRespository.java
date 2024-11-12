package com.app.csapp.repositories;

import com.app.csapp.models.Board;
import com.app.csapp.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRespository extends JpaRepository<Board, Long> {
}
