<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlgpay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.TLGPayCapitalProcessorRMI"%>

<%
	String TLGPayBankID = "028";
	TLGPayBankDAO dao = BankDAOFactory.getTLGPayDAO();
	String firmID = request.getParameter("firmID");
	
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='028' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "�ʽ��˺���ϢΪ�գ�����ʧ��";
	}else{
		ReturnValue rv = new ReturnValue();
		corr = vc.get(0);
		if(corr.isOpen !=1){
			mgs = "δǩԼ���ѽ�Լ�Ľ����̲������Լ";
		}else{
			rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
			TLGPayCapitalProcessorRMI cp = getTLGPayBankUrl(TLGPayBankID);
			corr.bankID=TLGPayBankID;
			
			rv = cp.delAccountMaket(corr);
			if(rv.result == 0){
				mgs += "�ɹ�";
			}else{
				mgs = rv.remark;
				if(mgs == null || "".equals(mgs)){
					mgs += "ʧ��";
				}
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
