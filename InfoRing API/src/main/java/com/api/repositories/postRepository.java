package com.api.repositories;

import com.api.entities.Category;
import com.api.entities.Post;
import com.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface postRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findPostByTitleContainingIgnoreCase(String keyword);
}
