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
<title>정보 수정</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/User.css">
<script type="text/javascript" src="<%=contextPath%>/js/User.js"></script>
</head>
<body>
    

<div class="page_join_container">
	<div class="join_header">
		<h2 class="join_color_text">WEEKEND</h2>
		<h2> 정보 수정</h2>
	</div>

	<form action="${pageContext.request.contextPath}/userupdate.do" method="post" name="frm" onsubmit="return validateForm()">
		
		<div class="join_inner">
		
            <div class="join_in_textbox">
                <div class="join_text">* 이메일</div>
            
                <div class="join_input_container">
                    <input type="text" name="email" size="20" id="email"  class="join_input" disabled value="${uVo.email}">
                </div>
            </div>

			<div class="join_in_textbox">
				<div class="join_text">* 닉네임</div>
				<div class="join_input_container">
					<input type="text" name="nickname" size="20" id="nickname" placeholder=" 닉네임 최대 10자 이내로 입력" class="join_input" value="${uVo.nickname}">
					<input type="hidden" name="originalNickname" value="${uVo.nickname}"> <!-- 기존 닉네임을 hidden으로 저장 -->
					<input type="hidden" name="renickname" value=""> <!-- 닉네임 중복 확인 상태 저장용 -->
					<input type="button" value="중복 체크" onclick="nicknameCheck()" class="join_dup_btn">
				</div>
			</div>
			
			<div class="join_in_textbox">
				<div class="join_text">* 비밀번호</div>
				<input type="password" name="pw" size="20" placeholder=" 비밀번호를 입력해주세요." class="join_input">
			</div>
			
			<div class="join_in_textbox">
				<div class="join_text">* 비밀번호 확인</div>
				<input type="password" name="pw_check" size="20" placeholder="비밀번호 다시 입력" class="join_input">
			</div>
			
		</div>
			
		<div class="join_btn">
			<input type="submit" value="수정" onclick="return updateCheck()">
			<c:choose>
  				  <c:when test="${param.from == 'community'}">
        				<input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/community/list.do'">
   				  </c:when>
    	    <c:otherwise>
       					 <input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/webzine/list.do'">
    		</c:otherwise>
			</c:choose>
			
		</div>
	
	</form>
</div>

</body>
</html>