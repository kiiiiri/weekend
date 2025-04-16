<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 네비게이션 바 -->
    <nav id="header" class="tealborder">
      <!-- 네비게이션 바 : 파라솔 로고 & 사이트 이름 -->
      <li class="nav nav_logo2"><a href="#"></a></li>
      <div class="nav_word">WEEKEND</div>

      <!-- 네비게이션 바 : 웹진 & 커뮤니티 -->
      <ul class="nav_item">
        <li class="nav nav_li">
          <a href="<%= request.getContextPath() %>/webzine/list.do">웹진</a>
        </li>
        <li class="nav nav_li">
          <a href="<%= request.getContextPath() %>/community/list.do">커뮤니티</a>
        </li>
      </ul>

      <!-- 네비게이션 바 : 로그인 시 : 내 정보 탭 -->
      <div class="nav nav_site">
        <button id="drop_btn" class="dropdown_btn"><span class="namespan2">${sessionScope.loginUser.nickname}&nbsp님</span></button>
        <div id="drop_menu" class="nav_dropdown">
          <a href="${pageContext.request.contextPath}/userupdate.do?from=community">내 정보 수정</a>
          <a href="${pageContext.request.contextPath}/logout.do?from=community">로그아웃</a>
        </div>
      </div>
    </nav>
</body>
</html>