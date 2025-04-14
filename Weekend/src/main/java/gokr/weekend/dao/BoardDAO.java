package gokr.weekend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import gokr.weekend.common.DBConnPool;
import gokr.weekend.dto.BoardDTO;

public class BoardDAO extends DBConnPool {
		public BoardDAO() {
			super();
		}
		
		//검색어 포함 게시물 수 조회
		public int selectCount(String field, String word) {
		    int totalCount = 0;
		    String query = "SELECT COUNT(*) FROM community";

		    if (word != null && !word.trim().equals("")) {
		        query += " WHERE " + field + " LIKE ?";
		    }

		    try {
		        psmt = con.prepareStatement(query);

		        if (word != null && !word.trim().equals("")) {
		            psmt.setString(1, "%" + word + "%");
		        }

		        rs = psmt.executeQuery();
		        if (rs.next()) {
		            totalCount = rs.getInt(1);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return totalCount;
		}
		
		// 검색어 포함 일반 게시물 목록 조회(+페이징.)
		public List<BoardDTO> selectListPage(String field, String word, int start, int end) {
		    List<BoardDTO> list = new ArrayList<>();
		    String query = "SELECT * FROM ("
		                 + " SELECT Tb.*, ROWNUM rNum FROM ("
		                 + " SELECT b.*, "
		                 + " (SELECT COUNT(*) FROM comments c WHERE c.cno = b.cno) AS replyCount "
		                 + " FROM community b WHERE b.ctype != 5";  

		    if (word != null && !word.trim().equals("")) {
		        query += " AND b." + field + " LIKE ?";
		    }

		    query += " ORDER BY b.cno DESC"
		           + " ) Tb"
		           + " ) WHERE rNum BETWEEN ? AND ?";

		    try {
		        psmt = con.prepareStatement(query);

		        int idx = 1;
		        if (word != null && !word.trim().equals("")) {
		            psmt.setString(idx++, "%" + word + "%");
		        }
		        psmt.setInt(idx++, start);
		        psmt.setInt(idx, end);

		        rs = psmt.executeQuery();

		        while (rs.next()) {
		            BoardDTO dto = new BoardDTO();
		            dto.setCno(rs.getString("cno"));
		            dto.setCtitle(rs.getString("ctitle"));
		            dto.setCwdate(rs.getDate("cwdate"));
		            dto.setCviewcount(rs.getInt("cviewcount"));
		            dto.setCwuser(rs.getString("cwuser"));
		            dto.setCtype(rs.getInt("ctype"));
		            dto.setReplyCount(rs.getInt("replyCount"));
		            list.add(dto);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return list;
		}
		
		//공지사항 우선 출력
		public List<BoardDTO> selectNotices() {
		    List<BoardDTO> list = new ArrayList<>();
		    String query = "SELECT b.*, "
		                 + " (SELECT COUNT(*) FROM comments c WHERE c.cno = b.cno) AS replyCount "
		                 + " FROM community b WHERE b.ctype = 5 "
		                 + " ORDER BY b.cno DESC";

		    try {
		        psmt = con.prepareStatement(query);
		        rs = psmt.executeQuery();

		        while (rs.next()) {
		            BoardDTO dto = new BoardDTO();
		            dto.setCno(rs.getString("cno"));
		            dto.setCtitle(rs.getString("ctitle"));
		            dto.setCwdate(rs.getDate("cwdate"));
		            dto.setCviewcount(rs.getInt("cviewcount"));
		            dto.setCwuser(rs.getString("cwuser"));
		            dto.setCtype(rs.getInt("ctype"));
		            dto.setReplyCount(rs.getInt("replyCount"));
		            
		            list.add(dto);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return list;
		}
		
		
	    // 게시글 작성 시 DB에 추가 (String만)
	    public int insertWrite(BoardDTO dto) {
	        int result = 0;
	        try {
	            String query = "INSERT INTO community ( "
	                         + " cno, ctitle, ctext, cwuser, cpw, cofile, csfile, ctype) "
	                         + " VALUES ( "
	                         + " seq_board_num2.NEXTVAL,?,?,?,?,?,?,?)";
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, dto.getCtitle());
	            psmt.setString(2, dto.getCtext());
	            psmt.setString(3, dto.getCwuser());
	            psmt.setString(4, dto.getCpw());
	            psmt.setString(5, dto.getCofile());
	            psmt.setString(6, dto.getCsfile());
	            psmt.setInt(7, dto.getCtype());
	            result = psmt.executeUpdate();
	        }
	        catch (Exception e) {
	            System.out.println("게시물 입력 중 예외 발생");
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	 // 특정 게시글의 댓글 수 조회
	    public int commentscount(String cno) {
	        int count = 0;
	        String query = "SELECT COUNT(*) FROM comments WHERE cno = ?";
	        try {
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, cno);
	            rs = psmt.executeQuery();
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return count;
	    }
	    
	    // 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
	    public BoardDTO selectView(String cno) {
	        BoardDTO dto = new BoardDTO();  // DTO 객체 생성
	        String query = "SELECT * FROM community WHERE cno=?";  // 쿼리문 템플릿 준비
	        try {
	            psmt = con.prepareStatement(query);  // 쿼리문 준비
	            psmt.setString(1, cno);  // 인파라미터 설정
	            rs = psmt.executeQuery();  // 쿼리문 실행

	            if (rs.next()) {  // 결과를 DTO 객체에 저장
	            	dto.setCno(rs.getString(1));
	                dto.setCtitle(rs.getString(2));
	                dto.setCtext(rs.getString(3));
	                dto.setCwdate(rs.getDate(4));
	                dto.setCviewcount(rs.getInt(5));
	                dto.setCwuser(rs.getString(6));
	                dto.setCpw(rs.getString(7));
	                dto.setCofile(rs.getString(8));
	                dto.setCsfile(rs.getString(9));
	                dto.setCtype(rs.getInt("ctype"));
	            }
	        }
	        catch (Exception e) {
	            System.out.println("게시물 상세보기 중 예외 발생");
	            e.printStackTrace();
	        }
	        return dto;  // 결과 반환
	    }
	    
	    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킵니다.
	    public void updateVisitCount(String cno) {
	        String query = "UPDATE community SET "
	                     + " cviewcount=cviewcount+1 "
	                     + " WHERE cno=?"; 
	        try {
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, cno);
	            psmt.executeQuery();
	        }
	        catch (Exception e) {
	            System.out.println("게시물 조회수 증가 중 예외 발생");
	            e.printStackTrace();
	        }
	    }
	    
	 // 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인합니다.
	    public boolean confirmPassword(String cpw, String cno) {
	    	boolean result = false;
	    	String sql = "SELECT COUNT(*) FROM community WHERE cno = ? AND cpw = ?";
	    	try {
	    		psmt = con.prepareStatement(sql);
	    		psmt.setString(1, cno);
	    		psmt.setString(2, cpw);
	    		rs = psmt.executeQuery();
	    		if (rs.next()) {
	    			result = rs.getInt(1) > 0;
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return result;
	    }
	    
	    // 지정한 일련번호의 게시물을 삭제합니다.
	    public int deletePost(String cno) {
	        int result = 0;
	        try {
	            String query = "DELETE FROM community WHERE cno=?";
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, cno);
	            result = psmt.executeUpdate();
	        }
	        catch (Exception e) {
	            System.out.println("게시물 삭제 중 예외 발생");
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    // 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신합니다(파일 업로드 지원).
	    public int updatePost(BoardDTO dto) {
	        int result = 0;
	        try {
	            // 쿼리문 템플릿 준비
	            String query = "UPDATE community"
	                         + " SET ctitle=?, cwuser=?, ctext=?, cofile=?, csfile=?, ctype=? "
	                         + " WHERE cno=? and cpw=?";

	            // 쿼리문 준비
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, dto.getCtitle());
	            psmt.setString(2, dto.getCwuser());
	            psmt.setString(3, dto.getCtext());
	            psmt.setString(4, dto.getCofile());
	            psmt.setString(5, dto.getCsfile());
	            psmt.setInt(6, dto.getCtype());
	            psmt.setString(7, dto.getCno());
	            psmt.setString(8, dto.getCpw());

	            // 쿼리문 실행
	            result = psmt.executeUpdate();
	        }
	        catch (Exception e) {
	            System.out.println("게시물 수정 중 예외 발생");
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    
}
