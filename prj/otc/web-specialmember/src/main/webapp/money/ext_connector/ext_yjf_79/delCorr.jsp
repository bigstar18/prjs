<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.YjfCapitalProcessorRMI"%>
<%!
StaticMsg staticMsghx = new  StaticMsg();
public YjfCapitalProcessorRMI getBankUrl_HX(String bankID){
	YjfCapitalProcessorRMI result = null;
	String procUrl = "";
	if(bankID != null && bankID.trim().length()>0){
		procUrl = staticMsghx.getBank(bankID).beleiveProcessor;
	}
	if(procUrl == null || procUrl.trim().length()<=0){
		procUrl = staticMsghx.getBankMap().values().iterator().next().beleiveProcessor;
	}
	System.out.println("procUrl===="+procUrl);
	try{
		result = (YjfCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return result;
}
%>
<%
	String YJFBankID = "79";
	YjfBankDAO dao = BankDAOFactory.getYjfDAO();
	String contact = request.getParameter("contact");
	String id = request.getParameter("id");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and id = '" + id + "' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "���׼����İ󶨹�ϵΪ�գ���Լʧ��";
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
		YjfCapitalProcessorRMI cp = getYjfBankUrl(YJFBankID);
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
	alert('<%=mgs%>')
</script>

<body>
<form name="frm" action="../../firmInfo.jsp" method="post">
<input type="hidden" name="contact" value="<%=contact%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>