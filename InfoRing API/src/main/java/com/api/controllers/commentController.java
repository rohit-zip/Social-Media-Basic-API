package com.api.controllers;

import com.api.payloads.apiResponse;
import com.api.payloads.commentDto;
import com.api.services.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class commentController {

    @Autowired
    private commentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<commentDto> createComment(@RequestBody commentDto commentDto, @PathVariable Integer postId){
        commentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new apiResponse("Deleted Successfully", true), HttpStatus.OK);
    }
}
