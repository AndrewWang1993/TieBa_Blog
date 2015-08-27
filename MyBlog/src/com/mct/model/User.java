package com.mct.model;

public class User {
	private int id;
	private String userName;
	private String nick;
	private String photo;
	private String phone;
	private String sex;
	private String birth;

	public User() {
		super();
	}

	public User(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getNick() {
		return nick;
	}

	public User setNick(String nick) {
		this.nick = nick;
		return this;
	}

	public String getPhoto() {
		return photo;
	}

	public User setPhoto(String photo) {
		this.photo = photo;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public User setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public String getBirth() {
		return birth;
	}

	public User setBirth(String birth) {
		this.birth = birth;
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id:" + id + " name:" + userName + " photo:" + photo;
	}
}
