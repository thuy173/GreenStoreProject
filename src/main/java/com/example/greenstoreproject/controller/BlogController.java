package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.blog.BlogRequest;
import com.example.greenstoreproject.bean.response.blog.BlogDetailResponse;
import com.example.greenstoreproject.bean.response.blog.BlogResponse;
import com.example.greenstoreproject.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping("/{id}")
    public BlogDetailResponse getById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addBlog(@Valid @ModelAttribute BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }

    @PutMapping("/{id}")
    public String updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequest blogRequest) {
        return blogService.updateBlog(id, blogRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }
}
