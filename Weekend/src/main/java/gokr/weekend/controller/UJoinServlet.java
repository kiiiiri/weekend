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
 * Servlet implementation class JoinServlet
 */
@WebServlet("/join.do")
public class UJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/UJoin.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String usertype=request.getParameter("usertype");
		
		// model 객체 (VO) 에 화면에 입력된 정보를 넣는다
		UserVO uVo = new UserVO();
		uVo.setEmail(email);
		uVo.setPw(pw);
		uVo.setName(name);
		uVo.setNickname(nickname);
		uVo.setUsertype(Integer.parseInt(usertype));
		
		UserDAO uDao = UserDAO.getInstance();
		
		int result = uDao.insertUser(uVo); // insert 할 때 vo를 사용
		HttpSession session = request.getSession();
		if (result == 1) {
			session.setAttribute("email", uVo.getEmail());
			request.setAttribute("message", "회원 가입에 성공했습니다.");
		} else {
			request.setAttribute("message", "회원 가입에 실패했습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ULogin.jsp");
		dispatcher.forward(request, response);
	}

}


