package Reporting;

import java.util.List;

public class User {
	private Long userId;
    private String userName;
    private String createDate;
    private String lastUpdateDate;
    private String endDate;
    private List<String> respId;
    
    public User() {
    }
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String string) {
		this.endDate = string;
	}
	
    public List<String> getRespId() {
		return respId;
	}
	public void setRespId(List<String> respId) {
		this.respId = respId;
	}
	
	public String toString() {
    	return "[UserId:"+this.getUserId()+"-UserName:"+this.getUserName()+"-CreateDate:"+this.getCreateDate()
    	+"-LastUpdateDate:"+this.getLastUpdateDate()+"-EndDate:"+this.getEndDate()+"-Responsibilities:"+this.getRespId()+"]";
    }
	
	/*
	 * @Override public boolean equals(Object o) { if (!(o instanceof User)) return
	 * false; User n = (User) o; return n.userId.equals(userId) &&
	 * n.lastUpdateDate.equals(lastUpdateDate); }
	 */

}
