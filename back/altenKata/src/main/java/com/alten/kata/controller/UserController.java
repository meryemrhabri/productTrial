package com.alten.kata.controller;

import com.alten.kata.service.UserService;
import com.alten.kata.entity.User;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    /**
     * Création d'un nouveau utilisateur
     * @param utilisateur
     * @return
     */

    @PostMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody User utilisateur) {

        logger.info("create new User...");
        try {
             userService.createUser(utilisateur);
            return new ResponseEntity<>("Utilisateur ajouté avec succés", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during creation User {}", e.getMessage());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
