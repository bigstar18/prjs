<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<jsp:directive.page import="gnnt.trade.bank.dao.BankDAOFactory"/>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<html>
	<head>
		<title>
		</title>
	</head>
	<body>
<%
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		System.out.print(cp.testRmi());
	} catch(Exception e) {
		e.printStackTrace();
	}
	String money = request.getParameter("money");
	String inoutMoney = request.getParameter("inoutMoney");
	String bankID = request.getParameter("bank");
	String pwd = request.getParameter("password");
	String FIRMID = (String)session.getAttribute("REGISTERID");
	String bankName = request.getParameter("bankName");
	long result = -1;
	String results = result+"";
	if(cp==null) {
		results="调用Rmi错误";
	}else if(Double.parseDouble(money)>=Double.parseDouble("10000000000")){
		results="单笔出入金范围超出限制";
	}else {
		boolean flag = true;
		String filter = " ";
		String acount = null;
		CorrespondValue cv = null;
		if(FIRMID!=null && bankID!=null) {
			filter = filter + " and FIRMID='"+FIRMID+"' and bankid='"+bankID+"'";
		}else{
			flag = false;
			results = "客户编号、银行不能为空";
		}
		if(flag){
			Vector<CorrespondValue> vcv = cp.getCorrespondValue(filter);
			System.out.println(vcv.size());
			if(vcv != null && vcv.size()>0) {
				cv = vcv.get(0);
				System.out.println("cv"+cv);
			}else{
				flag = false;
				results = "查询交易商绑定信息失败";
			}
		}
		if(flag){
			if(cv==null) {
				flag = false;
				results = "取得绑定银行信息失败";
			}else if(cv.isOpen!=1){
				flag = false;
				results = "您的账号非签约状态，不可以做转账";
			}else{
				acount = cv.account;
			}
		}
		if(flag){
			if(!showPasswordBank(inoutMoney,bankID,cv.cardType)){
				result = cp.isPassword((String)session.getAttribute("REGISTERID"),pwd);
				if(result == 1){
					flag = false;
						results="请先设置密码";
				}else if(result == -1){
					flag = false;
					results="密码验证失败";
				}else if(result == -2){
					flag = false;
					results="未查到交易商";
				}
			}
		}
		if(flag){
			if((""+ProcConstants.outMoneyType).equals(inoutMoney)) {
				OutMoneyMarket omm = new OutMoneyMarket();
				omm.bankID = bankID;
				omm.firmID = (String)session.getAttribute("REGISTERID");
				omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
				omm.account = acount;
				omm.contact = cv.contact;
				omm.money = Double.parseDouble(money);
				omm.remark = "市场端出金";
				ReturnValue rv = cp.outMoneyMarket(omm);
				result = rv.result;
				results = rv.remark;
			} else {
				InMoneyMarket imm = new InMoneyMarket();
				imm.firmID = (String)session.getAttribute("REGISTERID");
				imm.trader = (String)session.getAttribute("REGISTERID")+ "_trader";
				imm.bankID = bankID;
				imm.account = acount;
				imm.contact = cv.contact;
				imm.money = Double.parseDouble(money);
				imm.accountPwd = pwd;
				imm.remark = "市场端发起入金";
				ReturnValue rv = cp.inMoneyMarket(imm);
				result = rv.result;
				results = rv.remark;
			}
		}
		if(results == null || results.trim().length()<=0){
			results = ErrorCode.error.get(result);
			if(results == null || results.trim().length()<=0){
				results = ""+result;
			}
		}
	}
	%>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	alert('<%=results%>'); 
</SCRIPT>	
<SCRIPT LANGUAGE="JavaScript">
	window.location = "Money.jsp";
</SCRIPT>