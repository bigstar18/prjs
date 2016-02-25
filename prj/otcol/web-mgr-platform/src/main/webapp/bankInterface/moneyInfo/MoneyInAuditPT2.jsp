<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.bank.platform.rmi.PlatformProcessorRMI"%>
<%
	int size = Tool.strToInt(request.getParameter("pageSize"));
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	String firmID = Tool.delNull(request.getParameter("firmID"));
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	String e_time = Tool.delNull(request.getParameter("e_time"));
	
	BankDAO dao = BankDAOFactory.getDAO();
	PlatformProcessorRMI cp = null;
	try
	{
		System.out.println("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	String isOK = request.getParameter("submitFlag");
	if(isOK != null && isOK.equals("yes"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String errorInfo="审核";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				long result=-1;
				RequestMsg req = new RequestMsg();
				req.setBankID("");
				req.setMethodName("moneyAduitToSystem");
				req.setParams(new Object[]{Long.parseLong(id[i]), true});
				try{
					ReturnValue rv = cp.doWork(req);
					if(rv != null){
						result = rv.result;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水："+Tool.strToInt(id[i])+"单边帐处理通过失败，时间："+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("流水："+Tool.strToInt(id[i])+"单边帐处理通过成功，时间："+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds))
			{
				errorInfo = errorInfo+"部分成功！\\r失败流水号："+failureIds;
			}
			else
			{
				errorInfo = errorInfo+"成功！";
			}
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("<%=errorInfo %>");
				//-->
				</SCRIPT>	
				<%
		}
	}
	else if(isOK != null && isOK.equals("no"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String errorInfo="审核拒绝";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				long result=-1;
				RequestMsg req = new RequestMsg();
				req.setBankID("");
				req.setMethodName("moneyAduitToSystem");
				req.setParams(new Object[]{Long.parseLong(id[i]), false});
				try{
					ReturnValue rv = cp.doWork(req);
					if(rv != null){
						result = rv.result;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水："+Tool.strToInt(id[i])+"单边帐处理拒绝失败，时间："+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("流水："+Tool.strToInt(id[i])+"单边帐处理拒绝成功，时间："+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds))
			{
				errorInfo = errorInfo+"部分成功！\\r失败流水号："+failureIds;
			}
			else
			{
				errorInfo = errorInfo+"成功！";
			}
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("<%=errorInfo %>");
				//-->
				</SCRIPT>	
				<%
		}
	}
%>

<body>
<form name="frm" action="MoneyInAuditPT.jsp" method="post">
<input type="hidden" name="bankID" value="<%=bankID%>">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
<input type="hidden" name="s_time" value="<%=s_time%>">
<input type="hidden" name="firmID" value="<%=firmID%>">
<input type="hidden" name="e_time" value="<%=e_time%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>