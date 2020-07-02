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

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;

import object.FileChangeObject;
import object.FileChangeSetResultObject;
import service.GetHashUUID;
import service.SetFileChangeService;

public class SetFileChange extends HttpServlet{



	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();
		//String base64filechangejson = req.getParameter("filechangejson");
		//JsonReader.receivePost(req);
		String base64filechangejson =IOUtils.toString(req.getInputStream());
		String filechangejson=new String(decoder.decode(base64filechangejson ), "UTF-8");
		System.out.println("-- filechangejson:" +filechangejson);


		SetFileChangeService serv = new SetFileChangeService();
		//解析json
		//FileChangeObject fileChangeObject=JSON.parseObject(filechangejson, FileChangeObject.class);
		FileChangeObject fileChangeObjectList = JSON.parseObject(filechangejson, FileChangeObject.class);
		System.out.println(fileChangeObjectList);
		String getuuidString = GetHashUUID.GetUUIDByUsername(fileChangeObjectList.getBaseRequestObject().getUsername());


		int resultcode=-2;
		List<String> resultList = new ArrayList<String>();
		if (getuuidString.equals(fileChangeObjectList.getBaseRequestObject().getHashUUID())) {
			resultcode=-1;
			boolean sfls = false;
			if(serv.deleteByUsername(fileChangeObjectList.getBaseRequestObject().getUsername(),fileChangeObjectList.getBaseRequestObject().getHostname())){
				//设置文件时间结果
				for (int i = 0; i < fileChangeObjectList.getFileChangeObjectBaseList().size(); i++) {
					sfls = serv.SetFileTime(fileChangeObjectList.getFileChangeObjectBaseList().get(i),fileChangeObjectList.getBaseRequestObject().getUsername());
					if (sfls) {
						resultList.add("success");
					} else {
						resultList.add("failed");
					}
				}
			}else {
				System.out.println("删除失败");
			}

		}else {
			resultcode=-2;
			for (int i = 0; i < fileChangeObjectList.getFileChangeObjectBaseList().size(); i++) {
				resultList.add("failed");
			}

		}
		FileChangeSetResultObject fileChangeResultObject = new FileChangeSetResultObject(resultcode, resultList);
		String outString = JSON.toJSONString(fileChangeResultObject);
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
		System.out.print("SetFileChangeResults:"+outString);
		out.flush();
		out.close();





	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}


}
