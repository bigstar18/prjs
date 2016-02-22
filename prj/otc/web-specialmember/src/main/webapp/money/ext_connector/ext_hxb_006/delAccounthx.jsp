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
		mgs = "和华夏银行的绑定关系为空，解约失败";
	}else{
		corr = vc.get(0);
	}
	if(corr.isOpen == 0){
		mgs = "未签约的交易商不允许解约";
	}else if(corr.isOpen == 2){
		mgs = "已经解约的交易商不允许解约";
	}else{
		ReturnValue rv = new ReturnValue();
		rv.result = -999999;//赋值初始值，保证初始值的返回代表失败
		HXBCapitalProcessorRMI cp = getHXBBankUrl("006");
		rv = cp.delAccountMaket(corr);
		if(rv.result == 0){
			mgs = "解约成功";
		}else{
			mgs = rv.remark;
			if(mgs == null || "".equals(mgs)){
				mgs = "解约失败";
			}
		}
	}
%>
	
<SCRIPT LANGUAGE="JavaScript">
    alert('<%=mgs%>')
	window.close();
    window.location = "../../firmInfo.jsp";
</SCRIPT>
