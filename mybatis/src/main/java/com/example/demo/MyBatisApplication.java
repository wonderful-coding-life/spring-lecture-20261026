package com.example.demo;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyBatisApplication implements ApplicationRunner {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 전체 조회
//        List<Member> members = memberMapper.selectAll();
//        for (Member member : members) {
//            log.info("회원 {}", member);
//        }

        // 아이디로 조회
//        Member member = memberMapper.selectById(3L).orElse(Member.builder().name("홍길동").build());
//        log.info("3번 회원 {}", member);
//        member = memberMapper.selectById(32L).orElse(Member.builder().name("홍길동").build());
//        log.info("32번 회원 {}", member);

        // 회원 생성
//        Member member = Member.builder().name("홍길동").email("GildongHong@campus.co.kr").age(18).build();
//        int result = memberMapper.insert(member);
//        log.info("회원 가입 결과 {}, 가입 정보 {}", result, member);

        // 이메일 변경
//        memberMapper.updateEmail(1L, "SeojunYoon@campus.co.kr");
//        Member member = memberMapper.selectById(1L).orElseThrow();
//        log.info("1번 회원 이메일 변경 후...{}", member);

        // 회원 삭제
        memberMapper.deleteById(3L);
        Member member = memberMapper.selectById(3L).orElse(null);
        log.info("3번 회원 탈퇴 후, 회원={}", member);
    }
}
