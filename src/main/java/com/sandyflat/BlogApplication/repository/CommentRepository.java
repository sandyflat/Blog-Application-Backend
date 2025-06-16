package com.sandyflat.BlogApplication.repository;

import com.sandyflat.BlogApplication.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long > {
}
