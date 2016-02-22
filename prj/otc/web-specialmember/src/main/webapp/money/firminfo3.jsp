<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<base target="_self">

<%
	String[] remarks = request.getParameterValues("ck");
	String flag = request.getParameter("falg");
	String firmID =request.getParameter("firmID");
	String bankID = "";
	String id = "";
	String contact ="";
	if(remarks != null)
	{
		
		String str="";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			id = remark[10];
			bankID = remark[0];
			contact = remark[2];
		System.out.println("bankID-------"+bankID);	
			
			
		}
		
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>发起解约信息</title>
  </head>
  
<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
		<form id="frm" action="" method="post" name="frm">	    		
				<input type="hidden" value="<%=id%>" name="id">
                <input type="hidden" value="<%=bankID%>" name="bankID">
				<input type="hidden" value="<%=request.getParameter("firmID")%>" name="firmID" >	
				<input type="hidden" value="<%=request.getParameter("contact")%>" name="contact">
				<input type="hidden" value="del" name="falg">
				
		</form>		
		</table>

</body>
</html>
<script language="javascript">
	var bankid = frm.bankID.value;
			if(bankid=='050'){
				frm.action="ext_connector/03_delAccount2.jsp";
				frm.submit();
			}else if(bankid == '79'){
				frm.action="ext_connector/ext_yjf_79/delCorr.jsp";
				frm.submit();
		    }else if(bankid == '012'){
				var id = '<%=id%>';
				var contact = '<%=request.getParameter("contact")%>';
				window.showModalDialog("ext_connector/ext_boc_012/inputPwd.jsp?&contact="+contact+"&id="+id+"&flag=1","","dialogWidth=360px; dialogHeight=10px; status=no;scroll=no;help=no;");
				window.location = "firmInfo.jsp";
		    }else if(bankid == '66'){
				frm.action="ext_connector/ext_gfb_66/delCorr.jsp";
				frm.submit();
		    }else if(bankid=='021'){
			 
				frm.action="ext_connector/ext_ceb_021/delAccount.jsp";
				frm.submit();
			 
		    }else if(bankid=='006'){
			 
				frm.action="ext_connector/ext_hxb_006/delAccounthx.jsp";
				frm.submit();
			 
		    }else if(bankid == '016'){
				frm.action="ext_connector/ext_citic_016/delAccount.jsp";
				frm.submit();
				
		    }else if(bankid == '011'){
				frm.action="ext_connector/ext_icbce_011/delAccount.jsp";
				frm.submit();
				
		    }else if(bankid == '027'){
				frm.action="ext_connector/ext_tlapay_027/delAccount.jsp";
				frm.submit();
				
		    }else if(bankid == '028'){
				frm.action="ext_connector/ext_tlgpay_028/delAccount.jsp";
				frm.submit();
				
		    }else{
				alert("此银行不能解约");
				window.history.back();
			}
	
</script>
