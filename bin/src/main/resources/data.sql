-- 테이블 생성 SQL 명령어 (테이블이 없는 경우에만 사용)
CREATE TABLE IF NOT EXISTS board (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255),
    content TEXT
    );

-- 데이터 삽입
INSERT INTO board (id,title, content) VALUES (1,'첫 번째 게시글', '첫 번째 게시글 내용입니다.');
INSERT INTO board (id,title, content) VALUES (2,'두 번째 게시글', '두 번째 게시글 내용입니다.');