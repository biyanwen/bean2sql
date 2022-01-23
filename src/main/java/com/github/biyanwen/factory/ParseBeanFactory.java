package com.github.biyanwen.factory;

import com.github.biyanwen.parse.DefaultParseBeanToSql;
import com.github.biyanwen.parse.ParseBeanToSql;

/**
 * 解析bean工厂
 *
 * @Author byw
 * @Date 2021/12/29 14:32
 */
public class ParseBeanFactory {

	public static ParseBeanToSql createDefaultParseBean() {
		return new DefaultParseBeanToSql();
	}
}
