package com.example.demo.controller;

import com.example.demo.form.MemberForm;
import com.example.demo.exception.DuplicateMemberException;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/create";
    }

    @PostMapping("/members")
    public String register(
            @Valid @ModelAttribute("memberForm") MemberForm memberForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "members/create";
        }

        try {
            memberService.register(memberForm);
        } catch (DuplicateMemberException exception) {
            bindingResult.addError(new FieldError(
                    "memberForm",
                    exception.getField(),
                    exception.getMessage()
            ));
            return "members/create";
        }

        return "redirect:/login";
    }
}
