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
import service.LoginService;

public class Login extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();
		String base64loginJson = req.getParameter("loginJson");
		String loginJson = new String(decoder.decode(base64loginJson), "UTF-8");
		System.out.println("-- loginJson:" + loginJson);
		// 解析json
		UserObject userObject = JSON.parseObject(loginJson, UserObject.class);
		boolean boolean_login = false;
		LoginService loginService = new LoginService();
		String outString = "{";
		boolean_login = loginService.Login(userObject);
		outString += "'code':";
		if (boolean_login) {
			outString += "1,'hashuuid':'" + loginService.getHashuuid() + "'";
		} else {
			outString += "0,'hashuuid':''";
		}

		outString += "}";
		// 编码
		byte[] textByte = null;
		try {
			textByte = outString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);
		out.print(encodedText);
		System.out.print("loginResults:" + outString);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
