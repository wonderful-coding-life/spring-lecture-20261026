package com.example.demo.repository;

import com.example.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    @EntityGraph(attributePaths = "author")
    Optional<Article> findById(Long id);

    @EntityGraph(attributePaths = "author")
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
