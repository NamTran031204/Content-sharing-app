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

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
//    @Column(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
//    @Column(name = "following_id")
    private User following;

    @Column(name = "followed_at")
    private LocalDateTime followedAt;
    @PrePersist
    protected void onCreate() {
        followedAt = LocalDateTime.now();
    }
}
