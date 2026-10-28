package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceUnitTests {
    @MockitoBean
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    public void testGetMember() {

        when(memberRepository.findById(1L)).thenReturn(
                Optional.of(Member.builder()
                        .id(1L)
                        .name("윤서준")
                        .email("SeojunYoon@campus.co.kr")
                        .age(10).build())
        );
        when(memberRepository.findById(2L)).thenReturn(
                Optional.of(Member.builder()
                        .id(2L)
                        .name("윤광철")
                        .email("KwangcheolYoon@campus.co.kr")
                        .age(43).build())
        );

        var member = memberService.getMember(2L);

        assertThat(member.getId()).isEqualTo(2L);
        assertThat(member.getName()).isEqualTo("윤광철");
        assertThat(member.getEmail()).isEqualTo("KwangcheolYoon@campus.co.kr");
    }
}
