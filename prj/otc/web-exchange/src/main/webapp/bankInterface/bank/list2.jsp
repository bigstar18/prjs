<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	int size = Tool.strToInt(request.getParameter("pageSize"));
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	String submitFlag = request.getParameter("submitFlag");
	BankDAO dao = BankDAOFactory.getDAO();
	/* 多处理器路由
	CapitalProcessorRMI cp = null;
	try {
		System.out.println("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}*/
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogtime(new Date());
	lv.setIp(computerIP);
	String[] bankIDs = request.getParameterValues("ck");
	if(bankIDs != null && bankIDs.length > 0){
		CapitalProcessorRMI cp = null;
		Vector<String> banks = new Vector<String>();
		for(String bankID : bankIDs){
			banks.add(bankID);
		}
		//多处理器路由修改
		try {
			cp = getBankUrl(banks.get(0));
		} catch(Exception e) {
			e.printStackTrace();
		}
		String str = "银行管理";
		String strs = "";
		ReturnValue rv = null;
		if("disOutMoney".equalsIgnoreCase(submitFlag)){//禁用出金
			str+="-禁用出金";
			rv = cp.changeBankTradeType(banks,2,1);
		}else if("disInMoney".equalsIgnoreCase(submitFlag)){//禁用入金
			str+="-禁用入金";
			rv = cp.changeBankTradeType(banks,1,1);
		}else if("recOutMoney".equalsIgnoreCase(submitFlag)){//恢复出金
			str+="-恢复出金";
			rv = cp.changeBankTradeType(banks,2,0);
		}else if("recInMoney".equalsIgnoreCase(submitFlag)){//恢复入金
			str+="-恢复入金";
			rv = cp.changeBankTradeType(banks,1,0);
		}
		if(rv != null){
			strs=rv.remark;
			%>
				<script>
					alert('<%=strs%>');
				</script>
			<%
		}else{
			strs="返回信息为空";
		}
		lv.setLogcontent(str+"-"+strs);
		dao.log(lv);
	}
%>

<body>
<form name="frm" action="list.jsp" method="post">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
<input type="hidden" name="submitFlag" value="<%=submitFlag%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>