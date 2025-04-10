package utils;

public class BoardPage {
	// static메서드
	public static String pagingStr2(int totalCount, int pageSize, int blockPage, int pageNum, String pageUrl) {
	    StringBuilder sb = new StringBuilder();

	    int totalPage = (int) Math.ceil((double) totalCount / pageSize);

	    if (totalPage <= 1) return "";

	    // [처음] 링크
	    if (pageNum != 1) {
	        sb.append("<a href='" + pageUrl + "?pageNum=1' class='page_link endlink'>◀◀</a>");
	    }

	    int startPage;
	    int endPage;

	    if (totalPage <= blockPage) {
	        startPage = 1;
	        endPage = totalPage;
	    } else {
	        if (pageNum <= 3) {
	            startPage = 1;
	            endPage = 5;
	        } else if (pageNum >= totalPage - 2) {
	            startPage = totalPage - 4;
	            endPage = totalPage;
	        } else {
	            startPage = pageNum - 2;
	            endPage = pageNum + 2;
	        }
	    }

	    // ... 앞 생략
	    if (startPage > 2) {
	        sb.append("<a href='" + pageUrl + "?pageNum=1'>1</a>");
	        sb.append("<a href='" + pageUrl + "?pageNum=2'>2</a>");
	        sb.append("<span>...</span>");
	    }

	    // 페이지 번호 출력
	    for (int i = startPage; i <= endPage; i++) {
	        if (i < 1 || i > totalPage) continue;

	        if (i == pageNum) {
	        	sb.append("<a class='page_link current'>" + i + "</a>");
	        } else {
	        	sb.append("<a href='" + pageUrl + "?pageNum=" + i + "' class='page_link'>" + i + "</a>");
	        }
	    }

	    // ... 뒤 생략
	    if (endPage < totalPage - 1) {
	        sb.append("<span>...</span>");
	        sb.append("<a href='" + pageUrl + "?pageNum=" + (totalPage - 1) + "'>" + (totalPage - 1) + "</a>");
	        sb.append("<a href='" + pageUrl + "?pageNum=" + totalPage + "'>" + totalPage + "</a>");
	    }

	    // [마지막] 링크
	    if (pageNum != totalPage) {
	    	sb.append("<a href='" + pageUrl + "?pageNum=" + totalPage + "' class='page_link endlink'>▶▶</a>");
	    }

	    return sb.toString();
	}
}
