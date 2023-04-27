package com.api.controllers;

import com.api.configurations.appConstants;
import com.api.payloads.apiResponse;
import com.api.payloads.postDto;
import com.api.payloads.postResponse;
import com.api.services.fileService;
import com.api.services.postService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class postController {

    @Autowired
    private postService postService;

    @Autowired
    private fileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<postDto> createPost(@RequestBody postDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        postDto post = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<postDto>> getByCategory(@PathVariable Integer categoryId){
        List<postDto> allByCategory = this.postService.getAllByCategory(categoryId);
        return ResponseEntity.ok(allByCategory);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<postDto>> getByUser(@PathVariable Integer userId){
        List<postDto> allByUser = this.postService.getAllByUser(userId);
        return ResponseEntity.ok(allByUser);
    }

    @GetMapping("/post")
    public ResponseEntity<postResponse> getAllPost(@RequestParam(value = "pageNumber", defaultValue = appConstants.PAGE_NUMBER , required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = appConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                   @RequestParam(value = "pageSort", defaultValue = appConstants.PAGE_SORT, required = false) String pageSort,
                                                   @RequestParam(value = "sortDirection", defaultValue = appConstants.SORT_DIRECTION, required = false) String sortDirection){
        postResponse allPost = this.postService.getAllPost(pageNumber, pageSize, pageSort, sortDirection);
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<postDto> getPostById(@PathVariable Integer postId){
        postDto byId = this.postService.getById(postId);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new apiResponse("Deleted Successfully", true), HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<postDto> updatePost(@RequestBody postDto postDto, @PathVariable Integer postId){
        postDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<List<postDto>> searchPost(@PathVariable String keyword){
        List<postDto> postDto = this.postService.searchPost(keyword);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<postDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        postDto postDto = this.postService.getById(postId);
        String name = this.fileService.uploadImage(path, image);
        postDto.setImage(name);
        postDto postDto1 = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(postDto1);
    }

    @GetMapping("/post/image/{image}")
    public ResponseEntity<InputStream> downloadImage(@PathVariable String image, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        return ResponseEntity.ok(resource);
    }
}
