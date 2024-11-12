package com.app.csapp.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "boards")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "board_name", nullable = false, length = 200)
    private String boardName;

    @Column(name = "board_description", nullable = true, length = 100)
    private String boardDescription;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture pictureId;
}
