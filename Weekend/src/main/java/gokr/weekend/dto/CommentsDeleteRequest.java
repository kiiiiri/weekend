package gokr.weekend.dto;

public class CommentsDeleteRequest {
	private int rno;
    private String rpw;

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getRpw() {
        return rpw;
    }

    public void setRpw(String rpw) {
        this.rpw = rpw;
    }
}
