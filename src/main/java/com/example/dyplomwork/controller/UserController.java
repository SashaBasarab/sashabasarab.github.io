package com.example.dyplomwork.controller;


import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public Map<String, Object> getUserInfo(OAuth2AuthenticationToken authentication) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", authentication.getPrincipal().getAttributes().get("name"));
        userDetails.put("email", authentication.getPrincipal().getAttributes().get("email"));
        return userDetails;
    }
}
