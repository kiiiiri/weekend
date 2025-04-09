package gokr.weekend.dto;

import java.util.Date;

public class UserVO {
	private int uno;
	private String email;
	private String pw;
	private String nickname;
	private String name;
	private Date joindate;
	private int usertype;
	
	public int getUno() {
		return uno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getJoindate() {
		return joindate;
	}
	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	
	@Override
	public String toString() {
		return "UserVO [email=" + email + ", pw=" + pw + ", uno=" + uno
				+ ", nickname=" + nickname + ", name=" + name + ", joindate=" + joindate
				+ ", usertype=" + usertype +"]";
	}
	
	
}
