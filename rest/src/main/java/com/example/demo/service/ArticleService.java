package com.example.demo.service;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public ArticleResponse create(Long memberId, ArticleRequest articleRequest) {
        var member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        var article = Article.builder()
                .title(articleRequest.getTitle())
                .description(articleRequest.getDescription())
                .member(member).build();
        articleRepository.save(article);
        return mapToArticleResponse(article);
    }

    public Page<ArticleResponse> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable).map(this::mapToArticleResponse);
    }

    private ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .description(article.getDescription())
                .name(article.getMember().getName())
                .email(article.getMember().getEmail())
                .created(article.getCreated())
                .updated(article.getUpdated()).build();
    }
}
