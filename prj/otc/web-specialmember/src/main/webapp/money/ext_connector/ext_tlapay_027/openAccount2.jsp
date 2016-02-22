<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlapay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//是否存在乱码问题需要转码
	String TLAPayBankID = "027";
	TLAPayBankDAO dao = BankDAOFactory.getTLAPayDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String account = request.getParameter("account");
	String accountName = request.getParameter("accountName");
	String bankName=request.getParameter("bankName");
	String card = request.getParameter("card");
	String cardType = request.getParameter("cardType");
	String openBankCode=request.getParameter("openBankCode");
	if(isChartoGBK){
		accountName=new String(accountName.getBytes("ISO-8859-1"),"gbk");
		bankName=new String(bankName.getBytes("ISO-8859-1"),"gbk");
	}
	
	ReturnValue rv = new ReturnValue();
	
	CorrespondValue corr = null;
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='"+TLAPayBankID+"' ");
	String mgs = "";
	if(vc != null && vc.size() > 0){
		corr = vc.get(0);
	}
	if(corr!=null&&corr.isOpen == 1){
		mgs = "已签约的交易商不允许重复签约";
	}else{
		
		rv.result = -999999;//赋值初始值，保证初始值的返回代表失败
		TLAPayCapitalProcessorRMI cp = getTLAPayBankUrl(TLAPayBankID);
		if(cp!=null){
			corr=new CorrespondValue();
			corr.firmID=firmID;
			corr.contact=contact;
			corr.bankID=TLAPayBankID;
			corr.account=account;
			corr.accountName=accountName;
			corr.contact=contact;
			corr.card=card;
			corr.cardType=cardType;
			corr.bankName=bankName;
			corr.OpenBankCode=openBankCode;
			rv = cp.openAccountMarket(corr);
			if(rv.result == 0){
				mgs += "成功";
			}else{
				mgs = rv.remark;
				if(mgs == null || "".equals(mgs)){
					mgs += "失败";
				}
			}
		}else{
			mgs="系统异常-cpnull";
		}
	}
	
	
%>

<body>

</body>
<SCRIPT LANGUAGE="JavaScript">
alert('<%=mgs%>');
window.close();
</script>
