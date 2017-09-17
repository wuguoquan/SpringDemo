package com.demo.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

// 域对象，实现序列化接口
public class UserImage implements Serializable{

	private String username;
	private MultipartFile image;

	public UserImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
