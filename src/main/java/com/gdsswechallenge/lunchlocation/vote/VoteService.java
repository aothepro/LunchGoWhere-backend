package com.gdsswechallenge.lunchlocation.vote;

import com.gdsswechallenge.lunchlocation.session.Session;
import com.gdsswechallenge.lunchlocation.session.SessionService;
import com.gdsswechallenge.lunchlocation.user.User;
import com.gdsswechallenge.lunchlocation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final SessionService sessionService;
    private final UserService userService;

    public Optional<Vote> getVote(String creatorId, String sessionId) {
        Optional<Session> session = sessionService.getSessionById(sessionId);

        // Check if use can vote
        if (session.isEmpty() || !sessionService.canAcceptVotes(session.get())) {
            return Optional.empty();
        }

        // Check if user has voted
        return voteRepository.findBySessionIdAndVoterId(sessionId, creatorId);
    }

    public List<VotesWithUsernameResponse> getAllVoteBySessionId(String sessionId) {
        List<Vote> votes = voteRepository.findBySessionId(sessionId);
        List<VotesWithUsernameResponse> voteResponses = new ArrayList<>();
        for (Vote vote : votes) {
            Optional<User> user = userService.getUserById(vote.getVoterId());
            if (user.isEmpty()) {
                continue;
            }
            voteResponses.add(VotesWithUsernameResponse.builder()
                    .id(vote.getId())
                    .username(user.get().getUsername())
                    .restaurantName(vote.getRestaurantName())
                    .build());
        }

        return voteResponses;
    }

    public Optional<Vote> castVote(String creatorId, String sessionId, String restaurantName) {
        Optional<Vote> vote = getVote(creatorId, sessionId);
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
