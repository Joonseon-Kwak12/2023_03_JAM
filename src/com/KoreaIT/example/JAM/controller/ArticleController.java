package com.KoreaIT.example.JAM.controller;

import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dto.Article;
import com.KoreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {
	
	private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}
	
	public void doWrite(String cmd) {
		System.out.println("==게시물 작성==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int id = articleService.doWrite(title, body);
		System.out.println(id + "번 글이 생성되었습니다.");
	}
	
	public void showList(String cmd) {
		System.out.println("==게시물 목록==");

		List<Article> articles = articleService.getArticles();

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("번호   /   제목");

		for (Article article : articles) {
			System.out.printf("%d   /   %s\n", article.id, article.title);
		}
	}
	
	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		System.out.println("==게시물 상세보기==");
		
		Map<String, Object> articleMap = articleService.getArticleById(id);
				
		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 존재하지 않습니다."); // 상세보기에서는 이미 SELECT 하는 곳이 있으므로 ?번 게시물 존재하는지 길게 블럭 만들 필요가 없다.
			return;
		}
		Article article = new Article(articleMap);

		System.out.println("글번호: " + article.id);
		System.out.println("작성일: " + article.regDate);
		System.out.println("수정일: " + article.updateDate);
		System.out.println("제목: " + article.title);
		System.out.println("내용: " + article.body);
	}

	public void doModify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articlesCount = articleService.getArticlesCount(id);
		
		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		
		System.out.println("==게시물 수정==");

		System.out.print("수정 제목: ");
		String title = sc.nextLine();
		System.out.print("수정 내용: ");
		String body = sc.nextLine();
		
		articleService.doModify(id, title, body);

		System.out.println(id + "번 게시물이 수정되었습니다.");
	}

	public void doDelete(String cmd) {
		System.out.println("==게시물 삭제==");
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articlesCount = articleService.getArticlesCount(id); 
		
		if (articlesCount == 0) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}

		articleService.doDelete(id);

		System.out.println(id + "번 게시물이 삭제되었습니다.");
	}



	
}
