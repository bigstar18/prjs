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
		result = "��ȡƽ̨������ʧ��";
	}else{
		rv = cp.associatedFirmInfo(newFirmID, sysFirmID, oldFirmID, systemID);
	}
	if(rv.result == 0){
		result = "�ϲ��ɹ�";
	}else{
		if(rv.remark == null || "".equals(rv.remark)){
			result = "�ϲ�ʧ��";
		}else{
			result = rv.remark;
		}
	}
}else{
	result = "����������������ʧ��";
}
%>

<SCRIPT LANGUAGE="JavaScript">
	alert('<%=result%>');
	window.location = "Firm2SysFirmList.jsp";
</SCRIPT>