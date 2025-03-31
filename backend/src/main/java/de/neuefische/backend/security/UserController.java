package de.neuefische.backend.security;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            throw new IllegalStateException("User not authenticated");
        }
        return ResponseEntity.ok(user.getAttributes());
    }


}

