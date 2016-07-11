package com.zlj.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import com.zlj.po.AccessToken;
import com.zlj.po.TextMessage;
import com.zlj.util.CheckUtil;
import com.zlj.util.MessageUtil;
import com.zlj.util.WeixinUtil;

public class WeixinServlet extends HttpServlet
{
  @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	 
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
		
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce))
		{
			out.print(echostr);
		}
	}
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	  AccessToken token=WeixinUtil.localAccessToken();
  	  String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
  	  int result=WeixinUtil.createMenu(token.getAccess_token(), menu);
	  req.setCharacterEncoding("UTF-8");
	  resp.setCharacterEncoding("UTF-8");
	  PrintWriter out=resp.getWriter();
	  try {
		Map<String, String> map=MessageUtil.xmlToMap(req);
		String fromUserName=map.get("FromUserName");
		String toUserName=map.get("ToUserName");
		//String createTime=map.get("CreateTime");
		String msgType=map.get("MsgType");
		String content=map.get("Content");
		//String msgId=map.get("MsgId");
		String message=null;
		if(MessageUtil.MESSAGE_TEXT.equals(msgType))
		{
			if("1".equals(content))
			{
				message=MessageUtil.initText(toUserName, fromUserName,MessageUtil.firstMenu());
			}else if("2".equals(content))
			{
				message=MessageUtil.initText(toUserName, fromUserName,MessageUtil.secondMenu());
			}
			else if("3".equals(content))
			{
				message=MessageUtil.initNewsMessage(toUserName, fromUserName);
			}
			else if("4".equals(content))
			{
				message=MessageUtil.initNewsMessages(toUserName, fromUserName);
			}
			else if("5".equals(content))
			{
				message=MessageUtil.initImageMessage(toUserName, fromUserName);
			}
			else if("6".equals(content))
			{
				message=MessageUtil.initMusicMessage(toUserName, fromUserName);
			}
			else if("?".equals(content)||"？".equals(content))
			{
				message=MessageUtil.initText(toUserName, fromUserName,MessageUtil.menuText());
			}
			/*TextMessage text=new TextMessage();
			text.setFromUserName(toUserName);
			text.setToUserName(fromUserName);
			text.setMsgType("text");
			text.setCreateTime(new Date().getTime());
			text.setContent("您发送的消息是:"+content);
			message=MessageUtil.textMessageToXml(text);*/
		}
		else if(MessageUtil.MESSAGE_EVENT.equals(msgType))
		{
			String eventType=map.get("Event");
			if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){//关注事件
				message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
			}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){//点击事件
				message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
			}else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){//点击跳转事件
				String url = map.get("EventKey");
				message = MessageUtil.initText(toUserName, fromUserName, url);
			}else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){//扫描事件
				String key = map.get("EventKey");
				message = MessageUtil.initText(toUserName, fromUserName, key);
			}
		}
		else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){//获取地理位置事件
			String label = map.get("Label");
			message = MessageUtil.initText(toUserName, fromUserName, label);
		}
		System.out.println(message);
		out.print(message);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 finally{
		 out.close();
	 }
	  
  }
}
