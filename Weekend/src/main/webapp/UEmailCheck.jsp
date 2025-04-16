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
		<h3>이메일 중복확인</h3>
	</div>
	
	<form action="${pageContext.request.contextPath}/emailcheck.do" method="get" name="frm" class="check_form">
		이메일 <input type=text name="email" value="${email}">
	</form>
	
	<c:if test="${result == 1}">
			<script type="text/javascript">
				opener.document.frm.email.value = "";
			</script>
			<div>${email}는 이미 사용 중인 이메일 입니다. </div>
		</c:if>
		<c:if test="${result==-1}">
			<div>${email}는 사용 가능한 이메일 입니다.
			<input type="button" value="사용" class="cancel" onclick="emailok('${email}')">
			</div>

		</c:if>


</div>

</body>
</html>