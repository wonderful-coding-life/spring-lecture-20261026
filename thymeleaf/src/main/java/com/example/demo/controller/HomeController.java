package com.example.demo.controller;

import com.example.demo.dto.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping
    public String getHome() {
        return "home";
    }

    @GetMapping("/model")
    public String getModel(Model model) {
        var member = Member.builder()
                .id(1L)
                .name("윤서준")
                .email("SeojunYoon@campus.co.kr")
                .age(10).build();

        model.addAttribute("title", "올인원 스프링부트 정복에 오신 것을 환영합니다.");
        model.addAttribute("member", member);
        return "model";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        var members = List.of(
                Member.builder().name("윤서준").email("SeojunYoon@hanbit.co.kr").age(10).build(),
                Member.builder().name("윤광철").email("KwangcheolYoon@hanbit.co.kr").age(43).build(),
                Member.builder().name("공미영").email("MiyeongKong@hanbit.co.kr").age(23).build(),
                Member.builder().name("김도윤").email("DoyunKim@hanbit.co.kr").age(10).build()
        );
        model.addAttribute("members", members);
        return "list";
    }

    @GetMapping("/utility")
    public String getUtility(Model model) {
        var now = LocalDateTime.now();
        model.addAttribute("now", now);
        return "utility";
    }


}
