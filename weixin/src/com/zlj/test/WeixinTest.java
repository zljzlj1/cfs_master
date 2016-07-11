package com.zlj.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import com.zlj.po.AccessToken;
import com.zlj.util.WeixinUtil;

public class WeixinTest {
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
    	/*AccessToken token=WeixinUtil.getAccessToken();
    	System.out.println("票据"+token.getAccess_token());//DDc8iL07Yzkks5PvbH1yx5Tv4GHzloCd6d1Jen64c8Xv0t0NYGV8qgqwRgSYHZh9um5RtA5_wAq2KA0Kt-tNXdJTvFzpg8j3pJbb8JwxpbDkLsY3kKaAoMyzxIdhf3GkZIZeAFASBS
    	System.out.println("有效时间"+token.getExpires_in());
    	String path = "D:/bike.jpg";
		String mediaId = WeixinUtil.upload(path, token.getAccess_token(), "image");
		System.out.println(mediaId);*/
    	
    	/*System.out.println("票据"+token.getAccess_token());//DDc8iL07Yzkks5PvbH1yx5Tv4GHzloCd6d1Jen64c8Xv0t0NYGV8qgqwRgSYHZh9um5RtA5_wAq2KA0Kt-tNXdJTvFzpg8j3pJbb8JwxpbDkLsY3kKaAoMyzxIdhf3GkZIZeAFASBS
    	String path = "D:/bike.jpg";
		String mediaId = WeixinUtil.upload(path, token.getAccess_token(), "thumb");
	   System.out.println(mediaId);*/
    	AccessToken token=WeixinUtil.localAccessToken();
    	String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
    	int result=WeixinUtil.createMenu(token.getAccess_token(), menu);
    	if(result==0)
    	{
    		System.out.println("创建成功");
    	}else{
    		System.out.println("错误码:"+result);
    	}
    	/*AccessToken token=WeixinUtil.localAccessToken();
    	int  js=WeixinUtil.deleteMenu(token.getAccess_token());
    	System.out.println(js);*/
	}
}
