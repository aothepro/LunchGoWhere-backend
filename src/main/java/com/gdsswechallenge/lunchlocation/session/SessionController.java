package com.gdsswechallenge.lunchlocation.session;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<Session>> getActiveSessions() {
        return ResponseEntity.ok(sessionService.getAllActiveSessions());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        if (session.getName() == null) {
            return ResponseEntity.badRequest()
                    .body("Missing Session name");
        }
        if (session.getLunchDate() == null) {
            return ResponseEntity.badRequest()
                    .body("Missing date for lunch session");
        }
        Session createdSession = sessionService.createSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);

    }
}
