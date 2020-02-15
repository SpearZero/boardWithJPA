package jpa.board.dto;

import java.time.LocalDateTime;

public class BoardList {
	
	private Long seq;
	private String username;
	private String title;
	private String createdDate;
	private String modifiedDate;
	
	public BoardList() {}

	public BoardList(Long seq, String username, String title, String createdDate, String modifiedDate) {
		this.seq = seq;
		this.username = username;
		this.title = title;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "BoardList [seq=" + seq + ", username=" + username + ", title=" + title + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}
}
