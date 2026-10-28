package com.example.bbs;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.bbs.entity.Article;
import com.example.bbs.form.MemberForm;
import com.example.bbs.repository.ArticleRepository;
import com.example.bbs.repository.MemberRepository;
import com.example.bbs.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
class BbsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDatabase() {
        articleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void publicArticleListRenders() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SPRING BOARD")));
    }

    @Test
    void registrationStoresEncodedPasswordAndRedirectsToLogin() throws Exception {
        mockMvc.perform(post("/members")
                        .with(csrf())
                        .param("username", "member1")
                        .param("password", "password123")
                        .param("passwordConfirm", "password123")
                        .param("name", "Member One")
                        .param("email", "member1@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        var member = memberRepository.findByUsername("member1").orElseThrow();
        assertNotEquals("password123", member.getPassword());
        assertTrue(passwordEncoder.matches("password123", member.getPassword()));
    }

    @Test
    void onlyArticleAuthorCanModifyOrDelete() throws Exception {
        register("author", "author@example.com");
        register("reader", "reader@example.com");
        MockHttpSession authorSession = login("author", "password123");
        MockHttpSession readerSession = login("reader", "password123");

        mockMvc.perform(post("/articles")
                        .session(authorSession)
                        .with(csrf())
                        .param("title", "authorization-check")
                        .param("content", "only author can edit or delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));

        Article article = articleRepository.findAll().getFirst();

        mockMvc.perform(get("/articles/{id}", article.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("authorization-check")));

        mockMvc.perform(post("/articles/{id}/edit", article.getId())
                        .session(readerSession)
                        .with(csrf())
                        .param("title", "unauthorized-edit")
                        .param("content", "this change must be rejected"))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/articles/{id}/delete", article.getId())
                        .session(readerSession)
                        .with(csrf()))
                .andExpect(status().isForbidden());

        assertEquals("authorization-check", articleRepository.findById(article.getId()).orElseThrow().getTitle());

        mockMvc.perform(post("/articles/{id}/edit", article.getId())
                        .session(authorSession)
                        .with(csrf())
                        .param("title", "authorized-edit")
                        .param("content", "author changes are accepted"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles/" + article.getId()));
        assertEquals("authorized-edit", articleRepository.findById(article.getId()).orElseThrow().getTitle());

        mockMvc.perform(post("/articles/{id}/delete", article.getId())
                        .session(authorSession)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
        assertTrue(articleRepository.findById(article.getId()).isEmpty());
    }

    @Test
    void missingArticleReturnsNotFoundPage() throws Exception {
        mockMvc.perform(get("/articles/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("404")));
    }

    private void register(String username, String email) {
        MemberForm form = new MemberForm();
        form.setUsername(username);
        form.setPassword("password123");
        form.setPasswordConfirm("password123");
        form.setName(username);
        form.setEmail(email);
        memberService.register(form);
    }

    private MockHttpSession login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        return (MockHttpSession) result.getRequest().getSession(false);
    }
}
