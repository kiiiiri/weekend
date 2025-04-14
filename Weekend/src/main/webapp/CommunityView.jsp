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
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo2_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=contextPath%>/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/CommunityView.css" />

<script src="<%=contextPath%>/js/Nav.js"></script>


</head>
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
	
 	<div class="container">
      <div class="title">
			  <!-- 말머리 표시 -->
			  <c:choose>
			    <c:when test="${dto2.ctype == 1}">
			      <span class="badge bg-gray-500">잡담</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 2}">
			      <span class="badge bg-blue-500">질문</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 3}">
			      <span class="badge bg-green-500">후기</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 4}">
			      <span class="badge bg-yellow-500">여행지 추천</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 5}">
			      <span class="badge bg-red-500">공지</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 6}">
			      <span class="badge bg-purple-500">정보</span>
			    </c:when>
			    <c:when test="${dto2.ctype == 7}">
			      <span class="badge bg-pink-500">가입인사</span>
			    </c:when>
			  </c:choose>
			
			  <!-- 제목 강조 -->
			  <span class="post-title-text">${dto2.ctitle}</span>
			</div>

		<div class="custom-divider"></div>
      	
     	<div class="post-meta">
			  <div class="left-meta">
			    <img src="<%=contextPath%>/img/2020user.png" alt="작성자 아이콘" class="user-icon">
			    <span class="username">${dto2.cwuser}</span>
			  </div>
			  <div class="right-meta writedate">
			    ${dto2.cwdate}
			  </div>
		</div>
	 
      <div class="view-count">조회수 ${ dto2.cviewcount }</div>

      <div class="content">
        ${ dto2.ctext }
      </div>
      
      <div class="custom-divider"></div>
      
      
 		<div class="buttons">
 		<div class="left-buttons">
        <button class="button black"onclick="openPostModal('edit', ${dto2.cno})">수정하기</button>
        <button class="button red"onclick="openPostModal('delete', ${dto2.cno})">삭제하기</button>
        </div>
        <button type="button"
         		 onclick="location.href='<%= request.getContextPath() %>/community/list.do';"
         		 class="border px-4 py-2 rounded-md hover:bg-gray-100">
    			게시판 목록으로
  		</button>
      </div>
       <div class="custom-divider"></div>

<div class="comment-section">
  <!-- 댓글 작성 폼 -->
  <div class="comment-form">
    <form action="${pageContext.request.contextPath}/comments/write.do#comment-section" method="post">
      <input type="hidden" name="cno" value="${dto2.cno}" />

      <div class="form-row">
        <input type="text" name="rwuser" placeholder="작성자" required />
        <input type="password" name="rpw" placeholder="비밀번호" required />
      </div>

      <textarea name="rtext" rows="4" placeholder="댓글을 입력하세요" required></textarea>

      <div class="form-actions">
  <button type="submit">등록</button>
</div>
    </form>
  </div>

  <!-- 댓글 목록 출력 -->
  <h3 >💬 댓글 목록</h3>
  <div class="comment-list" id="comment-section">
    <c:if test="${not empty commentList}">
      <table>
        <tr>
          <th width="25%"></th>
          <th></th>
          <th width="20%"></th>
          <th width="10%"></th>
        </tr>
        <c:forEach var="reply" items="${commentList}">
          <tr id="reply-${reply.rno}">
			  <td>${reply.rwuser}</td>
			  <td>${reply.rtext}</td>
			  <td>${reply.rwdate}</td>
			  <td>
			    <a href="javascript:void(0);" onclick="openModal('delete', ${reply.rno}, ${dto2.cno})">삭제</a>
			  </td>
		 </tr>
        </c:forEach>
      </table>
    </c:if>

    <c:if test="${empty commentList}">
      <div class="no-comments">아직 등록된 댓글이 없습니다.</div>
    </c:if>
  </div>
</div>

<!-- 게시글 비밀번호 확인 모달 -->
<div id="postPwModal" style="display:none; position:fixed; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000;">
    <h4 id="postModalTitle">비밀번호 확인</h4>
    <input type="password" id="postModalPw" placeholder="비밀번호 입력" required />
    <br/><br/>
    <button onclick="submitPostModal()">확인</button>
    <button onclick="closePostModal()">취소</button>
</div>

<!-- 어두운 배경 -->
<div id="postModalOverlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background-color:rgba(0,0,0,0.5); z-index:999;"></div>


<!-- 댓글 비밀번호 입력 모달 -->
<div id="pwModal" style="display:none; position:fixed; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000;">
    <h4 id="modalTitle">비밀번호 확인</h4>
    <input type="password" id="modalPw" placeholder="비밀번호 입력" required />
    <br/><br/>
    <button onclick="submitModal()">확인</button>
    <button onclick="closeModal()">취소</button>
</div>

<!-- 어두운 배경 -->
<div id="modalOverlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background-color:rgba(0,0,0,0.5); z-index:999;"></div>

<script>
    const contextPath = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/js/CommunityView.js"></script>

</body>
</html>