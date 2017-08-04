package com.bingkun.weixin.qyh.network.httpRequest;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class WechatX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		//return new X509Certificate[0];
		return null;
	}

	public boolean isClientTrusted(X509Certificate[] arg0) {
		return true;
	}

	public boolean isServerTrusted(X509Certificate[] arg0) {
		return true;
	}

}
