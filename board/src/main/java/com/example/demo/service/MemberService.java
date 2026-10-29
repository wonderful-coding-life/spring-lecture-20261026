package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.exception.DuplicateMemberException;
import com.example.demo.form.MemberForm;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member register(MemberForm form) {
        if (memberRepository.existsByUsername(form.getUsername())) {
            throw new DuplicateMemberException("username", "이미 사용 중인 로그인 아이디입니다.");
        }
        if (memberRepository.existsByEmail(form.getEmail())) {
            throw new DuplicateMemberException("email", "이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(
                form.getUsername(),
                passwordEncoder.encode(form.getPassword()),
                form.getName(),
                form.getEmail()
        );
        return memberRepository.save(member);
    }
}
