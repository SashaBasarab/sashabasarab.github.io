package com.example.dyplomwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class StudentProfileController {

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("Користувач не аутентифікований");
        }

        Map<String, Object> attributes = user.getAttributes();

        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        return ResponseEntity.ok(Map.of(
                "email", email,
                "firstName", firstName,
                "lastName", lastName
        ));
    }
}
