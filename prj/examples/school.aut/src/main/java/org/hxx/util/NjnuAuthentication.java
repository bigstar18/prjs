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

public class NjnuAuthentication implements IAuthentication {

	@Override
	public boolean authenticate(String usercode, String username,
			String password) throws Exception {
		// 让HttpClient实现自动重定向
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRedirectStrategy(redirectStrategy).build();

		// 南师大实名认证地址
		String schoolurl = "https://cer.njnu.edu.cn/login?service=http%3A%2F%2Furp.njnu.edu.cn%2Findex.portal";

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

			// 获取lt的值
			String LT = HttpUtil.fetchNecessaryParam(
					EntityUtils.toString(response.getEntity()), "lt");
			// 填充需要提交的表单
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", usercode));
			nvps.add(new BasicNameValuePair("password", password));
			nvps.add(new BasicNameValuePair("lt", LT));
			nvps.add(new BasicNameValuePair("execution", "e1s1"));
			nvps.add(new BasicNameValuePair("_eventId", "submit"));
			nvps.add(new BasicNameValuePair("rmShown", "1"));
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
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return checkResult;
	}

}
