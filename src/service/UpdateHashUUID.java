package service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import myutil.HashUtils;
import myutil.MyDBUtill;
import myutil.MyDate;


public class UpdateHashUUID {
	String UUIDString ="";
	
	public String getUUIDString() {
		return UUIDString;
	}

	public void setUUIDString(String UUIDString) {
		this.UUIDString = UUIDString;
	}

	public boolean updateHashUUID(String username) {
		Connection conn = null;
		PreparedStatement pst = null;

		conn = MyDBUtill.getConnection();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("UPDATE user ");
		sbSql.append("		SET  hashuuid = ? ");
		sbSql.append("WHERE username= ? ");
		String encryptCMD5UUIDbefore=UUID.randomUUID().toString().replaceAll("-", "")+new MyDate().getCurrentTime();
		String encryptCMD5UUID = null;
		try {
			encryptCMD5UUID = HashUtils.encrypt(encryptCMD5UUIDbefore.getBytes("UTF-8"), "MD5");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setUUIDString(encryptCMD5UUID);
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,encryptCMD5UUID);
			pst.setString(2, username);
			int res = pst.executeUpdate();
			if (res > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(null, pst, conn);
		}

		return false;
	}
}
