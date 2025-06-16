package com.sandyflat.BlogApplication.repository;

import com.sandyflat.BlogApplication.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

}
