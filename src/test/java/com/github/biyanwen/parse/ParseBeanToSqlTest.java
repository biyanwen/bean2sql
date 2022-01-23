package com.github.biyanwen.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.biyanwen.json2sql.api.BeanProcessor;
import com.github.biyanwen.json2sql.config.Configuration;
import com.github.biyanwen.json2sql.config.ConfigurationBuilder;
import com.github.biyanwen.json2sql.enums.KeyConversionConfig;
import com.github.biyanwen.parse.handler.bean.Employee;
import com.github.biyanwen.parse.handler.bean.Gender;
import com.google.common.collect.Lists;
import com.github.biyanwen.enums.DbType;
import com.github.biyanwen.factory.ParseBeanFactory;
import com.github.biyanwen.parse.handler.bean.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author byw
 * @Date 2021/12/29 14:44
 */
class ParseBeanToSqlTest {

	/**
	 * 使用自定义 Configuration
	 */
	@Test
	void parse2BatchInsertSql() {
		ArrayList<Student> students = Lists.newArrayList(new Student(15, "小明", "天河区"),
				new Student(18, "小红", "荔湾区"));
		ParseBeanToSql defaultParseBean = ParseBeanFactory.createDefaultParseBean();

		Configuration configuration = ConfigurationBuilder.config()
				.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
				.build();
		String mysqlBatchInsertSql = defaultParseBean.parse2BatchInsertSql(students, configuration, DbType.MYSQL);
		Assertions.assertEquals("INSERT INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('天河区','小明','15'), ('荔湾区','小红','18');", mysqlBatchInsertSql);


		String dmBatchInsertSql = defaultParseBean.parse2BatchInsertSql(students, configuration, DbType.DM);
		Assertions.assertEquals("INSERT ALL INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('天河区','小明','15') INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('荔湾区','小红','18') SELECT 1 FROM dual;", dmBatchInsertSql);

	}

	/**
	 * 使用自定义序列化配置 ObjectMapper
	 */
	@Test
	void test_for_objectMapper() {
		ArrayList<Employee> employees = Lists.newArrayList(new Employee("1", "小李", Gender.MAN)
				, new Employee("2", "小磊", Gender.WOMAN));

		ParseBeanToSql defaultParseBean = ParseBeanFactory.createDefaultParseBean();
		Configuration configuration = ConfigurationBuilder.config()
				.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
				.build();

		String batchInsertSql = defaultParseBean.parse2BatchInsertSql(employees, configuration, DbType.MYSQL, createMapper());
		Assertions.assertEquals("INSERT INTO EMPLOYEE (GENDER,NAME,ID)VALUES ('1','小李','1'), ('2','小磊','2');", batchInsertSql);
	}

	/**
	 * 使用自定义配置更改字段名称
	 */
	@Test
	void test_for_modifyField() {
		Configuration configuration = ConfigurationBuilder.config()
				.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
				.withBeanProcessorMap("name", new ModifyFieldBean())
				.build();
		ArrayList<Employee> employees = Lists.newArrayList(new Employee("1", "小李", Gender.MAN)
				, new Employee("2", "小磊", Gender.WOMAN));

		ParseBeanToSql defaultParseBean = ParseBeanFactory.createDefaultParseBean();
		String batchInsertSql = defaultParseBean.parse2BatchInsertSql(employees, configuration, DbType.MYSQL);
		Assertions.assertEquals("INSERT INTO EMPLOYEE (GENDER,T_NAME,ID)VALUES ('MAN','小李','1'), ('WOMAN','小磊','2');", batchInsertSql);
	}

	private static class ModifyFieldBean implements BeanProcessor<Map<String, String>> {

		@Override
		public Map<String, String> processor(Map<String, String> map) {
			Map<String, String> result = new HashMap<>();
			for (Map.Entry<String, String> stringObjectEntry : map.entrySet()) {
				String key = stringObjectEntry.getKey();
				if (key.equals("name")) {
					key = "t_name";
				}
				String value = stringObjectEntry.getValue();
				result.put(key, value);
			}
			return result;
		}
	}

	private ObjectMapper createMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
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
		module.addSerializer(Gender.class, new JsonSerializer<Gender>() {
			@Override
			public void serialize(Gender gender, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				if (gender == null) {
					jsonGenerator.writeNull();
				} else {
					jsonGenerator.writeNumber(gender.getId());
				}
			}
		});
		objectMapper.registerModule(module);
		return objectMapper;
	}
}