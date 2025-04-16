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
<body class="check_body">

<div class="check_container">
	<div class="check_header">
		<h2>WEEKEND</h2>
		<h3>닉네임 중복확인</h3>
	</div>
	
	<form action="${pageContext.request.contextPath}/nicknamecheck.do" method="get" name="frm" class="check_form">
		닉네임 <input type=text name="nickname" value="${nickname}"> 
		<c:if test="${result == 1}">
			<script type="text/javascript">
				opener.document.frm.nickname.value = "";
			</script>
			<div>${nickname}는 이미 사용 중인 닉네임 입니다.</div>
		</c:if>
		<c:if test="${result==-1}">
			<div>${nickname}는 사용 가능한 닉네임 입니다
			<input type="button" value="사용" class="cancel" onclick="nicknameok('${nickname}')">
			</div>
		</c:if>
	</form>

</div>

</body>
</html>