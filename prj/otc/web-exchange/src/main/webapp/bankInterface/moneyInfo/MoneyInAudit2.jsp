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
	CapitalProcessorRMI cp = null;
	try {
		/* �ദ����·�� */
		//cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		cp = getBankUrl("");
	} catch(Exception e) {
		e.printStackTrace();
	}
	String isOK = request.getParameter("submitFlag");
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogtime(new Date());
	lv.setIp(computerIP);
	lv.setLogtype("2140");
	lv.setLogoprtype("E");
	if("yes".equals(isOK)) {
		String errorInfo="���";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			String funID = Tool.delNull(request.getParameter("funID"));
			for(int i=0;i<id.length;i++){
				long result=cp.moneyInAudit(Tool.strToInt(id[i]),funID,true).result;
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setResult(1);
					lv.setLogcontent("�����˴���-���ͨ��-��ˮ��"+Tool.strToInt(id[i])+"����ʧ��");
				}else{
					lv.setResult(0);
					lv.setLogcontent("�����˴���-���ͨ��-��ˮ��"+Tool.strToInt(id[i])+"����ɹ�");
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds)) {
				errorInfo = errorInfo+"ʧ����ˮ�ţ�"+failureIds;
			} else {
				errorInfo = errorInfo+"�ɹ���";
			}
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("<%=errorInfo %>");
				//-->
				</SCRIPT>	
				<%
		}
	} else if("no".equals(isOK)) {
		String errorInfo="��˾ܾ�";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			String funID = Tool.delNull(request.getParameter("funID"));
			for(int i=0;i<id.length;i++){
				long result=cp.moneyInAudit(Tool.strToInt(id[i]),funID,false).result;
				if(result!=0) {
					failureIds+=""+id[i]+";";
					lv.setResult(1);
					lv.setLogcontent("�����˴���-��˾ܾ�-��ˮ��"+Tool.strToInt(id[i])+"����ʧ��.");
				}else{
					lv.setResult(0);
					lv.setLogcontent("�����˴���-��˾ܾ�-��ˮ��"+Tool.strToInt(id[i])+"����ɹ�.");
				}
				dao.log(lv);
			} if(!"".equals(failureIds)) {
				errorInfo = errorInfo+"ʧ����ˮ�ţ�"+failureIds;
			} else {
				errorInfo = errorInfo+"�ɹ���";
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
<form name="frm" action="MoneyInAudit.jsp" method="post">
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