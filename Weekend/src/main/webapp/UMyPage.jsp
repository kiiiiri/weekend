<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="gokr.weekend.dto.UserVO" %> <!-- UserVO 임포트 -->
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/User.css">
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css">
</head>
<body>
 <!-- 사이트 제목 -->
    <div class="site_title">WEEKEND</div>

    <!-- 네비게이션 바 -->
    <nav id="header">
      <!-- 네비게이션 바 : 파라솔 로고 & 사이트 이름 -->
      <li class="nav nav_logo"><a href="lk.html"></a></li>
      <div class="nav_word">WEEKEND</div>

      <!-- 네비게이션 바 : 웹진 & 커뮤니티 -->
      <ul class="nav_item">
        <li class="nav nav_li">
          <a href="#">웹진</a>
        </li>
        <li class="nav nav_li">
          <a href="#">커뮤니티</a>
        </li>
      </ul>

      <!-- 로그인 상태에 따른 버튼 변경 -->
    <% 
    UserVO loginUser = (UserVO) request.getAttribute("loginUser");  // 서블릿에서 전달한 loginUser를 받아옴
    if (loginUser == null) { 
    %>
    
        <!-- 비로그인 상태일 때: 로그인/회원가입 -->
        <button class="nav_loginbutton">
            <a href="<%= request.getContextPath() %>/login.do">로그인/회원가입</a>
        </button>
    <% } else { %>
        <!-- 로그인 상태일 때: 마이페이지 -->
        <button class="nav_loginbutton">
            <a href="<%= request.getContextPath() %>/mypage.do">마이페이지</a>
        </button>
    <% } %>
    </nav>

<%
    if (loginUser == null) {
%>
	
    
    <!-- 로그인하지 않았을 때 보여줄 내용 -->
    <div class="mypage_container <%= loginUser != null ? "hidden" : "" %>"> <!-- 로그인을 하지 않았을 때 .hidden이 붙음 --> 
        <div class="mypage_title">
        <h2>마이 페이지</h2>
      </div>
    
      <div class="mypage_inner">
        <p>로그인 후에만 마이페이지를 이용하실 수 있습니다.</p>
        <input type="button" value="로그인" onclick="location.href='<%=request.getContextPath() %>/login.do'">
      </div>
      
  </div>
<%
    } else {
%>
	
    <!-- 로그인한 사용자에게 보여줄 마이페이지 내용 -->
    <div class="mypage_container">
        <div class="mypage_title">
        	<h2>마이 페이지</h2>
      	</div>
      	
        <div class="mypage_inner">
           	 <p>환영합니다, <%= loginUser.getName() %>님!</p> <!-- 사용자 이름 출력 -->
             <input type="button" value="정보 수정하기" onclick="location.href='<%=request.getContextPath() %>/userupdate.do'">
             <input type="button" value="로그아웃" onclick="location.href='<%=request.getContextPath() %>/logout.do'">
        </div>
    </div>
<%
    }
%>

</body>
</html>