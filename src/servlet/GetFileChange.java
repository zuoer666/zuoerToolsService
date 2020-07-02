package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import object.BaseRequestObject;
import object.FileChangeGetResultObject;
import object.FileChangeObjectBase;
import object.FileChangeSetResultObject;
import service.GetFileChangeService;
import service.GetHashUUID;

public class GetFileChange extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();
		String base64getFileChangeJson = req.getParameter("getFileChangeJson");
		String getFileChangeJson=new String(decoder.decode(base64getFileChangeJson ), "UTF-8");
		System.out.println("-- getFileChangeJson:" +getFileChangeJson);
		
		//解析json
		BaseRequestObject baseRequestObject = JSON.parseObject(getFileChangeJson, BaseRequestObject.class);
		System.out.println(baseRequestObject);
		String getuuidString = GetHashUUID.GetUUIDByUsername(baseRequestObject.getUsername());
		List<FileChangeObjectBase> list = new ArrayList<FileChangeObjectBase>();
		int resultcode=-2;
		if (getuuidString.equals(baseRequestObject.getHashUUID())) {
			resultcode=-1;
			list= new GetFileChangeService().getFileChange(baseRequestObject.getUsername(),baseRequestObject.getHostname());
		}else {
			resultcode=-2;
		}
		FileChangeGetResultObject fileChangeGetResultObject = new FileChangeGetResultObject(resultcode, list);
		String outString = JSON.toJSONString(fileChangeGetResultObject);
		//编码
		byte[] textByte = null;
		try {
			textByte = outString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);
		out.print(encodedText);
		System.out.print("GetFileChangeResults:"+outString);
		out.flush();
		out.close();
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
