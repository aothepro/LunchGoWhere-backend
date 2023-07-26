package com.gdsswechallenge.lunchlocation.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VotesWithUsernameResponse {
    public String id;
    public String username;
    public String restaurantName;
}
