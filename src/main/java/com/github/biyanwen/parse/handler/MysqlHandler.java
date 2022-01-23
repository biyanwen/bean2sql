package com.github.biyanwen.parse.handler;

import com.github.biyanwen.helper.SqlHelper;
import com.github.biyanwen.json2sql.config.Configuration;

import java.util.List;

/**
 * @Author byw
 * @Date 2021/12/16 16:48
 */
public class MysqlHandler implements Handler {
	@Override
	public String handle(String json, Configuration configuration, String tableName) {
		List<String> insertSqlArray = SqlHelper.createInsertSql(json, configuration, tableName);
		StringBuilder builder = new StringBuilder(insertSqlArray.get(0)).append(",");
		insertSqlArray.remove(0);
		for (String sqlItem : insertSqlArray) {
			String values = sqlItem.split("VALUES")[1];
			builder.append(values).append(",");
		}
		return builder.substring(0, builder.toString().length() - 1) + ";";
	}
}
