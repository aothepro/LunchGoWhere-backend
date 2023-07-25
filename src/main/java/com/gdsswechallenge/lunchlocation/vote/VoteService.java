package com.gdsswechallenge.lunchlocation.vote;

import com.gdsswechallenge.lunchlocation.session.Session;
import com.gdsswechallenge.lunchlocation.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final SessionService sessionService;


    public Optional<Vote> castVote(String creatorId, String sessionId, String restaurantName) {
        Optional<Session> session = sessionService.getSessionById(sessionId);

        // Check if use can vote
        if (session.isEmpty() || !sessionService.canAcceptVotes(session.get())) {
            return Optional.empty();
        }

        // Check if user has voted
        Optional<Vote> vote = voteRepository.findBySessionIdAndVoterId(sessionId, creatorId);


        if (vote.isEmpty()) {
            // First time user voting for session
            return Optional.of(voteRepository.save(Vote.builder()
                    .sessionId(sessionId)
                    .voterId(creatorId)
                    .restaurantName(restaurantName)
                    .build()));

        } else {
            // Update the user's vote
            Vote updatedVote = vote.get();
            updatedVote.setRestaurantName(restaurantName);
            return Optional.of(voteRepository.save(updatedVote));
        }

    }
}
