package gokr.weekend.dto;

import java.sql.Date;

public class CommentsDTO {
	private int rno;
	private String rwuser;
    private String rpw;
    private String rtext;
    private Date rwdate;
    private int cno; 
    
    public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getRwuser() {
		return rwuser;
	}
	public void setRwuser(String rwuser) {
		this.rwuser = rwuser;
	}
	public String getRpw() {
		return rpw;
	}
	public void setRpw(String rpw) {
		this.rpw = rpw;
	}
	public String getRtext() {
		return rtext;
	}
	public void setRtext(String rtext) {
		this.rtext = rtext;
	}
	public Date getRwdate() {
		return rwdate;
	}
	public void setRwdate(Date rwdate) {
		this.rwdate = rwdate;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	
}
