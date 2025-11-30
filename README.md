# 📘 ONE_LINE

**Spring Boot 3 + JWT 인증 기반 커뮤니티 REST API 서버**

Vanilla JavaScript로 만든 SPA 프론트엔드와 통신하며
게시글, 댓글, 좋아요, 사용자 인증을 책임지는 백엔드입니다.

React/Vue 없이 구성된 FE가 안정적으로 동작하도록
**API 응답 규약 · 토큰 플로우 · 카운트 캐싱 로직을 설계했습니다.**

---

# 1. 🚀 Project Overview

본 프로젝트는 **Spring Boot 3.5 / Java 21 기반의 REST API 서버**로,
Controller → Service → Repository의 계층을 명확히 분리하여 유지보수성과 테스트 용이성을 강화했습니다.

- JPA 엔티티(User/Post/Comment/PostLike)는 공통 `AuditingEntity`를 확장하여
  생성/수정 시점을 자동 기록합니다.
- 게시글 통계(view/like/comment count)는 엔티티 컬럼에 **즉시 캐싱**하고
  FE는 `PostCountDto` 등으로 **한 번에 정보를 조회**하도록 설계했습니다.
- 모든 응답은 `ApiResponse` 래퍼로 감싸
  `{status, code, message, data}` 포맷을 강제합니다.
- 인증은 **JWT 기반**이며, 로그인/토큰 재발급/보호된 경로 관리를 분리해
  완전한 Stateless 환경을 구성했습니다.

### Intro

- FE가 자체 구현한 SPA Router를 사용하므로, 서버는 **순수 JSON API + 명확한 message code**를 제공합니다.
- Repository(Interface)와 구현체(JPA / InMemory)를 분리해
  테스트 환경/부하 테스트에서 저장소를 쉽게 교체할 수 있습니다.

---

# 2. 🛠 Tech Stack

### 🔶 Language & Runtime

- Java 21 (Gradle Toolchain)
- Gradle 8.x
- Lombok 사용 (보일러플레이트 최소화)

---

### 🔶 Application Modules

- `spring-boot-starter-web` — REST 컨트롤러
- `spring-boot-starter-validation` — DTO 검증
- `spring-boot-starter-data-jpa` — 영속성 계층
- `springdoc-openapi-starter-webmvc-ui` — Swagger UI 자동 문서화
- Thymeleaf — 에러 템플릿/관리 콘솔 테스트 용도

---

### 🔶 Security & Auth

- `spring-boot-starter-security`
- `jwt-api/jwt-impl/jwt-jackson`
- BCryptPasswordEncoder
- AuthenticationManager + CustomUserDetailsService
- Stateless JWT 인증

---

### 🔶 Persistence & Infra

- MySQL 8 (InnoDB)
- `hibernate.default_batch_fetch_size=50`
- `@BatchSize`로 N+1 일부 완화
- Auditing 자동 기록

---

# 3. 시스템 아키텍처 (Core Architecture)

본 백엔드는 **Stateless JWT 인증 + 계층형 구조**를 기준으로 설계되었습니다.

---

## 3-1. 요청 파이프라인 (Request Pipeline)

```
Client
→ CorsFilter
→ JwtFilter
→ SecurityFilterChain
→ Controller
→ Service
→ Repository
→ MySQL
```

### 주요 구성

- **CorsFilter**: FE 오리진 허용, Preflight OPTIONS 전체 허용
- **JwtFilter**: Bearer 토큰 추출 → 검증 → `SecurityContext` 주입
- **Controller**: 인증 정보(email)를 받아 서비스 호출
- **Service**: 트랜잭션에서 엔티티 수정 및 DTO 투영
- **Repository**: fetch join, batch fetch 전략

---

## 3-2. 도메인 모델 & JPA 전략

| Entity       | 역할                                                          |
| ------------ | ------------------------------------------------------------- |
| **User**     | 이메일·닉네임·암호·refreshToken 저장. Post/Comment와 연관관계 |
| **Post**     | 본문·이미지·카운트 캐싱(view/like/comment), User와 N:1        |
| **Comment**  | 내용·작성자·게시글, 수정 시간 Auditing                        |
| **PostLike** | User–Post UniqueConstraint로 중복 좋아요 방지                 |

### 주요 전략

- 모든 엔티티는 `AuditingEntity` 확장
- 성능 최적화:

  - fetch join
  - `default_batch_fetch_size=50`
  - Projection(DTO) 기반 조회 (`PostCountDto`, `ViewCountDto`, …)

---

## 3-3. 인증 / 인가 & JWT 플로우

1. 로그인 → AuthenticationManager가 인증 → TokenProvider가 Access/Refresh 토큰 생성
2. Refresh Token은 DB(User.refreshToken)에 저장
3. `/users/refresh` 요청 시 입력 Refresh Token과 DB 저장본을 비교
4. 인증이 필요한 모든 API는 STATELESS 정책으로 세션 미사용
5. 인증 실패는 JSON 형태의 에러 메시지로 응답

---

## 3-4. 공통 인프라 & 응답 규약

- 모든 응답은 `ApiResponse.success/created/error`로
  `{status, code, message, data}` 구조 통일
- `GlobalExceptionHandler`가 예외를 HTTP 기준에 맞춰 처리
- 요청/응답 DTO를 분리하여 엔티티 외부 노출 방지
- FE는 `message` 코드만으로 UI 분기 가능

---

## 3-5. 패키지 구조 (Layered)

```
src/main/java/com/ran/community
|-- CommunityApplication.java
|-- global/            # ApiResponse, ExceptionHandler, IdGenerator, WebConfig
|-- security/          # JwtFilter, TokenProvider, SecurityConfig, UserDetailsService
|-- user/              # UserController, UserService, UserRepository, DTO, Entity(User)
|-- post/              # PostController, PostService, PostRepository, DTO, Entity(Post)
|-- comment/           # CommentController, CommentService, CommentRepository
|-- like/              # LikeService, LikeRepository, Entity(PostLike)
|-- global/entity      # AuditingEntity 등 공용 베이스
```

---

## 3-6. 주요 API 엔드포인트

| Domain   | Method      | Path                        | 설명                        | Auth |
| -------- | ----------- | --------------------------- | --------------------------- | ---- |
| Auth     | POST        | `/users/signup`             | 회원가입                    | ❌   |
| Auth     | POST        | `/users/login`              | 로그인                      | ❌   |
| Auth     | POST        | `/users/refresh`            | 토큰 재발급                 | ❌   |
| User     | GET         | `/users`                    | 내 정보 조회                | ✅   |
| User     | PATCH       | `/users/userInfo`           | 닉네임 수정                 | ✅   |
| User     | PATCH       | `/users/userPassword`       | 비밀번호 변경               | ✅   |
| User     | DELETE      | `/users`                    | 회원 탈퇴                   | ✅   |
| Posts    | GET         | `/posts`                    | 게시글 목록                 | ✅   |
| Posts    | GET         | `/posts/{id}`               | 게시글 상세 + 댓글 + 좋아요 | ✅   |
| Posts    | POST        | `/posts`                    | 게시글 생성                 | ✅   |
| Posts    | PATCH       | `/posts/{id}`               | 게시글 수정                 | ✅   |
| Posts    | DELETE      | `/posts/{id}`               | 게시글 삭제                 | ✅   |
| Comments | CRUD        | `/posts/{postId}/comments`  | 댓글 생성/조회/수정/삭제    | ✅   |
| Likes    | POST/DELETE | `/posts/{postId}/likes`     | 좋아요 토글                 | ✅   |
| Counts   | GET         | `/posts/{postId}/counts/**` | 통계 조회                   | ✅   |

---

# 4. ⭐ 주요 기능 (Features)

- **사용자 인증 전체 흐름**

  - 이메일/닉네임 중복 확인
  - BCrypt 암호화
  - 로그인 + 토큰 재발급 + 탈퇴 + 비밀번호 변경

- **게시글 CRUD + 통계**

  - 상세 조회 시 viewCount 자동 증가
  - 좋아요 여부 + 카운트 + 댓글 목록을 하나의 응답으로 전달

- **댓글 시스템**

  - `/posts/{postId}/comments` 하위 리소스로 CRUD 지원
  - 댓글 생성/삭제 시 `post.commentCount` 자동 증가/감소

- **좋아요 토글 시스템**

  - UniqueConstraint로 중복 좋아요 차단
  - `LikeStateDto(liked, count)`으로 FE 전역 상태와 즉시 동기화

- **에러/응답 일관성**

  - `ApiResponse` 응답 규약
  - `GlobalExceptionHandler`를 통한 공통 예외 처리

---

# 5. TroubleShooting
