package com.app.csapp.repositories;

import com.app.csapp.models.Board;
import com.app.csapp.models.PictureTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND b.boardName = :boardName")
    List<Board> findAllBoardOfUser(@Param("userId") Long userId,@Param("boardName") String boardName);

    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.boardName = :newBoardName WHERE b.boardName = :oldBoardName AND b.user.id = :userId")
    int updateBoardName(@Param("userId") Long userId, @Param("oldBoardName") String oldBoardName, @Param("newBoardName") String newBoardName);

    @Query("SELECT b FROM Board b WHERE b.boardName = :boardName")
    Optional<Board> findBoardByName(@Param("boardName") String boardName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Board b WHERE b.boardName = :boardName AND b.user.id = :userId")
    void deleteBoardOfUser(@Param("userId") Long userId, @Param("boardName") String boardName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Board b WHERE b.picture.id = :pictureId AND b.boardName = :boardName AND b.user.id = :userId")
    void deletePictureOfBoard(@Param("userId") Long userId, @Param("pictureId") Long pictureId,@Param("boardName") String boardName);
}
