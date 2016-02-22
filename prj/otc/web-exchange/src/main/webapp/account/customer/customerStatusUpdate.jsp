<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户激活</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm"
			action="${basePath}/account/customerActiveStatus/update.action"
			method="post" targetType="hidden">
			<div class="div_scromin1">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="30" height="28" align="absmiddle" />&nbsp;&nbsp;客户激活</div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right" width="35%">
							交易账号:
						</td>
						<td>
							<input type="text" id="id" name="obj.customerNo" value="${obj.customerNo}"
								class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="${obj.name}"
								class="input_text_pwd" readonly="readonly"">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户状态:
						</td>
						<td>
							<input type="hidden" id="status" name="obj.customerStatus.status" value="N">
							<input type="text" id="customerStatus" name="customerStatus" value="${firmStatusMap[obj.customerStatus.status]}" class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
				</table>
				</td>
				</tr>
				</table>
				</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
	   <tr>
		<td  align="center">
			<button  class="btn_sec" onClick="updateCustomerStatus()" id="update">激活</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">关闭</button>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
function updateCustomerStatus() {
var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
}
</script>
		</form>

	</body>
</html>

<%@ include file="/public/footInc.jsp"%>