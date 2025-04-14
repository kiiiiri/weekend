package gokr.weekend.controller;

import java.io.IOException;
import java.util.Map;

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
 * Servlet implementation class WebzineUpdateController
 */
@WebServlet("/webzine/update.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 1,
		maxRequestSize = 1024 * 1024 * 100
	)

public class WebzineUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebzineUpdateController() {
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		 req.setCharacterEncoding("UTF-8");

	        // 로그인 사용자 확인
	        HttpSession session = req.getSession();
	        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

	        if (loginUser == null) {
	            JSFunction.alertLocation(resp, "로그인이 필요합니다.", "../login.do");
	            return;
	        }

	        String saveDirectory = req.getServletContext().getRealPath("/Uploads");
	        WebzineDTO dto = new WebzineDTO();

	        // 기존 게시물 번호
	        String wno = req.getParameter("wno");
	        dto.setWno(wno);

	        // 제목, 내용 저장
	        dto.setWtitle(req.getParameter("wtitle"));
	        dto.setWtext(req.getParameter("wtext"));
	        dto.setUno(String.valueOf(loginUser.getUno()));

	        // TOAST UI 이미지 처리
	        String imageFiles = req.getParameter("imageFiles");
	        if (imageFiles != null && !imageFiles.isEmpty()) {
	            String[] filenames = imageFiles.split(",");
	            dto.setWofile(filenames[0]);
	            dto.setWsfile(filenames[0]);
	        }

	        // 일반 첨부파일 처리
	        String originalFileName = "";
	        try {
	            if (req.getPart("Wofile") != null && req.getPart("Wofile").getSize() > 0) {
	                originalFileName = FileUtil.uploadFile(req, saveDirectory);
	                String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
	                dto.setWofile(originalFileName);
	                dto.setWsfile(savedFileName);
	            }
	        } catch (Exception e) {
	            JSFunction.alertBack(resp, "파일 업로드 오류입니다.");
	            return;
	        }

	        // DB에 업데이트
	        WebzineDAO dao = new WebzineDAO();
	        int result = dao.updatePost(dto);
	        dao.close();
	        	
	        System.out.println("수정 대상 wno: " + dto.getWno());
	        System.out.println("제목: " + dto.getWtitle());
	        System.out.println("본문: " + dto.getWtext());
	        System.out.println("원본파일: " + dto.getWofile());
	        System.out.println("저장파일: " + dto.getWsfile());
	        System.out.println("업데이트 결과: " + result);
	        
	        if (result == 1) {
	            resp.sendRedirect("../webzine/wview.do?wno=" + wno);
	        } else {
	            JSFunction.alertBack(resp, "게시글 수정에 실패했습니다.");
	        }
	}

}
