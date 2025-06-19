package com.sandyflat.BlogApplication.service;

import com.sandyflat.BlogApplication.dto.CommentDTO;

public interface CommentService {

    CommentDTO createComment (CommentDTO commentDTO, Long postId);

    void deleteComment(Long commentId);
}
