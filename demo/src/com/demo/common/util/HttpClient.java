package com.demo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClient {
	private static final Logger LOGGER = Logger.getLogger(HttpClient.class);

	/**
	 * HttpClient连接SSL
	 */
	public void ssl(String url) {
		CloseableHttpClient httpclient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
			try {
				// 加载keyStore d:\\tomcat.keystore  
				trustStore.load(instream, "123456".toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			// 只允许使用TLSv1协议
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			// 创建http请求(get方式)
			HttpGet httpget = new HttpGet(url);
			LOGGER.info("executing request" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				LOGGER.info("----------------------------------------");
				LOGGER.info(response.getStatusLine());
				if (entity != null) {
					LOGGER.info("Response content length: " + entity.getContentLength());
					LOGGER.info(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static List<NameValuePair> params(Map<String, String> params){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = params.get(key);
			formparams.add(new BasicNameValuePair(key, value));
		}
		
		return formparams;
	}

	public static String post(String url,Map<String, String> params) {
		return post(url, params, null);
	}
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url,Map<String, String> params,Map<String, String> header) {
		String responseText = "";
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost  
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列  
		List<NameValuePair> formparams = params(params);
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			Iterator<String> it = header.keySet().iterator();
			while(it.hasNext()){
				String name = it.next();
				String value = header.get(name);
				httppost.addHeader(name,value);
			}
			
			LOGGER.info("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				Header[] headers = response.getHeaders("location");
				for (Header resheader : headers) {
					String location = resheader.getValue();
					LOGGER.info("备用跳转地址："+location);
				}
				if (entity != null) {
					LOGGER.info("--------------------------------------");
					responseText = EntityUtils.toString(entity, "UTF-8");
					LOGGER.info("Response content: " + responseText);
					LOGGER.info("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

	/**
	 * 发送 get请求
	 */
	public static String get(String url) {
		String responseText = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.  
			HttpGet httpget = new HttpGet(url);
			LOGGER.info("executing request " + httpget.getURI());
			// 执行get请求.  
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体  
				HttpEntity entity = response.getEntity();
				LOGGER.info("--------------------------------------");
				// 打印响应状态  
				LOGGER.info(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度  
					LOGGER.info("Response content length: " + entity.getContentLength());
					// 打印响应内容  
					responseText = EntityUtils.toString(entity);
					LOGGER.info("Response content: " + responseText);
				}
				LOGGER.info("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

	/**
	 * 上传文件
	 */
	public static void upload(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));
			StringBody comment = new StringBody("A binary file of some kind");

			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();

			httppost.setEntity(reqEntity);

			LOGGER.info("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				LOGGER.info("----------------------------------------");
				LOGGER.info(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					LOGGER.info("Response content length: " + resEntity.getContentLength());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<>();
		params.put("trustType", "0");
		
		get("http://www.cjol.com/jobs/job-7222591#source=101");
	}
}