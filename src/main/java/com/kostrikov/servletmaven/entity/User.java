package com.kostrikov.servletmaven.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String login;
    private String email;
    private String pwd;
}
