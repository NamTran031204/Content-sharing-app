package com.app.csapp.services;

import com.app.csapp.dtos.FollowerDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import com.app.csapp.responses.FollowerResponse;
import com.app.csapp.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IFollowerService {
    public Follower createFollow(long followerUser, long followingUser) throws DataNotFoundException;
    public void unfollowUser(long followerId, long followingId) throws DataNotFoundException;
    public Page<UserResponse> findAllByFollowerId(long followerId, PageRequest pageRequest) throws DataNotFoundException;
    public Page<UserResponse> findAllByFollowingId(long followingId, PageRequest pageRequest) throws DataNotFoundException;
}
