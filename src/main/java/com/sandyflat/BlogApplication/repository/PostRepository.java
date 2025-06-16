package com.sandyflat.BlogApplication.repository;

import com.sandyflat.BlogApplication.entity.Categories;
import com.sandyflat.BlogApplication.entity.Post;
import com.sandyflat.BlogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByCategories(Categories categories);

    List<Post> findByTitleContaining(String title);
}
