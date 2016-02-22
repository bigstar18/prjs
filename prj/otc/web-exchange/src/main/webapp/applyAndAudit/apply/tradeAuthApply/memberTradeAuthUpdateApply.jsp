<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head><title>会员交易权限</title>
	</head>
	<body style="overflow-y:hidden">
		<div class="st_title">
		&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息
		</div>
		<form name="myForm" action=""
			method="post" targetType="hidden">
			<div style="overflow:auto;height:470px;">
			<table border="0" width="90%" align="center">
				<tr>
				<td>
				<%@ include file="/commodity/tradeAuth/memberTradeAuthInfoTable.jsp"%>
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
		myForm.action="${basePath}/apply/memberTradeAuthApply/update.action";
		myForm.buttonClick.value=aa;		
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }

	}
	function updateaa() {
		myForm.action = "${basePath}/commodity/memberTradeAuth/update.action";
		myForm.submit();
	}
</script>
<%@ include file="/public/footInc.jsp"%>