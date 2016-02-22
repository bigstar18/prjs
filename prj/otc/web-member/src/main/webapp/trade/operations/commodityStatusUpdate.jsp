<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>修改选中商品</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/tradeManage/operator/updateCommodityStatus.action" method="post"  targetType="hidden">
				<div>
			<table border="0" width="500" align="center" >
				<tr height="70"></tr>
				<tr>
					<td>
						   <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;状态修改</div>
					<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="st_bor">
						<tr height="20">
							<td align="right">
							商品代码:
							</td>
							<td>
							  <input type="text" class="input_text" id="name" name="obj.id" value="${obj.id}" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td align="right">
							商品名称：
							</td>
							<td>
							  <input type="text" class="input_text" id="name" name="obj.name" value="${obj.name}" readonly="readonly">
							</td>
						</tr>
						<tr height="20">
							<td align="right">
							商品状态:
							</td>
							<td>
							 <select id="status" name="obj.status" disabled="disabled">
							  	<option value="0">编&nbsp;辑&nbsp;中</option>
							  	<option value="1">上&nbsp;&nbsp;市</option>
							  	<option value="2">退&nbsp;&nbsp;市</option>
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
						<button  class="btn_sec" onclick="updateCommodity()">上市</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onclick="window.close()">关闭</button>
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

