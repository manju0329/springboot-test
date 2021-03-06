package com.test.spring.web;

import com.test.spring.config.auth.LoginUser;
import com.test.spring.config.auth.dto.SessionUser;
import com.test.spring.service.posts.PostsService;
import com.test.spring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService; // non-static method는 먼저 선언필수
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        // 모든 컨트롤러에서 @LoginUser 어노테이션으로 세션 정보 가져올 수 있음
        model.addAttribute("posts", postsService.findAllDesc()); 
        // findAllDesc의 결과 posts라는 이름으로 index.mustache에 전달함

        if(user != null){ // 세션에 저장된 값이 있을때만 model에 저장
            model.addAttribute("userName", user.getName());
        }
        return "index"; // src/main/resources/templates/index.mustache return함
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; // /posts/save 호출 시 -> posts-save.mustache 호출함
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
