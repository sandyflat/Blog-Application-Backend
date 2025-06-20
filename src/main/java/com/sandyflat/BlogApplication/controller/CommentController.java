package com.sandyflat.BlogApplication.controller;

import com.sandyflat.BlogApplication.dto.ApiResponse;
import com.sandyflat.BlogApplication.dto.CommentDTO;
import com.sandyflat.BlogApplication.serviceimpl.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/post/{postId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Long postId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(commentDTO, postId));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("Comment deleted successfully", true));
    }
}
