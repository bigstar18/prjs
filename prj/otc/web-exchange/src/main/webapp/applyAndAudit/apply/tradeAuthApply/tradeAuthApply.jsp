<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head><title>默认交易权限</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="myForm" action=""
			method="post" targetType="hidden">
			<div style="overflow:auto;height:500px;">
			<table border="0" width="90%" align="center">
				<tr>
				<td>
				<div class="st_title">
				<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;详细信息</div>
				<%@ include file="/commodity/tradeAuth/tradeAuthInfoTable.jsp"%>
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
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
		myForm.action="${basePath}/apply/tradeAuthApply/update.action";
		myForm.buttonClick.value=aa;		
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }

	}
</script>
<%@ include file="/public/footInc.jsp"%>