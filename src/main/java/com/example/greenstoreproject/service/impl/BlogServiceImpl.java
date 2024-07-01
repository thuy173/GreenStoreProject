package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.entity.Blog;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.BlogMapper;
import com.example.greenstoreproject.mapper.CategoryMapper;
import com.example.greenstoreproject.repository.BlogRepository;
import com.example.greenstoreproject.service.BlogService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Override
    public List<BlogResponse> getAllBlog() {
        List<Blog> blog = blogRepository.findAll();

        return blog.stream()
                .map(BlogMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDetailResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));
        return BlogMapper.convertToDetailResponse(blog);
    }

    @Override
    public String createBlog(BlogRequest blogRequest) {
        Blog blog = BlogMapper.convertToEntity(blogRequest);
        blogRepository.save(blog);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateBlog(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));
        BlogMapper.updateFromRequest(blog, blogRequest);
        blogRepository.save(blog);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));
        blogRepository.delete(blog);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
