<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.member.ActiveUser.*"/>
<%@ include file="login_fun.jsp"%>
<%
	java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
	String re = br.readLine();
	StringBuffer strXML = new StringBuffer();
	while (re != null) {
		strXML.append(re);
		re = br.readLine();
	}

	String loginxml = strXML.toString();
	if (loginxml != null) {
		String username = getXmlParament(loginxml, "TRADER_ID");
		long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
		String moduleId = getXmlParament(loginxml, "MODULE_ID");
		String password = getXmlParament(loginxml, "PASSWORD");
		String key = getXmlParament(loginxml, "REGISTER_WORD");
		gnnt.MEBS.member.ActiveUser.LogonManager manager = gnnt.MEBS.member.ActiveUser.LogonManager.getInstance();
		String message = "";
		TraderInfo trader = manager.logon(username, password, key, request.getRemoteAddr());
		long sessionID = trader.auSessionId;
		StringBuffer ret = new StringBuffer();
		if (sessionID == 0) {
			session.setAttribute("FIRMID", trader.firmId);
			session.setAttribute("LOGINID", new Long(sessionID));
			session.setAttribute("username", username);
			session.setMaxInactiveInterval(SESSIONINTERVAL);
			ret
			.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
			ret
			.append(""
			+ 0
			+ "</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
			ret.append(MODELID);
			ret.append("</MODULE_ID>\n<NAME>");
			ret.append(trader.traderName + "</NAME>\n");
			ret.append("</RESULT>\n</REP>\n</GNNT>\n");
			out.print(ret.toString());
			return;
		} else if (sessionID == -1) {
			message = "����Ա���벻���ڣ�";
		} else if (sessionID == -2) {
			message = "�����ȷ��";
		} else if (sessionID == -3) {
			message = "��ֹ��¼��";
		} else if (sessionID == -4) {
			message = "Key����֤����";
		} else if (sessionID == -5) {
			message = "��¼ʧ�ܣ�";
		} else {
			message = "���װ�鱻��ֹ��";
		}
		ret
		.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
		ret.append("" + sessionID + "</RETCODE>\n<MESSAGE>");
		ret.append(message);
		ret.append("</MESSAGE>\n<MODULE_ID>5</MODULE_ID>\n<NAME>");
		ret.append(trader.traderName + "</NAME>\n");
		ret.append("</RESULT>\n</REP>\n</GNNT>\n");
		out.print(ret.toString());

	}
%>
