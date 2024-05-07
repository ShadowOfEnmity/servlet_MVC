package com.kostrikov.servletmaven.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long id;
    String name;
    String age;
    String email;
    String login;
}
