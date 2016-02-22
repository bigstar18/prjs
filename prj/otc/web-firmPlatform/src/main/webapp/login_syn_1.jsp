<%@ page contentType="text/html;charset=GBK" %><%@ include file="login_fun.jsp"%><%
BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
String re=br.readLine();
StringBuffer strXML=new StringBuffer();
while(re!=null ){
	strXML.append(re);
	re=br.readLine();
}
System.out.println("==============>strXML:"+strXML);
System.out.println("==========>end");
String loginxml = strXML.toString();
	if(loginxml!=null){
		//ȡ��xml����
		String type = getXmlType("name",parserStrtoDocument(loginxml));
		if("logon".equals(type))
		{
			String username = getXmlParament(loginxml, "TRADER_ID");
			if("".equals(username))
                   	{
                    	     username =  getXmlParament(loginxml,"USER_ID");
                  	}

			long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
			String moduleId = getXmlParament(loginxml, "MODULE_ID");
			String password = getXmlParament(loginxml, "PASSWORD");
			String key = getXmlParament(loginxml, "REGISTER_WORD");
			LogonManager manager = LogonManager.getInstance();
			String message="";
			TraderInfo trader = manager.logon(username, password, key, request.getRemoteAddr());		
			long sessionID=trader.auSessionId;
			StringBuffer ret=new StringBuffer();
			if(sessionID>0)
			{
				//String FIRMID=gnnt.MEBS.zcjs.xhzc.trade.DAO.TradeDAOFactory.getDAO().getfirmId(username);	
				session.setAttribute("FIRMID",trader.firmId);
				session.setAttribute("LOGINID",new Long(sessionID));
				session.setAttribute("username",username);
				session.setMaxInactiveInterval(SESSIONINTERVAL);
				ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logon\">\n<RESULT>\n<RETCODE>");
				ret.append(""+sessionID+"</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
				ret.append(MODELID);
				ret.append("</MODULE_ID>\n");
				ret.append("<LAST_TIME>"+trader.lastTime.substring(0,19)+"</LAST_TIME>\n");
				ret.append("<LAST_IP>"+trader.lastIP==null?"":trader.lastIP+"</LAST_IP>\n");
				ret.append("<CHG_PWD>"+trader.forceChangePwd+"</CHG_PWD>\n");
				ret.append("<NAME>"+trader.traderName+"</NAME>\n");
				
				ret.append("</RESULT>\n</REP>\n</GNNT>\n");
				out.print(ret.toString());
				


				//System.out.println("=============>ret:"+ret);
				//System.out.println("==========>end");
				return;
			}else if(sessionID == -1)
			{
				message="����Ա���벻���ڣ�";	
			}
			else if(sessionID == -2)
			{
				message="�����ȷ��";	
			}	
			else if(sessionID == -3)
			{
				message="��ֹ��¼��";	
			}	
			else if(sessionID == -4)
			{
				message="Key����֤����";	
			}
			else if(sessionID == -5)
			{
				message="��¼ʧ�ܣ�";	
			}
			else
			{
				message="���װ�鱻��ֹ��";
			}
			ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logon\">\n<RESULT>\n<RETCODE>");
			ret.append(""+sessionID+"</RETCODE>\n<MESSAGE>");
			ret.append(message);
			ret.append("</MESSAGE>\n<MODULE_ID>");
			ret.append(MODELID);
			ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
			out.print(ret.toString());
			//System.out.println("--------------->" + ret.toString());
			//System.out.println("----------->end");
		}
		else if("logoff".equals(type))
		{
			long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
			LogonManager manager = LogonManager.getInstance();
			String message="ע���ɹ�";
			manager.logoff(auSessionId);		
			session.removeAttribute("LOGINID");
			session.invalidate();
			StringBuffer ret=new StringBuffer();		
			ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logoff\">\n<RESULT>\n<RETCODE>");
			ret.append("0</RETCODE>\n<MESSAGE>");
			ret.append(message);
			ret.append("</MESSAGE>\n</RESULT>\n</REP>\n</GNNT>\n");
			out.print(ret.toString());
		}
		else if("check_user".equals(type))
		{
			StringBuffer ret=new StringBuffer();
			if(session.getAttribute("LOGINID")!=null&&((Long)session.getAttribute("LOGINID")).longValue()!=0)
			{//�Ѿ��ڱ�ϵͳ��¼��
				ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
				ret.append(""+0+"</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
				ret.append(MODELID); 
				ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
				out.print(ret.toString());
			}else{//�Ƿ��ڱ��ϵͳ��¼�� 				
				LogonManager manager = LogonManager.getInstance();
				String username = getXmlParament(loginxml, "TRADER_ID");
	if("".equals(username))
                   	{
                    	     username =  getXmlParament(loginxml,"USER_ID");
                  	}

				long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID")); 
				String moduleId = getXmlParament(loginxml, "MODULE_ID");
				TraderInfo traderInfo = manager.remoteLogon(username, moduleId,
				auSessionId, request.getRemoteAddr());
				String message="";
				long sessionID=traderInfo.auSessionId;
				if(sessionID>0)
				{
					session.setAttribute("FIRMID", traderInfo.firmId); 
					session.setAttribute("LOGINID",new Long(sessionID));
					session.setAttribute("username",username);
					session.setMaxInactiveInterval(SESSIONINTERVAL); 
					ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
					ret.append(""+0+"</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
					ret.append(MODELID);
					ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
					out.print(ret.toString());
					//System.out.println("ret:"+ret);
				  return;
				}else if (sessionID == -1) {
					message = "����Ա���벻���ڣ�";
				} else if (sessionID == -2) {
					message = "�����ȷ��";
				} else if (sessionID == -3) {
					message = "��ֹ��¼��";
				} else if (sessionID == -4) {
					message = "Key����֤����";
				} else if (sessionID== -5) {
					message = "���װ�鱻��ֹ��";
				} else {
					message = "�����쳣��";
				}
				ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
				ret.append(""+sessionID+"</RETCODE>\n<MESSAGE>");
				ret.append(message);
				ret.append("</MESSAGE>\n<MODULE_ID>");
				ret.append(MODELID);
				ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
				out.print(ret.toString());
				//System.out.print(ret.toString());
			}
		}
else if("change_password".equals(type))
		{
			String username = getXmlParament(loginxml, "TRADER_ID");
			if("".equals(username))
                   	{
                    	     username =  getXmlParament(loginxml,"USER_ID");
                  	}

			long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
			String CUSTOMER_ID = getXmlParament(loginxml, "CUSTOMER_ID");
			String OLD_PASSWORD = getXmlParament(loginxml, "OLD_PASSWORD");
			String NEW_PASSWORD = getXmlParament(loginxml, "NEW_PASSWORD");
			LogonManager manager = LogonManager.getInstance();
			String message="";
			int result = manager.changePassowrd(username, OLD_PASSWORD, NEW_PASSWORD);		
		
			StringBuffer ret=new StringBuffer();
			ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
			ret.append("<GNNT>\n<REP name=\"change_password\">\n");
			ret.append("<RESULT>\n");
			ret.append("<RETCODE>"+result+"</RETCODE>\n");
			
			if(result==0){
				message = "�޸�����ɹ�";
			}
			else if(result==-1)
			{
				message = "ԭ�����ȷ";
			}
			else if(result==-1)
			{
				message = "�����쳣";
			}else{
				message = "�����쳣";
			}
			ret.append("<MESSAGE>"+message+"</MESSAGE>\n");
			ret.append("</RESULT>\n</REP>\n</GNNT>\n");
			out.print(ret.toString());
		}
		
	}
%>