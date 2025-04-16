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
		<h2> 회원 가입</h2>
	</div>

	<form action="${pageContext.request.contextPath}/join.do" method="post" name="frm" onsubmit="return joinCheck();">
		
		<div class="join_inner">
			<div class="join_in_textbox">
				<div class="join_text">* 이름</div>
				<input type="text" name="name" size="20" placeholder=" 이름을 입력해주세요." class="join_input">
			</div>

			<div class="join_in_textbox">
				<div class="join_text">* 이메일</div>

				<div class="join_input_container">
					<input type="text" name="email" size="20"  id="email" placeholder=" 이메일 양식에 맞춰 입력" class="join_input">
					<input type="hidden" name="reemail" size="20"> 
					<input type="button" value="중복 체크" onclick="emailCheck()" class="join_dup_btn">
				</div>
			</div>	

			<div class="join_in_textbox">
				<div class="join_text">* 닉네임</div>

				<div class="join_input_container">
					<input type="text" name="nickname" size="20"  id="nickname" placeholder=" 닉네임 최대 10자 이내로 입력" class="join_input">
					<input type="hidden" name="renickname" size="20"> 
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
			
		<div class="join_inner">
			<input type="checkbox" id="termsCheckbox"> WEEKEND의 이용약관에 동의합니다.

			<div class="join_reporter"> 
				기자입니다
				<div class="toggle-btn" id="toggleBtn" onclick="toggleUserType()">
					<input type="hidden" name="usertype" id="usertype" value="1"> <!-- 기본값 1 설정 -->
				</div>
			</div>
		</div>	
		
		<div class="join_btn">
			<input type="submit" value="회원가입" onclick="return joinCheck()">
			<input type="reset" value="뒤로 가기" onclick="location.href='${pageContext.request.contextPath}/login.do'">
		</div>
	
	</form>
</div>
</body>
</html>