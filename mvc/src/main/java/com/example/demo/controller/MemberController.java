package com.example.demo.controller;

import com.example.demo.form.MemberForm;
import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("memberForm") MemberForm memberForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult bindingResult, Model model) {

        // 이메일이 중복인지?
        if ("SeojunYoon@campus.co.kr".equals(memberForm.getEmail())) {
            bindingResult.rejectValue("email", "duplicated-email", "중복된 이메일 입니다.");
        }

        // 비밀번호와 비밀번호 확인이 같은 값인지?
        if (!Objects.equals(memberForm.getPassword(), memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password-confirm-error", "입력된 비밀번호는 동일한 비밀번호로 해 주세요.");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 데이터베이스 저장
        // 웰컴 메일 전송
        // 포인트 적립

        return "redirect:/member/list";
    }


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
