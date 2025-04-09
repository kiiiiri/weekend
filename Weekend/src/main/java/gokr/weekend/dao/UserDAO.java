package gokr.weekend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import gokr.weekend.common.DBConnPool;
import gokr.weekend.dto.UserVO;




public class UserDAO extends DBConnPool {
		
	// private 생성자
	private UserDAO() {
		super();
	}
//	
//	// instance 생성
	private static UserDAO instance = new UserDAO();
//	
//	// instance 리턴
	public static UserDAO getInstance() {
		return instance;
	}

	
	// 로그인 시 사용하는 메서드
	public int userCheck(String email, String pw) {
		int result=1; // 기본값
		String sql="select pw from sitemember where email=?";
		
		
		try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, email); // ? 위치에 들어가는 값 세팅
			
			try (ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				if(rs.getString("pw")!=null && rs.getString("pw").equals(pw)) {
					result=1; // 비밀번호 일치
				} else {
					result=0; // 비밀번호 불일치
					}
				}else {
					result = -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
			
		
		return result;		
	}
	
	// 이메일로 회원 정보 가져오는 메소드
			public UserVO getUser(String email) {
				UserVO uVo = null;
				String sql = "select * from sitemember where email=?";
				
				try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
				     PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setString(1, email);
					
					try (ResultSet rs = pstmt.executeQuery()){
					if (rs.next()) {
						uVo = new UserVO();
						uVo.setUno(rs.getInt("uno"));
						uVo.setEmail(rs.getString("email"));
						uVo.setJoindate(rs.getDate("joindate"));
						uVo.setPw(rs.getString("pw"));
						uVo.setName(rs.getString("name"));
						uVo.setNickname(rs.getString("nickname"));
						uVo.setUsertype(rs.getInt("usertype"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
				return uVo;
		}
		
		
		// 이메일 중복 체크
		public int confirmEmail(String email) {
			int result = -1;
			String sql = "select email from sitemember where email=?";
			
			try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
//				
				pstmt.setString(1, email);
				
				try (ResultSet rs = pstmt.executeQuery()){
					
					if (rs.next()) {
						result = 1; // 이메일이 중복됨
					} else {
						result = -1; // 이메일이 중복안됨
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  
			
			return result;
		}
		
		// 닉네임 중복 체크
		public int confirmNickname(String nickname) {
			int result = -1;
			String sql = "select nickname from sitemember where nickname=?";
			
			try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
			     PreparedStatement pstmt = conn.prepareStatement(sql)) {
//					
					pstmt.setString(1, nickname);
					
					try (ResultSet rs = pstmt.executeQuery()){
					
						if (rs.next()) {
							result = 1; // 닉네임이 중복됨
						} else {
							result = -1; // 닉네임이 중복안됨
						}
					}
			} catch (Exception e) {
					e.printStackTrace();
			}
			
			return result;
		}
		
		// 회원등록
		public int insertUser(UserVO uVo) {
			int result = -1;
			String sql = "insert into sitemember (email, pw, nickname, name, usertype) values(?, ?, ?, ?, ?)";
			
			try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
		        PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, uVo.getEmail());
				pstmt.setString(2, uVo.getPw());
				pstmt.setString(3, uVo.getNickname());
				pstmt.setString(4, uVo.getName());
				pstmt.setInt(5, uVo.getUsertype());
				result = pstmt.executeUpdate();// 영향을 받은 행의 수 리턴. insert하면 1행이 추가되므로 1이 리턴됨.
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return result;
		}
		
		// 회원 정보 수정
		public int updateUser(UserVO uVo) {
			int result = -1;
			String sql = "update sitemember set pw=? where email=?";
			
			try (Connection conn = new DBConnPool().con; // DBConnPool에서 커넥션 얻기
			    PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				// 파라미터 설정
		        pstmt.setString(1, uVo.getPw());    // 새로운 비밀번호 설정
		        pstmt.setString(2, uVo.getEmail()); // email 기준으로 업데이트
		      
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}

}
