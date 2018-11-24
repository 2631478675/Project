package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private final static Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);


//	@Autowired
//	public DataSource dataSource;

	@Autowired
	@Qualifier("secondDataSource")
	private DataSource dataSource1;

	@Test
	public void contextLoads() throws SQLException {


		Connection connection = dataSource1.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement("select age from person where id = 1");

		ResultSet rs = preparedStatement.executeQuery();

		while(rs.next()){
			logger.info(String.valueOf(rs.getInt(1)));
		}



	}

}
