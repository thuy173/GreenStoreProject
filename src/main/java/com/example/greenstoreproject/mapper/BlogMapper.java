package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.*;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {
    public static BlogResponse convertToResponse(Blog blog) {
        BlogResponse response = new BlogResponse();
        response.setTitle(blog.getTitle());
        response.setApproved(blog.getApproved());

        return response;
    }
    public static BlogDetailResponse convertToDetailResponse(Blog blog) {
        BlogDetailResponse response = new BlogDetailResponse();
        response.setTitle(blog.getTitle());
        response.setAuthor(blog.getAuthor());
        response.setContent(blog.getContent());
        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());
        response.setApproved(blog.getApproved());

        return response;
    }

    public static Blog convertToEntity(BlogRequest request) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setAuthor(request.getAuthor());
        blog.setContent(request.getContent());
        blog.setCreatedAt(request.getCreatedAt());
        blog.setUpdatedAt(request.getUpdatedAt());
        blog.setApproved(request.getApproved());

        return blog;
    }

    public static void updateFromRequest(Blog blog, BlogRequest blogRequest) {
        blogRequest.setTitle(blog.getTitle());
        blogRequest.setAuthor(blog.getAuthor());
        blogRequest.setContent(blog.getContent());
        blogRequest.setCreatedAt(blog.getCreatedAt());
        blogRequest.setUpdatedAt(blog.getUpdatedAt());
        blogRequest.setApproved(blog.getApproved());


    }

}
