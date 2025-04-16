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
import gokr.weekend.dao.WebzineDAO;
import gokr.weekend.dto.BoardDTO;
import gokr.weekend.dto.WebzineDTO;
import utils.BoardPage;

/**
 * Servlet implementation class WebzineListController
 */
@WebServlet("/webzine/list.do")
public class WebzineListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebzineListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			    WebzineDAO dao = new WebzineDAO();

	    int pageSize = 6;  // 무한스크롤에서는 고정 크기
	    int pageNum = 1;

	    String pageParam = req.getParameter("pageNum");
	    if (pageParam != null && !pageParam.equals("")) {
	        pageNum = Integer.parseInt(pageParam);
	    }

	    int start = (pageNum - 1) * pageSize + 1;
	    int end = pageNum * pageSize;

	    Map<String, Object> map = new HashMap<>();
	    map.put("start", start);
	    map.put("end", end);

	    List<WebzineDTO> boardLists = dao.selectListPage(map);
	    dao.close();

	    req.setAttribute("boardLists", boardLists);

	    // AJAX 요청이면 JSP 조각만 응답
	    if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
	        req.getRequestDispatcher("/ListData.jsp").forward(req, resp);
	    }
	    // 첫 로딩이면 전체 페이지 렌더링
	    else {
	        req.getRequestDispatcher("/Main.jsp").forward(req, resp);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
