package com.sandyflat.BlogApplication.serviceimpl;

import com.sandyflat.BlogApplication.entity.Comment;
import com.sandyflat.BlogApplication.entity.Post;
import com.sandyflat.BlogApplication.exception.ResourceNotFoundException;
import com.sandyflat.BlogApplication.payload.CommentDTO;
import com.sandyflat.BlogApplication.repository.CommentRepository;
import com.sandyflat.BlogApplication.repository.PostRepository;
import com.sandyflat.BlogApplication.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", commentId));
        commentRepository.delete(comment);
    }
}
