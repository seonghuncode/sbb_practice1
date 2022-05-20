#sbb르포젝트 혼자 2장 부터 연습 하기 위한 디비

# DB 재생성
DROP DATABASE IF EXISTS sbb_practice1;
CREATE DATABASE sbb_practice1;
USE sbb_practice1;

# 질문 테이블 생성
CREATE TABLE question(
id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`subject` VARCHAR(200) NOT NULL,
content TEXT NOT NULL,
create_date DATETIME NOT NULL
);

# 답변 테이블 생성
CREATE TABLE answer(
id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
create_Date DATETIME NOT NULL,
question_id BIGINT UNSIGNED NOT NULL,
content TEXT NOT NULL
);


# 테스트용 질문 2개 생성
INSERT INTO question
SET create_date = NOW(),
`subject` = 'sbb가 무엇인가요?',
content = 'sbb에 대해서 알고 싶습니다.';

INSERT INTO question
SET create_date = NOW(),
`subject` = '스프링부트 모델 질문입니다.',
content = 'id는 자동으로 생성되나요?';


SELECT * FROM question;

SELECT * FROM answer;


# 질문 테이블에 site_user_id 칼럼 추가
ALTER TABLE question
ADD COLUMN author_id BIGINT UNSIGNED NOT NULL;

# 기존 질문을 특정 사용자와 연결짓기
UPDATE question
SET author_id = 1;

# 답변 테이블에 site_user_id 칼럼 추가
ALTER TABLE answer
ADD COLUMN author_id BIGINT UNSIGNED NOT NULL;


# 비밀번호 : sbs1234 => bcrypt 적용
# 테스트용 사용자 3명 생성(1명은 관리자)
INSERT INTO site_user
SET username = 'admin',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'admin@test.com';
INSERT INTO site_user
SET username = 'user1',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'user1@test.com';
INSERT INTO site_user
SET username = 'user2',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'user2@test.com';
email = 'user2@test.com';
