package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;

import myutil.MyDBUtill;
import object.FileChangeObjectBase;
import object.UserObject;

public class GetFileChangeService {
	
	public List<FileChangeObjectBase> getFileChange(String username,String hostname) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<FileChangeObjectBase> list = new ArrayList<FileChangeObjectBase>();
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();

		sbSql.append("Select ");
		sbSql.append("		hostname,pwd_directory,have_directory,file_name,file_time ");
		sbSql.append("From filechange ");
		sbSql.append("		Where hostname = ? and username = ?");
		
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1,hostname);
			pst.setString(2,username);
			System.out.println(pst.toString());
			rs = pst.executeQuery();

			while (rs.next()) {
				FileChangeObjectBase row = new FileChangeObjectBase(rs.getString("hostname"), rs.getString("pwd_directory"),
						String.valueOf(rs.getInt("have_directory")), rs.getString("file_name"), rs.getString("file_time"));
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyDBUtill.close(rs, pst, conn);
		}
		return list;
	}
}
