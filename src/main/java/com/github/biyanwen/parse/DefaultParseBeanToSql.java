package com.github.biyanwen.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.biyanwen.enums.DbType;
import com.github.biyanwen.json2sql.config.Configuration;
import com.github.biyanwen.json2sql.config.ConfigurationBuilder;
import com.github.biyanwen.json2sql.enums.KeyConversionConfig;
import com.github.biyanwen.parse.handler.Handler;
import com.github.biyanwen.parse.handler.HandlerContext;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author byw
 * @Date 2021/11/23 9:34
 */
public class DefaultParseBeanToSql implements ParseBeanToSql {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParseBeanToSql.class);

	@Override
	public <T> String parse2BatchInsertSql(List<T> list, DbType dbType) {
		return this.parse2BatchInsertSql(list, null, dbType, null);
	}

	@Override
	public <T> String parse2BatchInsertSql(List<T> list, Configuration configuration, DbType dbType) {
		return this.parse2BatchInsertSql(list, configuration, dbType, null);
	}

	@Override
	public <T> String parse2BatchInsertSql(List<T> list, DbType dbType, ObjectMapper objectMapper) {
		return this.parse2BatchInsertSql(list, null, dbType, objectMapper);
	}

	@SneakyThrows
	@Override
	public <T> String parse2BatchInsertSql(List<T> list, Configuration configuration, DbType dbType, ObjectMapper objectMapper) {
		if (configuration == null) {
			configuration = createConfig();
		}
		if (objectMapper == null) {
			objectMapper = createMapper();
		}
		if (CollectionUtils.isEmpty(list)) {
			LOGGER.error("list is empty, return null");
			return null;
		}
		if (list.get(0).getClass().getAnnotation(Table.class) == null) {
			throw new RuntimeException("bean is not config javax.persistence.Table , so we can not get table name");
		}

		String tableName = list.get(0).getClass().getAnnotation(Table.class).name();
		Handler handler = HandlerContext.getHandler(dbType);
		String entityJson = objectMapper.writeValueAsString(list);
		return handler.handle(entityJson, configuration, tableName);
	}

	private Configuration createConfig() {
		return ConfigurationBuilder.config()
				.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
				.build();
	}

	private ObjectMapper createMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		SimpleModule module = new SimpleModule();
		module.addSerializer(Boolean.class, new JsonSerializer<Boolean>() {
			@Override
			public void serialize(Boolean value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				if (value == null) {
					jsonGenerator.writeNull();
				} else {
					jsonGenerator.writeNumber(value ? 1 : 0);
				}
			}
		});
		objectMapper.registerModule(module);
		return objectMapper;
	}
}
