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
    @OneToMany
    @Column(name = "picture_id")
    private long pictureId;

    @Id
    @Column(name = "tag_id")
    private long tagId;
}
