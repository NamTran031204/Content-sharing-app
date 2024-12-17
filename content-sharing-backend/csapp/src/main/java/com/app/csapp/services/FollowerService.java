package com.app.csapp.services;

import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import com.app.csapp.repositories.FollowerRepository;
import com.app.csapp.repositories.UserRepository;
import com.app.csapp.responses.FollowerResponse;
import com.app.csapp.responses.UserResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Builder
@RequiredArgsConstructor
public class FollowerService implements IFollowerService{

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final IUserService userService;

    @Override
    public Follower createFollow(long followerId, long followingId) throws DataNotFoundException {
        User follower = userService.getUserById(followerId);
        User following = userService.getUserById(followingId);
        // Kiểm tra nếu lien ket đã tồn tại
        Optional<Long> followId = followerRepository.getIdByFollowerAndFollowing(follower, following);
        if (followId.isPresent()){
            throw new DataIntegrityViolationException("ban da follow nguoi nay roi");
        }
        Follower newfollower= Follower.builder()
                .follower(follower)
                .following(following)
                .build();
        return followerRepository.save(newfollower);
    }

    @Override
    public void unfollowUser(long followerId, long followingId) throws DataNotFoundException {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new DataNotFoundException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new DataNotFoundException("Following not found"));

        Long followId = followerRepository.getIdByFollowerAndFollowing(follower,following)
                .orElseThrow(() -> new DataNotFoundException("khong tim thay moi lien he"));

        followerRepository.deleteById(followId);
    }

    @Override
    public Page<UserResponse> findAllByFollowerId(long followerId, PageRequest pageRequest) throws DataNotFoundException {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new DataNotFoundException("Follower not found"));
        return followerRepository.findFollowingByFollower(follower, pageRequest).map(user -> UserResponse.fromUser(user));
    }

    @Override
    public Page<UserResponse> findAllByFollowingId(long followingId, PageRequest pageRequest) throws DataNotFoundException {
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new DataNotFoundException("Follower not found"));
        return followerRepository.findFollowerByFollowing(following, pageRequest).map(user -> UserResponse.fromUser(user));
    }

}
