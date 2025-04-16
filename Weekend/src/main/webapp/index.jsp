<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화면1</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<%
    String contextPath = request.getContextPath();
    response.sendRedirect(contextPath + "/webzine/list.do");
%>
	<div class="flex justify-center items-center h-screen">
      <a href="<%= request.getContextPath() %>/webzine/list.do"><button class="bg-black text-white px-6 py-3 text-lg rounded-2xl shadow-md hover:bg-gray-800 transition duration-300">Weekend 접속</button></a>
    </div>
</body>
</html>