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
		String str="消除帐户";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			CorrespondValue corr = new CorrespondValue();
			corr.bankID = remark[0];
			bankID = bankID + remark[0]+ "、";
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
						str1="信息不完整";
					}else if(result==-40014){
						str1="账号未注册";
					}else if(result==-40015){
						str1="银行解约失败";
					}else if(result==-1){
						str1="已签约帐户市场不能注销";
					}else{
						str1="其他错误";
					}
					str=str+corr.bankID+"("+str1+")";
					lv.setLogcontent("注销账户："+remark[1]+"银行："+remark[0]+str1+"时间："+Tool.fmtTime(new java.util.Date()));
					dao.log(lv);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(str.length()>4){
			str=str+"失败";
		}else{
			str=str+"成功";
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
			bankID = bankID + remark[0]+ "、";
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
	lv.setLogcontent("禁用银行账号：银行代码"+bankID);
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
			bankID = bankID + remark[0]+ "、";
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
		lv.setLogcontent("恢复银行账号：银行代码"+bankID);
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
		String str="预签约";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "、";
			CorrespondValue corr = dao.getCorrespond(remark[0],remark[1],remark[2]);
			FirmBalanceValue fv = cp.getMarketBalance(corr.firmID);
			if(fv != null && fv.getMarketBalance() != 0){
				str = "交易商资金不为0，预签约失败";
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
							str = str + "成功";
						}
					}else{
						str = str+corr.bankID+"银行不支持预签约";
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
		lv.setLogcontent("预签约：银行代码"+bankID);
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
		String str="同步账号";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "、";
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
						str = str + "成功";
					}
				}else{
					str = str+corr.bankID+"银行不能市场端同步";
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
		lv.setLogcontent("同步银行账号：银行代码"+bankID);
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
		String str="签约账号";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			bankID = bankID + remark[0]+ "、";
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
						str = str + "成功";
					}
				}else{
					str = str+corr.bankID+"银行不能市场端签约";
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
		lv.setLogcontent("签约银行账号：银行代码"+bankID);
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
				bankID=bankID+remark[0]+"、";
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
							alert("重置交易商转账密码为6个1成功！");				
							</SCRIPT>
					<%
				}else{
					%>
							<SCRIPT LANGUAGE="JavaScript">
							alert("重置交易商转账密码异常,请重试或联系管理员！");				
							</SCRIPT>
					<%
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("重置银行转账密码成功：银行代码"+bankID);
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