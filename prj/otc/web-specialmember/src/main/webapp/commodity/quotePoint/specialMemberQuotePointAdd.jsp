<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�ر��Ա���۵������</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm"
			action="${basePath}/commodity/specialMemberQuotePoint/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;���۵������
							</div>
							<%@ include file="/commodity/quotePoint/specialMemberQuotePointAddTable.jsp"%>
						</td>
						<td align="right">
						</td>
						<td>
						</td>
					</tr>

					</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">

				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addMargin()" id="update">
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
function addMargin() {
	var memberNo=document.getElementById('memberNo').value;
	var commodityNo=document.getElementById('commodityId').value;
	if(memberNo==""){
		alert('��ѡ���Ա����!');
		return false;
	}
	if(commodityNo==""){
		alert('��ѡ����Ʒ����!');
		return false;
	}
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>