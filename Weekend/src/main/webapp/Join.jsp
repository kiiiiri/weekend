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
<h2>회원 가입</h2>
	'*' 표시 항목은 필수 입력 항목입니다.
	<form action="${pageContext.request.contextPath}/join.do" method="post" name="frm" onsubmit="return joinCheck();">
		<table>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" size="20">*</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" name="email" size="20"  id="email">* 
					<input type="hidden" name="reemail" size="20"> 
					<input type="button" value="중복 체크" onclick="emailCheck()"></td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td><input type="text" name="nickname" size="20"  id="nickname">* 
					<input type="hidden" name="renickname" size="20"> 
					<input type="button" value="중복 체크" onclick="nicknameCheck()"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pw" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">비밀번호 확인</td>
				<td><input type="password" name="pw_check" size="20">*</td>
			</tr>
			<tr>
				<td><input type="checkbox" id="termsCheckbox">WEEKEND의 이용약관에 동의합니다.</td>
				<td>기자입니다
				<div class="toggle-btn" id="toggleBtn" onclick="toggleUserType()">
				<input type="hidden" name="usertype" id="usertype" value="1"> <!-- 기본값 1 설정 -->
				</div></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="확인"
					onclick="return joinCheck()">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="취소" onclick="location.href='${pageContext.request.contextPath}/login.do'">
				</td>
			</tr>
			<tr>
				<td colspan="2">${message }</td>
			</tr>
		</table>
	</form>
</body>
</html>