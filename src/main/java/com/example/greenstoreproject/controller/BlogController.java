package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog")
@SecurityRequirement(name = "bearerAuth")
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public List<BlogResponse> getAllBlog() {
        return blogService.getAllBlog();
    }

    @GetMapping("/app")
    public List<BlogResponse> getAllBlogApprove() {
        return blogService.getAllBlogApprove();
    }

    @GetMapping("/getAllByUser/{id}")
    public List<BlogResponse> getAllBlogByCustomer(@PathVariable Long id) {
        return blogService.getBlogByCustomerId(id);
    }

    @GetMapping("/{id}")
    public BlogDetailResponse getById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addBlog(@Valid @ModelAttribute BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateBlog(@PathVariable Long id, @Valid @ModelAttribute BlogRequest blogRequest) {
        return blogService.updateBlog(id, blogRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/approve")
    public String approveBlog(@PathVariable Long id) {
        return blogService.approveBlog(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }
}
