package com.app.csapp.responses;

import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowerResponse {
    @JsonProperty("follower_id")
    private long followerId;

    @JsonProperty("following_id")
    private long followingId;

    public static FollowerResponse fromFollower(Follower follower){
        FollowerResponse followerResponse = FollowerResponse.builder()
                .followingId(follower.getFollowing().getId())
                .build();
        return followerResponse;
    }

}
