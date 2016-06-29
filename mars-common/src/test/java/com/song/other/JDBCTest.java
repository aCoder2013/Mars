package com.song.other;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by song on 16/6/18.
 */
public class JDBCTest {

  private Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "iha0p1ng");

  public JDBCTest() throws SQLException {
  }

  @Test
  public void test() throws SQLException {
    PreparedStatement statement = null;
    String sql = "insert into user values(?,?,?)";
    statement = connection.prepareStatement(sql);
    statement.setInt(1, 3);
    statement.setString(2, "你懂的");
    statement.setString(3, "123123");
    statement.execute();
  }


  @After
  public void destroy() throws SQLException {
    connection.close();
  }
}
