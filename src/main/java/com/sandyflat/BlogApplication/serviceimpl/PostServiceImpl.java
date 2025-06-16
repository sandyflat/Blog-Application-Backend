package com.sandyflat.BlogApplication.serviceimpl;

import com.sandyflat.BlogApplication.entity.Categories;
import com.sandyflat.BlogApplication.entity.Post;
import com.sandyflat.BlogApplication.entity.User;
import com.sandyflat.BlogApplication.exception.ResourceNotFoundException;
import com.sandyflat.BlogApplication.payload.PostDTO;
import com.sandyflat.BlogApplication.payload.PostResponse;
import com.sandyflat.BlogApplication.repository.CategoryRepository;
import com.sandyflat.BlogApplication.repository.PostRepository;
import com.sandyflat.BlogApplication.repository.UserRepository;
import com.sandyflat.BlogApplication.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategories(categories);

        Post newPost = postRepository.save(post);

        return modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        Post updatedPost = postRepository.save(post);

        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDirection) {
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }
//    Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(pageRequest);

        List<Post> allPosts = pagePost.getContent();

        List<PostDTO> postDTOS = allPosts.stream()
                .map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostByCategory(Long categoryId) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

        List<Post> posts = postRepository.findByCategories(categories);
        List<PostDTO> postDTOS = posts.stream()
                .map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        return postDTOS;
    }

    @Override
    public List<PostDTO> getAllPostByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        List<Post> posts = postRepository.findByUser(user);
        List<PostDTO> postDTOS = posts.stream()
                .map((post) -> modelMapper.map(post, PostDTO.class)).toList();
        return postDTOS;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);

        List<PostDTO> postDTOS = posts.stream()
                .map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        return postDTOS;
    }
}
