package com.gdsswechallenge.lunchlocation.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionEndRequest {
    public String sessionId;
}
