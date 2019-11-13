package com.example.form;

/**
 * コメント情報投稿時に使用するフォーム.
 * 
 * @author sakai
 *
 */
public class CommentForm {

	/** 記事ID */
	private String articleId;
	/** コメント名 */
	private String name;
	/** コメント内容 */
	private String content;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

}
