# Today Seafood Backend 포트폴리오 초안

## 1) 프로젝트 개요
- 프로젝트명: 오늘의 수산 (Today Seafood)
- 기간: 2025.09 ~ 진행 중
- 형태: 1인 개발(백엔드 중심)
- 목표: 수산물 시세 정보와 커뮤니티 기능을 결합한 서비스 백엔드 구축

## 2) 담당 범위
- 멀티모듈 백엔드 아키텍처 설계 및 구현 (`api-service`, `gateway-service`)
- 인증/인가(JWT + Refresh Token + 권한 기반 접근제어) 구현
- 사용자/게시글/댓글/좋아요 도메인 API 설계 및 개발
- 예외 처리 표준화, 로깅/SQL 관측성, 테스트 자동화 환경 구성

## 3) 기술 스택
- Java 21, Spring Boot 3.5.x
- Spring Security, Spring Data JPA, Validation, AOP
- MySQL, Spring Cloud Gateway(WebFlux)
- Swagger(OpenAPI), P6Spy, JUnit5, Mockito, JaCoCo

## 4) 아키텍처 설계 역량
- API 서버와 Gateway 서버를 분리해 라우팅/보안 경계를 분리했습니다.
- Gateway에서 CORS를 중앙 처리하고 API 서버는 도메인 로직에 집중하도록 구성했습니다.
- `@ConfigurationProperties`로 토큰/쿠키/CORS 설정을 외부화해 운영환경 전환 비용을 낮췄습니다.

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/ApiApplication.java`
- `gateway-service/src/main/java/com/todayseafood/gateway/GatewayApplication.java`
- `gateway-service/src/main/java/com/todayseafood/gateway/GatewayCorsConfig.java`
- `api-service/src/main/java/com/todayseafood/api/auth/service/property/TokenProperty.java`

## 5) 기술적 역량 (핵심)
### 인증/인가
- Stateless SecurityFilterChain + JWT 필터 직접 구성
- AccessToken(헤더) + RefreshToken(HttpOnly Secure Cookie) 이중 전략 적용
- Role + Action(Authority) 분리 모델로 세밀한 권한 제어 적용
- Refresh Token 회전(Rotation) 검증으로 재사용 토큰 차단 처리

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/security/config/SecurityConfig.java`
- `api-service/src/main/java/com/todayseafood/api/security/filter/JwtAuthenticationFilter.java`
- `api-service/src/main/java/com/todayseafood/api/auth/service/TokenService.java`
- `api-service/src/main/java/com/todayseafood/api/security/role/RoleConfig.java`

### 도메인 모델링
- 게시글 좋아요를 복합키(`user_id + post_id`)로 모델링해 중복 좋아요를 DB 레벨에서 방지
- 게시글/댓글/유저 관계를 분리하고 연관 엔티티 생명주기를 cascade로 명확히 정의
- 서비스 레이어에서 `@PreAuthorize`를 사용해 “리소스 소유자 기반 접근 제어” 구현

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/post/entity/PostLikeId.java`
- `api-service/src/main/java/com/todayseafood/api/post/entity/PostLike.java`
- `api-service/src/main/java/com/todayseafood/api/user/service/UserService.java`
- `api-service/src/main/java/com/todayseafood/api/post/service/PostService.java`

### 운영 안정성
- 비즈니스 예외를 ErrorCode로 표준화해 일관된 오류 응답 제공
- `@RestControllerAdvice` 기반 글로벌 예외 처리로 컨트롤러 중복 코드를 제거
- 공통 응답 포맷(`BaseResponse`)으로 API 응답 계약을 통일

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/app/exception/handler/ErrorCode.java`
- `api-service/src/main/java/com/todayseafood/api/app/exception/handler/GlobalExceptionHandler.java`
- `api-service/src/main/java/com/todayseafood/api/app/response/BaseResponse.java`

## 6) 성능 개선 포인트 (코드 기반 적용)
### 1. N+1 조회 최소화
- 게시글/댓글 조회 시 `@EntityGraph(attributePaths = {"author"})`를 적용해 연관 작성자 조회를 최적화했습니다.
- 리스트 조회 시 연관 엔티티 접근으로 발생하는 추가 쿼리를 줄이는 구조로 설계했습니다.

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/post/repository/PostRepository.java`
- `api-service/src/main/java/com/todayseafood/api/post/repository/CommentRepository.java`

### 2. 조회수 증가 연산의 DB 원자성 확보
- 조회수 증가를 `UPDATE ... SET view_count = view_count + 1` 단일 쿼리로 처리해 동시성 환경에서 Lost Update 위험을 줄였습니다.

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/post/repository/PostRepository.java`

### 3. 페이징 기반 목록 조회
- 게시글/댓글 목록을 Page 단위로 조회하고 `totalPages`, `hasNext`를 응답에 포함해 무한 스크롤에 맞는 API 계약을 제공했습니다.
- 대량 데이터 상황에서 한 번에 전체 로딩하지 않도록 설계했습니다.

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/post/service/PostService.java`
- `api-service/src/main/java/com/todayseafood/api/app/util/paginationPolicy/PostPaginationPolicy.java`
- `api-service/src/main/java/com/todayseafood/api/app/util/paginationPolicy/CommentPaginationPolicy.java`

### 4. 성능 관측성 강화
- P6Spy 커스텀 포맷터로 SQL 실행시간을 서비스 메서드 컨텍스트와 함께 기록
- AOP `@Loggable`로 주요 서비스 실행시간(ms) 로깅

근거 코드:
- `api-service/src/main/java/com/todayseafood/api/app/p6spy/P6spyPrettySqlFormatter.java`
- `api-service/src/main/java/com/todayseafood/api/app/aop/aspect/log/LoggingAspect.java`

## 7) 테스트/품질 지표
- 프로덕션 코드: `api-service` Java 132개 파일 (약 4,397 LOC), `gateway-service` 61 LOC
- 테스트 코드: 8개 파일, `@Test` 58개, `@Nested` 24개
- 최근 실행 결과: 49 tests completed, 47 passed / 2 failed
  - 실패 원인: 테스트 패키지(`com.todatseafood...`)와 실제 애플리케이션 패키지(`com.todayseafood...`) 불일치로 인한 `@SpringBootConfiguration` 탐색 실패

근거 경로:
- `api-service/src/test/java/com/todatseafood/api/Ktb10WeekApplicationTests.java`
- `api-service/build/test-results/test/TEST-com.todatseafood.api.Ktb10WeekApplicationTests.xml`
- `api-service/build/test-results/test/TEST-com.todatseafood.api.user.repository.user.UserRepositoryTest.xml`

## 8) 포트폴리오용 핵심 문장 (짧은 버전)
Spring Boot 멀티모듈 구조(API/Gateway) 기반으로 커뮤니티 백엔드를 직접 설계/구현했습니다. JWT + Refresh Token 회전, Role/Authority 분리 인가, 글로벌 예외 표준화, 페이징 기반 API를 구축했고, `@EntityGraph`, 원자적 업데이트 쿼리, P6Spy/AOP 측정으로 성능 최적화 기반을 마련했습니다.

## 9) 면접 답변용 성능 개선 스토리 (STAR)
- 상황(S): 게시글/댓글 목록 조회에서 작성자 정보 접근 시 지연 로딩으로 쿼리 증가 가능성이 있었습니다.
- 과제(T): 무한 스크롤 요청에서 응답 지연을 줄이고 조회 안정성을 높여야 했습니다.
- 행동(A): Repository에 `@EntityGraph`를 도입하고, 조회수 증가는 단일 `UPDATE` 쿼리로 변경했으며, Page 기반 API 계약으로 대량 조회를 제어했습니다.
- 결과(R): 구조적으로 N+1 위험과 동시성 업데이트 리스크를 줄였고, SQL/AOP 측정 로그를 통해 병목 추적이 가능한 운영 형태를 갖췄습니다.

## 10) 기능별 문제 해결 사례 (코드 포함)
### 사례 A. 토큰 재발급(Refresh) 보안 강화
- 기능: 로그인 이후 Access Token 재발급
- 문제: 탈취된 과거 Refresh Token 재사용(Replay) 요청을 막아야 했음
- 해결: DB에 저장된 최신 Refresh Token과 요청 토큰을 비교하고, 일치하지 않으면 차단. 재발급 성공 시 즉시 토큰 회전
- 효과: 재사용 토큰 차단으로 세션 하이재킹 리스크 축소

```java
// api-service/src/main/java/com/todayseafood/api/auth/service/TokenService.java
RefreshToken saved = refreshTokenRepository.findByUserId(userId)
        .orElseThrow(RefreshTokenNotFoundException::new);
if (!saved.getToken().equals(refreshToken)) {
    throw new AlreadyRotatedTokenException();
}

String newAccessToken = issueAccessToken(userId, roleConfig);
String newRefreshToken = issueRefreshToken(userId, roleConfig);
saved.updateToken(newRefreshToken);
```

### 사례 B. 게시글 목록 조회 성능 개선 (N+1 완화)
- 기능: 게시글/댓글 목록 조회 API
- 문제: 목록 조회 후 작성자(author) 접근 시 지연 로딩으로 쿼리 수가 급증할 수 있음
- 해결: Repository 조회 메서드에 `@EntityGraph` 적용, 작성자 연관 데이터를 함께 조회
- 효과: 무한 스크롤 시 불필요한 추가 쿼리 감소, 응답 지연 완화

```java
// api-service/src/main/java/com/todayseafood/api/post/repository/PostRepository.java
@EntityGraph(attributePaths = {"author"})
Page<Post> findAll(Pageable pageable);

// api-service/src/main/java/com/todayseafood/api/post/repository/CommentRepository.java
@EntityGraph(attributePaths = "author")
Optional<Comment> findById(Long commentId);
```

### 사례 C. 조회수 증가 동시성 이슈 대응
- 기능: 게시글 조회수 증가
- 문제: 엔티티 조회 후 `+1` 저장 방식은 동시 요청에서 Lost Update가 발생할 수 있음
- 해결: DB 단일 업데이트 쿼리로 원자적 증가 처리
- 효과: 동시성 환경에서 조회수 정합성 향상

```java
// api-service/src/main/java/com/todayseafood/api/post/repository/PostRepository.java
@Modifying(flushAutomatically = true, clearAutomatically = true)
@Query("UPDATE Post p SET p.view_count = p.view_count + 1 WHERE p.id = :postId")
void increaseViewCount(@Param("postId") Long postId);
```

### 사례 D. 리소스 소유자 기반 인가 누락 방지
- 기능: 내 프로필/내 게시글 수정 및 삭제
- 문제: URL 기반 인증만으로는 타 사용자 리소스 변경 시도를 세밀하게 막기 어려움
- 해결: 서비스 레이어에 `@PreAuthorize` 적용, `principal.userId`와 Path 변수 매칭 검증
- 효과: 엔드포인트 단위가 아닌 도메인 행위 단위 권한 제어 확보

```java
// api-service/src/main/java/com/todayseafood/api/user/service/UserService.java
@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
public BaseResponse<FindUserResponseDto> findById(@P("userId") Long userId) { ... }

@PreAuthorize("#userId == principal.userId")
public BaseResponse<EditProfileResponseDto> editProfile(@P("userId") Long userId, EditProfileRequestDto req) { ... }
```

### 사례 E. 예외 응답 포맷 불일치 해소
- 기능: 전체 API 에러 응답
- 문제: 기능별 예외 처리 시 응답 형식이 달라지면 프론트 에러 핸들링 복잡도 증가
- 해결: `BusinessException + ErrorCode + RestControllerAdvice` 패턴으로 표준화
- 효과: 클라이언트가 상태코드/메시지/경로를 일관된 형태로 처리 가능

```java
// api-service/src/main/java/com/todayseafood/api/app/exception/handler/GlobalExceptionHandler.java
@ExceptionHandler(BusinessException.class)
public ResponseEntity<ErrorResponseEntity> handleBusinessException(BusinessException e, HttpServletRequest req) {
    return ErrorResponseEntity.toResponseEntity(e.getErrorCode(), req.getRequestURI());
}
```

### 사례 F. 테스트 컨텍스트 로딩 실패 이슈 (개선 진행 항목)
- 기능: 통합 테스트/리포지토리 테스트
- 문제: 테스트 패키지명 오타(`com.todatseafood`)로 `@SpringBootConfiguration` 탐색 실패
- 해결 방식: 테스트 패키지를 애플리케이션 루트와 정렬하거나, 테스트 클래스에 구성 클래스를 명시
- 기대 효과: CI에서 테스트 안정성 확보, 회귀 검증 신뢰도 향상

```java
// api-service/src/test/java/com/todatseafood/api/Ktb10WeekApplicationTests.java (현재)
@SpringBootTest
class Ktb10WeekApplicationTests { ... }

// 개선 예시
@SpringBootTest(classes = com.todayseafood.api.ApiApplication.class)
class Ktb10WeekApplicationTests { ... }
```
