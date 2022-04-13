package com.test.spring.config.auth.dto;

import com.test.spring.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){ // 인증된 사용자의 정보만 저장
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
