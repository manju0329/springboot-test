package com.test.spring.web;
import com.test.spring.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // json 반환 컨트롤러
public class HelloController {

    @GetMapping("/hello") // /hello 요청에 대한한 처리
     public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto
            (@RequestParam("name") String name, @RequestParam("amount") int amount){
            // 외부에서 api로 넘긴 파라미터 가져옴 - name 이란 이름으로 넘긴 파라미터 name(String name)에 저장
        return new HelloResponseDto(name, amount);
    }
}
