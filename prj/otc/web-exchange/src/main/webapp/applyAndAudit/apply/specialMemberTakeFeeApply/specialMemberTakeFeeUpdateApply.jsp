<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head><title>特别会员手续费</title>
	</head>
	<body class="st_body" style="overflow-y:hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div class="div_scrospmid">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;详细信息
							</div>
							<%@ include
								file="/commodity/commodityFee/specialMemberTakeFeeUpdateTable.jsp"%>
							</div>
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
<script type="text/javascript">
function audit(aa) {
	if (parseFloat(frm.feeRate.value) < parseFloat(frm.mkt_FeeRate.value)) {
			alert("交易所收取手续费不能大于手续费系数");
			return false;
		}
		var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
	frm.action = "${basePath}/apply/specialMemberTakeFeeApply/update.action";
	frm.buttonClick.value = aa;
	var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		frm.submit();
	    }else{
           return false;
	    }
}
</script>
<%@ include file="/public/footInc.jsp"%>