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
				WebzineDTO dto = new WebzineDTO();
				String query = "SELECT w.*, s.nickname FROM WEBZINE w "
										+ "JOIN SITEMEMBER s ON w.uno = s.uno "
										+ "WHERE w.wno = ?";
				try {
						psmt = con.prepareStatement(query);
						psmt.setString(1, wno);
						rs = psmt.executeQuery();

						if (rs.next()) {
								dto.setWno(rs.getString(1));
								dto.setWtitle(rs.getString(2));
								dto.setWtext(rs.getString(3));
								dto.setWwdate(rs.getDate(4));
								dto.setWviewcount(rs.getInt(5));
								dto.setWofile(rs.getString(6));
								dto.setWsfile(rs.getString(7));
								dto.setUno(rs.getString(8));
								dto.setNickname(rs.getString(9));  // 닉네임 추가
						}
				} catch (Exception e) {
						System.out.println("게시물 상세보기 중 예외 발생");
						e.printStackTrace();
				}
				return dto;
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
		    
		    
		    //기사 정보 수정
		    public int updatePost(WebzineDTO dto) {
		        int result = 0;
		        try {
		            // 쿼리문 템플릿 준비
		        	String query = "UPDATE webzine"
		                    + " SET wtitle = ?,"
		                    + "     wtext = ?,"
		                    + "     wofile = CASE WHEN ? IS NULL OR ? = '' THEN wofile ELSE ? END,"
		                    + "     wsfile = CASE WHEN ? IS NULL OR ? = '' THEN wsfile ELSE ? END"
		                    + " WHERE wno = ?";

		            // 쿼리문 준비
		        	psmt = con.prepareStatement(query);
		        	
		        	psmt.setString(1, dto.getWtitle());
		        	psmt.setString(2, dto.getWtext());
		        	psmt.setString(3, dto.getWofile()); // CASE 조건용
		        	psmt.setString(4, dto.getWofile()); // 공백 검사용
		        	psmt.setString(5, dto.getWofile()); // 대입용

		        	psmt.setString(6, dto.getWsfile());
		        	psmt.setString(7, dto.getWsfile());
		        	psmt.setString(8, dto.getWsfile());

		        	psmt.setString(9, dto.getWno());

		            // 쿼리문 실행
		            result = psmt.executeUpdate();
		        }
		        catch (Exception e) {
		            System.out.println("기사 수정 중 예외 발생");
		            e.printStackTrace();
		        }
		        return result;
		    }
		    
		    //기사 삭제
		    public int deletePost(String wno) {
		        int result = 0;
		        try {
		            String query = "DELETE FROM webzine WHERE wno=?";
		            psmt = con.prepareStatement(query);
		            psmt.setInt(1, Integer.parseInt(wno));
		            result = psmt.executeUpdate();
		        }
		        catch (Exception e) {
		            System.out.println("게시물 삭제 중 예외 발생");
		            e.printStackTrace();
		        }
		        return result;
		    }
		    
}
