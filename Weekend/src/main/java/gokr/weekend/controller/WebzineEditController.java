package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gokr.weekend.dao.WebzineDAO;
import gokr.weekend.dto.UserVO;
import gokr.weekend.dto.WebzineDTO;

/**
 * Servlet implementation class WebzineEditController
 */
@WebServlet("/webzine/edit.do")
public class WebzineEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebzineEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String wno = req.getParameter("wno");
		// 로그인 사용자 확인
				HttpSession session = req.getSession();
				UserVO loginUser = (UserVO) session.getAttribute("loginUser");

				if (wno == null || loginUser == null) {
					resp.sendRedirect(req.getContextPath() + "/webzine/list.do");
					return;
				}

				WebzineDAO dao = new WebzineDAO();
				WebzineDTO dto = dao.selectView(wno);
				dao.close();

				// 작성자와 로그인한 사용자가 다르면 접근 불가
				if (dto == null || Integer.parseInt(dto.getUno()) != loginUser.getUno()) {
				    resp.sendRedirect(req.getContextPath() + "/webzine/list.do");
				    return;
				}
				
				// 글 내용을 그대로 textarea에 보여줄 수 있도록 변환 제거
				dto.setWtext(dto.getWtext().replaceAll("<br\\s*/?>", "\r\n"));

				req.setAttribute("dto", dto);
				req.getRequestDispatcher("/WebzineEdit.jsp").forward(req, resp);
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
