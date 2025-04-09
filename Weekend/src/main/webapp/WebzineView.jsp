<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" width="90%">
    <colgroup>
        <col width="15%"/> <col width="35%"/>
        <col width="15%"/> <col width="*"/>
    </colgroup>

    <!-- 게시글 정보 -->
    <tr>
        <td>번호</td> <td>${ dto.wno }</td>
        <td>작성자</td> <td>${ dto.nickname }</td>
    </tr>
    <tr>
        <td>작성일</td> <td>${ dto.wwdate }</td>
        <td>조회수</td> <td>${ dto.wviewcount }</td>
    </tr>
    <tr>
        <td>제목</td>
        <td colspan="3">${ dto.wtitle }</td>
    </tr>
    <tr>
        <td>내용</td>
        <td colspan="3" height="100">
        	${ dto.wtext }
        	<c:if test="${ not empty dto.wofile and isImage eq true }">
        		<br><img src="../Uploads/${ dto.wsfile }" style="max-width:100%;"/>
        	</c:if>
        </td>
    </tr>

    <!-- 첨부파일 -->
    <tr>
        <td>첨부파일</td>
        <td>
            <c:if test="${ not empty dto.wofile }">
            ${ dto.wofile }
            <a href="../mvcboard/download.do?wofile=${ dto.wofile }&wsfile=${ dto.wsfile }&idx=${ dto.wno }">
                [다운로드]
            </a>
            </c:if>
        </td>
        <td>다운로드수</td>
        <td></td>
    </tr>

    <!-- 하단 메뉴(버튼) -->
    <tr>
        <td colspan="4" align="center">
            <button type="button" onclick="location.href='../mvcboard/pass.do?mode=edit&idx=${ param.wno }';">
                수정하기
            </button>
            <button type="button" onclick="location.href='../mvcboard/pass.do?mode=delete&idx=${ param.wno }';">
                삭제하기
            </button>
            <button type="button" onclick="location.href='../mvcboard/list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>


</body>
</html>