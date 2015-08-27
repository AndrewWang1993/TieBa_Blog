package com.mct.model;

/**
 * Ìû×ÓÄÚÈİÀà
 * 
 * @author Whunf
 * 
 */
public class Article {
	private int user_id;
	private int type;
	private String content;

	public Article() {
		super();
	}

	public Article(String content, int userId, int type) {
		super();
		this.content = content;
		user_id = userId;
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public Article setContent(String content) {
		this.content = content;
		return this;
	}

	public int getUser_id() {
		return user_id;
	}

	public Article setUser_id(int userId) {
		user_id = userId;
		return this;
	}

	public int getType() {
		return type;
	}

	public Article setType(int type) {
		this.type = type;
		return this;
	}

}
