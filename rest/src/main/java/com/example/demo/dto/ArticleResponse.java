package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 및 작성자 정보 응답")
public class ArticleResponse {
    @Schema(description = "게시글 ID", example = "10")
    private Long id;
    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;
    @Schema(description = "게시글 내용", example = "안녕하세요. 반갑습니다.")
    private String description;
    @Schema(description = "작성자 이름", example = "홍길동")
    private String name;
    @Schema(description = "작성자 이메일", example = "hong@example.com")
    private String email;
    @Schema(description = "작성 시각", example = "2026-10-29T10:30:00")
    private LocalDateTime created;
    @Schema(description = "수정 시각", example = "2026-10-29T10:30:00")
    private LocalDateTime updated;
}
