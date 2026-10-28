package com.example.bbs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bbs.entity.Member;
import com.example.bbs.exception.DuplicateMemberException;
import com.example.bbs.form.MemberForm;
import com.example.bbs.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(MemberForm form) {
        if (memberRepository.findByUsername(form.getUsername()).isPresent()) {
            throw new DuplicateMemberException("이미 사용 중인 로그인 아이디입니다.");
        }
        if (memberRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new DuplicateMemberException("이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(
                form.getUsername(),
                passwordEncoder.encode(form.getPassword()),
                form.getName(),
                form.getEmail());
        return memberRepository.save(member);
    }
}
