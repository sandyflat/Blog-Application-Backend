package com.sandyflat.BlogApplication.controller;

import com.sandyflat.BlogApplication.config.AppConstant;
import com.sandyflat.BlogApplication.payload.ApiResponse;
import com.sandyflat.BlogApplication.payload.PostDTO;
import com.sandyflat.BlogApplication.payload.PostResponse;
import com.sandyflat.BlogApplication.serviceimpl.PostServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable Long userId,
                                              @PathVariable Long categoryId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(postDTO, userId, categoryId));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long postId){
        return ResponseEntity.ok(postService.updatePost(postDTO, postId));

    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(postService.getPostByCategory(categoryId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(postService.getAllPostByUser(userId));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted", true);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortDirection
    ){
        return ResponseEntity.ok(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable ("keywords") String keywords){
        return ResponseEntity.ok(postService.searchPosts(keywords));
    }
}
