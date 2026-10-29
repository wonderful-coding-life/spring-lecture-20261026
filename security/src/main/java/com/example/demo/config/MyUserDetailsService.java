package com.example.demo.config;

import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var member = memberRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username + " 없는 사용자입니다"));
        return User.builder()
                .username(username)
                .password(member.getPassword())
                .authorities(member.getAuthority()).build();
    }
}
