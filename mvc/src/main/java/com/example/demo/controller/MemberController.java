package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/add")
    public String getMemberNew() {
        return "member-add";
    }

    @PostMapping("/member/add")
    public String postMemberAdd(Member member) {

        // member.name -> null
        // member.email -> ee@ttt -> fjdkfjkd


        memberRepository.save(member);
        return "redirect:/member/list";
    }

    @GetMapping("/member/edit")
    public String getMemberEdit(@RequestParam("id") Long id, Model model) {
        var member = memberRepository.findById(id).orElseThrow();
        model.addAttribute("member", member);
        return "member-edit";
    }

    @PostMapping("/member/edit")
    public String postMemberEdit(Member member) {
        memberRepository.save(member);
        return "redirect:/member/list";
    }

    @GetMapping("/member/delete")
    public String getMemberDelete(@RequestParam("id") Long id) {
        var member = memberRepository.findById(id).orElseThrow();
        memberRepository.delete(member);
        return "redirect:/member/list";
    }

    @GetMapping("/member/list")
    public String getMemberList(Model model) {
        var members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "member-list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("now", LocalDateTime.now());
        return "error";
    }

}
