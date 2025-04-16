<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="row" items="${boardLists}">
  <div class="recentwebzine_contents">
  
    <div class="contents_left">
      <div class="thumbnail">
        <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}">
          <img src="${pageContext.request.contextPath}/Uploads/${row.wsfile}" />
        </a>
      </div>
    </div>

    <div class="contents_right">
      <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}">
        <h2>${row.wtitle}</h2>
      </a>
      <p class="webzine_date">${row.wwdate}</p>
      <p class="webzine_writer">${row.nickname}</p>
      <p class="webzine_viewcount">조회수 ${row.wviewcount}</p>
      <a href="${pageContext.request.contextPath}/webzine/wview.do?wno=${row.wno}" class="webzine_link">상세보기 &gt;</a>
    </div>
  </div>
</c:forEach>