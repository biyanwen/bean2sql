package com.github.biyanwen.parse.handler.bean;

import javax.persistence.Table;

/**
 * @Author byw
 * @Date 2021/12/29 14:41
 */
@Table(name = "STUDENT")
public class Student {

	private Integer age;

	private String name;

	private String address;

	public Student() {
	}

	public Student(Integer age, String name, String address) {
		this.age = age;
		this.name = name;
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
