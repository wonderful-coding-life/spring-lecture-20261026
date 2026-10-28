package com.example.bbs.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bbs.entity.Article;
import com.example.bbs.form.ArticleForm;
import com.example.bbs.service.ArticleService;

@Controller
public class ArticleController {

    private static final int PAGE_SIZE = 10;

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/articles";
    }

    @GetMapping("/articles")
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Article> articlePage = articleService.findAll(PageRequest.of(Math.max(page, 0), PAGE_SIZE));
        model.addAttribute("articlePage", articlePage);
        return "articles/list";
    }

    @GetMapping("/articles/{articleId}")
    public String detail(@PathVariable Long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
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
            Authentication authentication,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "articles/form";
        }

        articleService.create(articleForm, authentication.getName());
        return "redirect:/articles";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String editForm(
            @PathVariable Long articleId,
            Authentication authentication,
            Model model) {
        Article article = articleService.findByIdForAuthor(articleId, authentication.getName());

        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());
        model.addAttribute("articleForm", articleForm);
        model.addAttribute("article", article);
        model.addAttribute("isEdit", true);
        return "articles/form";
    }

    @PostMapping("/articles/{articleId}/edit")
    public String update(
            @PathVariable Long articleId,
            @Valid @ModelAttribute("articleForm") ArticleForm articleForm,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", articleService.findByIdForAuthor(articleId, authentication.getName()));
            model.addAttribute("isEdit", true);
            return "articles/form";
        }

        articleService.update(articleId, articleForm, authentication.getName());
        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/articles/{articleId}/delete")
    public String delete(@PathVariable Long articleId, Authentication authentication) {
        articleService.delete(articleId, authentication.getName());
        return "redirect:/articles";
    }
}

