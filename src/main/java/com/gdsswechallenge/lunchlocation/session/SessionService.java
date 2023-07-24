package com.gdsswechallenge.lunchlocation.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;


    public List<Session> getAllActiveSessions() {
        return sessionRepository.findByIsActiveTrueAndLunchDateGreaterThanEqual(LocalDateTime.now().minusDays(1));
    }

    public Session createSession(String name, LocalDateTime lunchDate, String creatorId) {
        return sessionRepository.save(
                Session.builder()
                        .name(name)
                        .creatorId(creatorId)
                        .lunchDate(lunchDate)
                        .isActive(lunchDate.isAfter(LocalDateTime.now().minusDays(1)))
                        .build());

    }

    public void setSessionToInactive(Session session) {
        session.setActive(false);
        sessionRepository.save(session);
    }
}
