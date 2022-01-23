package com.github.biyanwen.parse.handler;

import com.github.biyanwen.helper.SqlHelper;
import com.github.biyanwen.json2sql.config.Configuration;

import java.util.List;

/**
 * @Author byw
 * @Date 2021/12/29 14:36
 */
public class OracleHandler implements Handler {
	@Override
	public String handle(String json, Configuration configuration, String tableName) {
		List<String> insertSqlArray = SqlHelper.createInsertSql(json, configuration, tableName);
		StringBuilder builder = new StringBuilder("INSERT ALL");
		insertSqlArray.forEach(sql -> {
			sql = sql.replace("INSERT", "");
			builder.append(sql);
		});
		builder.append(" SELECT 1 FROM dual;");
		return builder.toString();
	}
}
