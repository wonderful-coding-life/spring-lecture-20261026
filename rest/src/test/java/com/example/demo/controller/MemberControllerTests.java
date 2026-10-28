package com.example.demo.controller;

import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository.deleteAll();
    }

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    public void testSubscribe() throws Exception {
        var request = MockMvcRequestBuilders.post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "김광철",
                            "email": "KwangcheolKim@campus.co.kr",
                            "age": 10
                        }
                        """);

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "name": "김광철",
                            "email": "KwangcheolKim@campus.co.kr",
                            "age": 10
                        }
                """, JsonCompareMode.LENIENT))
                .andExpect(jsonPath("$.id").isNumber());
    }
}
