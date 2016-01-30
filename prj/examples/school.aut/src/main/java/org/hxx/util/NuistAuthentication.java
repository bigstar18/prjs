package org.hxx.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class NuistAuthentication implements IAuthentication {

	@Override
	public boolean authenticate(String usercode, String username,
			String password) throws Exception {
		// 让HttpClient实现自动重定向
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRedirectStrategy(redirectStrategy).build();
		// 禁止get请求重定向
		RequestConfig config = RequestConfig.custom()
				.setRedirectsEnabled(false).setSocketTimeout(3000)
				.setConnectTimeout(2000).build();// 设置传输和请求超时时间

		// 南信工实名认证地址
		// 第一次get请求，服务器会返回一个新的重定向地址
		String schoolurl = "http://wlkt.nuist.edu.cn";
		HttpGet httpGet = new HttpGet(schoolurl);
		// 增加user-agent
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		httpGet.setConfig(config);
		HttpPost httpPost = null;
		boolean checkResult = false;

		CloseableHttpResponse response = null;
		try {
			// 第一次请求的response,充这次的response中获取重定向的地址
			response = httpClient.execute(httpGet);
			// 第二次请求地址
			schoolurl = schoolurl
					+ response.getLastHeader("Location").getValue();
			httpGet = new HttpGet(schoolurl);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(2000).build();// 设置传输和请求超时时间
			httpGet.setConfig(requestConfig);
			// 增加user-agent
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
			// 从第二次请求的返回结果中取得__VIEWSTATE的值
			response = httpClient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity());
			String viewState = HttpUtil.fetchNecessaryParam(content,
					"__VIEWSTATE");

			// 填充需要提交的表单
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("__VIEWSTATE", viewState));
			nvps.add(new BasicNameValuePair("TextBox1", usercode));
			nvps.add(new BasicNameValuePair("TextBox2", password));
			nvps.add(new BasicNameValuePair("js", "RadioButton3"));
			nvps.add(new BasicNameValuePair("Button1", "登陆"));

			httpPost = new HttpPost(schoolurl);
			// 设置字符
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			// 增加user-agent
			httpPost.setHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
			httpPost.setConfig(requestConfig);
			// 获得第三次post请求的返回值
			response = httpClient.execute(httpPost);
			Pattern pattern = Pattern.compile(username);
			content = EntityUtils.toString(response.getEntity());
			Matcher matcher = pattern.matcher(content);
			if (matcher.find()) {
				checkResult = true;
			}
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return checkResult;
	}

}
