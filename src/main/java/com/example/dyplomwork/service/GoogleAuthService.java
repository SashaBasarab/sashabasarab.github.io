package com.example.dyplomwork.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthService {
    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID";

    public String verifyGoogleToken(String tokenId) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenId);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                if (email.endsWith("@ukd.edu.ua")) {
                    return generateJwtToken(email);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateJwtToken(String email) {
        return "mocked-jwt-token-for-" + email;
    }
}