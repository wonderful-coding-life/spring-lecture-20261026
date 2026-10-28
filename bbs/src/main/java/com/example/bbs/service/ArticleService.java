package com.example.bbs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bbs.entity.Article;
import com.example.bbs.entity.Member;
import com.example.bbs.exception.ResourceNotFoundException;
import com.example.bbs.form.ArticleForm;
import com.example.bbs.repository.ArticleRepository;
import com.example.bbs.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Article findById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 게시글을 찾을 수 없습니다."));
    }

    public Article findByIdForAuthor(Long articleId, String requesterUsername) {
        Article article = findById(articleId);
        validateAuthor(article, requesterUsername);
        return article;
    }

    @Transactional
    public Article create(ArticleForm form, String authorUsername) {
        Member author = memberRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 회원을 찾을 수 없습니다."));
        Article article = new Article(form.getTitle(), form.getContent(), author);
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long articleId, ArticleForm form, String requesterUsername) {
        Article article = findById(articleId);
        validateAuthor(article, requesterUsername);
        article.update(form.getTitle(), form.getContent());
        return article;
    }

    @Transactional
    public void delete(Long articleId, String requesterUsername) {
        Article article = findById(articleId);
        validateAuthor(article, requesterUsername);
        articleRepository.delete(article);
    }

    private void validateAuthor(Article article, String requesterUsername) {
        if (!article.getAuthor().getUsername().equals(requesterUsername)) {
            throw new AccessDeniedException("게시글 작성자만 수정하거나 삭제할 수 있습니다.");
        }
    }
}
