package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCustomerCustomerId(Long customerId);
    List<Blog> findByApprovedTrue();
}
