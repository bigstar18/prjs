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

public class NuaaAuthentication implements IAuthentication {

	@Override
	public boolean authenticate(String usercode, String username,
			String password) throws Exception {
		// 让HttpClient实现自动重定向
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRedirectStrategy(redirectStrategy).build();

		// 南航实名认证地址
		String schoolurl = "http://ded.nuaa.edu.cn/NetEa/Login.aspx";

		HttpGet httpGet = new HttpGet(schoolurl);
		HttpPost httpPost = new HttpPost(schoolurl);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(3000).setConnectTimeout(2000).build();// 设置传输和请求超时时间
		httpGet.setConfig(requestConfig);
		httpPost.setConfig(requestConfig);
		// 认证结果
		boolean checkResult = false;
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			// 填充需要提交的表单
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("__LASTFOCUS", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"wEPDwUKMTE5NDAwMTAyNw9kFgJmD2QWAgIDD2QWAgIDDw8WAh4EVGV4dAUJ6LCi5paw5a6HZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFIWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ2tiQXV0b6/oUpP2WKqeoQIH3YhnmCQswmQb"));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair("__EVENTVALIDATION",
					"/wEWBQKtnNHnBgL7ssu/DwL585DUBwLIoPPsBALIuMLBDzO4vsZoqwzneCTe/s7NmQgosPWW"));
			nvps.add(new BasicNameValuePair(
					"ctl00$ContentPlaceHolder1$user_id", usercode));
			nvps.add(new BasicNameValuePair(
					"ctl00$ContentPlaceHolder1$password", password));
			nvps.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$BtnOk",
					"登 录"));
			// 设置字符
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			// 获取返回的页面内容
			response = httpClient.execute(httpPost);
			String content = EntityUtils.toString(response.getEntity());

			// 匹配姓名
			Pattern pattern = Pattern.compile(username);
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
