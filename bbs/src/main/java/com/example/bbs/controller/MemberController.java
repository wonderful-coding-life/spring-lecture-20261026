package com.example.bbs.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bbs.form.MemberForm;
import com.example.bbs.exception.DuplicateMemberException;
import com.example.bbs.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/create";
    }

    @PostMapping("/members")
    public String register(
            @Valid @ModelAttribute("memberForm") MemberForm memberForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/create";
        }

        try {
            memberService.register(memberForm);
        } catch (DuplicateMemberException exception) {
            bindingResult.reject("registrationError", exception.getMessage());
            return "members/create";
        }
        return "redirect:/login";
    }
}
