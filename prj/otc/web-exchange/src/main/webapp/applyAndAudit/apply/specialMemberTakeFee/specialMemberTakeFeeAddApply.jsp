<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>特别会员收取手续费修改</title>
	</head>
	<body class="st_body">
		<form name="frm" action="" method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;详细信息
							</div>
							<%@ include
								file="/commodity/commodityFee/specialMemberTakeFeeAddTable.jsp"%>
							</div>
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
	frm.action = "${basePath}/apply/specialMemberTakeFeeApply/audits.action";
	frm.buttonClick.value = aa;
	frm.submit();
}
</script>
<%@ include file="/public/footInc.jsp"%>