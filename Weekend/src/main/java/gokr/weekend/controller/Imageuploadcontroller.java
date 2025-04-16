package gokr.weekend.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileupload.FileUtil;

/**
 * Servlet implementation class Imageuploadcontroller
 */
@WebServlet("/webzine/imageupload.do")
@MultipartConfig(
	    maxFileSize = 1024 * 1024 * 5, // 5MB
	    maxRequestSize = 1024 * 1024 * 100 // 100MB
	)
public class Imageuploadcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Imageuploadcontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
        String originalFileName = FileUtil.uploadFile(req, saveDirectory);

        String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
        String imageUrl = req.getContextPath() + "/Uploads/" + savedFileName;

        // JSON 응답 보내기
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write("{ \"success\": 1, \"message\": \"업로드 성공\", \"url\": \"" + imageUrl + "\" }");
	}

}
