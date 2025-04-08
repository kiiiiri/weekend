<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위캔드 | 주말이 즐거워지는 이유</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/Main.css" />
<script src="<%=contextPath%>/js/Nav.js"></script>
<script src="<%=contextPath%>/js/Main.js"></script>

</head>
<body>
<!-- 사이트 제목 -->
    <div class="site_title">
      <div class="title_logo"><a href="#"></a></div>
      <div class="title_wrap">WEEKEND</div>
    </div>
	<jsp:include page="Header.jsp"/> 
	
    <!-- Swiper 구조 -->
    <div class="swiper">
      <div class="swiper-wrapper">
        <div class="swiper-slide">
          <img src="" alt="swiper_img1" />
          <div class="overlay-text"><a href="#">testimg 1</a></div>
        </div>
        <div class="swiper-slide">
          <img src="" alt="swiper_img2" />
          <div class="overlay-text"><a href="#">testimg 2</a></div>
        </div>
        <div class="swiper-slide">
          <img src="" alt="swiper_img3""/>
          <div class="overlay-text"><a href="#">testimg 3</a></div>
        </div>
        <div class="swiper-slide">
          <img src="" alt="swiper_img4" />
          <div class="overlay-text"><a href="#">역사와 문화의 고장 경주</a></div>
        </div>
        <div class="swiper-slide">
          <img src="" alt="swiper_img5" />
          <div class="overlay-text"><a href="#">외국인 친구에게 꼭 추천해주고 싶은 지역관광 안테나숍 '트립집'</a></div>
        </div>
        <div class="swiper-slide">
          <img src="" alt="swiper_img6" />
          <div class="overlay-text"><a href="#">가나다라마바사아자차가나다라마바사아자차가나다라마바사아자차가나다라마바사아자차가나다라마바사아자차가나다라마바사아자차</a></div>
        </div>
      </div>
    </div>
        <!-- 최신 기사 소개 컨테이너 -->
    <div id="recentwebzine_container">
      <div id="recentwebzine_title">
        <span class="webzine_title_main">최신 기사</span><br />
        <span class="webzine_title_sub">알지 못했던 곳으로 여행을 떠나보세요.</span>
      </div>
      
      <!-- 페이지 기사 1 -->
      <div class="recentwebzine_contents">
      
        <div class="contents_left">
          <div class="thumbnail">
            <a href="#"><img src="" alt="페이지 기사1 사진" /></a>
          </div>
        </div>

        <div class="contents_right">
          <a href="#"><h2>역사와 문화의 고장 경주</h2></a>
          <p class="webzine_date">2025-04-07</p>
          <p class="webzine_writer">대한민국구석구석 SNS</p>
          <a href="#" class="webzine_link">상세보기 &gt;</a>
        </div>
      </div>
      <div class="webzine_writebutton_wrap">
		<button class="webzine_writebutton">기사 작성하기</button>
		</div>
    </div>
    
</body>
</html>