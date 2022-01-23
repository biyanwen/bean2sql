package com.github.biyanwen.parse.handler.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author byw
 * @Date 2022/1/23 13:25
 */
@Data
@Table(name = "EMPLOYEE")
public class Employee {

	private String id;

	private String name;

	private Gender gender;

	public Employee(String id, String name, Gender gender) {
		this.id = id;
		this.name = name;
		this.gender = gender;
	}
}
