-- userTB: 사용자 정보
CREATE TABLE IF NOT EXISTS userTB (
                                      userIdx INT AUTO_INCREMENT PRIMARY KEY,
                                      email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastLogin TIMESTAMP
    );

-- 초기 사용자 데이터 삽입
INSERT INTO userTB (nickname, email, password) VALUES ('user1', 'user1@example.com', '$2a$10$tByhOvUIUzqyL/a84QSsyOZrPXwSPHxR76OizQrQ9WFMJRjUmHDBi');

-- INSERT INTO userTB (nickname, email, password) VALUES ('user2', 'user2@example.com', 'password123');
-- INSERT INTO userTB (nickname, email, password) VALUES ('user3', 'user3@example.com', 'password123');

-- defaultColorTB: 사용자별 기본 색상 설정
CREATE TABLE IF NOT EXISTS defaultColorTB (
                                              colorId INT AUTO_INCREMENT PRIMARY KEY,
                                              userIdx INT NOT NULL,
                                              colorCode VARCHAR(7) NOT NULL,
    colorName VARCHAR(50),
    FOREIGN KEY (userIdx) REFERENCES userTB(userIdx)
    );

-- diaryTB: 사용자별 일기 저장
CREATE TABLE IF NOT EXISTS diaryTB (
                                       diaryId INT AUTO_INCREMENT PRIMARY KEY,
                                       userIdx INT NOT NULL,
                                       entryDate DATE NOT NULL,
                                       content TEXT,
                                       mood VARCHAR(50),
    FOREIGN KEY (userIdx) REFERENCES userTB(userIdx)
    );

-- tokenTB: 인증 토큰 저장
CREATE TABLE IF NOT EXISTS tokenTB (
                                       tokenIdx INT AUTO_INCREMENT PRIMARY KEY,
                                       userIdx INT NOT NULL,
                                       refreshToken VARCHAR(255) NOT NULL,
    FOREIGN KEY (userIdx) REFERENCES userTB(userIdx)
    );

INSERT INTO tokenTB (userIdx, refreshToken) VALUES (1, 'eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDk3ODg5NjcsImV4cCI6MTcwOTg3NTM2N30.36i_z2na0Q2GDSjfMEuLuwSJPYzeQx6AGWXo6OyY_7E');
