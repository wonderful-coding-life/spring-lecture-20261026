package com.example.demo.controller;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/articles")
    public ArticleResponse postArticles(@RequestParam("memberId") Long memberId, @RequestBody ArticleRequest articleRequest) {
        return articleService.create(memberId, articleRequest);
    }

    @GetMapping("/articles")
    public Page<ArticleResponse> getArticles(@PageableDefault(sort="id", direction=Sort.Direction.DESC, size=10, page=0) Pageable pageable) {
        return articleService.getAllArticles(pageable);
    }
}
