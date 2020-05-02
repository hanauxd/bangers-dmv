package lk.apiit.eirlss.dmv.controllers;

import lk.apiit.eirlss.dmv.dto.AuthenticationResponse;
import lk.apiit.eirlss.dmv.dto.UserDTO;
import lk.apiit.eirlss.dmv.security.JwtUtil;
import lk.apiit.eirlss.dmv.services.UserDetailsServiceImpl;
import lk.apiit.eirlss.dmv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class AuthenticationController {
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${app.token.expiry-time}")
    private String expiryTime;

    @Autowired
    public AuthenticationController(
            UserService userService,
            UserDetailsServiceImpl userDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.registerUser(dto.getUser()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), expiryTime, jwt));
    }
}
