package com.example.greenstoreproject.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleTokenVerifier {
    private final GoogleIdTokenVerifier verifier;
    public GoogleTokenVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("73919715033-8vil57eo7p9sd56ljlfl9o4gbdpvoebo.apps.googleusercontent.com"))
                .build();
    }

    public GoogleIdToken verify(String idTokenString) throws Exception {
        return verifier.verify(idTokenString);
    }
}
