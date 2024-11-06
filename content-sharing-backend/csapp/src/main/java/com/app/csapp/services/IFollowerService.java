package com.app.csapp.services;

import com.app.csapp.dtos.FollowerDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Follower;

public interface IFollowerService {
    Follower createFollow(long followerId, long followingId, FollowerDTO followerDTO) throws DataNotFoundException;
    Follower deleteFollow(long followerId, long followingId, FollowerDTO followerDTO) throws DataNotFoundException;
}
