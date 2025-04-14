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
 * Servlet implementation class UUpdatePwServlet
 */
@WebServlet("/updatepw.do")
public class UUpdatePwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UUpdatePwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 이메일 파라미터를 받아서 비밀번호 변경 페이지로 이동
        String email = request.getParameter("email");
        if (email != null && !email.isEmpty()) {
            request.setAttribute("email", email);  // 이메일을 JSP로 전달
            RequestDispatcher dispatcher = request.getRequestDispatcher("UUpdatePw.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("UFindPw.jsp");  // 이메일이 없으면 찾기 페이지로 이동
        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 // 새 비밀번호와 이메일 받기
        String email = request.getParameter("email");
        String newPw = request.getParameter("pw");

        // 이메일과 새 비밀번호를 UserVO 객체에 세팅
        UserVO uVo = new UserVO();
        uVo.setEmail(email);
        uVo.setPw(newPw);

        // 비밀번호 업데이트
        UserDAO uDao = UserDAO.getInstance();
        int result = uDao.updatePw(uVo); // DAO 메서드를 통해 비밀번호 업데이트

        if (result > 0) {
        	request.setAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("ULogin.jsp");
            dispatcher.forward(request, response);
        } else {
            // 실패 시 오류 메시지를 전달
            request.setAttribute("error", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("UUpdatePw.jsp").forward(request, response);
        }
        
	}

}
