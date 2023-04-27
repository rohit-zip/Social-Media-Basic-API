package com.api;

import com.api.configurations.appConstants;
import com.api.entities.Role;
import com.api.repositories.roleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class InfoRingApiApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private roleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(InfoRingApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            Role role1 = new Role();
            role1.setId(appConstants.ADMIN_USER);
            role1.setRole("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(appConstants.NORMAL_USER);
            role2.setRole("ROLE_NORMAL");

            List<Role> roles = List.of(role1, role2);
            List<Role> savedRoles = this.roleRepository.saveAll(roles);
        }
        catch (Exception e){

        }
    }
}
