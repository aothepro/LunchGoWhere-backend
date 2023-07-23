package com.gdsswechallenge.lunchlocation.session;

import com.gdsswechallenge.lunchlocation.config.JwtService;
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
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Session>> getActiveSessions() {
        return ResponseEntity.ok(sessionService.getAllActiveSessions());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestHeader(name = "Authorization") String token, @RequestBody SessionCreationRequest sessionCreationRequest) {
        if (sessionCreationRequest.getName() == null) {
            return ResponseEntity.badRequest().body("Missing Session name");
        }
        if (sessionCreationRequest.getLunchDate() == null) {
            return ResponseEntity.badRequest().body("Missing date for lunch session");
        }

        Session createdSession = sessionService.createSession(Session.builder().name(sessionCreationRequest.getName()).creatorId(jwtService.extractUserId(token)).lunchDate(sessionCreationRequest.getLunchDate()).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);

    }
}
