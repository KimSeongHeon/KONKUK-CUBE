<h1 align="center">KONKUK CUBE - 건국대학교 K-CUBE 예약 어플리케이션 </h1>
<p align="center"><img src="https://lh3.googleusercontent.com/0UdE_6jnGS3GnWKxV7KjydJEJZcEf38BMANpDznHk8wQgq452E_bV7NWczS8YsiKUN4=s180-rw" height="88" /></p>
<p align="center">K-CUBE! 번거롭게 웹 페이지에서 예약하지 말고 간편하게 앱으로 예약하자!</p>

<span><img src = "https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/276/square_480/github_logo.png" height = "20"/> https://github.com/KimSeongHeon/KONKUK-CUBE</span>
<br/>
<span><img src = "https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png" height = "20"/> https://play.google.com/store/apps/details?id=com.project.reservation_kcube </span>


## 구현 방법

#### 😃     Webview와 네이티브 간의 연동을 이용하여 구현
- ##### 이 방법을 이용하여 구현한 이유는 무엇인가요?
처음 okhttp 를 이용하여 request를 보내고 response로 html을 받아 구현할 계획이었지만 홈페이지에서 제공하는 다양한 validation을 체크할 수 없다는 문제점이 발생
따라서 조금 늦더라도 안정적인 방식인 webview & native 연동 방식을 통해 구현하였음

- ##### 이 방법을 이용하여 구현한 이유는 무엇인가요?
