<h1 align="center">KONKUK CUBE - 건국대학교 K-CUBE 예약 어플리케이션 </h1>
<p align="center"><img src="https://lh3.googleusercontent.com/0UdE_6jnGS3GnWKxV7KjydJEJZcEf38BMANpDznHk8wQgq452E_bV7NWczS8YsiKUN4=s180-rw" height="88" /></p>
<p align="center">K-CUBE! 번거롭게 웹 페이지에서 예약하지 말고 간편하게 앱으로 예약하자!</p>

<div align = "center"><img src = "https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/276/square_480/github_logo.png" height = "20"/> https://github.com/KimSeongHeon/KONKUK-CUBE</div>
<br/>
<div align = "center"><img src = "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png" height = "20"/> https://play.google.com/store/apps/details?id=com.project.reservation_kcube </div>

## 어플리케이션 기능 

- 번거로운 로그인 대신 내장 데이터베이스를 이용한 자동 로그인

- 최근 같이 이용한 친구의 학번을 저장하여 다음 예약 시 즐겨찾기에서 간편히 친구 추가 가능

- 예약 완료한 케이큐브에 대하여 카카오톡 링크를 통하여 친구와 공유 기능

## 구현 방법

#### Webview와 네이티브 간의 연동을 이용하여 구현
- ##### 이 방법을 이용하여 구현한 이유는 무엇인가요?
처음 okhttp 를 이용하여 request를 보내고 response로 html을 받아 구현할 계획이었지만 홈페이지에서 제공하는 다양한 validation을 체크할 수 없다는 문제점이 발생
따라서 조금 늦더라도 안정적인 방식인 webview & native 연동 방식을 통해 구현하였음

- ##### 구현에 어려웠던 점은 무엇인가요?
웹페이지에서 같이 이용할 학번을 선택할 때 동적으로 span태그가 생성되어 기존 사용했던 코드로는 동적 생성한 태그를 네이티브 상 불러올 수 없었음.
Dom의 변화를 감지하는 javascript의 mutationObserver라는 설정하여 이 문제를 해결하였음

