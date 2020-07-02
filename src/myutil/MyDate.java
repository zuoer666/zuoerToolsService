package myutil;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyDate {
	public Date dateChange(String date1)
	{
		SimpleDateFormat  sdf =new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date cDate = null;
		Date dd=null;

		try {
			cDate = sdf.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dd =new Date(cDate.getTime());
		System.out.println(dd);
		return dd;
	}
	public String getCurrentTime(){
		 SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
	        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记  
	        java.util.Date date = new java.util.Date();// 获取当前时间 
	        //System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制） 
			return sdf.format(date);
	}
}
