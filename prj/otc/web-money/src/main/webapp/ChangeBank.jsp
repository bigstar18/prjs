<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<jsp:directive.page import="gnnt.trade.bank.dao.BankDAOFactory"/>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%
	//����ҳ���ǿ��ˢ��
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
			result = "���б�����";
		}else if(bv.inMoneyFlag != 0){
			result = "��𱻽���";
		}else if(bv.outMoneyFlag != 0){
			result = "���𱻽���";
		}else{*/
			result = bv.beginTime+"--"+bv.endTime;
		/**}*/
	}else{
		result = "δ�鵽����";
	}
%>
<%=result%>_<%=result%>_<%=result%>
