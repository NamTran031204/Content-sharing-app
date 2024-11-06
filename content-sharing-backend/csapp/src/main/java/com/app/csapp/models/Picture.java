package com.app.csapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pictures")
public class Picture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

//    @Column(name = "board_id", nullable = false)
//    private long boardId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "image_url", nullable = false, length = 200)
    private String imageUrl;

    @Column(name = "title", nullable = true, length = 100)
    private String title;

    @Column(name = "image_description", nullable = true, length = 300)
    private String imageDescription;

}
