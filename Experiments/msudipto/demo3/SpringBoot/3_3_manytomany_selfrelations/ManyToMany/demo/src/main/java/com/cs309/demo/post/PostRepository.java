package com.cs309.demo.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Post findByTitle(String title); // Removed redundant 'public' modifier
}