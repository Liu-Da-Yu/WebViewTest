package com.example.webviewtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * author: JST - Dayu
 * date:   2019/8/22  19:07
 * context:
 */
@SuppressWarnings("all")
public class MySql {

    private final String driver = "com.mysql.jdbc.Driver";
    private final String url ="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private final String username= "root";
    private final String password = "123123123";

    public void connDB(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName(driver);
                    Connection conn = DriverManager.getConnection(url, username, password);
                    String sql = "select * from user where username = ? and password = ? ";
                    PreparedStatement psmt = conn.prepareStatement(sql);
                    psmt.setString(1, "111");
                    psmt.setString(2, "111");
                    ResultSet rs = psmt.executeQuery();
                    while (rs.next()) {
                        System.out.println(rs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
