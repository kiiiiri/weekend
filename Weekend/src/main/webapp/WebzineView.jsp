<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <% String contextPath =
request.getContextPath(); %>
<%@ page import="gokr.weekend.dto.UserVO" %>
<%@ page import="gokr.weekend.dto.WebzineDTO" %>
<%
    WebzineDTO dto = (WebzineDTO) request.getAttribute("dto");
    UserVO loginUser = (UserVO) session.getAttribute("loginUser");
    boolean isWriter = loginUser != null && loginUser.getUno() == Integer.parseInt(dto.getUno());
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>위캔드 | 주말이 즐거워지는 이유</title>
    <link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo_50.png" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=contextPath%>/css/font-awesome.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
    <link rel="stylesheet" href="<%=contextPath%>/css/WebzineView.css" />

    <script src="<%=contextPath%>/js/Nav.js"></script>
  </head>

  <body>
    <!-- 사이트 제목 -->
    <div class="site_title">
      <div class="title_logo"><a href="#"></a></div>
      <div class="title_wrap">WEEKEND</div>
    </div>
    <!-- 네비게이션 바 (비로그인/로그인) -->
    <c:choose>
      <c:when test="${not empty sessionScope.loginUser}">
        <jsp:include page="Header2.jsp" />
      </c:when>
      <c:otherwise>
        <jsp:include page="Header.jsp" />
      </c:otherwise>
    </c:choose>

    <div class="container">
      <div class="title">
        <!-- 제목 강조 -->
        <span class="post-title-text">${dto.wtitle}</span>
      </div>

      <div class="custom-divider"></div>

      <div class="post-meta">
        <div class="left-meta">
          📖 
          <span class="username">${dto.nickname}</span>
        </div>
        <div class="right-meta writedate">${dto.wwdate}</div>
      </div>

      <div class="view-count">조회수 ${ dto.wviewcount }</div>

      <div class="content">${ dto.wtext }</div>

      <div class="custom-divider"></div>

      <div class="buttons">
		  <div class="left-buttons">
		    <% if (isWriter) { %>
		      <button class="button black" onclick="location.href='edit.do?wno=<%= dto.getWno() %>'">수정하기</button>
      		  <button class="button red" onclick="location.href='delete.do?wno=<%= dto.getWno() %>'">삭제하기</button>
		    <% } %>
		  </div>
		  <button type="button" onclick="location.href='<%= request.getContextPath() %>/webzine/list.do';" class="border px-4 py-2 rounded-md hover:bg-gray-100">기사 목록으로</button>
		</div>
		
		<div class="custom-divider"></div>

    <script>
      const contextPath = "<%= request.getContextPath() %>";
    </script>
    <script src="<%= request.getContextPath() %>/js/WebzineView.js"></script>
  </body>
</html>
