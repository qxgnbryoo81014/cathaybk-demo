package com.example.demo.utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiCommonUtil {
	public static final Integer CONNECT_TIMEOUT = 10000;
	public static final Integer READ_TIMEOUT = 20000;
	
	public <T> T postServiceHttps(String url, String requestJSONBoby, HttpHeaders headers, Class<T> responseType) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		// 預設 MediaType: application/json
		if(headers == null) {
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		HttpEntity<String> request = new HttpEntity<String>(requestJSONBoby, headers);
        RestTemplate restTemplate = buildRestTemplate();
        return restTemplate.postForObject(url, request, responseType);
    }
	
	public <T> T postServiceHttps(String url, HttpEntity<?> request, Class<T> responseType) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        RestTemplate restTemplate = buildRestTemplate();
        return restTemplate.postForObject(url, request, responseType);
    }
	
	public RestTemplate defaulRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
	
	/** 取得禁用SSL驗證的HTTPS模板
	 */
	public RestTemplate buildRestTemplate() {
		SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(READ_TIMEOUT);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
	}
}
