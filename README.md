# Korea Fashion Information Sharing
### 패션 정보 공유 사이트

**데모사이트** : http://3.36.139.227:8080/<br>
**계정** : ID admin@admin.com / PW adminadmin

## 1. 프로젝트 요약
```
 - 프로젝트 명 : K-Fashion(패션 정보 공유 사이트)
 - 개발 기간 : 2020/11/18 ~ 2021/2/10
 - 프로젝트 인원 : 1명
 - 프로젝트 소개 : Spring Framework를 이용한 패션 정보 공유 사이트 개발
 - 개발 목표 : Spring Framework를 중심으로 여러가지 기술을 활용하여 
              전체적인 웹서비스 개발을 경험해보고 현재 수준을 파악해서 미래의 학습 방향에 대한 청사진을 제시
 - 참고 사이트 : https://eomisae.co.kr/
```

## 2. 개발 환경
```
 - Language : Java 11
 - O/S : Mac OS 10
 - DB : PostgreSQL, H2 Database
 - IDE : IntelliJ
 - FontEnd : HTML, CSS, Javascript, Jquery, Bootstrap, Font-Awesome
 - BackEnd : Tomcat, AWS EC2, RDS
 - Template Engine : Thymeleaf
 - Build Tool : Maven
 - Framework : Spring Framework
     - Spring Boot
     - Spring Security
     - Spring MVC
     - Spring Data JPA
     - Mail
 - 기타 툴 : Spring Boot devtools, Lombok, Modelmapper
```
## 3. 주요 기능
```
 - Spring Security를 이용한 회원 가입과 인증
 - Google SMTP를 이용한 회원 가입 인증 이메일 전송 기능
 - 텍스트와 이미지 파일로 게시글 작성 기능
 - 댓글과 대댓글 작성 기능
 - 키워드를 이용한 게시글 검색 기능
 - 회원 정보 확인 기능
 - 회원 정보 변경(닉네임, 패스워드) 기능
 - 회원 탈퇴(탈퇴 시 게시글 및 댓글도 자동 삭제) 기능
 - 회원 본인이 작성한 게시글과 댓글 목록 열람 기능
 - 이메일 미인증시 회원 가입 날짜기준 2주 뒤 회원 정보 자동 삭제 기능
```
## 4. 프로젝트 중 어려웠던 점
```
 - 멘토의 부재로 인해 문제가 발생시 해결하는 시간이 오래 걸렸고, 문제에 대한 해결책이 맞는지 확신이 없었습니다.
 - 개발 프로세스 경험 부족으로 각 기능 개발 순서가 뒤섞여 혼잡했습니다.
 - 기능 구현에만 집중하다보니 git 커밋을 잊을 때가 많았고, 커밋 단위를 나누는 것이 어려웠습니다.
 - 코드 작성 경험 부족으로 코드 컨벤션을 지키며 코드를 작성하는 것이 어려웠습니다.
 - Javascript의 실력 부족으로 스크립트를 작성하는데 많은 시간이 소요되었습니다.
 - QA 인원의 부재로 버그 발견이 힘들어 예외 처리가 허술하게 되었습니다.
```
## 5. 프로젝트를 개선 해야할 점
```
 - 테스트 코드 학습을 통해서 단위테스트로 프로젝트의 완성도를 높이고 싶습니다.  
 - UI 직관성을 높여 사용자가 보기 편하도록 개선하고 싶습니다.
 - 전체적인 코드를 리팩토링하여 모듈화하도록 개선하고 싶습니다.
```
## 6. 앞으로 추가하고 싶은 기능
```
 - 게시글과 댓글에 대한 알람 기능
 - 다른 회원 정보 열람 기능
 - 다른 회원의 게시글 및 댓글에 대한 알람설정 기능
 - 관리자 모드 
 - 소셜 로그인
 - 회원 전체 공지
```
