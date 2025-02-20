package com.example.altenKata.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.altenKata.model.User;
import com.example.altenKata.service.AuthentificationService;
import com.example.altenKata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gérer Authentification
 */
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthentificationService authentificationService;


    public AuthenticationController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    /**
     * Génération Token pour un utilisateur précis
     * @param userLogin
     * @return Token généré
     */
    @PostMapping()
    public ResponseEntity<String> authenticateUser(@RequestBody User userLogin) {
        return authentificationService.getToken(userLogin);
    }
}
