package com.app.csapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "followers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "follower_id")
    private long followerId;

    @Column(name = "following_id")
    private long followingId;

    @Column(name = "followed_at")
    private LocalDateTime followedAt;
}
