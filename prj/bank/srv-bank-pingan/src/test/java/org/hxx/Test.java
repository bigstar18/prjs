package org.hxx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test {
	HttpClient httpclient = new DefaultHttpClient();

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public String post(String url, String respEncoding) {
		return post(url, "UTF-8", respEncoding, new ArrayList<NameValuePair>());
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public String post(String url, String reqEncoding, String respEncoding, List<NameValuePair> param) {
		String resStr = "";
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = param;
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, reqEncoding);
			httppost.setEntity(uefEntity);
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resStr = EntityUtils.toString(entity, respEncoding);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return resStr;
	}

	/**
	 * 发送 get请求
	 */
	public String get(String url) {
		// httpclient = new DefaultHttpClient();
		String resStr = "";
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
				// System.out.println("Response content length: "
				// + entity.getContentLength());
				// 打印响应内容
				// System.out.println("Response content: "
				// + EntityUtils.toString(entity));
				resStr = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// httpclient.getConnectionManager().shutdown();
		}
		return resStr;
	}

	public static boolean sendXML(String path, String xml) throws Exception {
		byte[] data = xml.getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml; charset=GBK");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("GBK")));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			// end 读取整个页面内容
			System.out.println(buffer.toString());
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		Test ht = new Test();// 构造参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		// list.add(new BasicNameValuePair("li", "house"));// 登录参数

		// System.out.println(ht.post("http://127.0.0.1:7072", "GBK", "GBK", list));// 先post登录也卖弄
		// System.out.println(ht.get("http://localhost:2375/WebForm1.aspx"));// 此页面是需要登录才能访问的页面
		Test.sendXML("http://127.0.0.1:7072",
				"A001010101002010805000080150000000000053S001       0120100809171028    2010080981026055999999000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000<?xml version=\"1.0\" encoding=\"GBK\"?><Result></Result>");
	}
}
