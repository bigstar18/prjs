<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�޸�ѡ����Ʒ</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/tradeManage/operator/updateCommodityStatus.action" method="post"  targetType="hidden">
				<div>
			<table border="0" width="500" align="center" >
				<tr height="70"></tr>
				<tr>
					<td>
						   <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;״̬�޸�</div>
					<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="st_bor">
						<tr height="20">
							<td align="right">
							��Ʒ����:
							</td>
							<td>
							  <input type="text" class="input_text" id="name" name="obj.id" value="${obj.id}" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td align="right">
							��Ʒ���ƣ�
							</td>
							<td>
							  <input type="text" class="input_text" id="name" name="obj.name" value="${obj.name}" readonly="readonly">
							</td>
						</tr>
						<tr height="20">
							<td align="right">
							��Ʒ״̬:
							</td>
							<td>
							 <select id="status" name="obj.status" disabled="disabled">
							  	<option value="0">��&nbsp;��&nbsp;��</option>
							  	<option value="1">��&nbsp;&nbsp;��</option>
							  	<option value="2">��&nbsp;&nbsp;��</option>
							  </select>
							</td>
						</tr>
						
						</table> 
			</td>
			</tr>
			</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" style="padding-top:30px;">						
						<tr>
					<td  align="center">
						<button  class="btn_sec" onclick="updateCommodity()">����</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onclick="window.close()">�ر�</button>
					</td>
				</tr>
					</table> 
		</form>
	</body>

</html>
<script type="text/javascript">
	function updateCommodity(){
		frm.submit();
	}
	document.getElementById("status").value = ${obj.status};
</script>

<%@ include file="/public/footInc.jsp"%>

