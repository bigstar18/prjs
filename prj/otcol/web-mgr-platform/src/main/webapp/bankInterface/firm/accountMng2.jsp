<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
String firmID = request.getParameter("firmID");
int size = Tool.strToInt(request.getParameter("pageSize"));
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));


BankDAO dao = BankDAOFactory.getDAO();
CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("del"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		String str="�����ʻ�";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			CorrespondValue corr = new CorrespondValue();
			corr.bankID = remark[0];
			bankID = bankID + remark[0]+ "��";
			corr.firmID = remark[1];
			corr.account = remark[2];
			corr.account1 = remark[3];
			corr.card = remark[4];
			corr.cardType=remark[5];
			corr.isOpen=Integer.parseInt(remark[6]);
			
			try
			{
				long result=0;
				if(!delAccountBank(corr.bankID.trim()) && corr.isOpen==1){
					result=-1;
				}else{
					if(!noAdapterBank(corr.bankID)){
						result=cp.delAccountMaket(corr);
					}else{
						result = cp.delAccountNoAdapter(corr);
					}
				}
				if(result!=0){
					String str1="";
					if(result==-40011){
						str1="��Ϣ������";
					}else if(result==-40014){
						str1="�˺�δע��";
					}else if(result==-40015){
						str1="���н�Լʧ��";
					}else if(result==-1){
						str1="��ǩԼ�ʻ��г�����ע��";
					}else{
						str1="��������";
					}
					str=str+corr.bankID+"("+str1+")";
					lv.setLogcontent("ע���˻���"+remark[1]+"���У�"+remark[0]+str1+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
					dao.log(lv);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(str.length()>4){
			str=str+"ʧ��";
		}else{
			str=str+"�ɹ�";
		}
		%>
			<script>
				alert("<%=str%>");
			</script>
		<%
	}
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("forbid"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "��";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			corr.status = 1;
			try
			{
				dao.modCorrespond(corr);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	lv.setLogcontent("���������˺ţ����д���"+bankID);
	}
	dao.log(lv);	
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("recover"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "��";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			corr.status = 0;
			try
			{
				dao.modCorrespond(corr);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("�ָ������˺ţ����д���"+bankID);
	}
	dao.log(lv);	
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("synAccount"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		String str="ԤǩԼ";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "��";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			FirmBalanceValue fv = cp.getMarketBalance(corr.firmID);
			if(fv != null && fv.getMarketBalance() != 0){
				str = "�������ʽ�Ϊ0��ԤǩԼʧ��";
			}else{
				corr.status = 0;
				corr.isOpen = 2;
				try
				{
					if(openAccountBank(remark[0])){
						ReturnValue result=null;
						result=cp.synchroAccountMarket(corr);
						if(0!=result.result){
							String str1=result.remark;
							str=str+corr.bankID+"("+str1+")";
						}else{
							str = str + "�ɹ�";
						}
					}else{
						str = str+corr.bankID+"���в�֧��ԤǩԼ";
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		%>
			<script>
				alert('<%=str%>');
			</script>
		<%
		lv.setLogcontent("ԤǩԼ�����д���"+bankID);
	}
	dao.log(lv);	
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("synchro"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		String str="ͬ���˺�";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "��";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			corr.status = 0;
			corr.isOpen = 0;
			try
			{
				if(openAccountBank(remark[0])){
					ReturnValue result=null;
					result=cp.synchroAccountMarket(corr);
					if(0!=result.result){
						String str1=result.remark;
						str=str+corr.bankID+"("+str1+")";
					}else{
						str = str + "�ɹ�";
					}
				}else{
					str = str+corr.bankID+"���в����г���ͬ��";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>
			<script>
				alert('<%=str%>');
			</script>
		<%
		lv.setLogcontent("ͬ�������˺ţ����д���"+bankID);
	}
	dao.log(lv);	
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("open"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		String str="ǩԼ�˺�";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "��";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			corr.status = 0;
			corr.isOpen = 0;
			try
			{
				if(openAccountBank(remark[0])){
					ReturnValue result=null;
					result=cp.openAccountMarket(corr);
					if(0!=result.result){
						String str1=result.remark;
						str=str+corr.bankID+"("+str1+")";
					}else{
						str = str + "�ɹ�";
					}
				}else{
					str = str+corr.bankID+"���в����г���ǩԼ";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>
			<script>
				alert('<%=str%>');
			</script>
		<%
		lv.setLogcontent("ǩԼ�����˺ţ����д���"+bankID);
	}
	dao.log(lv);	
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("resetsmmy")){
LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] remarks = request.getParameterValues("ck");
	if(remarks != null)
	{
		String bankID = "";
		for(int i=0;i<remarks.length;i++)
		{
					try
			{
			String[] remark = remarks[i].split(",");
				bankID=bankID+remark[0]+"��";
				%>
							<SCRIPT LANGUAGE="JavaScript">
							alert(firmID);				
							</SCRIPT>
					<%
				FirmValue firm = dao.getFirm(firmID);
				firm.password = Encryption.encryption(firm.firmID,"111111",null);
				long result = dao.modFirm(firm);
				if(result==0){
					%>
							<SCRIPT LANGUAGE="JavaScript">
							alert("���ý�����ת������Ϊ6��1�ɹ���");				
							</SCRIPT>
					<%
				}else{
					%>
							<SCRIPT LANGUAGE="JavaScript">
							alert("���ý�����ת�������쳣,�����Ի���ϵ����Ա��");				
							</SCRIPT>
					<%
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("��������ת������ɹ������д���"+bankID);
	}
	dao.log(lv);	
}
%>

<body>
<form name="frm" action="accountMng.jsp" method="post">
<input type="hidden" name="firmID" value="<%=firmID%>">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>