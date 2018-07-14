## Project
try to apply what I have learned to practise
---
## 一、创建maven项目
#### idea生成src文件夹
- 在main文件夹下创建Java文件夹、resources文件夹
- 创建test文件夹
    
![image](http://p3379ff71.bkt.clouddn.com/2018-07-13_192824.png)

#### 在pom.xml导入相应的dependency(先导入基本的即可)

```
 <!--引入pageHelper分页插件 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.0.0</version>
        </dependency>

        <!-- MBG -->
        <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.5</version>
        </dependency>


        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>

        <!-- 返回json字符串的支持 -->
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.8.8</version>
        </dependency>

        <!--JSR303数据校验支持；tomcat7及以上的服务器，
        tomcat7以下的服务器：el表达式。额外给服务器的lib包中替换新的标准的el
        -->
        <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-validator -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>5.4.1.Final</version>
        </dependency>


        <!-- Spring-Jdbc -->
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>

        <!--Spring-test -->
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>


        <!-- Spring面向切面编程 -->
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>4.3.7.RELEASE</version>
        </dependency>

        <!--MyBatis -->
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.2</version>
        </dependency>
        <!-- MyBatis整合Spring的适配包 -->
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.1</version>
        </dependency>

        <!-- 数据库连接池、驱动 -->
        <!-- https://mvnrepository.com/artifact/c3p0/c3p0 -->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.41</version>
        </dependency>
        <!-- （jstl，servlet-api，junit） -->
        <!-- https://mvnrepository.com/artifact/jstl/jstl -->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.0.1</version>
            <scope>provided</scope>
        </dependency>


        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
```
#### 导入相应的spring、springmvc框架
- 会在WEB-INF文件夹下生成application.xml和dispatcher-servlet.xml
- 建议将上述生成的转移到resources文件夹中
- 会在web.xml中自动配置相应的spring、springmvc的配置文件
- 建议转移到resources中后修改web.xml中有关spring配置文件的扫描路径

---

## 二、配置相应的配置文件
#### 配置相应的web.xml
- 会自动配置spring、springmvc的文件（不用管）
- 配置常用的filter（注意配置位置，idea会提示）
    - 字符集编码过滤器（放在所有过滤器最前面）
    - 使用rest风格的将post转换成put、delete请求的过滤器

```
<web-app>

  <display-name>Archetype Created Web Application</display-name>
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <!-- filter的位置注意。idea会有提示 -->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceRequestEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 使用Rest风格的URI -->
  <filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/*</url-pattern>
  </servlet-mapping>

 
</web-app>

```
#### 配置springmvc的配置文件
- 配置扫描控制器
- 配置视图解析器
- 两件标配（有兴趣可以研究下）

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--SpringMVC的配置文件 -->
    <context:component-scan base-package="eCommerce" use-default-filters="false">
        <!--只扫描控制器 -->
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>


    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>
</beans>
```
#### 配置spring的配置文件
- 配置扫描的service层
- 配置数据源（可以去mybatis.xml的文件中配置），创建相应的properties文件
- ==配置整合mybatis==
    -  mybatis全局配置文件的位置
    -  映射文件的位置（映射文件自动生成），先在resources文件夹下创建mapper文件夹
    -  配置扫描器（将dao层加入到ioc容器中）
    -  配置sqlSession
- 配置事务控制，待续。。。

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    <!-- spring配置文件-->
    <context:component-scan base-package="eCommerce">
        <context:exclude-filter type="annotation"
                                expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <!-- 配置数据源 -->
    <context:property-placeholder location="classpath:db_config.properties" />
    <bean id="pooledDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
        <property name="driverClass" value="${jdbc.driverClass}"></property>
        <property name="user" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>

    <!-- 配置整合mybatis -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- mybatis全局配置文件的位置 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
        <property name="dataSource" ref="pooledDataSource"></property>
        <!-- 映射文件的位置文件的位置 -->
        <property name="mapperLocations" value="classpath:mapper/*.xml"></property>
    </bean>
    <!-- 配置扫描器，将mybatis接口的实现加入到ioc容器中 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--扫描所有dao接口的实现，加入到ioc容器中 -->
        <property name="basePackage" value="com.atguigu.crud.dao"></property>
    </bean>
    <!-- 配置一个可以执行批量的sqlSession -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
        <constructor-arg name="executorType" value="BATCH"></constructor-arg>
    </bean>


    <!-- 配置事务控制待续。。。-->


</beans>
```
#### 配置mybatis的配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>

    <typeAliases>
        <package name="eCommerce.controller"/>
    </typeAliases>

    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!--分页参数合理化  -->
            <property name="reasonable" value="true"/>
        </plugin>
    </plugins>

</configuration>
```

#### 整个的大致基础结构（src下）
![image](http://p3379ff71.bkt.clouddn.com/2018-07-13_195533.png)
## 三、使用mybatis逆向工程
#### mybatis-generator的使用
[官网地址](http://www.mybatis.org/generator/)
- 快速使用，导入相应的包，创建mbg.xml
- Create and fill out a configuration file appropriately. At a minimum, you must specify:（创建并填写一个配置文件。至少，你应指定以下东西）
    - A <jdbcConnection> element to specify how to connect to the target database（<jdbcConnection>标签中指定怎么去连接指定的数据库）
    - A <javaModelGenerator> element to specify target package and target project for generated Java model objects（<javaModelGenerator>标签为生成的pojo指定哪个项目中的什么包）
    - A <sqlMapGenerator> element to specify target package and target project for generated SQL map files（<sqlMapGenerator>为生成的mapper.xml生成指定位置）
    - (Optionally) A <javaClientGenerator> element to specify target package and target project for generated client interfaces and classes (you may omit the <javaClientGenerator> element if you don't wish to generate Java client code)（随意，<javaClientGenerator>为生成的dao接口指定位置，如果不想生成可以忽略掉）
    - At least one database <table> element（至少有一个表对应pojo）
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

	<context id="DB2Tables" targetRuntime="MyBatis3">
		<commentGenerator>
			<property name="suppressAllComments" value="true" />
		</commentGenerator>
		<!-- 配置数据库连接 -->
		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
			connectionURL="jdbc:mysql://localhost:3306/ecommerce" userId="root"
			password="123456">
		</jdbcConnection>

		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>

		<!-- pojo生成的位置 -->
		<javaModelGenerator targetPackage="eCommerce.pojo"
			targetProject=".\src\main\java">
			<property name="enableSubPackages" value="true" />
			<property name="trimStrings" value="true" />
		</javaModelGenerator>

		<!--映射文件mapper.xml生成的位置 -->
		<sqlMapGenerator targetPackage="mapper" targetProject=".\src\main\resources">
			<property name="enableSubPackages" value="true" />
		</sqlMapGenerator>

		<!-- dao层的位置-->
		<javaClientGenerator type="XMLMAPPER"
			targetPackage="eCommerce.dao" targetProject=".\src\main\java">
			<property name="enableSubPackages" value="true" />
		</javaClientGenerator>


		<!-- table对应pojo-->
		<table tableName="cart" domainObjectName="Cart"></table>
		<table tableName="category" domainObjectName="Category"></table>
		<table tableName="order" domainObjectName="Order"></table>
		<table tableName="order_item" domainObjectName="OrderItem"></table>

		<table tableName="pay_info" domainObjectName="payInfo"></table>
		<table tableName="product" domainObjectName="Product"></table>
		<table tableName="shipping" domainObjectName="Shipping"></table>
		<table tableName="user" domainObjectName="User"></table>
	</context>
</generatorConfiguration>
```

#### 创建相应的数据库表建立之间的关联及索引
- 这里使用慕课网中的.sql文件

#### 最后的目录结构
![image](http://p3379ff71.bkt.clouddn.com/2018-07-13_202942.png)
