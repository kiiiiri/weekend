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
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/userupdate.do")
public class UUserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UUserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String from = request.getParameter("from");
		if (from != null) {
	        request.setAttribute("from", from);
	    }
		
		HttpSession session = request.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser"); // 세션에서 로그인된 사용자 가져오기

        if (loginUser == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션
            response.sendRedirect("login.do");
            return;
        }
        
        String email = loginUser.getEmail(); // 로그인된 사용자의 이메일 가져오기
        UserDAO uDao = UserDAO.getInstance();
        UserVO uVo = uDao.getUser(email); // 사용자 정보 가져오기
        request.setAttribute("uVo", uVo); // view로 uVo 전달
        RequestDispatcher dispatcher = request.getRequestDispatcher("UUserUpdate.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // 한글 깨짐을 방지

        // 세션에서 로그인된 사용자 정보 가져오기
        HttpSession session = request.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션
            response.sendRedirect("login.do");
            return;
        }

        // 폼에서 입력한 새로운 비밀번호와 닉네임
        String newpw = request.getParameter("pw");
        String nickname = request.getParameter("nickname");
        String originalNickname = request.getParameter("originalNickname"); // 원래 닉네임

        // 닉네임 중복 체크 (닉네임이 변경되었을 때만 체크)
        boolean isNicknameDuplicated = false;
        if (nickname != null && !nickname.equals(originalNickname)) {
            int nicknameCheckResult = UserDAO.getInstance().confirmNickname(nickname);
            if (nicknameCheckResult == 1) {
                // 닉네임이 중복된 경우
                isNicknameDuplicated = true;
                request.setAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
                request.getRequestDispatcher("UUserUpdate.jsp").forward(request, response);
                return;
            }
        }

        // UserVO 객체에 수정할 이메일, 비밀번호, 닉네임 세팅
        UserVO uVo = new UserVO();
        uVo.setEmail(loginUser.getEmail()); // 로그인된 사용자의 이메일 세팅

        // 비밀번호 변경 여부 확인
        if (newpw != null && !newpw.isEmpty()) {
            uVo.setPw(newpw); // 새로운 비밀번호 설정
        } else {
            uVo.setPw(loginUser.getPw()); // 비밀번호 변경이 없다면 기존 비밀번호 유지
        }

        // 닉네임 변경 여부 확인
        if (nickname != null && !nickname.isEmpty() && !nickname.equals(originalNickname)) {
            uVo.setNickname(nickname); // 새로운 닉네임 설정
        } else {
            uVo.setNickname(loginUser.getNickname()); // 닉네임 변경이 없다면 기존 닉네임 유지
        }

        // DB에서 업데이트
        int result = UserDAO.getInstance().updateUser(uVo);

        if (result > 0) {
            // 성공 시 세션의 로그인 사용자 정보 업데이트
            loginUser.setPw(uVo.getPw()); // 비밀번호 변경
            loginUser.setNickname(uVo.getNickname()); // 닉네임 변경
            session.setAttribute("loginUser", loginUser); // 세션에 업데이트된 사용자 정보 저장

            // 수정 완료 후 로그인 페이지로 리디렉션
            session.invalidate(); // 세션 초기화
            response.sendRedirect("login.do"); // 다시 로그인 페이지로
        } else {
            // 실패 시 오류 메시지 출력 (예: 수정 실패)
            request.setAttribute("errorMessage", "정보 수정에 실패했습니다.");
            request.getRequestDispatcher("UUserUpdate.jsp").forward(request, response);
        }
	}

}
