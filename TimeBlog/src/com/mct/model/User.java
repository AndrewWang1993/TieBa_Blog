package com.mct.model;

public class User {
	private int id;
	private String userName;
	private String birth;
	private String nick;
	private String sex;
	private String photo;
	private String phone;

	public User(int id, String userName, String birth, String nick, String sex,
			String photo, String phone) {
		this.id = id;
		this.userName = userName;
		this.birth = birth;
		this.nick = nick;
		this.sex = sex;
		this.photo = photo;
		this.phone = phone;
	}

	public User(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
