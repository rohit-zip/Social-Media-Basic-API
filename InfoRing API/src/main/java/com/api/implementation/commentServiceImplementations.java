package com.api.implementation;

import com.api.entities.Comment;
import com.api.entities.Post;
import com.api.exceptions.resourceNotFoundException;
import com.api.payloads.commentDto;
import com.api.repositories.commentRepository;
import com.api.repositories.postRepository;
import com.api.services.commentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class commentServiceImplementations implements commentService {

    @Autowired
    private commentRepository commentRepository;

    @Autowired
    private postRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public commentDto createComment(commentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new resourceNotFoundException("Post", "Id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setCommentDate(LocalDate.now());
        comment.setPost(post);
        Comment createdComment = this.commentRepository.save(comment);
        return this.modelMapper.map(createdComment, commentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new resourceNotFoundException("Comment", "Id", commentId));
        this.commentRepository.delete(comment);
    }
}
