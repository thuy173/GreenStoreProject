package com.example.greenstoreproject.event;

import com.example.greenstoreproject.entity.Blog;
import org.springframework.context.ApplicationEvent;

public class NewBlogEvent extends ApplicationEvent {

    private final Blog blog;

    public NewBlogEvent(Object source, Blog blog) {
        super(source);
        this.blog = blog;
    }

    public Blog getBlog() {
        return blog;
    }
}
