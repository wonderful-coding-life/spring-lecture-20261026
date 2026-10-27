package com.example.demo;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JpaApplication implements ApplicationRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 회원 생성
//        Member member = Member.builder().name("윤광철").email("KwangcheolYoon@campus.co.kr").age(43).build();
//        memberRepository.save(member);
//        log.info("회원 {}", member);

        // 전체 회원 조회
//        List<Member> members = memberRepository.findAll();
//        members.stream().forEach(member -> log.info("회원 {}", member));

        // 아이디로 조회
//        Member member = memberRepository.findById(1L).orElseThrow();
//        log.info("1번 회원 {}", member);

        // 이메일 수정
//        member.setEmail("SeojunYoon@google.com");
//        memberRepository.save(member);

        // 회원 삭제
        //memberRepository.deleteById(2L);
//        memberRepository.delete(member);

        // 이름으로 조회
        List<Member> members = memberRepository.findByName("윤광철");
        members.stream().forEach(member -> log.info("윤광철 {}", member));

    }
}
