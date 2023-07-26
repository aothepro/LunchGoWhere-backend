package com.gdsswechallenge.lunchlocation.session;

import com.gdsswechallenge.lunchlocation.user.User;
import com.gdsswechallenge.lunchlocation.user.UserRepository;
import com.gdsswechallenge.lunchlocation.vote.Vote;
import com.gdsswechallenge.lunchlocation.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public List<SessionResponse> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionResponse> sessionResponses = new ArrayList<>();
        for (Session session : sessions) {
            Optional<User> sessionCreator = userRepository.findById(session.getCreatorId());
            if (sessionCreator.isEmpty()) {
                continue;
            }
            sessionResponses.add(SessionResponse.builder()
                    .id(session.getId())
                    .isActive(canAcceptVotes(session))
                    .name(session.getName())
                    .creatorName(userRepository.findById(session.getCreatorId()).get().getName())
                    .lunchDate(session.getLunchDate())
                    .winningRestaurantName(session.getWinningRestaurantName())
                    .build());
        }
        Collections.sort(sessionResponses, Comparator.comparing(SessionResponse::getLunchDate, (dateTime1, dateTime2) -> {
                    LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).minusDays(1).plusHours(8);

                    boolean isDateTime1BeforeToday = dateTime1.isBefore(today);
                    boolean isDateTime2BeforeToday = dateTime2.isBefore(today);

                    // If both dates are before today, sort them normally
                    if (isDateTime1BeforeToday && isDateTime2BeforeToday) {
                        return dateTime1.compareTo(dateTime2);
                    }

                    // If only one date is before today, move it to the back
                    if (isDateTime1BeforeToday) {
                        return 1;
                    } else if (isDateTime2BeforeToday) {
                        return -1;
                    }

                    // If both dates are after today, sort them normally
                    return dateTime1.compareTo(dateTime2);
                }
        ));

        return sessionResponses;
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
