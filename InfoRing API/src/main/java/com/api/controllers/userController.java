package com.api.controllers;

import com.api.payloads.apiResponse;
import com.api.payloads.userDto;
import com.api.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class userController{

    @Autowired
    private userService userService;

    @PostMapping("/")
    public ResponseEntity<userDto> createUser(@Valid @RequestBody userDto userDto){
        userDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<userDto> updateUser(@Valid @RequestBody userDto userDto, @PathVariable Integer userId){
        userDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<apiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new apiResponse("Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<userDto> getUserById(@PathVariable Integer userId){
        userDto user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<userDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }
}
