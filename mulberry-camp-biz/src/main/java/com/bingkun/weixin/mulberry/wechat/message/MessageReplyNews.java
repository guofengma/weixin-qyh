package com.bingkun.weixin.mulberry.wechat.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bingkun.weixin.mulberry.util.CommonUtil;

/**
 * 图文类型消息体
 * @author dingyongchang
 *
 */
public class MessageReplyNews extends MessageReply{

	//图文消息个数，限制为10条以内
	private int articleCount;
	//多条图文消息信息，默认第一个item为大图
	//private String articles;
	//图文消息标题   数组
	private String[] titleArr;
	//图文消息描述   数组
	private String[] descriptionArr;
	//图片链接   数组，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。
	private String[] picUrlArr;
	//点击图文消息跳转链接   数组
	private String[] urlArr; 
	private String funcFlag;
	


	public MessageReplyNews(){
		super("news");
	}
	
	@Override
	public String getReplyXml() {
//		<xml>
//		 <ToUserName><![CDATA[toUser]]></ToUserName>
//		 <FromUserName><![CDATA[fromUser]]></FromUserName>
//		 <CreateTime>12345678</CreateTime>
//		 <MsgType><![CDATA[news]]></MsgType>
//		 <ArticleCount>2</ArticleCount>
//		 <Articles>
//		 <item>
//		 <Title><![CDATA[title1]]></Title> 
//		 <Description><![CDATA[description1]]></Description>
//		 <PicUrl><![CDATA[picurl]]></PicUrl>
//		 <Url><![CDATA[url]]></Url>
//		 </item>
//		 <item>
//		 <Title><![CDATA[title]]></Title>
//		 <Description><![CDATA[description]]></Description>
//		 <PicUrl><![CDATA[picurl]]></PicUrl>
//		 <Url><![CDATA[url]]></Url>
//		 </item>
//		 </Articles>
//		 <FuncFlag>1</FuncFlag>
//		 </xml> 

		StringBuffer buff = new StringBuffer();
		buff.append("<xml>");
		
		buff.append("<ToUserName><![CDATA[");
		buff.append(this.getToUserName());
		buff.append("]]></ToUserName>");
		
		buff.append("<FromUserName><![CDATA[");
		buff.append(this.getFromUserName());
		buff.append("]]></FromUserName>");
		
		buff.append("<CreateTime>");
		buff.append(new Long(new Date().getTime()).toString());
		buff.append("</CreateTime>");
		
		buff.append("<MsgType><![CDATA[news]]></MsgType>");
		
		buff.append("<ArticleCount>");
		buff.append(articleCount);
		buff.append("</ArticleCount>");		
		buff.append("<Articles>");
		for(int i=0;i<articleCount;i++){
			buff.append("<item>");
			buff.append("<Title><![CDATA[");
			buff.append(titleArr[i]);
			buff.append("]]></Title>");
			
			buff.append("<Description><![CDATA[");
			buff.append(descriptionArr[i]);
			buff.append("]]></Description>");
			
			buff.append("<PicUrl><![CDATA[");
			buff.append(picUrlArr[i]);
			buff.append("]]></PicUrl>");
			
			buff.append("<Url><![CDATA[");
			buff.append(urlArr[i]);
			buff.append("]]></Url>");
			buff.append("</item>");
		}
		buff.append("</Articles>");
		buff.append("<FuncFlag>");
		buff.append(funcFlag==null||"".equals(funcFlag)?"0":funcFlag);
		buff.append("</FuncFlag>");
		buff.append("</xml>");
		//buff.append("http://www.witpos.com/witpos2010/index.asp");
		return buff.toString();
	}
	


	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount>10?10:articleCount;
	}

	public String[] getTitleArr() {
		return titleArr;
	}

	public void setTitleArr(String[] titleArr) {
		this.titleArr = titleArr;
	}

	public String[] getDescriptionArr() {
		return descriptionArr;
	}

	public void setDescriptionArr(String[] descriptionArr) {
		this.descriptionArr = descriptionArr;
	}

	public String[] getPicUrlArr() {
		return picUrlArr;
	}

	public void setPicUrlArr(String[] picUrlArr) {
		this.picUrlArr = picUrlArr;
	}

	public String[] getUrlArr() {
		return urlArr;
	}

	public void setUrlArr(String[] urlArr) {
		this.urlArr = urlArr;
	}
	public String getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	@Override
	public String getReplyJson() throws Exception {
//		{
//		    "touser":"OPENID",
//		    "msgtype":"news",
//		    "news":{
//		        "articles": [
//		         {
//		             "title":"Happy Day",
//		             "description":"Is Really A Happy Day",
//		             "url":"URL",
//		             "picurl":"PIC_URL"
//		         },
//		         {
//		             "title":"Happy Day",
//		             "description":"Is Really A Happy Day",
//		             "url":"URL",
//		             "picurl":"PIC_URL"
//		         }
//		         ]
//		    }
//		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", this.getToUserName());
		map.put("msgtype", "news");
		ArrayList<HashMap<String,String>> arrList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> detailMap;
		for(int i=0;i<urlArr.length;i++){
			detailMap = new HashMap<String,String>();
			detailMap.put("title", titleArr[i]);
			detailMap.put("description", descriptionArr[i]);
			detailMap.put("url", urlArr[i]);
			detailMap.put("picurl", picUrlArr[i]);
			arrList.add(i, detailMap);
		}
		Map<String, Object> articles = new HashMap<String, Object>();
		articles.put("articles", arrList);
		map.put("news",articles);
		return CommonUtil.map2Json(map);
	}
}
