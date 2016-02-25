<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<jsp:directive.page import="gnnt.trade.bank.dao.BankDAOFactory"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<body>
<%
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		System.out.print(cp.testRmi());
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	String money = request.getParameter("money");
	String bankID = request.getParameter("bank");
	String bankQuery = request.getParameter("bankQuery");
	String FIRMID = "MarketGSOut";
	String acount = null;
	long result = -1;
	String results = result+"";
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	if(cp==null)
	{
		results="调用Rmi错误";
	}
	int express=0;
	System.out.println("nnnn:"+bankID);
	
	String optValue = request.getParameter("opt");
	boolean showBabkBalance = true;
	FirmBalanceValue fv = null;
	
	if(cp!=null)
	{
		
		if(optValue!=null && "out".equals(optValue)){
			System.out.println("333");
			String filter = " where 1=1 ";
			if(FIRMID!=null)
			{
				filter = filter + " and FIRMID='"+FIRMID+"'";
			}
			if(bankID!=null)
			{
				filter = filter + " and bankID='"+bankID+"'";
			}
			System.out.println("444");
			Vector<CorrespondValue> vcv = cp.getCorrespondValue(filter);
			CorrespondValue cv = null;
			if(vcv != null && vcv.size()>0)
			{
				cv = vcv.get(0);
			}
			if(cv!=null && cv.isOpen==1)
			{
				acount = cv.account;
			}
			
			ReturnValue result2 =new ReturnValue();//调用处理器出金方法
			result2 =(cp.outMoneyGS(bankID,Double.parseDouble(money),FIRMID,acount,null,"market_out",express,0));
			result = result2.result;
			results = result2.remark;
			
			/**判断是否超出审核额度标志*/
			boolean Beyond = false;
			BankValue bVal = BankDAOFactory.getDAO().getBank(bankID);
			FirmValue fVal = BankDAOFactory.getDAO().getFirm(FIRMID);
			double auditBalance = bVal.maxAuditMoney;
			if(fVal!=null && fVal.maxAuditMoney > 0) auditBalance = fVal.maxAuditMoney;
			if(Double.parseDouble(money) <= auditBalance)
			{
				Beyond = true;
			}
			if(result>=0)
			{
				if(result==5)
				{
					results="资金划拨出金处理中！";
				}else if(result==1)
				{
					results="资金划拨出金已发送国付宝处理，请在资金流水中查看流水状态！";
				}
				else
				{
					results="资金划拨出金成功！";
				}
			}
			else if(result==-1)
			{
				results="资金划拨出金金额超过审核额度，出金被拒绝！";
			}
			else
			{
				results=ErrorCode.error.get(result);
				if(results==null) results = "资金划拨失败"+result+"";
			}
			lv.setLogcontent(""+results+ " 金额："+money+" "+new Date());
			dao.log(lv);
		}
	}
%>
<form name="frm" action="Money.jsp" method="post">
<input type="hidden" name="bankQuery" value="<%=bankQuery%>">
<input type="hidden" name="FIRMID" value="<%=FIRMID%>">
<input type="hidden" name="opt" value="<%=optValue%>">
</form>
</body>

<SCRIPT LANGUAGE="JavaScript">
<%if(optValue!=null && "out".equals(optValue)){%>
alert('<%=results%>'); 
<%}%>
frm.submit();
</SCRIPT>
