package com.app.csapp.repositories;

import com.app.csapp.models.PictureTag;
import com.app.csapp.models.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PictureTagRepository extends JpaRepository<PictureTag, Long> {
    @Query("SELECT pt FROM PictureTag pt WHERE pt.picture.id = :pictureId")
    List<PictureTag> findAllTagOfPicture(@Param("pictureId") Long pictureId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PictureTag pt WHERE pt.picture.id = :pictureId AND pt.tag.id = :tagId")
    void deleteByPictureIdAndTagName(@Param("pictureId") Long pictureId, @Param("tagId") Long tagId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PictureTag pt WHERE pt.picture.id = :pictureId")
    void deleteAllByPictureId(@Param("pictureId") Long pictureId);

    @Query(value = """
        SELECT t.tag_name AS tagName, COUNT(pt.tag_id) AS pictureCount
        FROM picture_tag pt
        JOIN tags t ON pt.tag_id = t.id
        GROUP BY pt.tag_id, t.tag_name
        ORDER BY pictureCount DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5Tags();
}
