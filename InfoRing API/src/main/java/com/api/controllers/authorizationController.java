package com.api.controllers;

import com.api.exceptions.apiException;
import com.api.payloads.JWTAuthenticationRequest;
import com.api.payloads.JWTAuthenticationResponse;
import com.api.payloads.userDto;
import com.api.security.JWTTokenHelper;
import com.api.security.userDetailService;
import com.api.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class authorizationController {

    @Autowired
    private userDetailService userDetailService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userService userService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationResponse> createToken(@RequestBody JWTAuthenticationRequest jwtAuthenticationRequest) throws Exception {
        this.authenticate(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword());
        UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
        String generatedToken = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(generatedToken);
        return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);
    }

    public void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
        this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            throw new apiException("Invalid Username or Password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<userDto> registerUser(@RequestBody userDto userDto){
        userDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
