CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_members_username UNIQUE (username),
    CONSTRAINT uk_members_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE articles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_articles_created_at (created_at),
    CONSTRAINT fk_articles_author FOREIGN KEY (author_id) REFERENCES members (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
