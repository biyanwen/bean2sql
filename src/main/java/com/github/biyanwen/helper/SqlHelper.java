package com.github.biyanwen.helper;

import com.github.biyanwen.json2sql.Json2sql;
import com.github.biyanwen.json2sql.config.Configuration;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author byw
 * @Date 2021/12/29 14:53
 */
public class SqlHelper {

	public static List<String> createInsertSql(String json, Configuration configuration, String tableName) {
		Json2sql.setConfiguration(configuration);
		String sql = Json2sql.parse2String(json, tableName);
		sql = sql.replaceAll("\\n", "").replaceAll("  ", "").replace("\r", "");
		ArrayList<String> sqlArray = Lists.newArrayList(sql.split(";"));
		sqlArray.remove(0);
		return sqlArray;
	}
}
