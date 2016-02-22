<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<jsp:directive.page import="gnnt.trade.bank.dao.BankDAOFactory"/>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%
	//设置页面的强制刷新
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	String result = "";
	String bankID=request.getParameter("bankID");
	CapitalProcessorRMI cp = null;
	try{
		cp = getBankUrl(bankID);
	}catch(Exception e){
		e.printStackTrace();
	}
	String filter = " and bankID='"+bankID+"'";
	Vector<BankValue> bankList=cp.getBankList(filter);
	if(bankList!=null && bankList.size()>0){
		BankValue bv = bankList.get(0);
		/**if(bv.validFlag != 0){
			result = "银行被禁用";
		}else if(bv.inMoneyFlag != 0){
			result = "入金被禁用";
		}else if(bv.outMoneyFlag != 0){
			result = "出金被禁用";
		}else{*/
			result = bv.beginTime+"--"+bv.endTime;
		/**}*/
	}else{
		result = "未查到银行";
	}
%>
<%=result%>_<%=result%>_<%=result%>
