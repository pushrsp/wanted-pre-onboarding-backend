# 지원자 성명
강민석

# 애플리케이션 실행 방법
## 배포
- ./gradlew build
- java -jar -Dspring.profiles.active=prod ./build/libs/preonboarding-0.0.1-SNAPSHOT.jar

## 로컬
- ./gradlew build
- java -jar -Dspring.profiles.active=local ./build/libs/preonboarding-0.0.1-SNAPSHOT.jar

## 테스트
- ./gradlew test --tests=com.wanted.preonboarding.*

# 데이터베이스 테이블 구조
![데이터베이스_관계도](https://github.com/pushrsp/wanted-pre-onboarding-backend/assets/58874665/f02ad30e-5bf5-40e3-85a5-0d29e0a3d596)

# AWS 구조
![AWS 구조](https://github.com/pushrsp/wanted-pre-onboarding-backend/assets/58874665/8e3377ee-0aac-4321-8a0b-c7bca993efff)

# 데모 영상 링크
TODO

# 구현 방법 및 이유에 대한 간략한 설명
도메인과 엔티티를 구분하여 작성했습니다. 그 엔티티는 데이터베이스 테이블과 1:1 매핑되는 클래스이고,
데이터베이스가 바뀔 시 수정해야 될 부분이 많아지기 때문입니다. 그래서 레파지토리 계층에서 데이터베이스로부터 Entity를 받아와서
서비스 계층으로 넘길 때 항상 도메인으로 컨버팅을 해주었습니다.

테이블 설계같은 경우 대부분의 커뮤니티가 상세 포스트로 넘어가지 않은 이상 게시글의 제목, 날짜, 작성자만 보여주는 커뮤니티를 많이 보았습니다.
그리고 본문 자체는 데이터가 굉장히 크기 때문에 하나의 테이블에서 관리를 하게 되면 해당 데이터를 메모리에 올리기 때문에 메모리 사용량이 높아질 수 있더
CONTENT 테이블을 따로 만들어 ARTICLE 테이블과 1:1 매핑을 해주었습니다.

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
