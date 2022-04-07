package com.test.spring;
import com.test.spring.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class) // 다른 스프링 실행자 사용
@WebMvcTest(controllers = HelloController.class) // controller 사용 o service, component, repository 사용 x
public class HelloControllerTest {

    @Autowired // bean 주입 받음
    private MockMvc mvc; // 웹 api 테스트

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) // /hello 주소로 http get
                .andExpect(status().isOk()) // 결과 검증 : ok = 200 인지 검증
                .andExpect(content().string(hello)); // 결과 검증 : 본문 = "hello"인지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // api 테스트 시 요청 파라미터(string)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // json 응답값 필드별로 검증
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
