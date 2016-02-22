<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>修改选中商品</title>
	</head>
	<body class="st_body" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/tradeManage/deOperator/updateCommodityDeStatus.action" method="post"  targetType="hidden">
			<div>
			<table border="0" width="90%" align="center" >
				<tr>
					<td>
				<div class="st_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;状态修改</div>
					<table border="0" cellspacing="0" cellpadding="10" width="80%" align="center" class="st_bor">
						<tr height="20">
							<td align="right" width="40%">
							商品代码:
							</td>
							<td>
							  <input class="input_text" type="text" id="name" name="obj.id" value="${obj.id}" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td align="right">
							商品名称:
							</td>
							<td>
							  <input class="input_text" type="text" id="name" name="obj.name" value="${obj.name}" readonly="readonly">
							</td>
						</tr>
						<tr height="20">
							<td align="right">
							商品状态:
							</td>
							<td>
							 <input type="hidden" name="obj.status" value="2" />
							 <select id="status" disabled="disabled" class="select_widmid">
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
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"  align="center" >
					<tr>
					<td  align="center">
						<button id="updateDeComm" class="btn_sec" onclick="updateDeCommodity()">退市</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onclick="window.close()">关闭</button>
					</td>
				</tr>
					</table> 
					</div>
		</form>
	</body>

</html>
<script type="text/javascript">
	function updateDeCommodity(){
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
	document.getElementById("status").value = ${obj.status};
</script>

<%@ include file="/public/footInc.jsp"%>

