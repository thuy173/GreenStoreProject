package com.example.greenstoreproject.bean.request.customer;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerUpdateRequest {

    private String firstName;

    private String lastName;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format"
    )
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phoneNumber;

}
