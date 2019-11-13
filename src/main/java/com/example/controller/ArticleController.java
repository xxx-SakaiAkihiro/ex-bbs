package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事情報を操作するコントローラ.
 * 
 * @author sakai
 *
 */
@Controller
@RequestMapping("/board")
public class ArticleController {

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	/**
	 * 記事一覧を表示する.
	 * 
	 * @param model requestスコープ
	 * @return 「掲示板画面」にフォワード
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId())); 
		}
		model.addAttribute("articleList", articleList);
		return "BulletinBoard";
	}

	/**
	 * 記事を投稿する.
	 * 
	 * @param articleForm 記事情報
	 * @return 「掲示板画面」にフォワード
	 */
	@RequestMapping("/inserArticle")
	public String inserArticle(ArticleForm articleForm) {
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		return "redirect:/board";
	}
	
	/**
	 * コメントを投稿する.
	 * 
	 * @param commentForm コメント情報
	 * @return 「掲示板画面」にフォワード
	 */
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm commentForm) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:/board";
	}
	
	/**
	 * 記事とコメントを削除する.
	 * 
	 * @param articleForm 記事ID
	 * @return 「掲示板画面」にフォワード
	 */
	@RequestMapping("/delete")
	public String delete(ArticleForm articleForm) {
		commentRepository.deleteByArticleId(articleForm.getId());
		articleRepository.deleteById(articleForm.getId());
		return "redirect:/board";
	}
	

}
