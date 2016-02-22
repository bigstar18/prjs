<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.ICBCECapitalProcessorRMI"%>

<%
	String ICBCEBankID = "011";
	ICBCEBankDAO dao = BankDAOFactory.getICBCEDAO();
	String firmID = request.getParameter("firmID");
	String account = request.getParameter("account");
	String accountName = request.getParameter("accountName");
	String account1 = request.getParameter("account1");
	String accountName1 = request.getParameter("accountName1");
	String falg=request.getParameter("falg");
	ReturnValue rv = new ReturnValue();
	
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='011' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "资金账号信息为空，操作失败";
	}else{
		corr = vc.get(0);
	}
	if(corr.isOpen !=1&&"del".equals(falg)){
		mgs = "未签约或已解约的交易商不允许解约";
	}else{
		
		rv.result = -999999;//赋值初始值，保证初始值的返回代表失败
		ICBCECapitalProcessorRMI cp = getICBCEBankUrl(ICBCEBankID);
		corr.bankID=ICBCEBankID;
		mgs="解约";
		rv = cp.delAccountMaket(corr);
		if(rv.result == 0){
			mgs += "成功";
		}else{
			mgs = rv.remark;
			if(mgs == null || "".equals(mgs)){
				mgs += "失败";
			}
		}
	}
	
	
%>

<body>

</body>
<SCRIPT LANGUAGE="JavaScript">
alert('<%=mgs%>');
window.location="../../firmInfo.jsp";
</script>
