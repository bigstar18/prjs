<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.BOCCapitalProcessorRMI"%>

<%
	String BOCBankID = "018";
	BOCBankDAO dao = BankDAOFactory.getBOCDAO();
	String contact = request.getParameter("contact");
	String pwd = request.getParameter("pwd");
	String id = request.getParameter("id");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and id = '" + id + "' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "�����еİ󶨹�ϵΪ�գ���Լʧ��";
	}else{
		corr = vc.get(0);
	}
	if(corr.isOpen == 0){
		mgs = "δǩԼ�Ľ����̲������Լ";
	}else if(corr.isOpen == 2){
		mgs = "�Ѿ���Լ�Ľ����̲������Լ";
	}else{
		ReturnValue rv = new ReturnValue();
		rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
		corr.bankCardPassword = pwd;
		BOCCapitalProcessorRMI cp = getBOCBankUrl(BOCBankID);
		rv = cp.delAccountMaket(corr);
		if(rv.result == 0){
			mgs = "��Լ�ɹ�";
		}else{
			mgs = rv.remark;
			if(mgs == null || "".equals(mgs)){
				mgs = "��Լʧ��";
			}
		}
	}
%>
<script>
	alert('<%=mgs%>');
	window.close();
</script>
