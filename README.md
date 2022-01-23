# 工具简介

## 作用

本工具可以将 JavaBean 转换成批量入库 sql ，目前支持 Mysql、Oracle、达梦数据库等三种数据库（如果没有你想要的可以提 issue）。

## 使用方式

### 1. 添加依赖

~~~xml

<dependency>
    <groupId>com.github.biyanwen</groupId>
    <artifactId>bean2sql</artifactId>
    <version>1.0.0</version>
</dependency>
~~~

### 2. 创建服务实例

~~~java
ParseBeanToSql defaultParseBean=ParseBeanFactory.createDefaultParseBean();
~~~

### 3. 创建配置类（可选）

~~~java
Configuration configuration=ConfigurationBuilder.config()
		.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
		.build();
~~~

本工具提供了很多可选的配置，例如:

1. 生成的 sql 是大写，还是小写；
2. 某些字段更改名称。例如，JavaBean 中的属性叫 size，但是想在装换的时候将其更改为 t_size；
3. 忽略特定字段，不让出现在 sql 语句中； 等等还有其他特性，具体请查看 com.github.biyanwen.json2sql.config.Configuration 类。

### 4. 为 JavaBean 加上 javax.persistence.Table 注解

这个注解用来获取表的名字

~~~java

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
~~~

### 5. 调用接口

~~~java
ArrayList<Student> students=Lists.newArrayList(new Student(15,"小明","天河区"),
		new Student(18,"小红","荔湾区"));
		ParseBeanToSql defaultParseBean=ParseBeanFactory.createDefaultParseBean();

		Configuration configuration=ConfigurationBuilder.config()
		.withKeyConversionConfig(KeyConversionConfig.hump2UnderscoreCapital)
		.build();
		String mysqlBatchInsertSql=defaultParseBean.parse2BatchInsertSql(students,configuration,DbType.MYSQL);
		Assertions.assertEquals("INSERT INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('天河区','小明','15'), ('荔湾区','小红','18');",mysqlBatchInsertSql);

		String dmBatchInsertSql=defaultParseBean.parse2BatchInsertSql(students,configuration,DbType.DM);
		Assertions.assertEquals("INSERT ALL INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('天河区','小明','15') INTO STUDENT (ADDRESS,NAME,AGE)VALUES ('荔湾区','小红','18') SELECT 1 FROM dual;",dmBatchInsertSql);

~~~

更多示例可以查看本工具的测试类