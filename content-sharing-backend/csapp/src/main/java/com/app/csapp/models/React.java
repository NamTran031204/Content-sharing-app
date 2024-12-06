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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = false)
    private Picture picture;

    @Column(name = "type_react", nullable = false)
    private ReactEnums typeReact;

    @Column(name = "content", nullable = true)
    private String content;
}
