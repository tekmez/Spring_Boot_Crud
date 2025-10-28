package com.tekmez.crud.model.dto;

import com.tekmez.crud.enums.Role;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private Role role;

    public static UserResponse from(Long id, String email, String name, Role role){
        UserResponse response = new UserResponse();
        response.id = id;
        response.email = email;
        response.name = name;
        response.role = role;
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
