package com.example.demo.controller;

import com.example.demo.dto.ArticleRequest;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시글", description = "게시글 작성 및 페이지 단위 조회 API")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/articles")
    @Operation(summary = "게시글 작성", description = "회원 ID와 게시글 제목 및 내용을 전달해 게시글을 작성합니다.")
    public ArticleResponse postArticles(@Parameter(description = "게시글 작성자 회원 ID", example = "1", required = true) @RequestParam("memberId") Long memberId, @RequestBody ArticleRequest articleRequest) {
        return articleService.create(memberId, articleRequest);
    }

    @GetMapping("/articles")
    @Operation(summary = "게시글 목록 조회", description = "게시글을 페이지 단위로 조회합니다. 기본 정렬은 ID 내림차순이며 페이지 크기는 10입니다.")
    public Page<ArticleResponse> getArticles(@ParameterObject @PageableDefault(sort="id", direction=Sort.Direction.DESC, size=10, page=0) Pageable pageable) {
        return articleService.getAllArticles(pageable);
    }
}
