package com.api.repositories;

import com.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepository extends JpaRepository<Category, Integer> {
}
