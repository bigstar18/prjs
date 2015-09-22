package org.hxx.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestAuthentication {

	@Test
	public void testAuthenticationServlet() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String schoolcode = "10319";
		// String usercode = "02130124";
		String usercode = "02130123";
		String password = "196712";
		String username = "钱嘉晖";
		HttpPost httpPost = new HttpPost(
				"http://uyoungweb.chinacloudapp.cn:8888/aut/");
		// HttpPost httpPost = new HttpPost("http://localhost:8080/aut");
		// 认证结果
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("schoolCode", schoolcode));
			nvps.add(new BasicNameValuePair("userCode", usercode));
			nvps.add(new BasicNameValuePair("password", password));
			nvps.add(new BasicNameValuePair("userName", username));
			// 设置字符
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			// 获取返回的页面内容
			response = httpClient.execute(httpPost);
			String content = EntityUtils.toString(response.getEntity());
			System.out.println(content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
