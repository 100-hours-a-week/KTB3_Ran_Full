package com.ran.community.user.dto.response;

import com.ran.community.user.entity.User;
import lombok.Getter;

@Getter
public class UserDataResponseDTO {
    private final long id;
    private final String username;
    private final String email;

    public UserDataResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
