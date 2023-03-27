package com.KoreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Member {
	public int id;
	public String regDate;
	public String updateDate;
	public String loginId;
	public String loginPw;
	public String name;
	
	public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}
	
	public Member(Map<String, Object> memberMap) { // DB에서 정보를 가져올 때 member 객체로 바꿔줌
		this.id = (int) memberMap.get("id");
		this.regDate = ((LocalDateTime) memberMap.get("regDate"))
				.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		this.updateDate = ((LocalDateTime) memberMap.get("updateDate"))
				.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", loginId=" + loginId
				+ ", loginPw=" + loginPw + ", name=" + name + "]";
	}
}
