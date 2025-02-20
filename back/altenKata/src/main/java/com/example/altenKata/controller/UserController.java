package com.example.altenKata.controller;

import com.example.altenKata.model.Product;
import com.example.altenKata.model.User;
import com.example.altenKata.service.ProduitService;
import com.example.altenKata.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
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

    @PostMapping
    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User utilisateur) {

        logger.info("create new User...");
        try {
             userService.createUser(utilisateur);
            return new ResponseEntity<>("Utilisateur ajouté avec succés", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during creation User", e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
