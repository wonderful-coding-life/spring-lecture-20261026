package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Demo 회원·게시글 API",
        version = "v1.0.0",
        description = "회원 관리와 게시글 작성·조회 기능을 제공하는 REST API입니다."
))
public class OpenApiConfig {
}
