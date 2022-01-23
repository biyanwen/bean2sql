package com.github.biyanwen.parse.handler.bean;

/**
 * @Author byw
 * @Date 2022/1/23 13:26
 */
public enum Gender {

	MAN(1, "男性"),
	WOMAN(2, "女性");

	private Integer id;

	private String text;

	public Integer getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	Gender(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
}
