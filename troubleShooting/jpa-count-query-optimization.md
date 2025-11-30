
# 게시글 카운트 조회 & 좋아요 카운트 저장 방식 개선

---

# 1. 문제 상황

## ◻️ A. 게시글 카운트 조회(API 3개 호출 문제)

게시글의 **좋아요 수 / 댓글 수 / 조회수**를 조회하기 위해
아래처럼 Repository에서 **각각 별도의 쿼리**를 호출하고 있었다.

### Repository (기존)

```java
@Query("SELECT p.likeCount FROM Post p WHERE p.id = :postId")
int findLikeCountByPostId(@Param("postId") long postId);

@Query("SELECT p.commentCount FROM Post p WHERE p.id = :postId")
int findCommentCountByPostId(@Param("postId") long postId);

@Query("SELECT p.viewCount FROM Post p WHERE p.id = :postId")
int findViewCountByPostId(@Param("postId") long postId);
```

### Service (기존)

```java
public PostCountDto getLikeCount(long postId) {
    int likeCount = postRepository.findLikeCountByPostId(postId);
    int commentCount = postRepository.findCommentCountByPostId(postId);
    int viewCount = postRepository.findViewCountByPostId(postId);

    return new PostCountDto(commentCount, likeCount, viewCount);
}
```

## 문제 원인

* 메서드가 3개이므로 **쿼리가 3번 실행됨**
* 쿼리 1회 = DB I/O 1회
  → 성능적으로 불리한 구조

---

# 2. 개선 방향 — **DTO Projection으로 쿼리 1회로 통합**

### Repository (개선)

```java
@Query("SELECT new com.ran.community.post.dto.response.PostCountDto(p.viewCount, p.commentCount, p.likeCount) 
        FROM Post p WHERE p.id = :postId")
PostCountDto findCountByPostId(@Param("postId") long postId);
```

### ✔ 개선 효과

* 쿼리 실행 **1회**로 축소
* DB I/O 감소 → 성능 안정성 증가
* 필요한 필드만 선택해서 메모리 낭비 없음
* 애플리케이션 레벨의 코드도 단순해짐

---

# 3. 문제 상황

## ◻️ B. 좋아요 카운트 처리 방식 — 매번 count() 호출 문제

기존 좋아요 저장 로직은 다음과 같았다.

### 좋아요 눌렀을 때 로직

```java
if(!exist){
    likeRepository.save(new PostLike(user, post)); // 좋아요 행 생성
    postService.saveLikeCount(postId);             // 좋아요 갯수 재계산
} else {
    deleteByLike(postId, userId);
}
```

### 카운트 저장 로직

```java
public void saveLikeCount(long postId){
    Post post = findByPostId(postId);
    post.updatePostLike(countLike(postId));
}

public int countLike(long postId){
    return likeRepository.countByPost_Id(postId);
}
```

## 문제 원인

좋아요 행이 생성될 때마다

→ **like 테이블 전체를 count()**
→ countByPost_Id(postId)가 **항상 전체 개수를 세는 쿼리 실행**

### 이는 다음 문제를 유발함

* 좋아요 수가 커질수록 COUNT 비용 증가
* 무조건 DB에서 전체 count() 쿼리를 실행
* 좋아요 토글할 때마다 불필요한 오버헤드 발생

---

# 4. 해결 전략 — “저장 시 +1 / 삭제 시 -1” 방식으로 전환

좋아요 갯수를 **항시 다시 count하지 않고**,
이벤트 발생 시 점진적으로 증가/감소시키는 방식으로 변경.

> **정답은 1번: 저장할 때 count 필드 값 +1 하기**

### 개선된 로직

* 좋아요 누를 때: likeCount++
* 좋아요 취소할 때: likeCount--
* count() 쿼리 필요 없음
* JPA가 Dirty Checking으로 필드 값 자동 갱신

### 💡 개선 이유

* count()는 **전체 행을 스캔하는 연산** → 비용이 큼
* +1 / -1 업데이트는 **단일 UPDATE** → 훨씬 저렴
* 단일 필드 변경은 EM의 Dirty Checking이 안정적으로 처리
* DB 부하를 획기적으로 줄일 수 있음

---

# 5. 종합 결론

| 항목         | 기존 방식 문제           | 개선 효과              |
| ---------- | ------------------ | ------------------ |
| 게시글 카운트 조회 | 쿼리 3회 실행 → I/O 과다  | Projection으로 쿼리 1회 |
| 좋아요 카운트 저장 | count()로 테이블 전체 스캔 | +1 / -1 방식으로 즉시 계산 |
| 성능         | 전체 I/O 증가          | 최소의 I/O로 안정적인 처리   |
| JPA 연산     | 매번 쿼리 발생           | Dirty Checking 활용  |

