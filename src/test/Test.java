package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


import com.alibaba.fastjson.JSON;

import object.FileChangeObjectBase;

public class Test {

	//@org.junit.Test
	public void name() {
		String text = "{'hostname':'123','pwd_directory':'pwd','have_directory':'have_wpd','"
				+ "file_name':'filename','file_time':'2020-05-06%2019:34:31'}";
		String filechangejson = "eydob3N0bmFtZSc6JzEyMycsJ3B3ZF9kaXJlY3RvcnknOidwd2QnLCdoYXZlX2RpcmVjdG9yeSc6J2hhdmVfd3BkJywnZmlsZV9uYW1lJzonZmlsZW5h"
				+ "bWUnLCdmaWxlX3RpbWUnOicyMDIwLTA1LTA2JTIwMTk6MzQ6MzEnfQ==";
		String textlist = "[{'hostname':'123','pwd_directory':'pwd','have_directory':'have_wpd','"
				+ "file_name':'filename','file_time':'2020-05-06 19:34:31'},{'hostname':'1223','pwd_directory':'pwd','have_directory':'have_wpd','"
				+ "file_name':'filename','file_time':'2020-05-06 19:34:31'}]";
		//System.out.println(text);
		//FileChangeObject fileChangeObject=JSON.parseObject(text, FileChangeObject.class);
		//System.out.println(fileChangeObject.getFile_time());
		List<FileChangeObjectBase> fileChangeObjectList = JSON.parseArray(text, FileChangeObjectBase.class);
		for (int i = 0; i < fileChangeObjectList.size(); i++) {
			System.out.println(fileChangeObjectList.get(i));
		}
	}

	
	@org.junit.Test
	public void proxy(){
		String[] cmds = {"curl", "-H", "Host: japan2.zuoerya.ga", "-H", "Cache-Control: max-age=0", "--compressed", "https://www.baidu.com/"};


		ProcessBuilder process = new ProcessBuilder(cmds);
		Process p;
		try {
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			System.out.println(builder.toString());

		} catch (IOException e) {
			System.out.print("error");
			e.printStackTrace();
		}

	}
}
