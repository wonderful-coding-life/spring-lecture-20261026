package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.exception.ArticleAccessDeniedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Article> findArticles(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Article findArticleForAuthor(Long articleId, String requesterUsername) {
        Article article = findArticle(articleId);
        verifyAuthor(article, requesterUsername);
        return article;
    }

    @Transactional
    public Article createArticle(String title, String content, String authorUsername) {
        Member author = memberRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("회원을 찾을 수 없습니다."));
        return articleRepository.save(new Article(title, content, author));
    }

    @Transactional
    public Article updateArticle(Long articleId, String title, String content, String requesterUsername) {
        Article article = findArticle(articleId);
        verifyAuthor(article, requesterUsername);
        article.setTitle(title);
        article.setContent(content);
        return article;
    }

    @Transactional
    public void deleteArticle(Long articleId, String requesterUsername) {
        Article article = findArticle(articleId);
        verifyAuthor(article, requesterUsername);
        articleRepository.delete(article);
    }

    private void verifyAuthor(Article article, String requesterUsername) {
        if (!article.getAuthor().getUsername().equals(requesterUsername)) {
            throw new ArticleAccessDeniedException("게시글 작성자만 수정하거나 삭제할 수 있습니다.");
        }
    }
}
