# API 명세서
### api/users/signup [POST] -> 회원가입
- email: @ 포함
- password: 8자 이상, 암호화

### api/users/login [POST] -> 로그인
- email, password 이용, 유효성 검사
- JWT 토큰 생성

### api/articles [POST] -> 게시글 생성
- title: 게시글 제목
- content: 게시글 본문

### api/articles [GET] -> 게시글 조회
- limit: 사이즈
- offset: 페이지

### api/articles/{articleId} [GET] -> 특정 게시글 조회
- 게시글 ID 입력

### api/articles/{articleId} [PATCH] -> 특정 게시글 수정
- 게시글 ID 입력
- 작성자만 수정 가능

### api/articles/{articleId} [DELETE] -> 특정 게시글 삭제
- 게시글 ID 입력
- 작성자만 삭제 가능

# 데이터베이스 관계도
![데이터베이스_관계도](https://github.com/pushrsp/wanted-pre-onboarding-backend/assets/58874665/f02ad30e-5bf5-40e3-85a5-0d29e0a3d596)
