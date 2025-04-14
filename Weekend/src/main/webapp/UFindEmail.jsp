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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/User.css">
<script type="text/javascript" src="<%=contextPath%>/js/User.js"></script>
</head>
<body>
	 
<div class="page_join_container">
	<div class="join_header">
		<h2 class="join_color_text">WEEKEND</h2>
		<h2> 이메일 찾기</h2>
	</div>
		
	<form action="${pageContext.request.contextPath}/findemail.do" method="post">
            
        <div class="join_inner">
		
            <div class="join_in_textbox">
                <div class="join_text">비밀번호</div>
                <input type="password" name="pw" size="20" placeholder=" 비밀번호를 입력해주세요." class="join_input">
            </div>

            <div class="join_in_textbox">
                <div class="join_text">이름</div>
                <input type="text" name="name" required placeholder="이름을 입력하세요" class="join_input">
            </div>

            <div class="join_in_textbox">
                <div class="join_text">닉네임</div>
                <input type="text" name="nickname" required placeholder="닉네임을 입력하세요" class="join_input">
            </div>
				
		</div>

        
        <div class="join_btn">
        	<input type="submit" value="아이디 찾기"> 
            <input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/login.do'">
        </div> 

        
	</form>
	
	<div class="join_inner">
            <!-- 결과 메시지 출력 -->
           <c:choose>
                <c:when test="${not empty email}">
                    <h3>찾은 이메일 : ${email}</h3>
                </c:when>
                <c:otherwise>
                    <h3 style="color: red;">${error}</h3>
                </c:otherwise>
            </c:choose> 
        </div>
	
    
</div>

</body>
</html>