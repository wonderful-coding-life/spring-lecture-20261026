package com.example.demo;

import com.example.demo.exception.ArticleAccessDeniedException;
import com.example.demo.exception.DuplicateMemberException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Member;
import com.example.demo.form.MemberForm;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardIntegrationTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void memberRegistrationEncodesPasswordAndRejectsDuplicates() {
        MemberForm form = memberForm("member1", "member1@example.com");

        var member = memberService.register(form);

        assertThat(member.getPassword()).isNotEqualTo(form.getPassword());
        assertThat(passwordEncoder.matches(form.getPassword(), member.getPassword())).isTrue();
        assertThatThrownBy(() -> memberService.register(form))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessageContaining("아이디");
    }

    @Test
    void articleCrudRequiresAuthorAndMissingArticlesUseNotFoundException() {
        var author = memberRepository.save(new Member(
                "author1", passwordEncoder.encode("password1"), "작성자", "author@example.com"));
        memberRepository.save(new Member(
                "reader1", passwordEncoder.encode("password1"), "다른 회원", "reader@example.com"));

        var article = articleService.createArticle("첫 게시글", "본문", author.getUsername());

        assertThat(articleRepository.findById(article.getId())).isPresent();
        assertThatThrownBy(() -> articleService.updateArticle(article.getId(), "변경", "본문", "reader1"))
                .isInstanceOf(ArticleAccessDeniedException.class);
        assertThatThrownBy(() -> articleService.deleteArticle(article.getId(), "reader1"))
                .isInstanceOf(ArticleAccessDeniedException.class);
        assertThatThrownBy(() -> articleService.findArticleForAuthor(article.getId(), "reader1"))
                .isInstanceOf(ArticleAccessDeniedException.class);
        assertThat(articleService.updateArticle(article.getId(), "수정된 제목", "수정된 본문", "author1").getTitle())
                .isEqualTo("수정된 제목");
        articleService.deleteArticle(article.getId(), "author1");
        assertThat(articleRepository.findById(article.getId())).isEmpty();
        assertThatThrownBy(() -> articleService.findArticle(-1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private MemberForm memberForm(String username, String email) {
        MemberForm form = new MemberForm();
        form.setUsername(username);
        form.setPassword("password1");
        form.setPasswordConfirmation("password1");
        form.setName("테스트 사용자");
        form.setEmail(email);
        return form;
    }
}
