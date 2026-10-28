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
@Schema(description = "회원 정보 응답")
public class MemberResponse {
    @Schema(description = "회원 ID", example = "1")
    private Long id;
    @Schema(description = "회원 이름", example = "홍길동")
    private String name;
    @Schema(description = "이메일 주소", example = "hong@example.com")
    private String email;
    @Schema(description = "나이", example = "30")
    private Integer age;
}
