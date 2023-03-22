package com.KoreaIT.example.JAM;

public class Main {
	public static void main(String[] args) {
		new App().start();
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

