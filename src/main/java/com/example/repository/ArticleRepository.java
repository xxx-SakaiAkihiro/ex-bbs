package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルを操作するリポジトリ(DAO).
 * 
 * @author sakai
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
	private static final ResultSetExtractor<List<Article>> ARTICLE_ResultSetExtractor = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		int preId = 0;
		while (rs.next()) {
			int nowId = rs.getInt("id");
			if (preId != nowId) {
				Article article = new Article();
				article.setId(nowId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				
				articleList.add(article);
			}
			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			preId = nowId;
		}
		return articleList;
	};

	/**
	 * 全ての記事情報を取得する.
	 * 
	 * @return 全ての記事情報
	 */
//	public List<Article> findAll() {
//		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
//		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
//		return articleList;
//	}

	public List<Article> findAll() {
		String sql = "SELECT a.id,a.name,a.content,c.id AS com_id,c.name AS com_name,c.content AS com_content,c.article_id FROM articles a LEFT OUTER JOIN comments c ON a.id = c.article_id ORDER BY a.id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_ResultSetExtractor);
		return articleList;
	}

	/**
	 * 記事情報を挿入する.
	 * 
	 * @param article 記事情報
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}

	/**
	 * 記事情報を削除する.
	 * 
	 * @param id 記事ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
