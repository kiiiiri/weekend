package model2.mvcboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPool;

public class ReplyDAO extends DBConnPool{
	public ReplyDAO(){
		super();
	}
	
	// 검색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(String idx) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM reply where idx=? ";
       
        try {
        	psmt = con.prepareStatement(query);
        	psmt.setString(1, idx);
            rs = psmt.executeQuery();
            rs.next();
            totalCount = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }

    // 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
    public List<ReplyDTO> selectListPage(Map<String,Object> map) {
        List<ReplyDTO> reply = new ArrayList<ReplyDTO>();
        String query = " "
                     + "SELECT * FROM ( "
                     + "    SELECT Tb.*, ROWNUM rNum FROM ( "
                     + "        SELECT * FROM reply where idx=? ";

       
        query += "        ORDER BY no DESC "
               + "    ) Tb "
               + " ) "
               + " WHERE rNum BETWEEN ? AND ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("idx").toString());
            psmt.setString(2, map.get("start").toString());
            psmt.setString(3, map.get("end").toString());
            rs = psmt.executeQuery();

            while (rs.next()) {
                ReplyDTO dto = new ReplyDTO();

                dto.setNo(rs.getInt("no"));
                dto.setIdx(rs.getInt("idx"));
                dto.setName(rs.getString("name"));
                dto.setContent(rs.getString("content"));
                dto.setPass(rs.getString("pass"));
                dto.setPostdate(rs.getDate("postdate"));

                reply.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return reply;
    }


}
