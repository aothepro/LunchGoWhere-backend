package com.gdsswechallenge.lunchlocation.session;

import com.gdsswechallenge.lunchlocation.config.JwtService;
import com.gdsswechallenge.lunchlocation.vote.VoteService;
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
    private final VoteService voteService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        List<SessionResponse> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSessions(@RequestHeader(name = "Authorization") String token, @PathVariable String sessionId) {
        String requesterId = jwtService.extractUserId(token);
        SessionResponse sessionresponse = sessionService.getSessionByIdWithCreatorName(sessionId,requesterId);
        return ResponseEntity.ok(sessionresponse);
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
    public Session endSession(@RequestHeader(name = "Authorization") String token, @RequestBody SessionEndRequest sessionEndRequest) {
        String requesterId = jwtService.extractUserId(token);
        Session session = sessionService.endSessionWithId(sessionEndRequest.getSessionId(), requesterId).orElseThrow();

        return session;
    }
}
