package ru.echominds.infohub.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.services.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDTO> getAllArticles(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return articleService.getAllArticles(PageRequest.of(offset, limit));
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @PostMapping
    public HttpStatus createArticle(@RequestBody ArticleDTO article) {
        articleService.createArticle(article);

        return HttpStatus.CREATED;
    }

    @PatchMapping("/{id}")
    public HttpStatus patchArticle(@RequestBody ArticleDTO updatedArticleDTO,
                                   @PathVariable Long id) {
        articleService.updateArticle(id, updatedArticleDTO);

        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticle(id);

        return HttpStatus.NO_CONTENT;

    }
}
