package com.gdsswechallenge.lunchlocation.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CastVoteRequest {
    private String sessionId;
    private String restaurantName;
}
