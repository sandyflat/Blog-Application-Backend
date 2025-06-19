package com.sandyflat.BlogApplication.repository;

import com.sandyflat.BlogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);
     Boolean existsByEmail(String email);
     Boolean existsByName(String name);

}
