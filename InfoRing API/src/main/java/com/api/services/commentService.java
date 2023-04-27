package com.api.services;

import com.api.payloads.commentDto;

public interface commentService {
    commentDto createComment(commentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
}
