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
public class SessionCreationRequest {


    private String name;
    private LocalDateTime lunchDate;
}
