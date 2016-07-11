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
 * xmlװ����map����
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
      * ���ı���Ϣ����תΪxml
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
      * ���˵�
      * @return
      */
     public static String menuText(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в���:\n\n");
    	 sb.append("1.�γ̽���\n");
    	 sb.append("2.С��\n");
    	 sb.append("3.��ͼ�Ľ���\n");
    	 sb.append("4.��ͼ�Ľ���\n");
    	 sb.append("5.�ظ�ͼƬ��Ϣ\n");
    	 sb.append("6.�ظ�������Ϣ\n\n");
    	 sb.append("�ظ�?�����˲˵���\n");
    	 return sb.toString();
     }
     public static String firstMenu(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("���׿γ���Ҫ����΢�Ź��ںſ�������Ҫ�漰���ںŽ��ܡ��༭ģʽ���ܡ�����ģʽ���ܵ�");
    	 return sb.toString();
     }
     public static String secondMenu(){
    	 StringBuffer sb=new StringBuffer();
    	 sb.append("��Զ��ס����ʼ���գ�ʵ����ѧϰJava���������м�����Ҫ��һ����������ʵ������ѧ���ü�����");
    	 sb.append("Javaѧϰ��ʵ��֮·��Ȼû������������һ����������Ҫѧ�Ķ����ܶ࣬�ڷ��ǽ�!");
    	 sb.append("��������Internet���ڶ����Դ������ȡЩ���˵ľ��顣");
    	 sb.append("Ŀǰ����Java�����ۺܶ࣬�硰Java��������·������Ruby�ؽ�ȡ��Java�����۵��������ϣ���������ǰ�����ԣ�����һ�ָ���ı��֣�Java��ǰ���ǳ��ã��ر���Java��Դ�Ժ󣡾�������ȥ��ȥ�ɣ�������Java����������˵�ǰ���ļ�����");
		 sb.append("��÷�����Կຮ������������������ʵ��Java��������������ܰ����е���ѡ����������������콣����÷���ˮ����ʱ����һ������һ�ֳɾ͸С�");
		 sb.append("����Java֪ʶ�����ڴ���");
		 sb.append("��Ҫ�˽����Java֪ʶ�����������һ��ѧϰ�����ɣ���Javaѧϰ��������");
    	 return sb.toString();
     }
     /**
      * ��ͼ����Ϣ����תΪxml
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
      * ��ͼ����Ϣ����װ
      * @param toUserName
      * @param fromUserName
      * @return
      */
     public static String initNewsMessage(String toUserName,String fromUserName){
    	 String message=null;
    	 List<News> newsList=new ArrayList<News>();
    	 NewsMessage newsmessage=new NewsMessage();
    	 News news=new News();
    	 news.setTitle("�ߵ���ѧ����");
    	 news.setDescription("���ߵ���ѧ��������ѧ�����տ�ѧ˼ά������������ѧ֪ʶ����ѧ��������Ҫ�����γ̡��ÿγ����ۼ��Ŀ�ѧ˼��ͷ����ۣ�����Ȼ��ѧ�����̼��������ú�����ѧ�������о��й㷺Ӧ�ú�ǿ���Ļ�������ѧ�ߵ���ѧ��ÿλ��ѧ����Ӧ�����յ�һ��ѧ�ƣ�����������������Ŀ�������Ϊ��ѧ��һ�Ź��϶���ʮ����Ҫ����Ȼѧ�ơ��ߵ���ѧ�����ڳ�����ѧ����֮�ϣ��ṹ�Ͻ�������ѧ�����߼�˼ά�Լ����������нϸߵ�Ҫ���Ǹ���ѧ�ƵĻ�����ѧ������ѧ��Ҳ��Ϊ����ѧ�Ƶ�ѧϰ�����˼�ʵ�Ļ������ߵ���ѧ�ǽ�����������������ù��ߣ������к������޺�΢�������ǹᴩ�����е���Ҫ���֣���ѧϰ�ĺ��ġ� ");
    	 news.setPicUrl(NEWSMESSAGE_URL+"image/math.jpg");
    	 news.setUrl("http://baike.baidu.com/link?url=C9Qh6zg5n1VvJq6uyR9FGiW33lcWOxSo9J9Te8TJPmL7MM4ejX4ebmAZOV_HVS9Guu3NTAy_JcBZS9tuziLkD_");//��ת�ĵ�ַ
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
      * ��ͼ����Ϣ����װ
      * @param toUserName
      * @param fromUserName
      * @return
      */
     public static String initNewsMessages(String toUserName,String fromUserName){
    	 String message=null;
    	 List<News> newsList=new ArrayList<News>();
    	 NewsMessage newsmessage=new NewsMessage();
    	 News news=new News();
    	 news.setTitle("�ߵ���ѧ����");
    	 news.setDescription("���ߵ���ѧ��������ѧ�����տ�ѧ˼ά������������ѧ֪ʶ����ѧ��������Ҫ�����γ̡��ÿγ����ۼ��Ŀ�ѧ˼��ͷ����ۣ�����Ȼ��ѧ�����̼��������ú�����ѧ�������о��й㷺Ӧ�ú�ǿ���Ļ�������ѧ�ߵ���ѧ��ÿλ��ѧ����Ӧ�����յ�һ��ѧ�ƣ�����������������Ŀ�������Ϊ��ѧ��һ�Ź��϶���ʮ����Ҫ����Ȼѧ�ơ��ߵ���ѧ�����ڳ�����ѧ����֮�ϣ��ṹ�Ͻ�������ѧ�����߼�˼ά�Լ����������нϸߵ�Ҫ���Ǹ���ѧ�ƵĻ�����ѧ������ѧ��Ҳ��Ϊ����ѧ�Ƶ�ѧϰ�����˼�ʵ�Ļ������ߵ���ѧ�ǽ�����������������ù��ߣ������к������޺�΢�������ǹᴩ�����е���Ҫ���֣���ѧϰ�ĺ��ġ� ");
    	 news.setPicUrl(NEWSMESSAGE_URL+"image/math.jpg");
    	 news.setUrl("http://baike.baidu.com/link?url=C9Qh6zg5n1VvJq6uyR9FGiW33lcWOxSo9J9Te8TJPmL7MM4ejX4ebmAZOV_HVS9Guu3NTAy_JcBZS9tuziLkD_");//��ת�ĵ�ַ
    	 newsList.add(news);
    	 News news1=new News();
    	 news1.setTitle("JPA criteria ��ѯ:���Ͱ�ȫ���������");
    	 news1.setDescription("�Թ�������,������ǰ�Ƚ�������hibernate,����һֱʹ��ORM �淶 JPA��.���⼸�칤����Ҫ,�о�����JPA�ı�׼��ѯ,��Ϊ:JPA criteria��ѯ.���JPQL,�����������Ͱ�ȫ,���ӵ��������.ʹ�ñ�׼��ѯ,������Ա���ڱ����ʱ��ͼ���ѯ����ȷ���.����ǰҲֻ����Hibernate����˵�й�.���岻��,û�ù�. ");
    	 news1.setPicUrl(NEWSMESSAGE_URL+"image/jpa.jpg");
    	 news1.setUrl("http://my.oschina.net/zhaoqian/blog/133500?fromerr=sLyuV1Iq");//��ת�ĵ�ַ
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
 	 * ͼƬ��ϢתΪxml
 	 * @param imageMessage
 	 * @return
 	 */
 	public static String imageMessageToXml(ImageMessage imageMessage){
 		XStream xstream = new XStream();
 		xstream.alias("xml", imageMessage.getClass());
 		return xstream.toXML(imageMessage);
 	}
     /**
 	 * ��װͼƬ��Ϣ
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
    	System.out.println("Ʊ��"+token.getAccess_token());//DDc8iL07Yzkks5PvbH1yx5Tv4GHzloCd6d1Jen64c8Xv0t0NYGV8qgqwRgSYHZh9um5RtA5_wAq2KA0Kt-tNXdJTvFzpg8j3pJbb8JwxpbDkLsY3kKaAoMyzxIdhf3GkZIZeAFASBS
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
	 * ������ϢתΪxml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
 	/**
	 * ��װ������Ϣ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName) throws Exception{
		Music music = new Music();
		music.setThumbMediaId("OC5IoCAiHCapPH1XuGYcgcr7LiRGUPS3Hfd2juWkeRdTK2iIFS1om90hfb02DeU3");
		music.setTitle("see you again");
		music.setDescription("��7Ƭβ��");
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
