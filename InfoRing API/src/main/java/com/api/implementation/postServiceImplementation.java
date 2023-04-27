package com.api.implementation;

import com.api.entities.Category;
import com.api.entities.Post;
import com.api.entities.User;
import com.api.exceptions.resourceNotFoundException;
import com.api.payloads.postResponse;
import org.springframework.data.domain.Pageable;
import com.api.payloads.postDto;
import com.api.repositories.categoryRepository;
import com.api.repositories.postRepository;
import com.api.repositories.userRepository;
import com.api.services.postService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImplementation implements postService {

    @Autowired
    private postRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private categoryRepository categoryRepository;

    @Override
    public postDto createPost(postDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new resourceNotFoundException("User", "User Id", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new resourceNotFoundException("Category", "category Id", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);
        return this.modelMapper.map(newPost, postDto.class);
    }

    @Override
    public postDto getById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new resourceNotFoundException("Post", "Id", postId));
        return this.modelMapper.map(post, postDto.class);
    }

    @Override
    public postResponse getAllPost(Integer pageNumber, Integer pageSize, String pageSort, String sortDirection) {
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort=Sort.by(pageSort).ascending();
        }
        else{
            sort=Sort.by(pageSort).ascending();
        }
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pageContent = this.postRepository.findAll(page);
        List<Post> allPost = pageContent.getContent();
        List<postDto> postDtoList = allPost.stream().map((e) -> this.modelMapper.map(e, postDto.class)).collect(Collectors.toList());
        postResponse postResponse = new postResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageSize(pageContent.getSize());
        postResponse.setPageNumber(pageContent.getNumber());
        postResponse.setTotalPages(pageContent.getTotalPages());
        postResponse.setTotalElements(pageContent.getTotalElements() + 1);
        postResponse.setLastPage(pageContent.isLast());
        if(postResponse.getContent().isEmpty()){
            throw new resourceNotFoundException("Page", "Page Number", postResponse.getPageNumber());
        }
        return postResponse;
    }

    @Override
    public postDto updatePost(postDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new resourceNotFoundException("Post", "Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImage(postDto.getImage());
        post.setUpdatedDate(new Date());
        Post updatedPost = this.postRepository.save(post);
        postDto updatedPostDto = this.modelMapper.map(updatedPost, postDto.class);
        return updatedPostDto;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new resourceNotFoundException("Post", "Id", postId));
        this.postRepository.delete(post);
    }

    @Override
    public List<postDto> getAllByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new resourceNotFoundException("Category", "Id", categoryId));
        List<Post> allPosts = this.postRepository.findByCategory(category);
        List<postDto> allPostDto = allPosts.stream().map(e -> this.modelMapper.map(e, postDto.class)).collect(Collectors.toList());
        return allPostDto;
    }

    @Override
    public List<postDto> getAllByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new resourceNotFoundException("User", "Id", userId));
        List<Post> allPosts = this.postRepository.findByUser(user);
        List<postDto> allPostDto = allPosts.stream().map(e -> this.modelMapper.map(e, postDto.class)).collect(Collectors.toList());
        return allPostDto;
    }

    @Override
    public List<postDto> searchPost(String keyword) {
        List<Post> post = this.postRepository.findPostByTitleContainingIgnoreCase(keyword);
        if(post.isEmpty()){
            throw new resourceNotFoundException("Post", "Keyword");
        }
        else {
            List<postDto> postDtoList = post.stream().map(e -> this.modelMapper.map(e, postDto.class)).collect(Collectors.toList());
            return postDtoList;
        }
    }}
