package com.gdsswechallenge.lunchlocation.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse {
    private String id;
    private String name;
    private LocalDateTime lunchDate;
    private String creatorName;
    private boolean isActive;
    private String winningRestaurantName;
    private boolean isCreator;
}
