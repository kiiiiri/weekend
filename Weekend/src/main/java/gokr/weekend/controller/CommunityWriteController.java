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
 * Servlet implementation class CommunityWriteController
 */
@WebServlet("/community/cwrite.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 1,  // 1mbyte == 1024kb == 1024*1024 byte
		maxRequestSize = 1024 * 1024 * 100 // 100mbyte
	)
public class CommunityWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityWriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/Communitywrite.jsp").forward(req, resp);
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
            JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "../community/cwrite.do");
            return;
        }
        
        // 카테고리 타입 변환
        String ctypeParam = req.getParameter("ctype");
        int ctype = 1; 

        if (ctypeParam != null && !ctypeParam.trim().equals("")) {
            try {
                ctype = Integer.parseInt(ctypeParam);
            } catch (NumberFormatException e) {
                ctype = 1; 
            }
        }
        
        // 2. 파일 업로드 외 처리 =============================
        
        // 폼값을 DTO에 저장
        dto2.setCtitle(req.getParameter("ctitle"));
        dto2.setCtext(req.getParameter("ctext"));
        dto2.setCwuser(req.getParameter("cwuser"));
        dto2.setCpw(req.getParameter("cpw"));
        dto2.setCtype(ctype);

        // 원본 파일명과 저장된 파일 이름 설정
        if (originalFileName != "") { 
        	// 파일명 변경
        	String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
        	
            dto2.setCofile(originalFileName);  // 원래 파일 이름
            dto2.setCsfile(savedFileName);  // 서버에 저장된 파일 이름
        }

        // DAO를 통해 DB에 게시 내용 저장
        BoardDAO dao2 = new BoardDAO();
        int result = dao2.insertWrite(dto2);
        dao2.close();

        // 성공 or 실패?	
        if (result == 1) {  // 글쓰기 성공
            resp.sendRedirect("../community/list.do");
            }
        else {  // 글쓰기 실패
        	 JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.",
                     "../community/cwrite.do");
	}

}
}
