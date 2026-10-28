CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(60) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(254) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_members_username UNIQUE (username),
    CONSTRAINT uk_members_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE articles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_articles_author FOREIGN KEY (author_id) REFERENCES members (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
