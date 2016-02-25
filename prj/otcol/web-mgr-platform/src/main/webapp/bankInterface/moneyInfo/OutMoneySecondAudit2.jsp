<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	int size = Tool.strToInt(request.getParameter("pageSize"));
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	String firmID = Tool.delNull(request.getParameter("firmID"));
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	String e_time = Tool.delNull(request.getParameter("e_time"));
	request.setAttribute("pageSize", size);
	request.setAttribute("pageIndex", pageIndex);
	request.setAttribute("firmID", firmID);
	request.setAttribute("bankID", bankID);
	request.setAttribute("s_time", s_time);
	request.setAttribute("e_time", e_time);
	
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
	String isOK = request.getParameter("submitFlag");
	if(isOK != null && isOK.equals("yes"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String errorInfo="二次审核！";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				long result=cp.outMoneySecondAudit(Tool.strToInt(id[i]),true);
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"二次审核通过失败，时间："+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"二次审核通过成功，时间："+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds))
			{
				errorInfo = errorInfo+"\\r失败流水号："+failureIds;
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
		String errorInfo="二次审核拒绝！";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				long result=cp.outMoneySecondAudit(Tool.strToInt(id[i]),false);
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"二次审核拒绝失败，时间："+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"二次审核拒绝成功，时间："+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds))
			{
				errorInfo = errorInfo+"\\r失败流水号："+failureIds;
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
<form name="frm" action="OutMoneySecondAudit.jsp" method="post">
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