package jpa.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	private String title;
	private String content;
	
	private String createdDate;
	private String modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Member member;
	
	public Board() {}

	public Board(Long seq) {
		this.seq = seq;
	}

	public Long getSeq() {
		return seq;
	}
	
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	// 순환참조로 인한 Member 제거
	@Override
	public String toString() {
		return "Board [seq=" + seq + ", title=" + title + ", content=" + content + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}
}
