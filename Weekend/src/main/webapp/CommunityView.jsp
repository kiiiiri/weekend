<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.wrap_01 img {
  display: block; 
  max-width: 70%;
}
</style>
</head>
<body>
<table border="1" width="90%">
    <colgroup>
        <col width="15%"/> <col width="35%"/>
        <col width="15%"/> <col width="*"/>
    </colgroup>

    <!-- 게시글 정보 -->
    <tr>
        <td>번호</td> <td>${ dto2.cno }</td>
        <td>작성자</td> <td>${ dto2.cwuser }</td>
    </tr>
    <tr>
        <td>작성일</td> <td>${ dto2.cwdate }</td>
        <td>조회수</td> <td>${ dto2.cviewcount }</td>
    </tr>
    <tr>
        <td>제목</td>
        <td colspan="3">${ dto2.ctitle }</td>
    </tr>
    <tr>
        <td>내용</td>
        <td colspan="3" height="100" class="wrap_01">
        	${ dto2.ctext }
        </td>
    </tr>

    <!-- 하단 메뉴(버튼) -->
    <tr>
        <td colspan="4" align="center">
            <button type="button" onclick="location.href='/community/pass.do?mode=edit&cno=${ param.cno }';">
                수정하기
            </button>
            <button type="button" onclick="location.href='/community/pass.do?mode=delete&cno=${ param.cno }';">
                삭제하기
            </button>
            <button type="button" onclick="location.href='/community/list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>


</body>
</html>