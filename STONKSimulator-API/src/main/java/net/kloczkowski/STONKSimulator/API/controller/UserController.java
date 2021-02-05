package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.dto.RegisterDTO;
import net.kloczkowski.STONKSimulator.API.dto.UserDTO;
import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.function.Function;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO register) {
        try {
            var payload = transformToDTO().apply(userService.register(register.getUsername(), register.getPassword()));

            return new ResponseEntity<>(payload, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        try {
            var payload = transformToDTO().apply(userService.getByUsername(principal.getName()));

            return new ResponseEntity<>(payload, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Function<User, UserDTO> transformToDTO() {
        return UserDTO::new;
    }
}
