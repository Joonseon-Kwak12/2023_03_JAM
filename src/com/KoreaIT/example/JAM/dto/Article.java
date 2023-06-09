package com.KoreaIT.example.JAM.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Article extends Object {
	// DB의 article 테이블의 컬럼과 1:1 매칭되는 것
	public int id;	
	public String regDate;
	public String updateDate;
	public int memberId;
	public String title;
	public String body;
	public int hit;
	// DB의 article 테이블의 컬럼에 없는 것
	public String extra__writer;

	public Article(int id, String regDate, String updateDate, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}

	public Article(Map<String, Object> articleMap) { // DB에서 정보를 가져올 때 article 객체로 바꿔줌
		this.id = (int) articleMap.get("id");
		this.regDate = ((LocalDateTime) articleMap.get("regDate"))
				.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		this.updateDate = ((LocalDateTime) articleMap.get("updateDate"))
				.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		this.memberId = (int) articleMap.get("memberId");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.hit = (int) articleMap.get("hit");
		
		if (articleMap.get("extra__writer") != null) {
			this.extra__writer = (String) articleMap.get("extra__writer");
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", memberId=" + memberId
				+ ", title=" + title + ", body=" + body + ", extra__writer=" + extra__writer + "]";
	}
}