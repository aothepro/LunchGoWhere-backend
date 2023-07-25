package com.gdsswechallenge.lunchlocation.vote;

import com.gdsswechallenge.lunchlocation.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final JwtService jwtService;


    @PostMapping
    public ResponseEntity<?> castVote(@RequestHeader(name = "Authorization") String token, @RequestBody CastVoteRequest castVoteRequest) {
        String creatorId = jwtService.extractUserId(token);
        System.out.println(castVoteRequest.toString() + " : " + creatorId);

       Optional<Vote> vote = voteService.castVote(creatorId, castVoteRequest.getSessionId(), castVoteRequest.getRestaurantName());

       if(vote.isEmpty()) {
           return ResponseEntity.badRequest().build();
       }
        return ResponseEntity.ok().body(vote);
//
    }
}
