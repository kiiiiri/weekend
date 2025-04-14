package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gokr.weekend.dto.UserVO;

/**
 * Servlet implementation class UMyPageServlet
 */
@WebServlet("/mypage.do")
public class UMyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UMyPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        // 로그인 상태를 JSP로 전달
        request.setAttribute("loginUser", loginUser);

	
        RequestDispatcher dispatcher = request.getRequestDispatcher("UMyPage.jsp");
        dispatcher.forward(request, response);  // 마이페이지로 요청을 포워딩
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
