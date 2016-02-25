<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>

<%
	CapitalProcessorRMI cp = null;
	try{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = PAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	int fileType = Tool.strToInt(request.getParameter("fileType"));
	String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
	String result = "---";
	//取银行数据
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("getData"))
	{
		if(!noAdapterBank(bankID)){
			LogValue lv = new LogValue();
			lv.setLogopr(AclCtrl.getLogonID(request));
			lv.setLogdate(new Date());
			//lv.setIp(computerIP);
			int getDataResult = cp.getBankCompareInfo(bankID,Tool.strToDate(s_time));
			if(getDataResult == 0)
			{
				result = "取银行数据成功。银行代码："+bankID+"时间："+Tool.fmtTime(new java.util.Date());
			}
			else if(getDataResult == -1)
			{
				result = "取银行数据:系统尚未结算。银行代码："+bankID+"时间："+Tool.fmtTime(new java.util.Date());
			}
			else
			{
				result = "取银行数据:失败。银行代码："+bankID+"时间："+Tool.fmtTime(new java.util.Date());
			}
			lv.setLogcontent(result);
			dao.log(lv);
		}
	}
	else if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("sentQS"))
	{
		if(!noAdapterBank(bankID)){
			LogValue lv = new LogValue();
			lv.setLogopr(AclCtrl.getLogonID(request));
			lv.setLogdate(new Date());
			ReturnValue rv = new ReturnValue();
			if(bankID.equals("18")){
				rv = cp.sentMaketQS(Tool.strToDate(s_time), bankID);
			}else if(bankID.equals("17")){
				rv = cp.hxSentQS(bankID,Tool.strToDate(s_time));
			}else if("10".equals(bankID)){
				rv = cp.sendGHQS(bankID,null ,Tool.strToDate(s_time));
			}else{
				rv = cp.sentFirmBalance(bankID,Tool.strToDate(s_time));
			}
			result = rv.remark;
			lv.setLogcontent(result);
			dao.log(lv);
		}
	}
	else if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("fileType"))
	{
		if(!noAdapterBank(bankID)){
			LogValue lv = new LogValue();
			lv.setLogopr(AclCtrl.getLogonID(request));
			lv.setLogdate(new Date());
			ReturnValue rv = cp.getBankFileStatus(Tool.strToDate(s_time),fileType,bankID);
			result = rv.remark;
			lv.setLogcontent(result);
			dao.log(lv);
		}
	}
	else if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("sentDZ"))//资金核对
	{
		if(!noAdapterBank(bankID)){
			LogValue lv = new LogValue();
			lv.setLogopr(AclCtrl.getLogonID(request));
			lv.setLogdate(new Date());
			lv.setIp(computerIP);
			ReturnValue rv = cp.hxSentDZ(bankID,Tool.strToDate(s_time));
			result = rv.remark;
			lv.setLogcontent(result);
			dao.log(lv);
		}
	}
%>

<body>
<form name="frm" action="result.jsp" method="post">
<input type="hidden" name="bankID" value="<%=bankID%>">
<input type="hidden" name="fileType" value="<%=fileType%>">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
<input type="hidden" name="s_time" value="<%=s_time%>">
<input type="hidden" name="result" value="<%=result%>">
<input type="hidden" name="submitFlag" value="<%=submitFlag%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>