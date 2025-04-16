package gokr.weekend.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
 * Servlet implementation class CommunityEditController
 */
@WebServlet("/community/edit.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 1,
		maxRequestSize = 1024 * 1024 * 100
	)

public class CommunityEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cno=req.getParameter("cno");
		
		BoardDAO dao=new BoardDAO();
		BoardDTO dto=dao.selectView(cno);
		
		req.setAttribute("dto", dto);  
		req.setAttribute("cno", cno);
		req.getRequestDispatcher("/CommunityEdit.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 파일 업로드 처리 =============================
        // 업로드 디렉터리의 물리적 경로 확인
        String saveDirectory = req.getServletContext().getRealPath("/Uploads");
        System.out.println(saveDirectory);        
        BoardDTO dto2 = new BoardDTO(); 
        
        // 파일 업로드
        String originalFileName = "";
        try {
            // 업로드된 일반 첨부 파일이 있을 때만 처리
            if (req.getPart("Wofile") != null && req.getPart("Wofile").getSize() > 0) {
                originalFileName = FileUtil.uploadFile(req, saveDirectory);

                // 파일명 변경
                String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);

                dto2.setCofile(originalFileName);  // 원래 파일 이름
                dto2.setCsfile(savedFileName);     // 저장된 파일 이름
            }
        }
        catch (Exception e) {
            JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "../community/edit.do");
            return;
        }

        // 2. 파일 업로드 외 처리 =============================
        
        String cno=req.getParameter("cno");
		String prevOfile=req.getParameter("prevOfile"); //이전 원본파일명
		String prevSfile=req.getParameter("prevSfile"); //이전 저장파일명
		
		String cwuser=req.getParameter("cwuser");
		String ctitle=req.getParameter("ctitle");
		String ctext=req.getParameter("ctext");
		
		String ctypeParam = req.getParameter("ctype");
		int ctype = 1; 

        if (ctypeParam != null && !ctypeParam.trim().equals("")) {
            try {
                ctype = Integer.parseInt(ctypeParam);
            } catch (NumberFormatException e) {
                ctype = 1; 
            }
        }
		
		HttpSession session=req.getSession();
		String cpw = req.getParameter("cpw");
		
        // model에 데이터 저장
		dto2.setCno(cno);
		dto2.setCwuser(cwuser);
		dto2.setCtitle(ctitle);
		dto2.setCtext(ctext);
		dto2.setCpw(cpw);
		dto2.setCtype(ctype);
		
		// 원본 파일명과 저장된 파일 이름 설정
        if (originalFileName != "") { 
        	// 파일명 변경
        	String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
        	
            dto2.setCofile(originalFileName);  // 원래 파일 이름
            dto2.setCsfile(savedFileName);  // 서버에 저장된 파일 이름
            
            //삭제시 기존파일 삭제 
            FileUtil.deleteFile(req, "/Uploads", prevSfile);//기존파일 삭제
    		}else { // 첨부파일을 변경하지 않는 경우. 기존 파일명 그대로 사용 
    		dto2.setCofile(prevOfile); //기존 원본파일명
    		dto2.setCsfile(prevSfile); //기존 저장파일명
    		}
        
        
        	// DAO를 통해 DB에 게시 내용 저장
        	BoardDAO dao2 = new BoardDAO();
        	int result = dao2.updatePost(dto2);
        	dao2.close();

        	// 성공 or 실패?	
        	if (result == 1) {  // 수정 성공
        	session.removeAttribute("cpw"); // pass attribute 삭제
    		resp.sendRedirect("../community/cview.do?cno="+cno);
            }
        	else {  // 업데이트 실패
        		JSFunction.alertLocation(resp, "수정에 실패했습니다.", "../community/cview.do?cno=" + cno);
        	 
        	 
        	}
	}
}
