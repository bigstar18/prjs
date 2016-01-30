package org.hxx.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

public class SeuAuthentication implements IAuthentication {

	@Override
	public boolean authenticate(String usercode,String username, String password) {
		//让HttpClient实现自动重定向
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpClient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();
		//东南大学实名认证地址
		String schoolurl = "http://my.seu.edu.cn/userPasswordValidate.portal";
		String indexurl = "http://my.seu.edu.cn/index.portal";
		HttpGet httpGet = new HttpGet(indexurl);
		HttpPost httpPost = new HttpPost(schoolurl);
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setSocketTimeout(3000).setConnectTimeout(2000).build();// 设置传输和请求超时时间
//		httpGet.setConfig(requestConfig);
//		httpPost.setConfig(requestConfig);
        //认证结果
        boolean checkResult = false;
        CloseableHttpResponse response = null;
		try {
			//填充需要提交的表单
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("Login.Token2", "170712"));
	        nvps.add(new BasicNameValuePair("Login.Token1", "213111282"));
	        nvps.add(new BasicNameValuePair("gotoOnFail","http://my.seu.edu.cn/loginFailure.portal"));
	        nvps.add(new BasicNameValuePair("goto", "http://my.seu.edu.cn/loginSuccess.portal"));
	        //设置字符
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
	        //获取返回的页面内容
			response = httpClient.execute(httpPost);
			response = httpClient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity());
			//匹配姓名
			Pattern pattern = Pattern.compile(username);
			Matcher matcher = pattern.matcher(content);
			if(matcher.find())
			{
				checkResult = true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return checkResult;
	}

	

}
