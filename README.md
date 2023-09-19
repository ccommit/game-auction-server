# game-aution-server
- 로스트아크 사이트에 있는 게임에 관련된 경매 백엔드 시스템 

# 목적
- 객체지향과 디자인패턴을 의미있게 적용
- 코드 가독성과 확장성을 고려하여 작성
- 대용량 트래픽 기반 안정성 있는 어플리케이션 개발

# 사용 기술
- JAVA 17.0
- MySQL
- SpringBoot

# 기획
- 온라인 게임 경매장 시스템을 웹 홈페이지에서 사용할 수 있도록 도메인을 통한 웹 옥션 시스템 구현
  [https://ovenapp.io/project/9YfT2bRPTitLHVIsqgRCwW5oypAWsFY5#BOXfZ](https://ovenapp.io/view/9YfT2bRPTitLHVIsqgRCwW5oypAWsFY5/ThKzM)

# 프로그램 주요 기능

__계정__
- 회원가입
  - 아이디 중복 체크
  - 비밀번호 암호화
  - 닉네임 설정
- 로그인 (1시간 세션 유지)
- 로그아웃
- 회원정보 변경(비밀번호, 휴대폰 번호, 닉네임)
- 회원 상태(경매 불가, 경매 가능(캐릭터 레벨 10이상), 제재 상태, 탈퇴 상태)

__어드민__ (API 만 개발)
- 어뷰징 유저는 어드민이 제재
- 불필요한 경매 물품 삭제
- 공지글 추가 기능

__로그인__
- 로그인한 유저의 보유한 아이템 테이블
  - 아이템 명, 등급, 레벨, 캐릭터 레벨제한, 속성1, 속성2
- 유저 정보
  - 유저가 보유한 금액
  - 관심 목록 (장바구니)
  - 등록 물품 (판매)
  - 입찰 진행중인 물품 (구매, 입찰 내역)
  - 거래 완료 물품 (판매 및 구매 완료, 최근 거래)

__경매장 상품(아이템) 등록__
- 최소입찰 가격
- 즉시구매 가격
- 경매장 등록 제한 시간(24시간)

__경매장 상품 조회__
- 메인 페이지 카테고리 별(장비, 장신구 | 무기, 방어구, 목걸이, 귀걸이) 최저,최대값의 정렬 기능 구현
- 상품의 정보에 따른 필터 기능
  - 조회할 아이템 명이 포함된 경우
  - 아이템 레벨의 최소, 최대값에 대한 필터링
  - 특정 금액에 따른 필터
 
__경매장 상품 입찰__
- 즉시 구매
- 입찰
  - 입찰 시작가격 설정
  - 입찰 가격에 대한 경계 설정 (등록하려는 입찰 가격은 즉시구매의 값을 넘을 수 없다.)
- 유찰
  - 구매자 : 다른 구매자가 상위 입찰을 할 경우 입찰한 금액 반환
  - 판매자 : 등록한 상품이 제한시간 내 입찰이 없을 경우 아이템 반환

__경매장 입찰 내역 조회__
- 판매자
  - 경매장에 등록되어있는 상품에 대한 정보 조회(등록 물품)
  - 경매장에 판매된 상품에 대한 정보(최근 거래)
- 구매자
  - 입찰한 상품의 대한 정보(내 입찰 내역)
  - 낙찰에 성공한 상품의 대한 정보(최근거래)



# 시퀀스 다이어그램

<details>
<summary>경매장 검색</summary>
  
![경매장 검색-3](https://github.com/ccommit/game-aution-server/assets/43266403/dc3584ac-98b9-4be5-9547-0ee2b06bb047)

</details>

<details>
<summary>경매장 입찰</summary>
  
![경매장 입찰-3](https://github.com/ccommit/game-aution-server/assets/43266403/b5f77f7b-8613-40f4-8b17-87a35520208d)

</details>


# 경매장 ERD

<details>
<summary>경매장 ERD</summary>

![image](https://github.com/ccommit/game-aution-server/assets/43266403/24e35f2d-d92f-4abe-b79b-1221c78172e6)


</details>
