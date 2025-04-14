package gokr.weekend.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.CommentsDAO;
import gokr.weekend.dto.CommentsDTO;

/**
 * Servlet implementation class CommentwriteController
 */
@WebServlet("/comments/write.do")
public class CommentwriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentwriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        // 폼에서 넘어온 파라미터 받기
        String rwuser = request.getParameter("rwuser");
        String rpw = request.getParameter("rpw");
        String rtext = request.getParameter("rtext");
        String cnoStr = request.getParameter("cno");

        int cno = Integer.parseInt(cnoStr);

        // DTO 객체 생성
        CommentsDTO dto = new CommentsDTO();
        dto.setRwuser(rwuser);
        dto.setRpw(rpw);
        dto.setRtext(rtext);
        dto.setCno(cno);

        // DAO 호출해서 댓글 등록
        CommentsDAO dao = new CommentsDAO();
        int result = dao.insertComments(dto);

        // 등록 성공 시 해당 게시글 상세 페이지로 리다이렉트
        String redirectURL = request.getContextPath() + "/community/cview.do?cno=" + cno;

        response.sendRedirect(redirectURL);
	}

}
