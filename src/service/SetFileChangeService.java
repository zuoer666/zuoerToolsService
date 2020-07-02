package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import myutil.MyDBUtill;
import object.FileChangeObjectBase;



public class SetFileChangeService {
	
	public Boolean SetFileTime(FileChangeObjectBase fileChangeObject,String username) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = MyDBUtill.getConnection();
		//sql语句
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("Insert ");
		sbSql.append("into filechange(uuid,hostname,pwd_directory,have_directory,file_name,file_time,username) ");
		sbSql.append("values(?,?,?,?,?,?,?)");
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
			pst.setString(2,fileChangeObject.getHostname());
			pst.setString(3,fileChangeObject.getPwd_directory());
			pst.setInt(4,Integer.parseInt(fileChangeObject.getHave_directory()));
			pst.setString(5,fileChangeObject.getFile_name());
			pst.setString(6,fileChangeObject.getFile_time());
			pst.setString(7,username);

			int resul=pst.executeUpdate();
			if(resul!=0) {
				System.out.println("设置时间成功");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyDBUtill.close(rs, pst, conn);
		}

		return false;
	}
	
	public boolean deleteByUsername(String username,String hostname) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		conn = MyDBUtill.getConnection();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("Delete ");
		sbSql.append("from filechange ");
		sbSql.append("where username=? and hostname = ?");
		try {
			pst = conn.prepareStatement(sbSql.toString());
			pst.setString(1, username);
			pst.setString(2, hostname);
			System.out.println(pst);
			int resul=pst.executeUpdate();
			if(resul!=-1) {
				System.out.println("操作成功");
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
