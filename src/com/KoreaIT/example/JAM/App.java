package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

				PreparedStatement pstmt = null;

				try {
					String sql = "INSERT INTO article";
					sql += " SET regDate = NOW(),"; // '" + regDate + "',";
					sql += " updateDate = NOW(),"; // '" + regDate + "',";
					sql += " title = '" + title + "',";
					sql += " `body` = '" + body + "';";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt.executeUpdate();

				} catch (SQLException e) {
					System.out.println("에러2 : " + e);
				} finally {
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				System.out.println("?번 글이 생성되었습니다"); // 수정해야할 것
				//
				//
				//
			} else if (cmd.equals("article list")) {
				System.out.println("==게시물 목록==");

				PreparedStatement pstmt = null;
				ResultSet rs = null;

				List<Article> articles = new ArrayList<>();

				try {
					String sql = "SELECT *";
					sql += " FROM article";
					sql += " ORDER BY id DESC;"; // id 값이 큰 것부터 조회되도록

					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery(sql);

					while (rs.next()) { // rs.next() 다음 꺼 있으면 true, 없으면 false
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");

						articles.add(new Article(id, regDate, updateDate, title, body));
					}

				} catch (SQLException e) {
					System.out.println("에러3 : " + e);
				} finally {
					try { // 꺼줄 때는 역순으로
						if (rs != null && !rs.isClosed()) {
							rs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
			} else if (cmd.startsWith("article modify")) {
				System.out.println("==게시물 수정==");

				int id = Integer.parseInt(cmd.split(" ")[2]);

				System.out.print("수정 제목: ");
				String title = sc.nextLine();
				System.out.print("수정 내용: ");
				String body = sc.nextLine();

				PreparedStatement pstmt = null;

				try {
					String sql = "UPDATE article";
					sql += " SET updateDate = NOW(),";
					sql += " title = '" + title + "',";
					sql += " `body` = '" + body + "'";
					sql += " WHERE id = " + id + ";";

					pstmt = conn.prepareStatement(sql);

					pstmt.executeUpdate();

				} catch (SQLException e) {
					System.out.println("에러4 : " + e);
				} finally {
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				System.out.println(id + "번 게시물이 수정되었습니다.");
				//
				//
				//
			} else {
				System.out.println("해당 명령어는 존재하지 않습니다.");
			}
			return 0;
		}
	}