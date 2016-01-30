package org.hxx.event.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.hxx.app.dao.IAppDao;
import org.hxx.app.dao.impl.AppDao;

public class ProxyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static Logger LOG = LoggerFactory.getLogger(ProxyServlet.class);

	private String attention = "http://uyoungweb.chinacloudapp.cn:8080/APPWebService.asmx/AttentionUser";

	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	private IAppDao appDao = new AppDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String attentionUserUID = (String) req.getParameter("AttentionUserUID");
		String signId = (String) req.getParameter("signId");
		String userName = (String) req.getParameter("phone");
		String eventId = (String) req.getParameter("eventId");
		LOG.info(eventId + "," + signId + "," + userName + "," + attentionUserUID);
		if (StringUtils.isEmpty(attentionUserUID) || StringUtils.isEmpty(signId) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(eventId)) {
			resp.getWriter().write("输入数据错误");
			return;
		}

		// 让HttpClient实现自动重定向
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpClient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(attention);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(2000).build();// 设置传输和请求超时时间
			httpPost.setConfig(requestConfig);

			String uid = appDao.getUIDByUserAccount2(userName);
			// 填充需要提交的表单
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("UserUID", uid));
			nvps.add(new BasicNameValuePair("AttentionUserUID", attentionUserUID));
			nvps.add(new BasicNameValuePair("SignId", signId));
			// 设置字符
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			// 获取返回的页面内容
			response = httpClient.execute(httpPost);
			String content = EntityUtils.toString(response.getEntity());
			LOG.info(content);

			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(content.getBytes()));
			Element root = doc.getDocumentElement();
			String jsonStr = root.getTextContent();
			Map map = (Map) JSON.parse(jsonStr);
			if (map.containsKey("Msg"))
				resp.getWriter().write((String) map.get("Msg"));
			else
				resp.getWriter().write("关注失败");
		} catch (Exception e) {
			LOG.error("attention", e);
			resp.getWriter().write("发生错误");
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String content = "<string xmlns=\"http://tempuri.org/\">{\"Code\":\"400\",\"Msg\":\"用户不存在\",\"Total\":1,\"PIndex\":1,\"RecordCount\":1,\"Datalist\":[]}</string>";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(content.getBytes()));
		Element root = doc.getDocumentElement();
		String jsonStr = root.getTextContent();
		Map map = (Map) JSON.parse(jsonStr);

		System.out.println(map);
	}
}
