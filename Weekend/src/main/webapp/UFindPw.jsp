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
            <h2> 비밀번호 변경</h2>
        </div>

        <form name="frm" action="${pageContext.request.contextPath}/findpw.do" method="post" onsubmit="return findPassword();">  
            
            <div class="join_inner">

                <div class="join_in_textbox">

                    <div class="join_text">이메일</div>

                    <div class="join_input_container">
                        <input type="text" id="email" name="email" class="join_input" required>
                    </div>
                </div>
                

                <div class="join_btn">
                    <input type="submit" value="확인" onclick="return findPassword()"> 
                    <input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/login.do'">
                </div>
                 
            </div>

            
        </form>
        
        <div class="join_inner">
                <!-- 오류 메시지 출력 -->
                <c:if test="${not empty error}">
                    <p style="color: red;">${error}</p>
                </c:if>
            </div>
    
    </div>

</body>
</html>