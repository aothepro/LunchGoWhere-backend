package com.gdsswechallenge.lunchlocation.session;

import com.gdsswechallenge.lunchlocation.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        List<SessionResponse> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestHeader(name = "Authorization") String token, @RequestBody SessionCreationRequest sessionCreationRequest) {

        String creatorId = jwtService.extractUserId(token);
        if (sessionCreationRequest.getName() == null) {
            return ResponseEntity.badRequest().body("Missing Session name");
        }
        if (sessionCreationRequest.getLunchDate() == null) {
            return ResponseEntity.badRequest().body("Missing date for lunch session");
        }

        Session createdSession = sessionService.createSession(sessionCreationRequest.getName(), sessionCreationRequest.getLunchDate(), creatorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @PostMapping("/end")
    public ResponseEntity<?> endSession(@RequestHeader(name = "Authorization") String token, @RequestBody SessionEndRequest sessionEndRequest) {
        String requesterId = jwtService.extractUserId(token);
        Optional<Session> session = sessionService.endSessionWithId(sessionEndRequest.getSessionId(), requesterId);

        if (session.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(session.get());
    }
}
