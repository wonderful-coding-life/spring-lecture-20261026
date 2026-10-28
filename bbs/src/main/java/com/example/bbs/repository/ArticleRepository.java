package com.example.bbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bbs.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
