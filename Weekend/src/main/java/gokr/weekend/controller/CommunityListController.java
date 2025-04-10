package gokr.weekend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.BoardDAO;
import gokr.weekend.dto.BoardDTO;
import utils.BoardPage;

/**
 * Servlet implementation class ListController
 */
@WebServlet("/community/list.do")
public class CommunityListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// DAO 생성
	    BoardDAO dao = new BoardDAO();

	    int totalCount = dao.selectCount();  // 전체 게시물 수

	    int pageSize = 10;
	    int blockPage = 5;

	    int pageNum = 1;
	    String pageTemp = req.getParameter("pageNum");
	    if (pageTemp != null && !pageTemp.equals("")) {
	        pageNum = Integer.parseInt(pageTemp);
	    }

	    int start = (pageNum - 1) * pageSize + 1;
	    int end = pageNum * pageSize;

	    List<BoardDTO> boardLists2 = dao.selectListPage(start, end);
	    dao.close();

	    String pagingImg = BoardPage.pagingStr2(totalCount, pageSize, blockPage, pageNum, req.getContextPath() + "/community/list.do");

	    req.setAttribute("boardLists2", boardLists2);
	    req.setAttribute("pagingImg", pagingImg);
	    req.setAttribute("totalCount", totalCount);
	    req.setAttribute("pageNum", pageNum);
	    req.setAttribute("pageSize", pageSize);
	    

	    req.getRequestDispatcher("/CommunityBoard.jsp").forward(req, resp);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
