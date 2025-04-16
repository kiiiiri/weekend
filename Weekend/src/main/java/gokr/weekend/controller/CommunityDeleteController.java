package gokr.weekend.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gokr.weekend.dao.BoardDAO;

/**
 * Servlet implementation class CommunityDeleteController
 */
@WebServlet("/community/delete.ajax")
public class CommunityDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// JSON 데이터를 읽기 위한 BufferedReader
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(reader, Map.class);
        
        String cno = String.valueOf(((Double) data.get("cno")).intValue());
        System.out.println("삭제 요청 받은 CNO: " + cno);  // 로그 추가
        
        BoardDAO dao = new BoardDAO();
        int result = dao.deletePost(cno); // 게시물 삭제
        System.out.println("삭제 결과: " + result);  // 삭제 성공 여부 출력
        
        Map<String, Object> resultMap = Map.of("success", result == 1); // 성공 여부
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(resultMap));  // JSON 응답 보내기
	}

}
