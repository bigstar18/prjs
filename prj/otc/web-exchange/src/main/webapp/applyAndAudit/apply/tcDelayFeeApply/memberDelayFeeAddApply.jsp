<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head>
		<title>��Ա���ڷ�</title>
	</head>
	<body>
		<form name="myForm" action=""
			method="post" targetType="hidden" >
			<div>
			<table border="0" width="90%" align="center">
				<tr>
				<td>
				<div class="st_title">
				<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ</div>
				<%@ include file="/commodity/tcDelayFee/memberDelayFeeInfoTable.jsp"%>
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
		myForm.action="${basePath}/apply/memberDelayFeeApply/update.action";
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