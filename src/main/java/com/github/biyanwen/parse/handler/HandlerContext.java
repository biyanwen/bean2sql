package com.github.biyanwen.parse.handler;

import com.github.biyanwen.enums.DbType;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理程序上下文
 *
 * @Author byw
 * @Date 2021/12/16 16:54
 */
public class HandlerContext {

	private static final Map<DbType, Handler> HANDLER_MAP = new HashMap<>();

	static {
		HANDLER_MAP.put(DbType.MYSQL, new MysqlHandler());
		HANDLER_MAP.put(DbType.DM, new OracleHandler());
		HANDLER_MAP.put(DbType.ORACLE, new OracleHandler());
	}

	public static Handler getHandler(DbType dbType) {
		return HANDLER_MAP.get(dbType);
	}
}
