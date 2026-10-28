

## Tomcat이 직접 /images에 응답하려면 ./uploads/images 만들고 다음과 같이 ./uploads 폴더를 리소스 위치로 등록

```properties
spring.web.resources.static-locations=classpath:/static/, file:./uploads/
```

## 앞단의 웹 서버(Nginx)가 /images에 응답하려면 다음과 같이 Nginx 설정
```properties
    server {
        listen 80;
        server_name localhost;

        # 이미지 요청은 Nginx가 직접 처리
        location /images/ {
            alias /images/;
        }

        # 회원 API 요청은 Spring Boot(Tomcat)로 전달
        location /members {
            proxy_pass http://127.0.0.1:8080;
        }
    }
```