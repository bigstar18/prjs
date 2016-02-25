<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.processorrmi.PTCapitalProcessorRMI"%>

<%
String result = "";
String oldFirmID = request.getParameter("oldFirmID");
String sysFirmID = request.getParameter("sysFirmID");
String systemID = request.getParameter("systemID");
String newFirmID = request.getParameter("newFirmID");
if(oldFirmID != null && sysFirmID != null && systemID != null && newFirmID != null){
	PTCapitalProcessorRMI cp = null;
	ReturnValue rv = new ReturnValue();
	try
	{
		cp = (PTCapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/TradeCapitalProcessorRMI");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	if(cp == null){
		result = "获取平台处理器失败";
	}else{
		rv = cp.associatedFirmInfo(newFirmID, sysFirmID, oldFirmID, systemID);
	}
	if(rv.result == 0){
		result = "合并成功";
	}else{
		if(rv.remark == null || "".equals(rv.remark)){
			result = "合并失败";
		}else{
			result = rv.remark;
		}
	}
}else{
	result = "参数不完整，操作失败";
}
%>

<SCRIPT LANGUAGE="JavaScript">
	alert('<%=result%>');
	window.location = "Firm2SysFirmList.jsp";
</SCRIPT>