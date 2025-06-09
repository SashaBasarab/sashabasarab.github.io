package com.example.dyplomwork.controller;


import com.example.dyplomwork.dto.LoginRequest;
import com.example.dyplomwork.dto.LoginResponse;
import com.example.dyplomwork.service.GoogleAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final GoogleAuthService googleAuthService;

    public AuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = googleAuthService.verifyGoogleToken(request.getTokenId());

        if (token != null) {
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Невірний токен"));
        }
    }
}
