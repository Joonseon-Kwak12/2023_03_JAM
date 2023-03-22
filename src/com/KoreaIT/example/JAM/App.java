package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class App {
	public void start() {
		System.out.println("=프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // Class.forName("com.mysql.jdbc.Driver");라고 써있던 거 수정함
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");
				System.out.println("연결 성공!");

				int actionResult = doAction(conn, sc, cmd);

				if (actionResult == -1) {
					System.out.println("프로그램을 종료합니다.");
					break;
				}
			} catch (SQLException e) {
				System.out.println("에러1 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int doAction(Connection conn, Scanner sc, String cmd) {

		if (cmd.equals("exit")) {
			System.out.println("프로그램을 종료합니다.");
			return -1;
		}

		if (cmd.equals("article write")) {
			System.out.println("==게시물 작성==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();
			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);

			int id = DBUtil.insert(conn, sql);
			System.out.println(id + "번 글이 생성되었습니다.");
			//
			//
			//
		} else if (cmd.equals("article list")) {
			System.out.println("==게시물 목록==");

			List<Article> articles = new ArrayList<>();

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");

			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
			}

			if (articles.size() == 0) {
				System.out.println("게시글이 없습니다");
				return 0;
			}

			System.out.println("번호   /   제목");

			for (Article article : articles) {
				System.out.printf("%d   /   %s\n", article.id, article.title);
			}
			//
			//
			//
		} else if (cmd.startsWith("article detail")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			System.out.println("==게시물 상세보기==");
			
			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?;", id);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 존재하지 않습니다."); // 상세보기에서는 이미 SELECT 하는 곳이 있으므로 ?번 게시물 존재하는지 길게 블럭 만들 필요가 없다.
				return 0;
			}
			Article article = new Article(articleMap);

			System.out.println("글번호: " + article.id);
			System.out.println("작성일: " + article.regDate);
			System.out.println("수정일: " + article.updateDate);
			System.out.println("제목: " + article.title);
			System.out.println("내용: " + article.body);
			//
			//
			//
		} else if (cmd.startsWith("article modify")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);			
			
			int articlesCount = DBUtil.selectRowIntValue(conn, sql);
			
			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다.");
				return 0;
			}
			
			System.out.println("==게시물 수정==");

			System.out.print("수정 제목: ");
			String title = sc.nextLine();
			System.out.print("수정 내용: ");
			String body = sc.nextLine();

			sql = new SecSql();
			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append("WHERE id = ?", id);

			DBUtil.update(conn, sql);

			System.out.println(id + "번 게시물이 수정되었습니다.");
			//
			//
			//
		} else if (cmd.startsWith("article delete")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);			
			
			int articlesCount = DBUtil.selectRowIntValue(conn, sql);
			
			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다.");
				return 0;
			}
			
			System.out.println("==게시물 삭제==");

			sql = new SecSql();
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);

			System.out.println(id + "번 게시물이 삭제되었습니다.");
			//
			//
			//
		} else if (cmd.equals("member join")) {
			String loginId = null;
			String loginPw = null;
			String loginPwConfirm = null;
			String name = null;
			
			System.out.println("==회원 가입==");
			
			
			SecSql sql = null;
			int duplication = -1;
			
			
			while (true) {
				System.out.print("아이디: ");
				loginId = sc.nextLine().trim();
				sql = new SecSql();
				
				if (loginId.length() == 0) {
					System.out.println("아이디를 입력해주세요.");
					continue;
				}
				
				
				sql.append("SELECT COUNT(*)");
				sql.append("FROM member");
				sql.append("WHERE loginId = ?;", loginId);
				duplication = DBUtil.selectRowIntValue(conn, sql);
				
				if (duplication == 1) {
					System.out.println("중복 아이디가 존재합니다.");
					continue;
				}
				break;
			}
			
			while (true) {
				System.out.print("비밀번호: ");
				loginPw = sc.nextLine().trim();
				
				if (loginPw.length() == 0) {
					System.out.println("비밀번호를 입력해주세요");
					continue;
				}
				
				boolean loginPwCheck = true;
				
				while (true) {
					System.out.print("비밀번호 확인: ");
					loginPwConfirm = sc.nextLine();
					
					if (loginPwConfirm.length() == 0) {
						System.out.println("비밀번호 확인을 입력해주세요.");
					}
					
					if (!loginPw.equals(loginPwConfirm)) {
						System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						loginPwCheck = false;
						break;
					}
					break;
				}
				if (loginPwCheck) {
					break;
				}
			}
			
			while (true) {
				System.out.print("이름 : ");
				name = sc.nextLine();
				
				if (name.length() == 0) {
					System.out.println("이름을 입력해주세요.");
					continue;
				}
				break;
			}
			
			sql = new SecSql();
			sql.append("INSERT INTO member");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("loginId = ?,", loginId);
			sql.append("loginPw = ?,", loginPw);
			sql.append("`name` = ?;", name);

			int id = DBUtil.insert(conn, sql);
			
			System.out.println(id + "번 회원님, 가입되었습니다.");
			//
			//
			//
		} else {
			System.out.println("해당 명령어는 존재하지 않습니다.");
		}
		return 0;
	}
}