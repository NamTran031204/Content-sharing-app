package com.app.csapp.repositories;

import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
