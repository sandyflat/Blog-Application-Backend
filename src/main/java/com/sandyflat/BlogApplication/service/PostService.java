package com.sandyflat.BlogApplication.service;

import com.sandyflat.BlogApplication.dto.PostDTO;
import com.sandyflat.BlogApplication.dto.PostResponse;

import java.util.List;

public interface PostService {

    // create
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

    // update
    PostDTO updatePost(PostDTO postDTO, Long postId);

    // delete
    void deletePost(Long postId);

    // get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDirection);

    // get particular posts
    PostDTO getPostById(Long postId);

    // get all post by category
    List<PostDTO> getPostByCategory(Long categoryId);

    // get all post by user
    List<PostDTO> getAllPostByUser(Long userId);

    // search post
    List<PostDTO> searchPosts(String keyword);
}
