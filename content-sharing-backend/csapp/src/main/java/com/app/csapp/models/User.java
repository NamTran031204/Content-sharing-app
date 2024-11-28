package com.app.csapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 30)
    private String userName;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "user_password", length = 30, nullable = false)
    private String userPassword;

    @Column(name = "profile_picture", length = 200)
    private String profilePicture;

//    @OneToMany(mappedBy = "follower_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Follower> followerUsers;
//
//    @OneToMany(mappedBy = "following_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Follower> followingUsers;
}
