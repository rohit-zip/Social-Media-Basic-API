package com.api.repositories;

import com.api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role, Integer> {
}
