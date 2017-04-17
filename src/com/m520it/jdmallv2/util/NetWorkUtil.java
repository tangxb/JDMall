package com.m520it.jdmallv2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetWorkUtil {

	public static String doGet(String urlStr) {
		BufferedReader reader=null;
		try {
			URL url=new URL(urlStr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode()==200) {
				InputStream is = conn.getInputStream();
				InputStreamReader in = new InputStreamReader(is);
				reader=new BufferedReader(in);
				return reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public static String doPost(String urlStr,Map<String, String> params){
		BufferedReader reader=null;
		try {
			URL url=new URL(urlStr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			String paramStr=parseMap2Str(params);
			conn.getOutputStream().write(paramStr.getBytes());
			if (conn.getResponseCode()==200) {
				InputStream is = conn.getInputStream();
				InputStreamReader in = new InputStreamReader(is);
				reader=new BufferedReader(in);
				return reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * 	将Map格式的参数转换成String
	 */
	private static String parseMap2Str(Map<String, String> params) {
		StringBuilder builder=new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.append("&"+entry.getKey()+"="+entry.getValue());
		}
		return builder.toString().substring(1);
	}

	public static String doPost(String url, List<NameValuePair> params) {
		try {
			// 创建一个浏览器
			HttpClient client = new DefaultHttpClient();
			// 创建一个Post请求
			HttpPost post = new HttpPost(url);
			// 将参数设置到Post请求里面
			HttpEntity entity = new UrlEncodedFormEntity(params);
			post.setEntity(entity);
//			执行一个Post请求
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode()==200) {
				InputStream is = response.getEntity().getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				return reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
