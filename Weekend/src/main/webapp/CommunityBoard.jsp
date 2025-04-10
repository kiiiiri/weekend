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
<title>위캔드 | 주말이 즐거워지는 이유</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/CommunityBoard.css" />
<script src="<%=contextPath%>/js/Nav.js"></script>
</head>
<body>
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

<div id="community_title">
<span class="community_title_main">유저 커뮤니티</span><br />
<span class="community_title_sub">유용한 정보와 소중한 기억들을 소개해보세요.</span>
</div>

	<!-- 게시글 목록 테이블 -->
    <div class="board_container">
      <table class="boardtable">
        <thead>
          <tr>
            <th>글번호</th>
            <th>제목</th>
            <th>작성일</th>
            <th>조회수</th>
            <th>작성자</th>
          </tr>
        </thead>
        <tbody>
    <c:forEach var="row" items="${boardLists2}">
      <tr>
        <td>${row.cno}</td>
        <td>
          <a href="${pageContext.request.contextPath}/community/view.do?cno=${row.cno}">
            ${row.ctitle}
            <%-- <c:if test="${row.replyCount > 0}">[${row.replyCount}]</c:if> --%>
          </a>
        </td>
        <td>${row.cwdate}</td>
        <td>${row.cviewcount}</td>
        <td>${row.cwuser}</td>
      </tr>
    </c:forEach>
  </tbody>
      </table>

<!-- 페이징 -->
      <div class="pagination">
  			${pagingImg}
	</div>
	
<!-- 검색 폼  -->
<div class="search_box">
  <form method="get" class="search_form">
    <select name="searchField" class="search_select">
      <option value="title">제목</option>
      <option value="content">내용</option>
    </select>
    <input type="text" name="searchWord" class="search_input" placeholder="검색어를 입력하세요" />
    <button type="submit" class="search_btn">검색하기</button>
  </form>
</div>


    </div>
    

    
</body>
</body>
</html>