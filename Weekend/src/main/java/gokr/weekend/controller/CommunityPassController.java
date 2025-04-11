package gokr.weekend.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import gokr.weekend.dao.BoardDAO;
import gokr.weekend.dto.BoardDTO;
import utils.JSFunction;

/**
 * Servlet implementation class CommunityPassController
 */
@WebServlet("/community/pass.do")
public class CommunityPassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityPassController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/CommunityPass.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cno=req.getParameter("cno");
		String mode=req.getParameter("mode");
		String cpw=req.getParameter("cpw");
		
		BoardDAO dao=new BoardDAO();
		boolean confirmed=dao.confirmPassword(cpw, cno);
		dao.close();
		
		if(confirmed) { //비밀번호 일치하는 경우
			if(mode.equals("edit")) { //수정하는 경우
				HttpSession session=req.getSession();
				session.setAttribute("pass",cpw); // 세션 Attribute에 비밀번호저장
				resp.sendRedirect("/community/edit.do?cno="+cno);//수정화면으로 이동
			}else if(mode.equals("delete")){ //삭제하는 경우
				dao=new BoardDAO();
				BoardDTO dto=dao.selectView(cno);
				int result=dao.deletePost(cno); //글삭제
				dao.close();
				if(result==1) {//글삭제성공시
					String saveFileName=dto.getCsfile(); //저장된 파일명
					FileUtil.deleteFile(req, "/Uploads", saveFileName); //파일삭제
				}
				JSFunction.alertLocation(resp, "삭제되었습니다.","/community/list.do");
			}
		}else { //비밀번호 불일치하는 경우
			JSFunction.alertBack(resp, "비밀번호가 일치하지 않습니다.");
		}
	}

}
