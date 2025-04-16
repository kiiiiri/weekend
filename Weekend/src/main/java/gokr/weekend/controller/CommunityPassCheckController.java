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

import gokr.weekend.dao.BoardDAO;

/**
 * Servlet implementation class CommunityPassCheckController
 */
@WebServlet("/community/checkpass.ajax")
public class CommunityPassCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityPassCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// JSON 데이터를 읽기 위한 BufferedReader
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(reader, Map.class);
        
        String cno = String.valueOf(((Double) data.get("cno")).intValue());
        String cpw = (String) data.get("cpw"); // cpw를 받아옵니다.

        BoardDAO dao = new BoardDAO();
        boolean confirmed = dao.confirmPassword(cpw, cno);
        dao.close();

        // 응답으로 성공/실패를 보냅니다.
        Map<String, Object> result = Map.of("success", confirmed);
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(result));  // JSON 응답 보내기
	}

}
