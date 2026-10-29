package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.form.ArticleForm;
import com.example.demo.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    private static final int PAGE_SIZE = 10;

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Article> articles = articleService.findArticles(PageRequest.of(Math.max(page, 0), PAGE_SIZE));
        model.addAttribute("articlePage", articles);
        return "articles/list";
    }

    @GetMapping("/articles/{articleId}")
    public String detail(@PathVariable Long articleId, Model model) {
        model.addAttribute("article", articleService.findArticle(articleId));
        return "articles/detail";
    }

    @GetMapping("/articles/new")
    public String createForm(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("isEdit", false);
        return "articles/form";
    }

    @PostMapping("/articles")
    public String create(
            @Valid @ModelAttribute("articleForm") ArticleForm articleForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "articles/form";
        }

        articleService.createArticle(articleForm.getTitle(), articleForm.getContent(), userDetails.getUsername());
        return "redirect:/articles";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String editForm(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        Article article = articleService.findArticleForAuthor(articleId, userDetails.getUsername());
        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        model.addAttribute("articleForm", articleForm);
        model.addAttribute("articleId", articleId);
        model.addAttribute("isEdit", true);
        return "articles/form";
    }

    @PostMapping("/articles/{articleId}/edit")
    public String edit(
            @PathVariable Long articleId,
            @Valid @ModelAttribute("articleForm") ArticleForm articleForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", articleId);
            model.addAttribute("isEdit", true);
            return "articles/form";
        }

        articleService.updateArticle(
                articleId,
                articleForm.getTitle(),
                articleForm.getContent(),
                userDetails.getUsername()
        );
        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/articles/{articleId}/delete")
    public String delete(
            @PathVariable Long articleId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        articleService.deleteArticle(articleId, userDetails.getUsername());
        return "redirect:/articles";
    }
}
