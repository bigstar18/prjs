<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա��ȡ�������޸�</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}/commodity/takeFee/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;��ȡ�������޸�
							</div>
							<%@ include file="/commodity/commodityFee/memberTakeFeeAddTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addTakeFeeStatus()" id="update">
								���
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								�ر�
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function addTakeFeeStatus() {
	if (parseFloat(frm.feeRate.value) < parseFloat(frm.mkt_FeeRate.value)) {
		alert("��������ȡ�����ѱ���С��������ϵ��");
		return false;
	}
	var objM_FirmIdOption = document.getElementById("m_FirmId");
	var objCommodityIdOption = document.getElementById("commodityId");
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.memberName.value = objM_FirmIdOption.options[objM_FirmIdOption.selectedIndex].text;
		frm.commodityName.value = objCommodityIdOption.options[objCommodityIdOption.selectedIndex].text;
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>