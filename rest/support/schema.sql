-- MariaDB / Spring Data JPA 초기 스키마
-- 외래 키 참조 때문에 article을 먼저 삭제합니다.
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    age INTEGER,
    profile_photo_url VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE article (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(255),
    created DATETIME(6),
    updated DATETIME(6),
    member_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_article_member FOREIGN KEY (member_id) REFERENCES member(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
