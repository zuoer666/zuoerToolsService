package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import myutil.MyDBUtill;


public class isExistFileTime {
	public Boolean isExistFile(String username,String hostname) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();

		sbSql.append("Select ");
		sbSql.append("		uuid ");
		sbSql.append("From filechange ");
		sbSql.append("		Where hostname = ? and username = ?");
		
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,hostname);
			pst.setString(2,username);
			rs = pst.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(rs, pst, conn);
		}
		return false;
		
	}
}
