package com.mct.model;

/**
 * ªÿ∏¥¿‡
 * 
 * @author Whunf
 * 
 */
public class Reply {
	private int id;
	private int user_id;
	private int article_id;
	private int parent;
	private String content;
	private String time;
	private String user_name;
	private String photo;
	private boolean isShow;

	public Reply() {
		super();
	}

	public Reply(int id, int userId, int articleId, int parent, String content,
			String time, String userName, String photo) {
		super();
		this.id = id;
		user_id = userId;
		article_id = articleId;
		this.parent = parent;
		this.content = content;
		this.time = time;
		user_name = userName;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public Reply setId(int id) {
		this.id = id;
		return this;
	}

	public int getUser_id() {
		return user_id;
	}

	public Reply setUser_id(int userId) {
		user_id = userId;
		return this;
	}

	public int getArticle_id() {
		return article_id;
	}

	public Reply setArticle_id(int articleId) {
		article_id = articleId;
		return this;
	}

	public int getParent() {
		return parent;
	}

	public Reply setParent(int parent) {
		this.parent = parent;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Reply setContent(String content) {
		this.content = content;
		return this;
	}

	public String getTime() {
		return time;
	}

	public Reply setTime(String time) {
		this.time = time;
		return this;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

}
