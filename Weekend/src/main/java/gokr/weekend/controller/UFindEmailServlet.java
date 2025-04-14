package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.UserDAO;

/**
 * Servlet implementation class UFindEmailServlet
 */
@WebServlet("/findemail.do")
public class UFindEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UFindEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션에서 이메일 값이 있으면 삭제
	    request.getSession().removeAttribute("email");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("UFindEmail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유저 정보 입력 받기
				String pw = request.getParameter("pw");
				String name = request.getParameter("name");
				String nickname = request.getParameter("nickname");
				
				// 유저 정보로 이메일 찾기
				UserDAO uDao = UserDAO.getInstance();
				String email = uDao.findEmail(pw, name, nickname);
				
				// 이메일을 찾았으면, JSP 에 이메일 전달
				if (email != null) {
					request.setAttribute("email", email);
					RequestDispatcher dispatcher = request.getRequestDispatcher("UFindEmail.jsp");
			        dispatcher.forward(request, response);
				} else {
			        request.setAttribute("error", "입력하신 정보가 일치하는 회원 이메일이 존재하지 않습니다. 다시 확인해주세요");
			        RequestDispatcher dispatcher = request.getRequestDispatcher("UFindEmail.jsp");
			        dispatcher.forward(request, response);
			    }
	}

}
