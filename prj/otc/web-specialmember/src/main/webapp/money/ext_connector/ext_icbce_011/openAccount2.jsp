<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//是否存在乱码问题需要转码
	String ICBCEBankID = "011";
	ICBCEBankDAO dao = BankDAOFactory.getICBCEDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String account = request.getParameter("account");
	String accountName = request.getParameter("accountName");
	String account1 = Tool.delNull(request.getParameter("account1"));
	String accountName1 = Tool.delNull(request.getParameter("accountName1"));
	String card = request.getParameter("card");
	String cardType = request.getParameter("cardType");
	String OpenBankCode = Tool.delNull(request.getParameter("OpenBankCode"));
	String bankName = Tool.delNull(request.getParameter("bankName"));
	String InOutStart = Tool.delNull(request.getParameter("InOutStart"));
	
	if(isChartoGBK){
		accountName=new String(accountName.getBytes("ISO-8859-1"),"gbk");
		accountName1=new String(accountName1.getBytes("ISO-8859-1"),"gbk");
		bankName=new String(bankName.getBytes("ISO-8859-1"),"gbk");
	}
	
	ReturnValue rv = new ReturnValue();
	
	CorrespondValue corr = null;
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='011' ");
	String mgs = "";
	boolean flag=true;
	if(vc != null && vc.size() > 0){
		corr = vc.get(0);
		flag=false;
	}
	if(corr!=null&&corr.isOpen == 1){
		mgs = "已签约的交易商不允许重复签约";
	}else{
		
		rv.result = -999999;//赋值初始值，保证初始值的返回代表失败
		ICBCECapitalProcessorRMI cp = getICBCEBankUrl(ICBCEBankID);
		if(cp==null){
			mgs ="连接处理器失败";
		}else{
			corr=new CorrespondValue();
			corr.firmID=firmID;
			corr.contact=contact;
			corr.bankID=ICBCEBankID;
			corr.account=account;
			corr.accountName=accountName;
			corr.account1=account1;
			corr.accountName1=accountName1;
			corr.contact=contact;
			corr.card=card;
			corr.cardType=cardType;
			corr.OpenBankCode=OpenBankCode;
			corr.bankName=bankName;
			corr.isCrossline=InOutStart;
			
			rv = cp.openAccountMarket(corr);
			
			if(rv.result == 0){
				mgs += "成功";
			}else{
				mgs = rv.remark;
				if(mgs == null || "".equals(mgs)){
					mgs += "失败";
				}
			}
		}
	}
	
	
%>

<body>

</body>
<SCRIPT LANGUAGE="JavaScript">
alert('<%=mgs%>');
window.close();
</script>
