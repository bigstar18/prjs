<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlgpay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//�Ƿ��������������Ҫת��
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
		mgs="ͨ������֧��������Ϊ�����У�����ǩԼ��������";
	}else if(corr!=null&&corr.isOpen == 1){
		mgs = "��ǩԼ�Ľ����̲������ظ�ǩԼ";
	}else{
		
		rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
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
