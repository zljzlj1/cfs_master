package com.zlj.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.thoughtworks.xstream.XStream;
import com.zlj.menu.Button;
import com.zlj.menu.ClickButton;
import com.zlj.menu.Menu;
import com.zlj.menu.ViewButton;
import com.zlj.po.AccessToken;
import com.zlj.po.MusicMessage;

public class WeixinUtil {
	/**
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 *	 @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
      private static final String APPID="wxef3cc97167694a7d";
      private static final String APPSECRET="e0ba47186b2d96e20af68abd3f056084";
      private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
      public static long t1=0;
      public static  AccessToken ftoken=null;
      /**
       *get请求
       * @author Administrator
     * @throws IOException 
     * @throws ClientProtocolException 
       *
       */
     public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException{//url为接口地址参数
    	 DefaultHttpClient client = new DefaultHttpClient();
 		HttpGet httpGet = new HttpGet(url);
 		JSONObject jsonObject = null;
 		HttpResponse httpResponse = client.execute(httpGet);
 		HttpEntity entity = httpResponse.getEntity();
 		if(entity != null){
 			String result = EntityUtils.toString(entity,"UTF-8");
 			jsonObject = JSONObject.fromObject(result);
 		}
 		return jsonObject;
     }
     public static AccessToken getAccessToken() throws ClientProtocolException, IOException{
    	 AccessToken token=new AccessToken();
    	 String url=ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
    	 JSONObject jsonObject=doGetStr(url);
    	 if(jsonObject!=null)
    	 {
    		 token.setAccess_token(jsonObject.getString("access_token"));
    		 token.setExpires_in(jsonObject.getInt("expires_in"));
    	 }
    	 ftoken=token;
    	 return token;
     }
     /**
      * 获取access_token
      * @return
     * @throws IOException 
     * @throws ClientProtocolException 
      */
     public static AccessToken localAccessToken() throws ClientProtocolException, IOException{
    	 AccessToken token=new AccessToken();
    	 if(t1==0)
    	 {
    		 t1=new Date().getTime();
	    	 token=getAccessToken();
	    	 ftoken=token;
	    	 return token;
    	 }
    	 else{
	    	 long between = (new Date().getTime() - t1) / 1000;// 除以1000是为了转换成秒
	    	 long hour1 = between % (24 * 3600) / 3600;
	    	 if(hour1>=2)
	    	 {
	    		 t1=new Date().getTime();
		    	 token=getAccessToken();
		    	 ftoken=token;
		    	 return token;
	    	 }
    	 }

    	 return ftoken;
     }
     /***
      * get请求
      * @param url
      * @param outstr
      * @return
     * @throws IOException 
     * @throws ParseException 
      */
     public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{//url为接口地址参数
    	 DefaultHttpClient client = new DefaultHttpClient();
 		HttpPost httpost = new HttpPost(url);
 		JSONObject jsonObject = null;
 		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
 		HttpResponse response = client.execute(httpost);
 		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
 		jsonObject = JSONObject.fromObject(result);
 		return jsonObject;
    	
     }
     /**
 	 * 音乐消息转为xml
 	 * @param musicMessage
 	 * @return
 	 */
 	public static String musicMessageToXml(MusicMessage musicMessage){
 		XStream xstream = new XStream();
 		xstream.alias("xml", musicMessage.getClass());
 		return xstream.toXML(musicMessage);
 	}
 	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("百度一下");
		button21.setType("view");
		button21.setUrl("https://www.baidu.com/");
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
 	
}
