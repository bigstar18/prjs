<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlgpay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//是否存在乱码问题需要转码
	String TLGPayBankID = "028";
	TLGPayBankDAO dao = BankDAOFactory.getTLGPayDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String account = request.getParameter("account");
	String accountName = request.getParameter("accountName");
	String card = request.getParameter("card");
	String cardType = request.getParameter("cardType");
	
	if(isChartoGBK){
		accountName=new String(accountName.getBytes("ISO-8859-1"),"gbk");
	}
	
	ReturnValue rv = new ReturnValue();
	
	CorrespondValue corr = null;
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='"+TLGPayBankID+"' ");
	String mgs = "";
	if(vc != null && vc.size() > 0){
		corr = vc.get(0);
	}
	
	vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='027' and isopen=1 ");
	if(vc==null||vc.size()<1){
		mgs="通联网关支付不能作为主银行，请先签约其他银行";
	}else if(corr!=null&&corr.isOpen == 1){
		mgs = "已签约的交易商不允许重复签约";
	}else{
		
		rv.result = -999999;//赋值初始值，保证初始值的返回代表失败
		TLGPayCapitalProcessorRMI cp = getTLGPayBankUrl(TLGPayBankID);
		System.out.println("cp=============:"+cp);
		if(cp!=null){
			corr=new CorrespondValue();
			corr.firmID=firmID;
			corr.contact=contact;
			corr.bankID=TLGPayBankID;
			corr.account=account;
			corr.accountName=accountName;
			corr.contact=contact;
			corr.card=card;
			corr.cardType=cardType;
			
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
