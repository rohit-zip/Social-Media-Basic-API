package com.api.repositories;

import com.api.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepository extends JpaRepository<Comment, Integer> {
}
