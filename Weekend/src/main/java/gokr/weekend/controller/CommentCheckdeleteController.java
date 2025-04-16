package gokr.weekend.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gokr.weekend.dao.CommentsDAO;
import gokr.weekend.dto.CommentsDeleteRequest;

/**
 * Servlet implementation class CommentCheckEditController
 */
@WebServlet("/comments/delete.do")
public class CommentCheckdeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentCheckdeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 // JSON 
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();

        CommentsDeleteRequest data = gson.fromJson(reader, CommentsDeleteRequest.class);
        int rno = data.getRno();
        String rpw = data.getRpw();

        CommentsDAO dao = new CommentsDAO();

        // 비밀번호 확인
        boolean verified = dao.checkPassword(rno, rpw);

        Map<String, Object> result = new HashMap<>();
        result.put("success", verified);

        if (verified) {
            int delResult = dao.deleteComment(rno, rpw);
            result.put("deleted", delResult > 0);
        }

        dao.close();

        // 응답 타입 및 JSON 반환
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(result));
	}

}
