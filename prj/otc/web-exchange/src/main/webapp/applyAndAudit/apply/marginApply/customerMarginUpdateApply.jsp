<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head><title>�ͻ���֤��</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="myForm" action=""
			method="post" targetType="hidden" >
			<div class="div_scro1">
			<table border="0" width="90%" align="center">
				<tr>
				<td>
				<div class="st_title">
				<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ</div>
				<%@ include file="/commodity/margin/customerMarginInfoUpdateTable.jsp"%>
				</td>
				</tr>
				<tr>
				<td>
				<%@ include file="/applyAndAudit/public/applyedNote.jsp"%>
				</td>
				</tr>
				</table>
				</div>
				<%@ include file="/applyAndAudit/public/buttonList.jsp"%>
		</form>
	</body>
</html>
<script language="javascript">
	function audit(aa){
		var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}              
		myForm.action="${basePath}/apply/customerMarginApply/update.action";
		myForm.buttonClick.value=aa;		
		var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }
	}
</script>
<%@ include file="/public/footInc.jsp"%>