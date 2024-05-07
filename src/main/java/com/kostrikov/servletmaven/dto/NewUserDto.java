package com.kostrikov.servletmaven.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewUserDto {
    String name;
    String age;
    String email;
    String login;
    String pwd;
}
