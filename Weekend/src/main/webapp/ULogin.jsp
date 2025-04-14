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
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/User.css">
<script type="text/javascript" src="<%=contextPath%>/js/User.js"></script>
</head>
<body>
	<div id="page_login_container">
		<div id="back_icon">
			<c:choose>
  				  <c:when test="${param.from == 'community'}">
        				<a href="${pageContext.request.contextPath}/community/list.do"><i class="fa-solid fa-arrow-left"></i></a>
   				  </c:when>
    	    <c:otherwise>
       					 <a href="${pageContext.request.contextPath}/webzine/list.do"><i class="fa-solid fa-arrow-left"></i></a>
    		</c:otherwise>
			</c:choose>
		</div>

		<div id="login_container">
			<div id="login_header">
				<h2>ENJOY YOUR</h2>
				<h2 id="login_h_color">WEEKEND !</h2>
			</div>
		
			<form action="${pageContext.request.contextPath}/login.do" name="frm" method="post">
			<input type="hidden" name="from" value="${from}">
			
				<div class="login_inner">
				</div>
				
				<div class="login_inner" id="login_input">
					<input type="text" name="email" value="${email}" placeholder="이메일을 입력하세요">
					<input type="password" name="pw" placeholder="비밀번호를 입력하세요">
				</div>


				<div class="login_inner">
					<input type="submit" value="Sign in" id="sign_btn" onclick="return loginCheck()">
				</div>

				<div class="login_other">
					<a href="${pageContext.request.contextPath}/join.do" class="button-link">회원가입</a>
					<span>|</span>
					<a href="${pageContext.request.contextPath}/findemail.do" class="button-link">이메일 찾기</a>
					<span>|</span>
					<a href="${pageContext.request.contextPath}/findpw.do">비밀번호 찾기</a>	
				</div>
			</form>
		</div>
	</div>
	
<%-- 세션에 메시지가 있을 경우 자바스크립트로 전달 --%>
<% 
    String message = (String) session.getAttribute("message"); // 세션에서 메시지 가져오기
    if (message != null) {
        session.removeAttribute("message"); // 메시지를 표시한 후 세션에서 삭제
%>
    <script type="text/javascript">
        // 세션에서 가져온 메시지를 자바스크립트로 전달
        window.loginErrorMessage = "<%= message %>";
    </script>
<% 
    }
%>

<!-- 메시지 확인 및 표시 -->
<script type="text/javascript">
    window.onload = function() {
        // 페이지 로딩 시 메시지가 있으면 alert()으로 표시
        if (window.loginErrorMessage) {
            alert(window.loginErrorMessage);  // 메시지를 alert로 표시
        }
    }
</script>

</body>
</html>