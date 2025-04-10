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
    <!-- 네비게이션 바 (비로그인/로그인) -->
    <c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <jsp:include page="Header2.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:include page="Header.jsp" />
  </c:otherwise>
</c:choose>
	
<!-- Swiper 구조 -->
<div class="swiper">
  <div class="swiper-wrapper">
    <c:forEach var="row" items="${boardLists}" varStatus="status">
      <c:if test="${status.index < 6}">
        <div class="swiper-slide">
  <a class="slide_link" href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}">
    <!-- 배경 레이어 -->
    <div class="slide_overlay"></div>

    <!-- 이미지와 텍스트 -->
    <img src="${pageContext.request.contextPath}/Uploads/${row.wsfile}" alt="swiper_img${status.index + 1}" />
    <div class="overlay-text">${row.wtitle}</div>
  </a>
</div>
      </c:if>
    </c:forEach>
  </div>
</div>
        <!-- 최신 기사 소개 컨테이너 -->
    <div id="recentwebzine_container">
      <div id="recentwebzine_title">
        <span class="webzine_title_main">최신 기사</span><br />
        <span class="webzine_title_sub">알지 못했던 곳으로 여행을 떠나보세요.</span>
      </div>
      <c:if test="${not empty sessionScope.loginUser && (sessionScope.loginUser.usertype == 2 || sessionScope.loginUser.usertype == 3)}">
  		<div class="webzine_writebutton_wrap">
   		 <button type="button" onclick="location.href='${pageContext.request.contextPath}/webzine/wwrite.do'" class="webzine_writebutton">기사 작성하기</button>
 		</div>
	  </c:if>
		
      <!-- 기사 목록 -->
<c:forEach var="row" items="${boardLists}">
  <div class="recentwebzine_contents">
  
    <div class="contents_left">
      <div class="thumbnail">
        <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}">
          <img src="${pageContext.request.contextPath}/Uploads/${row.wsfile}" />
        </a>
      </div>
    </div>

    <div class="contents_right">
      <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}">
        <h2>${row.wtitle}</h2>
      </a>
      <p class="webzine_date">${row.wwdate}</p>
      <p class="webzine_writer">${row.nickname}</p>
      <p class="webzine_viewcount">조회수 ${row.wviewcount}</p>	
      <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}" class="webzine_link">상세보기 &gt;</a>
    </div>
  </div>
</c:forEach>
      <div id="loading" style="display: none; text-align:center; margin:20px;">
  <img src="<%=contextPath%>/img/loading.gif" alt="로딩 중..." />
</div>
    </div>
    
    <!-- 무한 스크롤 기사 불러오기 -->
    <script src="<%=contextPath%>/js/InfiniteScroll.js"></script>
    <script>
  const contextPath = "${pageContext.request.contextPath}";
</script>
</body>
</html>