package com.api.implementation;

import com.api.configurations.appConstants;
import com.api.entities.Role;
import com.api.entities.User;
import com.api.exceptions.resourceNotFoundException;
import com.api.payloads.userDto;
import com.api.repositories.roleRepository;
import com.api.repositories.userRepository;
import com.api.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImplementation implements userService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private roleRepository roleRepository;

    @Override
    public userDto registerNewUser(userDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepository.findById(appConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser, userDto.class);
    }

    @Override
    public userDto createUser(userDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public userDto updateUser(userDto userDto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new resourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public userDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new resourceNotFoundException("User", "Id", userId));
        userDto userDto = this.userToDto(user);
        return userDto;
    }

    @Override
    public List<userDto> getAllUser() {
        List<User> allUser = this.userRepository.findAll();
        List<userDto> userDtoList = allUser.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new resourceNotFoundException("User", "Id", userId));
        this.userRepository.delete(user);
    }

    public User dtoToUser(userDto userDto) {
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//        return user;
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public userDto userToDto(User user) {
//        userDto userDto = new userDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//        return userDto;
        userDto userDto = this.modelMapper.map(user, userDto.class);
        return userDto;
    }
}
