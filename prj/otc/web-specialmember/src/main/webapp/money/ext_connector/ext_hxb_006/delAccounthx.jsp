<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	HXBBankDAO dao = BankDAOFactory.getHXBDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String bankID = request.getParameter("bankID");
	String id = request.getParameter("id");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and id = '" + id + "' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "�ͻ������еİ󶨹�ϵΪ�գ���Լʧ��";
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
		HXBCapitalProcessorRMI cp = getHXBBankUrl("006");
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
	
<SCRIPT LANGUAGE="JavaScript">
    alert('<%=mgs%>')
	window.close();
    window.location = "../../firmInfo.jsp";
</SCRIPT>
