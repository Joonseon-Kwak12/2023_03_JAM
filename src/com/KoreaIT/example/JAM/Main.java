package com.KoreaIT.example.JAM;

import com.KoreaIT.example.JAM.exception.SQLErrorException;

public class Main {
	public static void main(String[] args) {
		try {
			new App().start();
		} catch (SQLErrorException e) {
			System.err.println(e.getMessage());
			e.getOrigin().printStackTrace();
		}
	}
}

// DB에서 직접 NOW() 가져오면 돼서 크게 상관 없음
//class NowTime {
//	public static String getNowDateTime() {
//		LocalDateTime now = LocalDateTime.now();
//		String nowDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//		return nowDateTime; 
//	}
//}

