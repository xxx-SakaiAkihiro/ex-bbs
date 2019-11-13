package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ(DAO).
 * 
 * @author sakai
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * 全てのコメント情報を取得する.
	 * 
	 * @param articleId 記載ID
	 * @return 全てのコメント情報
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * コメント情報を挿入する.
	 * 
	 * @param comment コメント情報
	 */
	public void insert(Comment comment) {
		String sql = "INSERT INTO comments(name, content,article_id) VALUES(:name, :content, :articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}

	/**
	 * コメント情報の削除
	 * 
	 * @param articleId 記事ID
	 */
	public void deleteByArticleId(int articleId) {
		String sql = "DELETE FROM comments WHERE article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}

}
