package com.app.csapp.models;


import com.app.csapp.enums.ReactEnums;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reacts")
public class React extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = false)
    private Picture pictureId;

    @Column(name = "type_react")
    private ReactEnums typeReact;

    @Column(name = "content", nullable = true)
    private String content;
}
