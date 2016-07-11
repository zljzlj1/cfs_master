package com.zlj.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.zlj.po.AccessToken;
import com.zlj.po.Image;
import com.zlj.po.ImageMessage;
import com.zlj.po.Music;
import com.zlj.po.MusicMessage;
import com.zlj.po.News;
import com.zlj.po.NewsMessage;
import com.zlj.po.TextMessage;
/**
 * xml装换成map集合
 * @author Administrator
 *
 */
public class MessageUtil {
	 public static final String NEWSMESSAGE_URL="http://weixin.zlj.ngrok.cc/";
	 public static final String MESSAGE_NEWS="news";
	 public static final String MESSAGE_TEXT="text";
	 public static final String MESSAGE_IMAGE="image";
	 public static final String MESSAGE_VOICE="voice";
	 public static final String MESSAGE_MUSIC = "music";
	 public static final String MESSAGE_VIDEO="video";
	 public static final String MESSAGE_LINK="link";
	 public static final String MESSAGE_LOCATION="location";
	 public static final String MESSAGE_EVENT="event";
	 public static final String MESSAGE_SUBSCRIBE="subscribe";
	 public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	 public static final String MESSAGE_CLICK="CLICK";
	 public static final String MESSAGE_VIEW="VIEW";
	 public static final String MESSAGE_SCANCODE= "scancode_push";
     public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
    	 Map<String,String> map=new HashMap<String, String>();
    	 SAXReader reader=new SAXReader();
    	 InputStream ins=request.getInputStream();
    	 Document doc=reader.read(ins);
    	 Element root=doc.getRootElement();
    	 List<Element> list=root.elements();
    	 for(Element e:list)
    	 {
    		 map.put(e.getName(), e.getText());
    	 }
    	 ins.close();
    	 return map;
     }
     /**
      * 将文本消息对象转为xml
      * @param textMessage
      * @return
      */
     public static String textMessageToXml(TextMessage textMessage)
     {
    	 XStream xstream=new XStream();
    	 xstream.alias("xml", textMessage.getClass());
    	 return xstream.toXML(textMessage); 
     }
     public static String initText(String toUserName,String fromUserName,String content){
    	 TextMessage text=new TextMessage();
			text.setFromUserName(toUserName);
			text.setToUserName(fromUserName);
			text.setMsgType(MessageUtil.MESSAGE_TEXT);
			text.setCreateTime(new Date().getTime());
			text.setContent(content);
			return textMessageToXml(text); 
     }
     /**
      * 主菜单
      * @return
      */
     public static String menuText(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("欢迎您的关注，请按照菜单提示进行操作:\n\n");
    	 sb.append("1.课程介绍\n");
    	 sb.append("2.小结\n");
    	 sb.append("3.单图文介绍\n");
    	 sb.append("4.多图文介绍\n");
    	 sb.append("5.回复图片消息\n");
    	 sb.append("6.回复音乐消息\n\n");
    	 sb.append("回复?调出此菜单。\n");
    	 return sb.toString();
     }
     public static String firstMenu(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("本套课程主要介绍微信公众号开发，主要涉及公众号介绍、编辑模式介绍、开发模式介绍等");
    	 return sb.toString();
     }
     public static String secondMenu(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("永远记住：自始至终，实践是学习Java技术历程中极其重要的一环。脱离了实践，是学不好技术的");
    	 sb.append("Java学习、实践之路依然没有银弹，况且一步步走来，要学的东西很多，勤奋是金!");
    	 sb.append("善于利用Internet上众多的资源，多吸取些别人的经验。");
    	 sb.append("目前关于Java的争论很多，如“Java正走下坡路”、“Ruby必将取代Java”等论点甚嚣尘上，但正如我前面所言，这是一种浮躁的表现，Java的前景非常好，特别是Java开源以后！就让他们去吵去吧，掌握了Java，你就掌握了当前最火的技术。");
		 sb.append("“梅花香自苦寒来”，当你掌握了扎实的Java开发基础，你就能把手中的这把“屠龙刀”、“倚天剑”舞得风声水起！那时，你一定会有一种成就感。");
		 sb.append("更多Java知识敬请期待！");
		 sb.append("想要了解更多Java知识点击加入我们一起学习交流吧！【Java学习交流】：");
    	 return sb.toString();
     }
     /**
      * 将图文消息对象转为xml
      * @param newsMessage
      * @return
      */
     public static String newsMessageToXml(NewsMessage newsMessage)
     {
    	 XStream xstream=new XStream();
    	 xstream.alias("xml", newsMessage.getClass());
    	 xstream.alias("item", new News().getClass());
    	 return xstream.toXML(newsMessage); 
     }
     /**
      * 单图文消息的组装
      * @param toUserName
      * @param fromUserName
      * @return
      */
     public static String initNewsMessage(String toUserName,String fromUserName){
    	 String message=null;
    	 List<News> newsList=new ArrayList<News>();
    	 NewsMessage newsmessage=new NewsMessage();
    	 News news=new News();
    	 news.setTitle("高等数学介绍");
    	 news.setDescription("《高等数学》是培养学生掌握科学思维能力、掌握数学知识和数学技术的重要基础课程。该课程所论及的科学思想和方法论，在自然科学、工程技术、经济和社会科学等领域中具有广泛应用和强劲的活力。大学高等数学是每位大学生都应该掌握的一门学科，不管是理科生还是文科生。因为数学是一门古老而又十分重要的自然学科。高等数学建立在初等数学基础之上，结构严谨，对于学生的逻辑思维以及运算能力有较高的要求，是各理工学科的基础。学好了数学，也就为其他学科的学习打下了坚实的基础。高等数学是解决其他相关问题的良好工具，而其中函数极限和微积分又是贯穿于其中的重要部分，是学习的核心。 ");
    	 news.setPicUrl(NEWSMESSAGE_URL+"image/math.jpg");
    	 news.setUrl("http://baike.baidu.com/link?url=C9Qh6zg5n1VvJq6uyR9FGiW33lcWOxSo9J9Te8TJPmL7MM4ejX4ebmAZOV_HVS9Guu3NTAy_JcBZS9tuziLkD_");//跳转的地址
    	 newsList.add(news);
    	 newsmessage.setFromUserName(toUserName);
    	 newsmessage.setToUserName(fromUserName);
    	 newsmessage.setCreateTime(new Date().getTime());
    	 newsmessage.setMsgType(MESSAGE_NEWS);
    	 newsmessage.setArticles(newsList);
    	 newsmessage.setArticleCount(newsList.size());
    	 return newsMessageToXml(newsmessage);
     }
     /**
      * 多图文消息的组装
      * @param toUserName
      * @param fromUserName
      * @return
      */
     public static String initNewsMessages(String toUserName,String fromUserName){
    	 String message=null;
    	 List<News> newsList=new ArrayList<News>();
    	 NewsMessage newsmessage=new NewsMessage();
    	 News news=new News();
    	 news.setTitle("高等数学介绍");
    	 news.setDescription("《高等数学》是培养学生掌握科学思维能力、掌握数学知识和数学技术的重要基础课程。该课程所论及的科学思想和方法论，在自然科学、工程技术、经济和社会科学等领域中具有广泛应用和强劲的活力。大学高等数学是每位大学生都应该掌握的一门学科，不管是理科生还是文科生。因为数学是一门古老而又十分重要的自然学科。高等数学建立在初等数学基础之上，结构严谨，对于学生的逻辑思维以及运算能力有较高的要求，是各理工学科的基础。学好了数学，也就为其他学科的学习打下了坚实的基础。高等数学是解决其他相关问题的良好工具，而其中函数极限和微积分又是贯穿于其中的重要部分，是学习的核心。 ");
    	 news.setPicUrl(NEWSMESSAGE_URL+"image/math.jpg");
    	 news.setUrl("http://baike.baidu.com/link?url=C9Qh6zg5n1VvJq6uyR9FGiW33lcWOxSo9J9Te8TJPmL7MM4ejX4ebmAZOV_HVS9Guu3NTAy_JcBZS9tuziLkD_");//跳转的地址
    	 newsList.add(news);
    	 News news1=new News();
    	 news1.setTitle("JPA criteria 查询:类型安全与面向对象");
    	 news1.setDescription("自工作以来,除了以前比较流量的hibernate,就是一直使用ORM 规范 JPA了.而这几天工作需要,研究了下JPA的标准查询,名为:JPA criteria查询.相比JPQL,其优势是类型安全,更加的面向对象.使用标准查询,开发人员可在编译的时候就检查查询的正确与否.而以前也只是在Hibernate中听说有过.具体不详,没用过. ");
    	 news1.setPicUrl(NEWSMESSAGE_URL+"image/jpa.jpg");
    	 news1.setUrl("http://my.oschina.net/zhaoqian/blog/133500?fromerr=sLyuV1Iq");//跳转的地址
    	 newsList.add(news1);
    	 newsmessage.setFromUserName(toUserName);
    	 newsmessage.setToUserName(fromUserName);
    	 newsmessage.setCreateTime(new Date().getTime());
    	 newsmessage.setMsgType(MESSAGE_NEWS);
    	 newsmessage.setArticles(newsList);
    	 newsmessage.setArticleCount(newsList.size());
    	 return newsMessageToXml(newsmessage);
			
     }
     /**
 	 * 图片消息转为xml
 	 * @param imageMessage
 	 * @return
 	 */
 	public static String imageMessageToXml(ImageMessage imageMessage){
 		XStream xstream = new XStream();
 		xstream.alias("xml", imageMessage.getClass());
 		return xstream.toXML(imageMessage);
 	}
     /**
 	 * 组装图片消息
 	 * @param toUserName
 	 * @param fromUserName
 	 * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
 	 */
 	public static String initImageMessage(String toUserName,String fromUserName) throws Exception{
 		AccessToken token=WeixinUtil.localAccessToken();
    	System.out.println("票据"+token.getAccess_token());//DDc8iL07Yzkks5PvbH1yx5Tv4GHzloCd6d1Jen64c8Xv0t0NYGV8qgqwRgSYHZh9um5RtA5_wAq2KA0Kt-tNXdJTvFzpg8j3pJbb8JwxpbDkLsY3kKaAoMyzxIdhf3GkZIZeAFASBS
    	String path = "D:/bike.jpg";
		String mediaId = WeixinUtil.upload(path, token.getAccess_token(), "image");
 		Image image = new Image();
 		image.setMediaId(mediaId);
 		ImageMessage imageMessage = new ImageMessage();
 		imageMessage.setFromUserName(toUserName);
 		imageMessage.setToUserName(fromUserName);
 		imageMessage.setMsgType(MESSAGE_IMAGE);
 		imageMessage.setCreateTime(new Date().getTime());
 		imageMessage.setImage(image);
 		return imageMessageToXml(imageMessage);
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
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName) throws Exception{
		Music music = new Music();
		music.setThumbMediaId("OC5IoCAiHCapPH1XuGYcgcr7LiRGUPS3Hfd2juWkeRdTK2iIFS1om90hfb02DeU3");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl(NEWSMESSAGE_URL+"resource/See You Again.mp3");
		music.setHQMusicUrl(NEWSMESSAGE_URL+"resource/See You Again.mp3");
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		return musicMessageToXml(musicMessage);
	}
}
