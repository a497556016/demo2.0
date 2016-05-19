package com.demo.common.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.demo.common.model.SysIcon;
import com.sina.sae.util.SaeUserInfo;

public class Test {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_weixinheshaowei";
		String username = SaeUserInfo.getAccessKey();
		String password = SaeUserInfo.getSecretKey();
		String driver="com.mysql.jdbc.Driver";
		Class.forName(driver).newInstance();
		Connection con = DriverManager.getConnection(url,username,password);
		String sql = "select * form user";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Object obj1 = rs.getObject(1);
			Object obj2 = rs.getObject(2);
			System.out.println(obj1+"\n"+obj2);
		}
	}
}
