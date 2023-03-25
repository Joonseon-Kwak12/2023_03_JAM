package com.KoreaIT.example.JAM.controller;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;

public class MemberController extends Controller{
	public void doJoin(String cmd) {
		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;
		
		System.out.println("==회원 가입==");
				
		SecSql sql = null;			
		
		while (true) {
			System.out.print("아이디: ");
			loginId = sc.nextLine().trim();
							
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			sql = new SecSql();
			
			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);
			
			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);
			
			if (isLoginIdDup) {
				System.out.println(loginId + "는 이미 사용중인 아이디입니다.");
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
					continue;
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
	}


}
