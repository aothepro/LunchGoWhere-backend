package com.gdsswechallenge.lunchlocation.session;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "session")
public class Session {
    @Id
    private String id;
    private String name;
    private LocalDateTime lunchDate;
    private String creatorId;
    @Indexed
    private boolean isActive = true;
    private String winningRestaurantName;

}
