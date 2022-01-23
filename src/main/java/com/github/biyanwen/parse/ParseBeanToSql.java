package com.github.biyanwen.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.biyanwen.enums.DbType;
import com.github.biyanwen.json2sql.config.Configuration;

import java.util.List;

/**
 * @Author byw
 * @Date 2021/11/23 9:34
 */
public interface ParseBeanToSql {

	/**
	 * 生成批量入库 sql
	 *
	 * @param list   列表
	 * @param dbType 数据库类型
	 * @return {@link String}
	 */
	<T> String parse2BatchInsertSql(List<T> list, DbType dbType);

	/**
	 * 生成批量入库 sql
	 *
	 * @param list          列表
	 * @param configuration 配置
	 * @param dbType        数据库类型
	 * @return {@link String}
	 */
	<T> String parse2BatchInsertSql(List<T> list, Configuration configuration, DbType dbType);

	/**
	 * 生成批量入库 sql
	 *
	 * @param list         列表
	 * @param dbType       数据库类型
	 * @param objectMapper 对象映射器
	 * @return {@link String}
	 */
	<T> String parse2BatchInsertSql(List<T> list, DbType dbType, ObjectMapper objectMapper);

	/**
	 * 生成批量入库 sql
	 *
	 * @param list          列表
	 * @param configuration 配置
	 * @param dbType        数据库类型
	 * @param objectMapper  对象映射器
	 * @return {@link String}
	 */
	<T> String parse2BatchInsertSql(List<T> list, Configuration configuration, DbType dbType, ObjectMapper objectMapper);
}