package com.app.csapp.services;

import com.app.csapp.dtos.FollowerDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Follower;

public class FollowerService implements IFollowerService{

    @Override
    public Follower createFollow(long followerId, long followingId, FollowerDTO followerDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public Follower deleteFollow(long followerId, long followingId, FollowerDTO followerDTO) throws DataNotFoundException {
        return null;
    }
}
