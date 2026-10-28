package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 작성 요청")
public class ArticleRequest {
    @Schema(description = "게시글 제목", example = "첫 번째 게시글", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "게시글 내용", example = "안녕하세요. 반갑습니다.")
    private String description;
}
