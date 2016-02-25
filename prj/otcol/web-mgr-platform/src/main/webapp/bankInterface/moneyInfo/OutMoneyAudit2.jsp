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
	BankDAO dao = BankDAOFactory.getDAO();
	PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	ReturnValue result = new ReturnValue();
	RequestMsg req = new RequestMsg();
	req.setBankID("");
	req.setMethodName("outMoneyAudit");
	String isOK = request.getParameter("submitFlag");
	if(isOK != null && isOK.equals("yes")){
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String errorInfo="审核";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				req.setParams(new Object[]{Long.parseLong(id[i]),true});
				try{
					result = cp.doWork(req);//审核通过
				}catch(Exception e){
					e.printStackTrace();
					result.result = -1;
				}
				if(result == null || result.result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"一次审核通过失败，时间："+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"一次审核通过成功，时间："+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds)){
				errorInfo = errorInfo+"部分成功！\\r失败流水号："+failureIds;
			}else{
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
				req.setParams(new Object[]{Long.parseLong(id[i]),false});
				try{
					result = cp.doWork(req);//审核拒绝
				}catch(Exception e){
					e.printStackTrace();
					result.result = -1;
				}
				if(result == null || result.result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"一次审核拒绝失败，时间："+new java.util.Date());
				}else{
					lv.setLogcontent("流水"+Tool.strToInt(id[i])+"一次审核拒绝成功，时间："+new java.util.Date());
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
<form name="frm" action="OutMoneyAudit.jsp" method="post">
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