<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>

<%
	CapitalProcessorRMI cp = null;
	/* �ദ����·��
	try{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}*/
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	int pageSize2 = BANKPAGESIZE;
	int size2 = Tool.strToInt(request.getParameter("pageSize2"));
	if(size2>0){
		pageSize2 = size2;
	}
	int pageIndex2= Tool.strToInt(request.getParameter("pageIndex2"));
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	int fileType = Tool.strToInt(request.getParameter("fileType"));
	String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
	/* �ദ����·�� */
	try{
		cp = getBankUrl(bankID);
	}catch(Exception e){
		e.printStackTrace();
	}
	//--�޸Ľ���--
	String result = "---";
	//ȡ��������
	if("getData".equals(request.getParameter("submitFlag"))) {
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		lv.setLogtype("2150");
		lv.setLogoprtype("E");
		lv.setResult(1);
		int getDataResult = cp.getBankCompareInfo(bankID,Tool.strToDate(s_time));
		if(getDataResult >= 0) {
			result = "�����ж����ļ��ɹ������ж����ļ�����["+getDataResult+"]";
			lv.setResult(0);
			submitFlag = "do";
		} else {
			result = "�����ж����ļ�ʧ��";
		}
		lv.setLogcontent(result);
		dao.log(lv);
	}else if("roughInfo".equals(request.getParameter("submitFlag"))){
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		lv.setLogtype("2150");
		lv.setLogoprtype("E");
		lv.setResult(1);
		ReturnValue rv = cp.roughInfo(bankID,Tool.strToDate(s_time));
		if(rv != null){
			if(rv.result>=0){
				lv.setResult(0);
			}
			result = rv.remark;
		}else{
			result = "������ϢΪ��";
		}
		lv.setLogcontent("ͬ���������� "+result);
		dao.log(lv);
	}
%>

<body>
<form name="frm" action="result.jsp" method="post">
<input type="hidden" name="bankID" value="<%=bankID%>">
<input type="hidden" name="fileType" value="<%=fileType%>">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
<input type="hidden" name="pageSize2" value="<%=size2%>">
<input type="hidden" name="pageIndex2" value="<%=pageIndex2%>">
<input type="hidden" name="s_time" value="<%=s_time%>">
<input type="hidden" name="result" value="<%=result%>">
<input type="hidden" name="submitFlag" value="<%=submitFlag%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>