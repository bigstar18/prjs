<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.BOCCapitalProcessorRMI"%>

<%
	String BOCBankID = "012";
	BOCBankDAO dao = BankDAOFactory.getBOCDAO();
	String contact = request.getParameter("contact");
	String pwd = request.getParameter("pwd");
	String id = request.getParameter("id");
	System.out.println("==============================>pwd=[" + pwd + "]");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and id = '" + id + "' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "�����еİ󶨹�ϵΪ�գ�ǩԼʧ��";
	}else{
		corr = vc.get(0);
	}
	if(corr.isOpen == 1){
		mgs = "��ǩԼ�Ľ����̲�����ǩԼ";
	}else{
		ReturnValue rv = new ReturnValue();
		rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
		corr.bankCardPassword = pwd;
		BOCCapitalProcessorRMI cp = getBOCBankUrl(BOCBankID);
		rv = cp.openAccountMarket(corr);
		if(rv.result == 0){
			mgs = "ǩԼ�ɹ�";
		}else{
			mgs = rv.remark;
			if(mgs == null || "".equals(mgs)){
				mgs = "ǩԼʧ��";
			}
		}
	}
%>
<script>
	alert('<%=mgs%>');
	window.close();
</script>
<!--
<body>
<form name="frm" action="../../firmInfo.jsp" method="post">
<input type="hidden" name="contact" value="<%=contact%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
	window.close();
</SCRIPT>
-->