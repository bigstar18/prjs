<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head><title>客户手续费</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="myForm" action="" targetType="hidden"
			method="post" >
			<div class="div_scrospmid">
			<table border="0" width="90%" align="center">
				<tr height="30"></tr>
				<tr>
				<td>
				<div class="st_title">
				<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;详细信息</div>
				<%@ include file="/commodity/commodityFee/customerTakeFeeUpdateTable.jsp"%>
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
		if (parseFloat(myForm.feeRate.value) < parseFloat(myForm.mkt_FeeRate.value)) {
			alert("交易所收取手续费必须小于手续费系数");
			return false;
		}
		var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
		myForm.action="${basePath}/apply/customerTakeFeeApply/update.action";
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