package com.bingkun.weixin.qyh.network.httpRequest;

import com.bingkun.weixin.qyh.util.CommonUtil;
import lombok.extern.log4j.Log4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HTTP请求共通
 */
@Log4j
public class HttpRequest {

	/**
	 * HTTP请求处理
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param data          提交的数据
	 * @return 请求结果
	 */
	public static Map<String, Object> sendRequest(String requestUrl, String requestMethod, String data) {
		try {
			String result = null;
			if (requestUrl.startsWith("http://")) {
				result = httpRequest(requestUrl, requestMethod, data);
			} else if (requestUrl.startsWith("https://")) {
				result = httpsRequest(requestUrl, requestMethod, data);
			}
			if (result != null && !"".equals(result)) {
				return CommonUtil.json2Map(result);
			}
		} catch (Exception e) {
			log.error(">>>requestWechatAPI error", e);
		}
		return null;
	}

	/**
	 * 发起HTTP请求
	 *
	 * @param requestUrl 请求的地址
	 * @param requestMethod 方法 GET、POST
	 * @param submitData 提交的数据
	 * @return 请求结果
	 */
	public static String httpRequest(String requestUrl,String requestMethod, String submitData) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn =null;
			httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(10000);
			httpUrlConn.setReadTimeout(10000);
			//httpUrlConn.setRequestProperty("content-type", "application/x-java-serialized-object");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != submitData) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(submitData.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			log.debug(">>> buffer: "+buffer.toString());
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			log.debug(">>>http request url:" + requestUrl);
		} catch (Exception e) {
			log.error("http request error:" + requestUrl, e);
		}
		return buffer.toString();
	}

	/**
	 * 发起HTTPS请求
	 *
	 * @param requestUrl 请求的地址
	 * @param requestMethod 方法 GET、POST
	 * @param submitData 提交的数据
	 * @return 请求结果
	 */
	public static String httpsRequest(String requestUrl,String requestMethod, String submitData) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new WechatX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn =null;
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(10000);
			httpUrlConn.setReadTimeout(10000);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != submitData) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(submitData.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			log.debug(">>> buffer: "+buffer.toString());
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			log.debug(">>>https request url:" + requestUrl);
		} catch (Exception e) {
			log.error("https request error:" + requestUrl, e);
		}
		return buffer.toString();
	}
}
