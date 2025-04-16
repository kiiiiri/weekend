package gokr.weekend.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import gokr.weekend.dao.WebzineDAO;
import gokr.weekend.dto.UserVO;
import gokr.weekend.dto.WebzineDTO;
import utils.JSFunction;

/**
 * Servlet implementation class WebzineWriteController
 */
@WebServlet("/webzine/wwrite.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 1,  // 1mbyte == 1024kb == 1024*1024 byte
		maxRequestSize = 1024 * 1024 * 100 // 100mbyte
	)
public class WebzineWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebzineWriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/Webzinewrite.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 파일 업로드 처리 =============================
        // 업로드 디렉터리의 물리적 경로 확인
        String saveDirectory = req.getServletContext().getRealPath("/Uploads");
        System.out.println(saveDirectory);        
        WebzineDTO dto = new WebzineDTO(); 
        
        
        // 파일 업로드
        String originalFileName = "";
        try {
            // 업로드된 일반 첨부 파일이 있을 때만 처리
            if (req.getPart("Wofile") != null && req.getPart("Wofile").getSize() > 0) {
                originalFileName = FileUtil.uploadFile(req, saveDirectory);

                // 파일명 변경
                String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);

                dto.setWofile(originalFileName);  // 원래 파일 이름
                dto.setWsfile(savedFileName);     // 저장된 파일 이름
            }
        }
        catch (Exception e) {
            JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "../webzine/wwrite.do");
            return;
        }
        

        // 2. 파일 업로드 외 처리 =============================
        // 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = req.getSession();
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            JSFunction.alertLocation(resp, "로그인이 필요합니다.", "../login.do");
            return;
        }
        
        // 폼값을 DTO에 저장
        dto.setWtitle(req.getParameter("wtitle"));
        dto.setWtext(req.getParameter("wtext"));
        dto.setUno(String.valueOf(loginUser.getUno()));

        
     // ✅ TOAST UI 이미지 업로드 파일 처리
        String imageFiles = req.getParameter("imageFiles");
        if (imageFiles != null && !imageFiles.isEmpty()) {
            // 예: "20250411_145831691.png,20250411_145832005.png"
            String[] filenames = imageFiles.split(",");

            // 현재는 첫 번째 이미지 파일만 대표 이미지로 저장
            dto.setWofile(filenames[0]);
            dto.setWsfile(filenames[0]); // 서버에 저장된 파일명이므로 동일
        }
        
        // 원본 파일명과 저장된 파일 이름 설정
        if (originalFileName != "") { 
        	// 파일명 변경
        	String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
        	
            dto.setWofile(originalFileName);  // 원래 파일 이름
            dto.setWsfile(savedFileName);  // 서버에 저장된 파일 이름
        }

        // DAO를 통해 DB에 게시 내용 저장
        WebzineDAO dao = new WebzineDAO();
        int result = dao.insertWrite(dto);
        dao.close();

        // 성공 or 실패?	
        if (result == 1) {  // 글쓰기 성공
            resp.sendRedirect("../webzine/list.do");
            }
        else {  // 글쓰기 실패
        	 JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.",
                     "../webzine/wwrite.do");
        }
	}

}
