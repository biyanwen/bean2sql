package com.github.biyanwen.parse.handler;


import com.github.biyanwen.json2sql.config.Configuration;

/**
 * @Author byw
 * @Date 2021/12/16 16:46
 */
public interface Handler {


	/**
	 * 处理
	 *
	 * @param json          json
	 * @param configuration 配置
	 * @param tableName     表名
	 * @return {@link String}
	 */
	String handle(String json, Configuration configuration, String tableName);
}
