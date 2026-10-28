package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository.deleteAll();
        var member = Member.builder()
                .name("윤서준")
                .email("SeojunYoon@campus.co.kr")
                .age(10).build();
        memberRepository.save(member);
    }

    @AfterEach()
    public void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void testCreateMember() {
        long count = memberRepository.count();

        var member = Member.builder()
                .name("윤서준")
                .email("SeojunYoon@campus.co.kr")
                .age(10).build();
        memberRepository.save(member);

        assertThat(memberRepository.count()).isEqualTo(count + 1);
    }

    @Test
    public void testCreateMemberNormal() {

    }

    @Test
    public void testCreateMemberWithDuplicatedEmail() {

    }


    @Test
    @Disabled
    public void testDeleteMember() {

    }


}
