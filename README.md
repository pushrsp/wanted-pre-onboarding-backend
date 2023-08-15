# 지원자 성명
강민석

# 애플리케이션 실행 방법
## 배포
- ./gradlew build
- java -jar -Dspring.profiles.active=prod ./build/libs/preonboarding-0.0.1-SNAPSHOT.jar

(application-prod.yaml 파일 만들어야 됨)

## 로컬
- ./gradlew build
- java -jar -Dspring.profiles.active=local ./build/libs/preonboarding-0.0.1-SNAPSHOT.jar

## 테스트
- ./gradlew test --tests=com.wanted.preonboarding.*

# 데이터베이스 테이블 구조
![데이터베이스_관계도](https://github.com/pushrsp/wanted-pre-onboarding-backend/assets/58874665/f02ad30e-5bf5-40e3-85a5-0d29e0a3d596)

# AWS 구조
![AWS 구조](https://github.com/pushrsp/wanted-pre-onboarding-backend/assets/58874665/b41d13f6-3629-4233-b1f6-679b64217454)

- 회원가입 http://118.67.131.243:8080/api/members/signup [POST]
- 로그인 http://118.67.131.243:8080/api/members/login [POST]
- 게시글 생성 http://118.67.131.243:8080/api/articles [POST]
- 게시글 조회 http://118.67.131.243:8080/api/articles?page={{page}}&size={{size}} [GET]
- 게시글 수정 http://118.67.131.243:8080/api/articles/{{articleId}} [PATCH]
- 게시글 삭제 http://118.67.131.243:8080/api/articles/{{articleId}} [DELETE]
- 게시글 세부 조회 http://118.67.131.243:8080/api/articles/{{articleId}} [GET]


# 데모 영상 링크
[![데모 영상](http://img.youtube.com/vi/1BKwWndzg8I/0.jpg)](https://youtu.be/1BKwWndzg8I)

# 구현 방법 및 이유에 대한 간략한 설명
도메인과 엔티티를 구분하여 작성했습니다. 그 엔티티는 데이터베이스 테이블과 1:1 매핑되는 클래스이고,
데이터베이스가 바뀔 시 수정해야 될 부분이 많아지기 때문입니다. 그래서 레파지토리 계층에서 데이터베이스로부터 Entity를 받아와서
서비스 계층으로 넘길 때 항상 도메인으로 컨버팅을 해주었습니다.

테이블 설계같은 경우 대부분의 커뮤니티가 상세 포스트로 넘어가지 않은 이상 게시글의 제목, 날짜, 작성자만 보여주는 커뮤니티를 많이 보았습니다.
그리고 본문 자체는 데이터가 굉장히 크기 때문에 하나의 테이블에서 관리를 하게 되면 해당 데이터를 메모리에 올리기 때문에 메모리 사용량이 높아질 수 있더
CONTENT 테이블을 따로 만들어 ARTICLE 테이블과 1:1 매핑을 해주었습니다.

# API 명세서
### api/users/signup [POST] -> 회원가입
request body
```
email: string
password: string
```

response
```
data: string
success: boolean
```

### api/users/login [POST] -> 로그인
request body
```
email: string
password: string
```

response
```
data: string
success: boolean
```

### api/articles [POST] -> 게시글 생성
request header
```
Authorization: Bearer {{token}}
```

request body
```
title: string
content: string
```

response
```
data: {
    articleId: string
}
success: boolean
```

### api/articles [GET] -> 게시글 조회
request params
```
page: int (optional)
size: int (optional)
```

response
```
data: {
    hasNext: boolean
    articles: [
        {
            articleId: string
            title: string
            writerId: string
            wrtierEmail: string
            createdTime: date
            modifiedTime: date
        },
        ...
    ]
} 
success: boolean
```

### api/articles/{articleId} [GET] -> 특정 게시글 조회
response
```
data: {
    contentId: string
    content: string
}
success: boolean
```

### api/articles/{articleId} [PATCH] -> 특정 게시글 수정
request header
```
Authorization: Bearer {{token}}
```

request body
```
title: string (optional)
content: string (optional)
```

response
```
data: {
    articleId: string
}
success: boolean
```

### api/articles/{articleId} [DELETE] -> 특정 게시글 삭제
request header
```
Authorization: Bearer {{token}}
```

response
```
data: {
    articleId: string
}
success: boolean
```
