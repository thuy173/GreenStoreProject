package com.example.greenstoreproject.mapper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class BlogMapper {
    public static BlogResponse convertToResponse(Blog blog) {
        BlogResponse response = new BlogResponse();
        response.setBlogId(blog.getBlogId());
        response.setTitle(blog.getTitle());
        response.setDescription(blog.getDescription());
        response.setThumbnail(blog.getThumbnail());
        response.setApproved(blog.getApproved());

        return response;
    }

    public static BlogDetailResponse convertToDetailResponse(Blog blog, String author) {
        BlogDetailResponse response = new BlogDetailResponse();
        response.setBlogId(blog.getBlogId());
        response.setAuthor(author);
        response.setTitle(blog.getTitle());
        response.setContent(blog.getContent());
        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());
        response.setApproved(blog.getApproved());

        return response;
    }

    public static Blog convertToEntity(BlogRequest request, Cloudinary cloudinary) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        try {
            Map uploadResult = cloudinary.uploader().upload(request.getThumbnail().getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
            blog.setThumbnail(url);
        } catch (IOException e) {
            throw new RuntimeException("Thumbnail upload failed", e);
        }

        return blog;
    }

    public static void updateFromRequest(Blog blog, BlogRequest blogRequest) {
        blogRequest.setTitle(blog.getTitle());
        blogRequest.setDescription(blog.getDescription());
        blogRequest.setThumbnail(blogRequest.getThumbnail());
        blogRequest.setContent(blog.getContent());
    }

}
