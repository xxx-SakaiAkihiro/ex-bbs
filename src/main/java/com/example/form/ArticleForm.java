package com.example.form;

/**
 * 記事情報投稿時に使用するフォーム.
 * 
 * @author sakai
 *
 */
public class ArticleForm {

	/** 記事ID */
	private Integer id;
	/** 投稿者名 */
	private String name;
	/** 投稿内容 */
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "ArticleForm [id=" + id + ", name=" + name + ", content=" + content + "]";
	}

}
