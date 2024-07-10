package com.example.greenstoreproject.bean.request.customer;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerUpdateAvatarRequest {
    private MultipartFile avatar;
}
