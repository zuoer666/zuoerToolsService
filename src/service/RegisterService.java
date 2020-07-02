package service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import myutil.HashUtils;
import myutil.MyDBUtill;
import myutil.MyDate;
import object.UserObject;

public class RegisterService {
	public Boolean Register(UserObject userObject) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();
		
		//加密
		String encryptCMD5UUIDbefore=UUID.randomUUID().toString().replaceAll("-", "")+new MyDate().getCurrentTime();
		String encryptCMD5UUID = null;
		try {
			encryptCMD5UUID = HashUtils.encrypt(encryptCMD5UUIDbefore.getBytes("UTF-8"), "MD5");
			//String fileMd5 = HashUtils.encrypt(new File("demo.jpg"), "MD5");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sbSql.append("Insert ");
		sbSql.append("into user(username,password,hashuuid) ");
		sbSql.append("values(?,?,?)");
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,userObject.getUsername());
			pst.setString(2,userObject.getPassword());
			pst.setString(3, encryptCMD5UUID);
			System.out.println(pst.toString());
			int resul=pst.executeUpdate();
			if(resul!=0) {
				System.out.println("注册成功");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyDBUtill.close(rs, pst, conn);
		}

		return false;
	}
}
