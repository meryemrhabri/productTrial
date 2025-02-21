package com.alten.kata.controller;

import com.alten.kata.entity.User;
import com.alten.kata.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Génération Token pour un utilisateur précis
     * @param userLogin
     * @return Token généré
     */
    @PostMapping()
    public ResponseEntity<String> authenticateUser(@RequestBody User userLogin) {
        return authenticationService.getToken(userLogin);
    }
}
