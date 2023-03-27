package com.KoreaIT.example.JAM.service;

import java.util.List;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dao.ArticleDao;
import com.KoreaIT.example.JAM.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;

	public ArticleService() {
		this.articleDao = Container.articleDao;
	}
	
	public int doWrite(int memberId, String title, String body) {
		return articleDao.doWrite(memberId, title, body);
	}
	
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
	
	public int getArticlesCount(int id) {
		return articleDao.getArticlesCount(id);
	}
	
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}
	
	public void doModify(int id, String title, String body) {
		articleDao.doModify(id, title, body);
	}
	
	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public void increseHit(int id) {
		articleDao.increaseHit(id);
	}
}
