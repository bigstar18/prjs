<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlapay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//�Ƿ��������������Ҫת��
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
		mgs = "��ǩԼ�Ľ����̲������ظ�ǩԼ";
	}else{
		
		rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
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
				mgs += "�ɹ�";
			}else{
				mgs = rv.remark;
				if(mgs == null || "".equals(mgs)){
					mgs += "ʧ��";
				}
			}
		}else{
			mgs="ϵͳ�쳣-cpnull";
		}
	}
	
	
%>

<body>

</body>
<SCRIPT LANGUAGE="JavaScript">
alert('<%=mgs%>');
window.close();
</script>
