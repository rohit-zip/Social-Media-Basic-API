package com.api.services;

import com.api.payloads.userDto;

import java.util.List;


public interface userService{

    userDto registerNewUser(userDto userDto);
    userDto createUser(userDto user);
    userDto updateUser(userDto user, Integer userId);
    userDto getUserById(Integer userId);
    List<userDto> getAllUser();
    void deleteUser(Integer userId);
}
