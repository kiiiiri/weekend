package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.UserDAO;
import gokr.weekend.dto.UserVO;

/**
 * Servlet implementation class UFindPwServlet
 */
@WebServlet("/findpw.do")
public class UFindPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UFindPwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션에서 이메일 값이 있으면 삭제
	    request.getSession().removeAttribute("email");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("UFindPw.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 유저 정보 입력 받기
	    String email = request.getParameter("email");
	    
	    // 이메일로 유저 정보 찾기
	    UserDAO uDao = UserDAO.getInstance();
	    UserVO uVo = uDao.getUser(email);
	            
	    if (uVo != null) {
	        // 이메일이 존재하면 비밀번호 수정 페이지로 이동
	        request.setAttribute("email", email);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UUpdatePw.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        // 이메일이 존재하지 않으면 오류 메시지 표시
	        request.setAttribute("error", "입력하신 정보가 일치하는 회원 이메일이 존재하지 않습니다. 다시 확인해주세요");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UFindPw.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	}

}
