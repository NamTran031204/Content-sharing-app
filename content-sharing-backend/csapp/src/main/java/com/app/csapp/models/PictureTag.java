package com.app.csapp.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "picture_tag")
public class PictureTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture pictureId;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tagId;
}
