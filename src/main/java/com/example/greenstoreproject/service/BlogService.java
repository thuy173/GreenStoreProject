package com.example.greenstoreproject.service;

import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.bean.response.category.CategoryDetailResponse;
import com.example.greenstoreproject.bean.response.category.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    List<BlogResponse> getAllBlog();

    List<BlogResponse> getAllBlogApprove();

    List<BlogResponse> getBlogByCustomerId(Long customerId);

    BlogDetailResponse getBlogById(Long id);

    String createBlog(BlogRequest blogRequest);

    String updateBlog(Long id, BlogRequest blogRequest);

    String approveBlog(Long id);

    String deleteBlog(Long id);
}
