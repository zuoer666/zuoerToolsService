package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import myutil.MyDBUtill;


public class GetHashUUID {
	public static String GetUsernameByUUID(String UUID) {
		String result="";
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();

		sbSql.append("Select ");
		sbSql.append("		username ");
		sbSql.append("From user ");
		sbSql.append("		Where hashuuid = ?");
		
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,UUID);
			System.out.println(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {
				result=rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(rs, pst, conn);
		}
		
		return result;
		
	}
	public static String GetUUIDByUsername(String username) {
		String result="";
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();

		sbSql.append("Select ");
		sbSql.append("		hashuuid ");
		sbSql.append("From user ");
		sbSql.append("		Where username = ?");
		
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,username);
			System.out.println(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {
				result=rs.getString("hashuuid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(rs, pst, conn);
		}
		
		return result;
		
	}
}
