package com.api.services;

import com.api.entities.Post;
import com.api.payloads.postDto;
import com.api.payloads.postResponse;

import java.util.List;

public interface postService {
    postDto createPost(postDto postDto, Integer userId, Integer categoryId);
    postDto getById(Integer postId);
    postResponse getAllPost(Integer pageNumber, Integer pageSize, String pageSort, String sortDirection);
    postDto updatePost(postDto postDto, Integer postId);
    void deletePost(Integer postId);
    List<postDto> getAllByCategory(Integer categoryId);
    List<postDto> getAllByUser(Integer userId);
    List<postDto> searchPost(String keyword);
}
