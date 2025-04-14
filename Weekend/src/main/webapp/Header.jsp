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
    <nav id="header" class="orangeborder">
      <!-- 네비게이션 바 : 파라솔 로고 & 사이트 이름 -->
      <li class="nav nav_logo"><a href="#"></a></li>
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

      <!-- 비로그인 시 : 로그인 & 회원가입 -->
      <button class="nav_loginbutton"><a href="<%=request.getContextPath()%>/login.do?from=webzine">로그인/회원가입</a></button>
    </nav>
</body>
</html>