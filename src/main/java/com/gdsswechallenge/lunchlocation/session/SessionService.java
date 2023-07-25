package com.gdsswechallenge.lunchlocation.session;

import com.gdsswechallenge.lunchlocation.vote.Vote;
import com.gdsswechallenge.lunchlocation.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;

    public List<Session> getAllActiveSessions() {
        return sessionRepository.findByIsActiveTrueAndLunchDateGreaterThanEqual(LocalDateTime.now().minusDays(1));
    }

    public Session createSession(String name, LocalDateTime lunchDate, String creatorId) {
        return sessionRepository.save(Session.builder().name(name).creatorId(creatorId).lunchDate(lunchDate).isActive(lunchDate.isAfter(LocalDateTime.now().minusDays(1))).build());

    }

    public void setSessionToInactive(Session session) {
        session.setActive(false);
        sessionRepository.save(session);
    }

    public Optional<Session> getSessionById(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public boolean canAcceptVotes(Session session) {
        return session.isActive() && session.getLunchDate().isAfter(LocalDateTime.now().minusDays(1));
    }

    public Optional<Session> endSessionWithId(String sessionId, String requesterId) {
        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isEmpty() || session.get().getCreatorId() != requesterId) {
            return Optional.empty();
        }

        List<Vote> votes = voteRepository.findBySessionId(sessionId);
        Session updatedSession = session.get();
        updatedSession.setActive(false);

        int numberOfVotes = votes.size();
        if (numberOfVotes > 0) {
            int randomIndex = new Random().nextInt(numberOfVotes);
            updatedSession.setWinningRestaurantName(votes.get(randomIndex).getRestaurantName());
        }
        return Optional.of(sessionRepository.save(updatedSession));

    }
}
