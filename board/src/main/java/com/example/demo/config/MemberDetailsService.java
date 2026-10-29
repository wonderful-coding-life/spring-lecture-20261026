package com.example.demo.config;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import java.util.function.Function;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        return User.withUsername(member.getUsername())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}
