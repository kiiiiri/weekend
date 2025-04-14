<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    String contextPath = request.getContextPath();
%>
<%@ page import="java.net.URLDecoder" %>
<%
    String searchWord = request.getParameter("searchWord");
    if (searchWord == null) searchWord = "";
    else searchWord = URLDecoder.decode(searchWord, "UTF-8");

    String searchField = request.getParameter("searchField");
    if (searchField == null) searchField = "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위캔드 | 주말이 즐거워지는 이유</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo2_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/CommunityBoard.css" />
<script src="<%=contextPath%>/js/Nav.js"></script>
<script src="<%=contextPath%>/js/CommunityBoard.js"></script>
</head>
<body>
<body>
	<!-- 사이트 제목 -->
    <div class="site_title">
      <div class="title_logo2"><a href="#"></a></div>
      <div class="title_wrap2">WEEKEND</div>
    </div>
    <!-- 네비게이션 바 (비로그인/로그인) -->
    <c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <jsp:include page="Header4.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:include page="Header3.jsp" />
  </c:otherwise>
</c:choose>

<div id="community_title">
<span class="community_title_main">커뮤니티</span><br />
<span class="community_title_sub">유용한 정보와 소중한 기억들을 소개해보세요.</span>
</div>

<!-- 검색 폼  -->
	<div class="community_banner">
	<% 
	int randomimg = (int)(Math.random() * 5) + 1;
	String randomIS = "community_board0" + randomimg + ".png";
	%>
  <img src="<%=contextPath%>/img/<%= randomIS %>" alt="커뮤니티 배너 이미지" class="banner_img" />
  <div class="search_box">
    <form method="get" action="<%= request.getContextPath() %>/community/list.do#board" class="search_form">
      <select name="searchField" class="search_select">
        <option value="ctitle" <%= "ctitle".equals(request.getAttribute("searchField")) ? "selected" : "" %>>제목</option>
        <option value="ctext" <%= "ctext".equals(request.getAttribute("searchField")) ? "selected" : "" %>>내용</option>
      </select>
      <input type="text" name="searchWord" class="search_input" placeholder="4월 가장 핫한 여행지는?" 
      value="<%= searchWord %>" />
      <button type="submit" class="search_btn">검색</button>
    </form>
  </div>
</div>
    
    <!-- 페이징 빠른 시점 전환 체크포인트 -->
<a name="board"></a>

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
        <!-- 공지사항 -->
		<c:forEach var="dto" items="${noticeList}">
  		  <tr class="notice">
        	<td></td>
       		 <td><span class="inline-block px-1 text-xs text-white bg-red-500 rounded">⛔ 공지</span>
       		 <a href="cview.do?cno=${dto.cno}" class="text-red-600 font-semibold">${dto.ctitle}</a> [${dto.replyCount}]</td>
        <td>${dto.cwdate}</td>
        <td>${dto.cviewcount}</td>
        <td>${dto.cwuser}</td>
    	  </tr>
		</c:forEach>
  	    <c:forEach var="row" items="${boardLists2}">
     		 <tr>
       			 <td>${row.cno}</td>
        		 <td>
        		 	   <c:choose>
					    <c:when test="${row.ctype == 1}">
					      
					    </c:when>
					    <c:when test="${row.ctype == 2}">
					      <span class="inline-block px-1 text-xs text-white bg-blue-500 rounded">❓ 질문</span>
					    </c:when>
					    <c:when test="${row.ctype == 3}">
					      <span class="inline-block px-1 text-xs text-white bg-green-500 rounded">후기</span>
					    </c:when>
					    <c:when test="${row.ctype == 4}">
					      <span class="inline-block px-1 text-xs text-white bg-yellow-500 rounded"> 여행지 추천</span>
					    </c:when>
					    <c:when test="${row.ctype == 5}">
					      <span class="inline-block px-1 text-xs text-white bg-red-500 rounded">공지</span>
					    </c:when>
					    <c:when test="${row.ctype == 6}">
					      <span class="inline-block px-1 text-xs text-white bg-purple-500 rounded">❕ 정보</span>
					    </c:when>
					    <c:when test="${row.ctype == 7}">
					      <span class="inline-block px-1 text-xs text-white bg-pink-500 rounded">가입인사</span>
					    </c:when>
					  </c:choose>
         			 <a href="${pageContext.request.contextPath}/community/cview.do?cno=${row.cno}">
          			  ${row.ctitle}
            			<c:if test="${row.replyCount > 0}"><span class="text-red-500">[${row.replyCount}]</span></c:if>
         			 </a>
        		</td>
       			 <td>${row.cwdate}</td>
        		 <td>${row.cviewcount}</td>
       			 <td>${row.cwuser}</td>
    	    </tr>
       </c:forEach>
       </tbody>
      </table>

<!-- 글쓰기 버튼 + 페이징 -->

<div class="pagination_wrap">
  <div class="pagination">
    ${pagingImg}
  </div>
</div>

<div class="write_btn_wrap">
  <a href="${pageContext.request.contextPath}/community/cwrite.do" class="write_btn">글쓰기</a>
</div>
<!-- 광고 include  -->
<%
int randomAd = (int)(Math.random() * 2) + 3;
String adPage = "Ad0" + randomAd + ".jsp";
%>
<jsp:include page="<%= adPage %>" />

    
</body>
</body>
</html>