package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import myutil.MyDBUtill;
import object.UserObject;

public class LoginService {
	private String hashuuid="";
	
	public String getHashuuid() {
		return hashuuid;
	}

	public void setHashuuid(String hashuuid) {
		this.hashuuid = hashuuid;
	}

	public Boolean Login(UserObject userObject) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Object[]> rows = new ArrayList<>();
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();

		sbSql.append("Select ");
		sbSql.append("		password,hashuuid ");
		sbSql.append("From user ");
		sbSql.append("		Where username = ?");
		
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,userObject.getUsername());
			System.out.println(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {
				Object[] row = new Object[2];
				row[0] = rs.getString("password");
				row[1] = rs.getString("hashuuid");//旧UUID
				rows.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(rs, pst, conn);
		}
		if (rows.isEmpty()) {
			return false;
		}
		String sqlpasswordString=String.valueOf(rows.get(0)[0]);
		if (sqlpasswordString.equals(userObject.getPassword())) {
			UpdateHashUUID updateHashUUID = new UpdateHashUUID();
			//更新UUID
			Boolean tempBoolean =updateHashUUID.updateHashUUID(userObject.getUsername());
			
			//获取新UUID并赋值
			if (tempBoolean) {
				setHashuuid(updateHashUUID.getUUIDString());
			}else {
				return false;
			}
			return true;
		}
		return false;
		
	}
	
	
}
