package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import object.UserObject;
import service.RegisterService;

public class Register extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();
		String base64registerJson = req.getParameter("registerJson");
		String registerJson=new String(decoder.decode(base64registerJson ), "UTF-8");
		System.out.println("-- registerJson:" +registerJson);
		
		//解析json
		UserObject userObject = JSON.parseObject(registerJson,UserObject.class);
		boolean boolean_register = false;
		RegisterService registerService = new RegisterService();
		String outString="";
		boolean_register = registerService.Register(userObject);
			if (boolean_register) {
				outString+="success";
			} else {
				outString+="failed";
			}
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
		System.out.print("registerResults:"+outString);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

	
}
