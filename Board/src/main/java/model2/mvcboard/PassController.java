package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

/**
 * Servlet implementation class PassController
 */
@WebServlet("/mvcboard/pass.do")
public class PassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/14MVCBoard/Pass.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx=req.getParameter("idx");
		String mode=req.getParameter("mode");
		String pass=req.getParameter("pass");
		
		MVCBoardDAO dao=new MVCBoardDAO();
		boolean confirmed=dao.confirmPassword(pass, idx);
		dao.close();
		
		if(confirmed) { //비밀번호 일치하는 경우
			if(mode.equals("edit")) { //수정하는 경우
				HttpSession session=req.getSession();
				session.setAttribute("pass",pass); // 세션 Attribute에 비밀번호저장
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);//수정화면으로 이동
			}else if(mode.equals("delete")){ //삭제하는 경우
				dao=new MVCBoardDAO();
				MVCBoardDTO dto=dao.selectView(idx);
				int result=dao.deletePost(idx); //글삭제
				dao.close();
				if(result==1) {//글삭제성공시
					String saveFileName=dto.getSfile(); //저장된 파일명
					FileUtil.deleteFile(req, "/Uploads", saveFileName); //파일삭제
				}
				JSFunction.alertLocation(resp, "삭제되었습니다.","../mvcboard/list.do");
			}
		}else { //비밀번호 불일치하는 경우
			JSFunction.alertBack(resp, "비밀번호가 일치하지 않습니다.");
		}
	}

}
