package com.gdsswechallenge.lunchlocation.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;


    public List<Session> getAllActiveSessions() {
        return sessionRepository.findByIsActiveTrue();

    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public void setSessionToInactive(Session session) {
        session.setActive(false);
        sessionRepository.save(session);
    }
}
