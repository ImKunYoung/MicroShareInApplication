# Outer Architecture - 1 Release

![KakaoTalk_20230118_105744811.jpg](readmefile%2FKakaoTalk_20230118_105744811.jpg)


## 서비스 요구사항

<hr/>

#### 사용자 기능 요구사항

- 게시판 추가: 회원은 게시판을 추가할 수 있다.
- 게시글 추가: 회원은 게시판에 글을 생성할 수 있다.
- 회원정보 수정: 회원은 회원정보 (이메일, 닉네임, 아파트 단지 이름 수정)를 수정할 수 있다.
- 비밀번호 수정: 회원은 비밀번호 수정 또는 업데이트가 가능하다.
- 아파트 인증 데이터 DB 저장: 아파트를 인증해서 아파트 단지 데이터를 저장한다
- 검색기능: 게시판 글을 기반으로 검색을 했을 시 기능이 만들어진다. (DB조회)

> ➡️ 요구사항 불명확 -> 이벤트스토밍 진행 결정

<br/>

#### 비기능 요구사항

-
-
-

<br/>
<br/>


## 분석/설계

<hr/>

#### 이벤트스토밍 결과

<details>
<summary>회원 BC</summary>

![Blank diagram (3).png](https://raw.githubusercontent.com/shareIn-official-corp/EventStorming/main/imgfile/Blank%20diagram%20(3).png)

</details>


<details>
<summary>아파트 BC</summary>

![Blank diagram - Page 2 (8).png](https://raw.githubusercontent.com/shareIn-official-corp/EventStorming/main/imgfile/Blank%20diagram%20-%20Page%202%20(8).png)

</details>




<details>
<summary>게시판 BC</summary>

![shareIn (3).jpg](https://raw.githubusercontent.com/shareIn-official-corp/EventStorming/main/imgfile/shareIn%20(3).jpg)

</details>



<details>
<summary>전체</summary>

![shareIn (4).jpg](https://raw.githubusercontent.com/shareIn-official-corp/EventStorming/main/imgfile/shareIn%20(4).jpg)

</details>




<br/>

<details>
<summary>1차 릴리스 이후 추가</summary>

![shareIn (5).jpg](https://raw.githubusercontent.com/shareIn-official-corp/EventStorming/main/imgfile/shareIn%20(5).jpg)

</details>


<br/> 

#### 우선 순위 식별

![img.png](readmefile%2Fimg.png)

<br/>
<br/>


## 1차 릴리스 백로그


<details>
<summary>서버</summary>

<table>
  <tr>
   <td>BC
   </td>
   <td>커맨드
   </td>
   <td>액터
   </td>
   <td>정책
   </td>
   <td>써드파티 (클라이언트에서 처리)
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>회원가입
   </td>
   <td>비회원
   </td>
   <td>회원정보동의되면가입됨
   </td>
   <td>애플OAuth, 구글OAuth, 네이버OAuth, 카카오OAuth
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>회원정보변경
   </td>
   <td>회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>회원아파트정보변경
   </td>
   <td>회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>회원정보삭제
   </td>
   <td>회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>로그인
   </td>
   <td>비로그인회원
   </td>
   <td>
   </td>
   <td>애플OAuth, 구글OAuth, 네이버OAuth, 카카오OAuth
   </td>
  </tr>
  <tr>
   <td>회원BC
   </td>
   <td>로그아웃
   </td>
   <td>로그인회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>아파트BC
   </td>
   <td>아파트인증
   </td>
   <td>아파트비인증회원
   </td>
   <td>
   </td>
   <td>카카오맵API
   </td>
  </tr>
  <tr>
   <td>아파트BC
   </td>
   <td>아파트정보조회됨
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>아파트BC
   </td>
   <td>아파트정보생성
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>아파트BC
   </td>
   <td>아파트정보수정
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판생성
   </td>
   <td>아파트회원
   </td>
   <td>게시판생성요청승인 되면 게시판 생성
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판리스트조회
   </td>
   <td>아파트회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판변경
   </td>
   <td>게시판생성회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판삭제
   </td>
   <td>게시판생성회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판요청조회
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판요청승인
   </td>
   <td>관리자
   </td>
   <td>게시판정보를 아파트게시판에 추가
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>게시판요청반려
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글추가
   </td>
   <td>아파트인증회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글조회
   </td>
   <td>아파트인증회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글수정
   </td>
   <td>게시글작성자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글삭제
   </td>
   <td>게시글작성자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글리스트조회
   </td>
   <td>아파트인증회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>아파트게시글검색
   </td>
   <td>아파트인증회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>신고추가
   </td>
   <td>아파트인증회원
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>신고조회
   </td>
   <td>관리자
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>게시판 BC
   </td>
   <td>신고처리
   </td>
   <td>Sys
   </td>
   <td>10번이상신고되면영구정지
   </td>
   <td>
   </td>
  </tr>
</table>


</details>
