package com.github.biyanwen.enums;

/**
 * @Author byw
 * @Date 2021/12/16 16:42
 */
public enum DbType {
	/**
	 * mysql
	 */
	MYSQL(1, "mysql"),
	ORACLE(2, "oracle"),
	DM(3, "达梦");

	private Integer index;

	private String text;

	DbType(Integer index, String text) {
		this.index = index;
		this.text = text;
	}

	public Integer getIndex() {
		return index;
	}

	public String getText() {
		return text;
	}
}
