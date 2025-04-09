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
<script type="text/javascript" src="<%=contextPath%>/js/User.js"></script>
</head>
<body>
<h2>이메일 중복확인</h2>
	<form action="${pageContext.request.contextPath}/emailcheck.do" method="get" name="frm">
		이메일 <input type=text name="email" value="${email}"> 
		<input type="submit" value="중복 체크"> <br>
		<c:if test="${result == 1}">
			<script type="text/javascript">
				opener.document.frm.userid.value = "";
			</script>
			${email}는 이미 사용 중인 이메일 입니다.
		</c:if>
		<c:if test="${result==-1}">
		${email}는 사용 가능한 이메일 입니다.
		<input type="button" value="사용" class="cancel" onclick="emailok('${email}')">
		</c:if>
	</form>
</body>
</html>