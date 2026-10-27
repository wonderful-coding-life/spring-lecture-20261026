package com.example.demo;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaApplication implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 회원 생성
//        var member = Member.builder().name("윤서준").email("SeojunYoon@campus.co.kr").age(10).build();
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
//        List<Member> members = memberRepository.findByName("윤광철");
//        members.stream().forEach(member -> log.info("윤광철 {}", member));

        // 나이로 조회
//        var members = memberRepository.findByAgeGreaterThanEqual(10);
//        members.stream().forEach(member -> log.info("회원 {}", member));

        // 정렬 조회
//        var members = memberRepository.findByNameContainingOrderByNameAsc("윤");
//        members.stream().forEach(m -> log.info("회원 {}", m));

        // 게시글 생성
        var member = memberRepository.findByName("윤서준").getFirst();
        var article = Article.builder()
                .title("방학 세째날이다")
                .description("오늘도 또 신난다")
//                .created(LocalDateTime.now())
//                .updated(LocalDateTime.now())
                .member(member).build();
        articleRepository.save(article);
        article = articleRepository.findById(2L).orElseThrow();
        log.info("{} {}", article.getMember().getName(), article.getTitle());

        // 페이지 단위 조회
//        Pageable pageable = PageRequest.of(0, 20, Sort.by(DESC, "created"));
//        var page = articleRepository.findAll(pageable);
//        page.getContent().stream().forEach(article -> log.info("게시글 {}", article));
    }
}
