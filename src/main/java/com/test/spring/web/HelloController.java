package com.test.spring.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // json 반환 컨트롤러
public class HelloController {

    @GetMapping("/hello") // /hello 요청에 대한한 처리
     public String hello(){
        return "hello";
    }
}
