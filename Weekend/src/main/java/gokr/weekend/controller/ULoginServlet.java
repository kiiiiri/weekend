package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gokr.weekend.dao.UserDAO;
import gokr.weekend.dto.UserVO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class ULoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ULoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="/Login.jsp";
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") != null) {// 이미 로그인 된 사용자이면
			url = "/Main.jsp"; // 메인 페이지로 이동한다.
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/Login.jsp";
	      String email = request.getParameter("email");
	      String pw = request.getParameter("pw");
	      UserDAO uDao = UserDAO.getInstance();
	      int result = uDao.userCheck(email, pw);
	      
	      if (result == 1) { // 이메일, 비밀번호가 일치할 때
	         UserVO uVo = uDao.getUser(email);
	         HttpSession session = request.getSession();
	         session.setAttribute("loginUser", uVo);
	        //  request.setAttribute("message", "회원 가입에 성공했습니다.");
	         url = request.getContextPath() + "/webzine/list.do";
	         response.sendRedirect(url); //주소 변경
	      } 
	      else if (result == 0) {
	    	 request.getSession().setAttribute("message", "비밀번호가 맞지 않습니다.");  // 세션에 메시지 설정
	         RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	 		 dispatcher.forward(request, response);
	      } 
	      else if (result == -1) {
	    	 request.getSession().setAttribute("message", "존재하지 않는 회원입니다.");  // 세션에 메시지 설정
	         RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	 		 dispatcher.forward(request, response);	
	      }
	      //RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		  //dispatcher.forward(request, response);
		 
	}

}
