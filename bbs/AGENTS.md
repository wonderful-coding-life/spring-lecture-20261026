# AGENTS.md

## 1. 프로젝트 개요

Spring Boot 기반 웹 게시판 애플리케이션이다.

- 기술: Spring MVC, Spring Data JPA, Spring Security, Thymeleaf, MariaDB, H2, Gradle, Lombok, Tailwind CSS
- Java 및 라이브러리 버전은 기존 프로젝트 설정을 따른다.
- 구현할 기능과 요구사항은 `PRD.md`를 기준으로 한다.

## 2. 작업 원칙

- 작업 전에 기존 코드와 프로젝트 구조를 확인한다.
- 요청과 관련된 파일만 생성하거나 수정한다.
- 기존 기능과 코드의 명명 규칙을 유지한다.
- 이해하기 쉽고 간결한 코드를 작성하며 불필요한 추상화를 피한다.
- 클래스, 메서드, 변수는 역할이 드러나는 이름을 사용한다.
- 들여쓰기는 공백 4칸을 사용하고, 긴 문장은 적절히 줄바꿈한다.
- 주석은 필요한 경우에만 작성한다.

## 3. 패키지 및 계층 구조

기본 패키지 아래에 다음 패키지를 사용한다.

- `entity`: JPA Entity
- `repository`: 데이터 접근
- `service`: 비즈니스 로직, 권한 검사, 트랜잭션
- `controller`: HTTP 요청 처리 및 화면 이동
- `form`: 사용자 입력 및 Validation
- `config`: 애플리케이션 설정

**Controller → Service → Repository** 구조를 유지한다.

- Controller에서 Repository를 직접 호출하지 않는다.
- 의존성 주입은 생성자 주입을 사용한다.
- 사용자 입력은 Entity 대신 Form 객체로 받는다.
- 입력값 검증은 `@Valid`와 `BindingResult`를 사용한다.
- 게시글 작성자 권한 검사는 Service에서 처리한다.
- 비밀번호는 BCrypt로 암호화한다.

## 4. 화면 구현

- Thymeleaf 템플릿은 `templates`, 정적 리소스는 `static` 폴더에 작성한다.
- CSS는 Tailwind Play CDN을 사용하고 별도의 프런트엔드 빌드 과정은 추가하지 않는다.
- 화면은 반응형으로 작성하고 공통 화면은 가능한 한 재사용한다.

## 5. 구현 단계 관리

프로젝트 루트의 `progress.json`으로 진행 상태를 관리한다.

- 단계별 작업을 시작하기 전에 현재 상태를 확인한다.
- 단계 시작 시 `status`를 `"in_progress"`로 변경한다.
- 개발자가 코드 리뷰 후 승인하면 status를 "done"으로 변경한다.
- 상태 값은 `"pending"`, `"in_progress"`, `"done"`만 사용한다.

## 6. 작업 완료 보고

각 단계의 작업이 끝나면 다음 내용을 간단히 보고한다.

- 생성하거나 수정한 파일
- 구현한 주요 기능
- 코드 리뷰할 핵심 클래스와 메서드
- 남아 있는 문제 또는 확인 사항