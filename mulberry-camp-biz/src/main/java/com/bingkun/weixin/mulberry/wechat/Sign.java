package com.bingkun.weixin.mulberry.wechat;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Sign {
    public static void main(String[] args) {
        String jsapi_ticket = "jsapi_ticket";

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("签名失败", e);
        }
        catch (UnsupportedEncodingException e)
        {
        	throw new RuntimeException("签名失败", e);
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    /**
     * 获取微信卡券签名
     * @param api_ticket
     * @param card_id 卡券id
     * @return
     */
    public  static  Map<String, String> cardSign(String api_ticket,String card_id){
        Map<String, String> cardSign = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String origin = "";
        String signature="";
        String[] source={api_ticket,timestamp,card_id,nonce_str};
        Arrays.sort(source);
        List<String> strings = Arrays.asList(source);
        for (String s:strings){
            origin=origin+s;
        }
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(origin.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("卡券签名失败", e);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("卡券签名失败", e);
        }
//        cardSign.put("api_ticket",api_ticket);
//        cardSign.put("card_id",card_id);
        cardSign.put("nonce_str",nonce_str);
        cardSign.put("timestamp",timestamp);
        cardSign.put("signature",signature);
        return cardSign;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
