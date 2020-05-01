package lk.apiit.eirlss.dmv.controllers;

import lk.apiit.eirlss.dmv.exceptions.CustomException;
import lk.apiit.eirlss.dmv.models.User;
import lk.apiit.eirlss.dmv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User username = userService.getUserByUsername(user.getUsername());
        if (username == null) {
            throw new CustomException("Username not found", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(username);
    }
}
