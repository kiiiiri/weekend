package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

/**
 * Servlet implementation class EditController
 */
@WebServlet("/mvcboard/edit.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 1,
		maxRequestSize = 1024 * 1024 * 10
	)
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx=req.getParameter("idx");
		MVCBoardDAO dao=new MVCBoardDAO();
		MVCBoardDTO dto=dao.selectView(idx);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveDirectory=req.getServletContext().getRealPath("/Uploads");
		
		String originalFileName="";
		try {
			originalFileName=FileUtil.uploadFile(req, saveDirectory);
		}catch(Exception e) {
			JSFunction.alertBack(resp, "파일업로드 오류입니다.");
			return; // doPost() 종료
		}
		
		String idx=req.getParameter("idx");
		String prevOfile=req.getParameter("prevOfile"); //이전 원본파일명
		String prevSfile=req.getParameter("prevSfile"); //이전 저장파일명
		
		String name=req.getParameter("name");
		String title=req.getParameter("title");
		String content=req.getParameter("content");
		
		HttpSession session=req.getSession();
		String pass=(String)session.getAttribute("pass");
		//model에 데이터저장
		MVCBoardDTO dto=new MVCBoardDTO();
		dto.setIdx(idx);
		dto.setName(name);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPass(pass);
		
		if(originalFileName!="") { //첨부파일을 변경하는 경우
			String savedFileName=FileUtil.renameFile(saveDirectory, originalFileName);
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
			
			FileUtil.deleteFile(req, "/Uploads", prevSfile);//기존파일 삭제
		}else { // 첨부파일을 변경하지 않는 경우. 기존 파일명 그대로 사용 
			dto.setOfile(prevOfile); //기존 원본파일명
			dto.setSfile(prevSfile); //기존 저장파일명
		}
		
		MVCBoardDAO dao=new MVCBoardDAO();
		int result=dao.updatePost(dto);
		dao.close();
		if(result==1) { // 업데이트 성공한 경우
			session.removeAttribute("pass"); // pass attribute 삭제
			resp.sendRedirect("../mvcboard/view.do?idx="+idx);
		}else { // 업데이트 실패한 경우
			JSFunction.alertLocation(resp, "수정이 실패하였습니다" , "../mvcboard/view.do?idx="+idx);
		}
	}

}
