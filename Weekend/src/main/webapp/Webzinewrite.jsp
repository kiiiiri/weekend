<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=contextPath%>/js/WebzineWrite.js"></script>
</head>
<body>
<h2>파일 첨부형 게시판 - 글쓰기(Write)</h2>
<form name="writeForm" method="post" enctype="multipart/form-data"
      action="<%= request.getContextPath() %>/webzine/wwrite.do" onsubmit="return validateForm(this);">
<table border="1" width="90%">
    <tr>
        <td>제목</td>
        <td>
            <input type="text" name="wtitle" style="width:90%;" />
        </td>
    </tr>
    <tr>
        <td>내용</td>
        <td>
            <textarea name="wtext" style="width:90%;height:100px;"></textarea>
        </td>
    </tr>
    <tr>
        <td>첨부 파일</td>
        <td>
            <input type="file" name="Wofile" />
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button type="submit">작성 완료</button>
            <button type="reset">RESET</button>
            <button type="button" onclick="location.href='<%= request.getContextPath() %>/webzine/list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>    
</form>
</body>
</html>