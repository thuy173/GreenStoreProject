package com.example.greenstoreproject.service.impl;

import com.cloudinary.Cloudinary;
import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.entity.Blog;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.BlogMapper;
import com.example.greenstoreproject.repository.BlogRepository;
import com.example.greenstoreproject.service.BlogService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final Cloudinary cloudinary;
    private final AuthServiceImpl authService;


    @Override
    public List<BlogResponse> getAllBlog() {
        if (!authService.isAdmin()) {
            throw new NotFoundException("Only admins can view all blogs");
        }

        List<Blog> blog = blogRepository.findAll();

        return blog.stream()
                .map(BlogMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogResponse> getAllBlogApprove() {
        List<Blog> blog = blogRepository.findByApprovedTrue();

        return blog.stream()
                .map(BlogMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDetailResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));

        String author = blog.getCustomer() != null
                ? blog.getCustomer().getFirstName() + " " + blog.getCustomer().getLastName()
                : "Unknown Author";

        return BlogMapper.convertToDetailResponse(blog, author);
    }

    @Override
    public List<BlogResponse> getBlogByCustomerId(Long customerId) {
        List<Blog> blogs = blogRepository.findByCustomerCustomerId(customerId);
        if (blogs.isEmpty()) {
            throw new NotFoundException("No blogs found for customer ID " + customerId);
        }
        return blogs.stream()
                .map(BlogMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String createBlog(BlogRequest blogRequest) {
        String email = getAuthenticatedUserEmail();
        Customers user = authService.getCustomerByEmail(email);

        Blog blog = BlogMapper.convertToEntity(blogRequest, cloudinary);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setCustomer(user);

        if (authService.isAdmin()) {
            blog.setApproved(true);
        } else {
            blog.setApproved(false);
        }

        blogRepository.save(blog);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateBlog(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));

        if (!isUserAllowedToEditBlog(blog)) {
            throw new NotFoundException("You do not have permission to edit this blog");
        }

        BlogMapper.updateFromRequest(blog, blogRequest);
        blog.setUpdatedAt(LocalDateTime.now());
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

    @Override
    public String approveBlog(Long id) {
        if (!authService.isAdmin()) {
            throw new NotFoundException("Only admins can approve blogs");
        }

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Blog not found " + id));

        blog.setApproved(true);
        blogRepository.save(blog);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }


    private String getAuthenticatedUserEmail() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    private boolean isUserAllowedToEditBlog(Blog blog) {
        String email = getAuthenticatedUserEmail();
        Customers user = authService.getCustomerByEmail(email);
        return authService.isAdmin() || (user.getCustomerId().equals(blog.getCustomer().getCustomerId()));
    }
}
