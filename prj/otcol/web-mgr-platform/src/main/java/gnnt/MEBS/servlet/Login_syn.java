package gnnt.MEBS.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.TraderInfo;

public class Login_syn extends HttpServlet {
	private String MODELID = "";

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException {
		paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
		paramHttpServletResponse.setHeader("Cache-Control", "no-store");
		paramHttpServletResponse.setHeader("Pragma", "no-cache");
		paramHttpServletResponse.setContentType("text/xml");
		paramHttpServletResponse.setCharacterEncoding("GBK");
		BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramHttpServletRequest.getInputStream()));
		String str1 = localBufferedReader.readLine();
		StringBuffer localStringBuffer1 = new StringBuffer();
		while (str1 != null) {
			localStringBuffer1.append(str1);
			str1 = localBufferedReader.readLine();
		}
		PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
		try {
			System.out.println("servlet==============>strXML:" + localStringBuffer1);
			System.out.println("servlet==========>end");
			String str2 = localStringBuffer1.toString();
			if (str2 == null) {
				return;
			}
			String str3 = getXmlType("name", parserStrtoDocument(str2));
			String str5;
			if ("logon".equals(str3)) {
				String str4 = getXmlParament(str2, "TRADER_ID");
				if ("".equals(str4)) {
					str4 = getXmlParament(str2, "USER_ID");
				}
				long l2 = toLong(getXmlParament(str2, "SESSION_ID"));
				str5 = getXmlParament(str2, "MODULE_ID");
				String localObject3 = getXmlParament(str2, "PASSWORD");
				String str7 = getXmlParament(str2, "REGISTER_WORD");
				LogonManager localObject4 = LogonManager.getInstance();
				String str8 = "";
				TraderInfo localTraderInfo = ((LogonManager) localObject4).logon(str4, (String) localObject3, str7,
						paramHttpServletRequest.getRemoteAddr());
				long l6 = localTraderInfo.auSessionId;
				StringBuffer localStringBuffer3 = new StringBuffer();
				if (l6 > 0L) {
					String str9 = localTraderInfo.firmId;
					String str10 = localTraderInfo.traderId;
					paramHttpServletRequest.getSession().setAttribute("FIRMID", str9);
					paramHttpServletRequest.getSession().setAttribute("TRADERID", str10);
					paramHttpServletRequest.getSession().setAttribute("LOGINID", new Long(l6));
					paramHttpServletRequest.getSession().setAttribute("username", str4);
					paramHttpServletRequest.getSession().setMaxInactiveInterval(60000);
					localStringBuffer3.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logon\">\n<RESULT>\n<RETCODE>");
					localStringBuffer3.append(l6 + "</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
					localStringBuffer3.append(this.MODELID);
					localStringBuffer3.append("</MODULE_ID>\n");
					localStringBuffer3.append("<LAST_TIME>" + ((localTraderInfo.lastTime == null) || (localTraderInfo.lastTime.length() < 20) ? ""
							: localTraderInfo.lastTime.substring(0, 19)) + "</LAST_TIME>\n");
					localStringBuffer3.append("<LAST_IP>" + localTraderInfo.lastIP + "</LAST_IP>\n");
					localStringBuffer3.append("<CHG_PWD>" + localTraderInfo.forceChangePwd + "</CHG_PWD>\n");
					localStringBuffer3.append("<NAME>" + localTraderInfo.traderName + "</NAME>\n");
					localStringBuffer3.append("<RANDOM_KEY>" + localTraderInfo.keyCode + "</RANDOM_KEY>\n");
					localStringBuffer3.append("</RESULT>\n</REP>\n</GNNT>\n");
					localPrintWriter.print(localStringBuffer3.toString());
					return;
				}
				if (l6 == -1L) {
					str8 = "交易员代码不存在！";
				} else if (l6 == -2L) {
					str8 = "口令不正确！";
				} else if (l6 == -3L) {
					str8 = "禁止登录！";
				} else if (l6 == -4L) {
					str8 = "Key盘验证错误！";
				} else if (l6 == -5L) {
					str8 = "登录失败！";
				} else {
					str8 = "交易板块被禁止！";
				}
				localStringBuffer3.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logon\">\n<RESULT>\n<RETCODE>");
				localStringBuffer3.append(l6 + "</RETCODE>\n<MESSAGE>");
				localStringBuffer3.append(str8);
				localStringBuffer3.append("</MESSAGE>\n<MODULE_ID>");
				localStringBuffer3.append(this.MODELID);
				localStringBuffer3.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
				localPrintWriter.print(localStringBuffer3.toString());
				return;
			}
			Object localObject2;
			if ("logoff".equals(str3)) {
				long l1 = toLong(getXmlParament(str2, "SESSION_ID"));
				localObject2 = LogonManager.getInstance();
				str5 = "注销成功";
				((LogonManager) localObject2).logoff(l1);
				paramHttpServletRequest.getSession().removeAttribute("LOGINID");
				paramHttpServletRequest.getSession().invalidate();
				StringBuffer localObject3 = new StringBuffer();
				((StringBuffer) localObject3)
						.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logoff\">\n<RESULT>\n<RETCODE>");
				((StringBuffer) localObject3).append("0</RETCODE>\n<MESSAGE>");
				((StringBuffer) localObject3).append(str5);
				((StringBuffer) localObject3).append("</MESSAGE>\n</RESULT>\n</REP>\n</GNNT>\n");
				localPrintWriter.print(((StringBuffer) localObject3).toString());
				return;
			}
			if ("check_user".equals(str3)) {
				StringBuffer localObject1 = new StringBuffer();
				LogonManager localLogonManager = LogonManager.getInstance();
				localObject2 = null;
				try {
					localObject2 = getXmlParament(str2, "TRADER_ID");
				} catch (Exception localException2) {
					localException2.printStackTrace();
				}
				if ("".equals(localObject2)) {
					localObject2 = getXmlParament(str2, "USER_ID");
				}
				long l4 = toLong(getXmlParament(str2, "SESSION_ID"));
				String str7 = getXmlParament(str2, "MODULE_ID");
				TraderInfo localObject4 = localLogonManager.remoteLogon((String) localObject2, str7, l4, paramHttpServletRequest.getRemoteAddr());
				String str8 = "";
				long l5 = ((TraderInfo) localObject4).auSessionId;
				if (l5 > 0L) {
					paramHttpServletRequest.getSession().setAttribute("FIRMID", ((TraderInfo) localObject4).firmId);
					paramHttpServletRequest.getSession().setAttribute("TRADERID", ((TraderInfo) localObject4).traderId);
					paramHttpServletRequest.getSession().setAttribute("LOGINID", new Long(l5));
					paramHttpServletRequest.getSession().setAttribute("username", localObject2);
					paramHttpServletRequest.getSession().setMaxInactiveInterval(60000);
					((StringBuffer) localObject1)
							.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
					((StringBuffer) localObject1).append("0</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
					((StringBuffer) localObject1).append(this.MODELID);
					((StringBuffer) localObject1).append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
					localPrintWriter.print(((StringBuffer) localObject1).toString());
					return;
				}
				if (l5 == -1L) {
					str8 = "交易员代码不存在！";
				} else if (l5 == -2L) {
					str8 = "口令不正确！";
				} else if (l5 == -3L) {
					str8 = "禁止登陆！";
				} else if (l5 == -4L) {
					str8 = "Key盘验证错误！";
				} else if (l5 == -5L) {
					str8 = "交易板块被禁止！";
				} else {
					str8 = "其他异常！";
				}
				((StringBuffer) localObject1)
						.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
				((StringBuffer) localObject1).append(l5 + "</RETCODE>\n<MESSAGE>");
				((StringBuffer) localObject1).append(str8);
				((StringBuffer) localObject1).append("</MESSAGE>\n<MODULE_ID>");
				((StringBuffer) localObject1).append(this.MODELID);
				((StringBuffer) localObject1).append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
				localPrintWriter.print(((StringBuffer) localObject1).toString());
				return;
			}
			if (!"change_password".equals(str3)) {
				return;
			}
			Object localObject1 = getXmlParament(str2, "TRADER_ID");
			if ("".equals(localObject1)) {
				localObject1 = getXmlParament(str2, "USER_ID");
			}
			long l3 = toLong(getXmlParament(str2, "SESSION_ID"));
			String str6 = getXmlParament(str2, "CUSTOMER_ID");
			Object localObject3 = getXmlParament(str2, "OLD_PASSWORD");
			String str7 = getXmlParament(str2, "NEW_PASSWORD");
			Object localObject4 = LogonManager.getInstance();
			String str8 = "";
			int i = ((LogonManager) localObject4).changePassowrd((String) localObject1, (String) localObject3, str7);
			StringBuffer localStringBuffer2 = new StringBuffer();
			localStringBuffer2.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
			localStringBuffer2.append("<GNNT>\n<REP name=\"change_password\">\n");
			localStringBuffer2.append("<RESULT>\n");
			localStringBuffer2.append("<RETCODE>" + i + "</RETCODE>\n");
			if (i == 0) {
				str8 = "修改密码成功";
			} else if (i == -1) {
				str8 = "原口令不正确";
			} else if (i == -1) {
				str8 = "操作异常";
			} else {
				str8 = "其他异常";
			}
			localStringBuffer2.append("<MESSAGE>" + str8 + "</MESSAGE>\n");
			localStringBuffer2.append("</RESULT>\n</REP>\n</GNNT>\n");
			localPrintWriter.print(localStringBuffer2.toString());
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doGet(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void init(ServletConfig paramServletConfig) throws ServletException {
		this.MODELID = paramServletConfig.getInitParameter("MODELID");
	}

	private Document parserStrtoDocument(String paramString) throws DocumentException {
		Document localDocument = null;
		try {
			localDocument = DocumentHelper.parseText(paramString);
		} catch (Exception localException) {
		}
		return localDocument;
	}

	private String getXmlType(String paramString, Document paramDocument) {
		String str = null;
		try {
			Element localElement1 = paramDocument.getRootElement();
			Element localElement2 = localElement1.element("REQ");
			str = localElement2.attributeValue(paramString);
		} catch (Exception localException) {
		}
		return str;
	}

	private String getXmlParament(String paramString1, String paramString2) throws Exception {
		paramString1.replaceAll("\n", "");
		if (paramString1 == null) {
			return "";
		}
		String str1 = "<" + paramString2 + ">(.*)" + "</" + paramString2 + ">";
		Pattern localPattern = Pattern.compile(str1);
		Matcher localMatcher = localPattern.matcher(paramString1);
		boolean bool = localMatcher.find();
		String str2 = "";
		if (bool) {
			str2 = localMatcher.group();
			str2 = str2.replaceAll("<" + paramString2 + ">", "");
			str2 = str2.replaceAll("</" + paramString2 + ">", "");
			System.out.println(localMatcher.group());
		}
		return str2;
	}

	private long toLong(String paramString) {
		long l = -1L;
		try {
			l = Long.parseLong(paramString);
		} catch (Exception localException) {
		}
		return l;
	}
}
