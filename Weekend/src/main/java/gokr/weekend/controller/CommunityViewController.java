package gokr.weekend.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.BoardDAO;
import gokr.weekend.dto.BoardDTO;

/**
 * Servlet implementation class CommunityViewController
 */
@WebServlet("/community/cview.do")
public class CommunityViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 불러오기
        BoardDAO dao2 = new BoardDAO();
        String cno = req.getParameter("cno");
        dao2.updateVisitCount(cno);  // 조회수 1 증가
        BoardDTO dto2 = dao2.selectView(cno);
        dao2.close();

        // 줄바꿈 처리
        dto2.setCtext(dto2.getCtext().replaceAll("\r\n", "<br/>"));
        
        //첨부파일 확장자 추출 및 이미지 타입 확인
        String ext = null, fileName = dto2.getCsfile();
        if(fileName!=null) {
        	ext = fileName.substring(fileName.lastIndexOf(".")+1);
        }
        String[] mimeStr2 = {"png","jpg","gif","bmp","jpeg","webp"};
        List<String> mimeList2 = Arrays.asList(mimeStr2);
        boolean isImage2 = false;
        if(mimeList2.contains(ext)) {
        	isImage2 = true;
        }
        
        //---------------------------------------
        
        // 게시물(dto2) 저장 후 뷰로 포워드
        req.setAttribute("dto2", dto2);
        req.setAttribute("isImage2", isImage2);
        req.getRequestDispatcher("/CommunityView.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
