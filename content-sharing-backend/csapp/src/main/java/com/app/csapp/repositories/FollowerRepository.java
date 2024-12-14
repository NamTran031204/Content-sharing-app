package com.app.csapp.repositories;

import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
//    @Query("SELECT f FROM Follower f WHERE f.follower = :follower AND f.following = :following")
//    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Followers f WHERE f.follower = :follower AND f.following = :following")
//    boolean existsByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

    @Query("SELECT f.id FROM Follower f WHERE f.follower = :follower AND f.following = :following")
    Optional<Long> getIdByFollowerAndFollowing(User follower, User following);

    @Query("SELECT f.following FROM Follower f WHERE f.follower = :follower")
    Page<User> findFollowingByFollower(@Param("follower") User follower, PageRequest pageRequest);

    @Query("SELECT f.follower FROM Follower f WHERE f.following = :following")
    Page<User> findFollowerByFollowing(@Param("following") User following, PageRequest pageRequest);
}
