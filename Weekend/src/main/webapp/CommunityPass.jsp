<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function validateForm(form){
		if(form.cpw.value==""){
			alert("비밀번호를 입력하세요");
			form.cpw.focus();
			return false;
		}
	}
</script>
</head>
<body>
<h1>비밀번호 확인</h1>
	<form method="post" action="/community/pass.do" onsubmit="return validateForm(this);">
		<input type="hidden" name="cno" value="${param.cno}">
		<input type="hidden" name="mode" value="${param.mode}">
		<table border="1" width="90%">
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="cpw" style="width:100px">					
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">확인</button>
					<button type="reset">RESET</button>
					<button type="button" onclick="location.href='/community/list.do'">목록</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>