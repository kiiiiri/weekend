package gokr.weekend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gokr.weekend.dao.WebzineDAO;

/**
 * Servlet implementation class WebzineDeleteController
 */
@WebServlet("/webzine/delete.do")
public class WebzineDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebzineDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String wno = req.getParameter("wno");

        WebzineDAO dao = new WebzineDAO();
        int result = dao.deletePost(wno);
        dao.close();

        if (result == 1) {
            // 삭제 성공 
            resp.sendRedirect(req.getContextPath() + "/webzine/list.do");
        } else {
            // 삭제 실패
        	resp.setContentType("text/html; charset=UTF-8"); 
        	PrintWriter out = resp.getWriter();
        	out.println("<script>");
            out.println("alert('삭제에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
