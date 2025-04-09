package gokr.weekend.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import gokr.weekend.common.DBConnPool;
import gokr.weekend.dto.WebzineDTO;

public class WebzineDAO extends DBConnPool{
	public WebzineDAO() {
		super();
	}
	
	//검색 시 조건 충족 게시글 개수 반환
			public int selectCount(Map<String, Object> map) {
				int totalCount =0;
				String query = "SELECT COUNT(*) FROM WEBZINE";
				if (map.get("searchWord") != null) {
						query += " WHERE " + map.get("searchField") + " "
								+ " LIKE '%" + map.get("searchWord") + "%'";
				} try { 
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					rs.next();
					totalCount = rs.getInt(1);
				} catch (Exception e) {
					System.out.println("게시글 개수 카운팅 중 오류 발생..");
					e.printStackTrace();
				}
				return totalCount;
			} 
			
			//검색 조건에 맞는 게시물 목록을 반환(+페이징.)
			public List<WebzineDTO> selectListPage(Map<String,Object> map) {
		        List<WebzineDTO> board = new Vector<WebzineDTO>();
		        String query = " "
		        	    + "SELECT * FROM ( "
		        	    + "    SELECT Tb.*, ROWNUM rNum FROM ( "
		        	    + "        SELECT w.*, s.nickname FROM WEBZINE w "
		        	    + "        JOIN SITEMEMBER s ON w.uno = s.uno ";

		        if (map.get("searchWord") != null) {
		            query += " WHERE " + map.get("searchField")
		                   + " LIKE '%" + map.get("searchWord") + "%' ";
		        }

		        query += "        ORDER BY w.wno DESC "
		        	       + "    ) Tb "
		        	       + " ) "
		        	       + " WHERE rNum BETWEEN ? AND ?";

		        try {
		            psmt = con.prepareStatement(query);
		            psmt.setString(1, map.get("start").toString());
		            psmt.setString(2, map.get("end").toString());
		            rs = psmt.executeQuery();

		            while (rs.next()) {
		            	WebzineDTO dto = new WebzineDTO();

		                dto.setWno(rs.getString(1));
		                dto.setWtitle(rs.getString(2));
		                dto.setWtext(rs.getString(3));
		                dto.setWwdate(rs.getDate(4));
		                dto.setWviewcount(rs.getInt(5));
		                dto.setWofile(rs.getString(6));
		                dto.setWsfile(rs.getString(7));	
		                dto.setUno(rs.getString(8));
		                dto.setNickname(rs.getString(9));
		                board.add(dto);
		            }
		        }
		        catch (Exception e) {
		            System.out.println("게시물 조회 중 예외 발생");
		            e.printStackTrace();
		        }
		        return board;
		    }
		    // 게시글 데이터를 받아 DB에 추가합니다(파일 업로드 지원).
		    public int insertWrite(WebzineDTO dto) {
		        int result = 0;
		        try {
		            String query = "INSERT INTO WEBZINE ( "
		                         + " wno, wtitle, wtext, wofile, wsfile, uno) "
		                         + " VALUES ( "
		                         + " seq_board_num.NEXTVAL,?,?,?,?,?)";
		            psmt = con.prepareStatement(query);
		            psmt.setString(1, dto.getWtitle());
		            psmt.setString(2, dto.getWtext());
		            psmt.setString(3, dto.getWofile());
		            psmt.setString(4, dto.getWsfile());
		            psmt.setString(5, dto.getUno());
		            result = psmt.executeUpdate();
		        }
		        catch (Exception e) {
		            System.out.println("게시물 입력 중 예외 발생");
		            e.printStackTrace();
		        }
		        return result;
		    }
		    
		 // 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
		    public WebzineDTO selectView(String wno) {
		    	WebzineDTO dto = new WebzineDTO();  // DTO 객체 생성
		        String query = "SELECT * FROM WEBZINE WHERE wno=?";  // 쿼리문 템플릿 준비
		        try {
		            psmt = con.prepareStatement(query);  // 쿼리문 준비
		            psmt.setString(1, wno);  // 인파라미터 설정
		            rs = psmt.executeQuery();  // 쿼리문 실행

		            if (rs.next()) {  // 결과를 DTO 객체에 저장
		                dto.setWno(rs.getString(1));
		                dto.setWtitle(rs.getString(2));
		                dto.setWtext(rs.getString(3));
		                dto.setWwdate(rs.getDate(4));
		                dto.setWviewcount(rs.getInt(5));
		                dto.setWofile(rs.getString(6));
		                dto.setWsfile(rs.getString(7));
		                dto.setUno(rs.getString(8));
		            }
		        }
		        catch (Exception e) {
		            System.out.println("게시물 상세보기 중 예외 발생");
		            e.printStackTrace();
		        }
		        return dto;  // 결과 반환
		    }

		    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킵니다.
		    public void updateVisitCount(String wno) {
		        String query = "UPDATE WEBZINE SET "
		                     + " wviewcount=wviewcount+1 "
		                     + " WHERE wno=?"; 
		        try {
		            psmt = con.prepareStatement(query);
		            psmt.setString(1, wno);
		            psmt.executeQuery();
		        }
		        catch (Exception e) {
		            System.out.println("게시물 조회수 증가 중 예외 발생");
		            e.printStackTrace();
		        }
		    }
		    
}
