<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        
        <form name="frm" action="${pageContext.request.contextPath}/updatepw.do" method="post" onsubmit="return validateAndShowMessage();">
            <input type="hidden" name="email" value="${email}"> <!-- 이메일을 hidden 필드로 전달 -->
    
            <div class="join_inner">
            
                <div class="join_in_textbox">
                    <div class="join_text">새 비밀번호</div>
                    <input type="password" id="pw" name="pw" placeholder=" 비밀번호를 입력해주세요." class="join_input" required>
                </div>
    
                <div class="join_in_textbox">
                    <div class="join_text">비밀번호 확인</div>
                    <input type="password" id="confirmpw" name="confirmpw" placeholder="비밀번호 다시 입력" class="join_input" required>
                </div>
            
            </div>

            <div class="join_btn">
                <input type="submit" value="비밀번호 변경">
                <input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/login.do'">
            </div>
        
        </form>

    </div>
</body>
</html>
