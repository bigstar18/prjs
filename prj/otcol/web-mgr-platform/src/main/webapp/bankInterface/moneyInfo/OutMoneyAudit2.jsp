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
		String errorInfo="���";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				req.setParams(new Object[]{Long.parseLong(id[i]),true});
				try{
					result = cp.doWork(req);//���ͨ��
				}catch(Exception e){
					e.printStackTrace();
					result.result = -1;
				}
				if(result == null || result.result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("��ˮ"+Tool.strToInt(id[i])+"һ�����ͨ��ʧ�ܣ�ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
				}else{
					lv.setLogcontent("��ˮ"+Tool.strToInt(id[i])+"һ�����ͨ���ɹ���ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds)){
				errorInfo = errorInfo+"���ֳɹ���\\rʧ����ˮ�ţ�"+failureIds;
			}else{
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
	else if(isOK != null && isOK.equals("no"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String errorInfo="��˾ܾ�";
		String failureIds = "";
		String[] id = request.getParameterValues("ck");
		if(id != null && id.length > 0){
			for(int i=0;i<id.length;i++){
				req.setParams(new Object[]{Long.parseLong(id[i]),false});
				try{
					result = cp.doWork(req);//��˾ܾ�
				}catch(Exception e){
					e.printStackTrace();
					result.result = -1;
				}
				if(result == null || result.result!=0) {
					failureIds+=""+id[i]+";";
					lv.setLogcontent("��ˮ"+Tool.strToInt(id[i])+"һ����˾ܾ�ʧ�ܣ�ʱ�䣺"+new java.util.Date());
				}else{
					lv.setLogcontent("��ˮ"+Tool.strToInt(id[i])+"һ����˾ܾ��ɹ���ʱ�䣺"+new java.util.Date());
				}
				dao.log(lv);
			}
			if(!"".equals(failureIds))
			{
				errorInfo = errorInfo+"���ֳɹ���\\rʧ����ˮ�ţ�"+failureIds;
			}
			else
			{
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