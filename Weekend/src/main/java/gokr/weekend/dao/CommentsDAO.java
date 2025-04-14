package gokr.weekend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gokr.weekend.common.DBConnPool;
import gokr.weekend.dto.CommentsDTO;

public class CommentsDAO extends DBConnPool {
	public CommentsDAO() {
		super();
	}
	
	// 조건에 맞는 댓글
    public int selectCount(String cno) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM comments where cno=? ";
       
        try {
        	psmt = con.prepareStatement(query);
        	psmt.setString(1, cno);
            rs = psmt.executeQuery();
            rs.next();
            totalCount = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println("댓글 필터링 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }
    
 // 조건에 맞는 댓글 목록을 반환합니다(페이징 기능 지원).
    public List<CommentsDTO> selectListPage(Map<String,Object> map) {
        List<CommentsDTO> reply = new ArrayList<CommentsDTO>();
        String query = " "
                     + "SELECT * FROM ( "
                     + "    SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "        SELECT * FROM comments where cno=? ";

       
        query += "        ORDER BY rno DESC "
               + "    ) Tb "
               + " ) "
               + " WHERE rNum BETWEEN ? AND ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("cno").toString());
            psmt.setString(2, map.get("start").toString());
            psmt.setString(3, map.get("end").toString());
            rs = psmt.executeQuery();

            while (rs.next()) {
                CommentsDTO dto = new CommentsDTO();

                dto.setRno(rs.getInt("rno"));
                dto.setRwuser(rs.getString("rwuser"));
                dto.setRpw(rs.getString("rpw"));
                dto.setRtext(rs.getString("rtext"));
                dto.setRwdate(rs.getDate("rwdate"));
                dto.setCno(rs.getInt("cno"));
                
                reply.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("댓글 페이징 목록 조회 중 예외 발생");
            e.printStackTrace();
        }
        return reply;
    }
    
    // 댓글 등록
    public int insertComments(CommentsDTO dto) {
        int result = 0;
        String query = "INSERT INTO comments (rwuser, rpw, rtext, cno) VALUES (?, ?, ?, ?)";
        
        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getRwuser());
            psmt.setString(2, dto.getRpw());
            psmt.setString(3, dto.getRtext());
            psmt.setInt(4, dto.getCno());
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 작성 중 예외 발생");
            e.printStackTrace();
        }
        
        return result;
    }
    
 
 // 댓글 삭제 메서드
    public int deleteComment(int rno, String rpw) {
        int result = 0;
        // 먼저 비밀번호 검증
        if (checkPassword(rno, rpw)) {
            String query = "DELETE FROM comments WHERE rno = ?";
            
            try {
                psmt = con.prepareStatement(query);
                psmt.setInt(1, rno);
                result = psmt.executeUpdate(); // 삭제 성공 여부 반환
            } catch (Exception e) {
                System.out.println("댓글 삭제 중 예외 발생");
                e.printStackTrace();
            }
        }
        return result;
    }

    // 댓글 비밀번호 확인
    public boolean checkPassword(int rno, String rpw) {
        boolean result = false;
        String query = "SELECT COUNT(*) FROM comments WHERE rno = ? AND rpw = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setInt(1, rno);
            psmt.setString(2, rpw);
            rs = psmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    
}
