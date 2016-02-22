<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户状态还原</title>
	</head>
	<body style="overflow-y:hidden">
		<br />
		<form name="frm" action="${basePath}/account/customerRecycle/update.action"
			method="post" targetType="hidden">
			<div class="div_scromin">
			<table border="0" height="300" width="500" align="center" >
				<tr height="100"></tr>
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;客户还原</div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right">
							交易账号:
						</td>
						<td>
							<input type="text" id="id" name="obj.customerNo" value="${obj.customerNo}"  style="background-color: #bebebe" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="${obj.name}" style="background-color: #bebebe" readonly="readonly"">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户状态:
						</td>
						<td>
							<input type="hidden" name="obj.customerStatus.status" value="N">
							 <select id="status" name="customerstatus" disabled="disabled">
									<c:forEach items="${firmStatusMap}" var="result">
										<option value="${result.key}" <c:if test="${result.key==obj.customerStatus.status}">selected="selected"</c:if>>${result.value}</option>
									</c:forEach>
								</select> 
						</td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="tab_padmax">	
		<td align="center">
			<button  class="btn_sec" onClick="updateRecycleCustomerStatus()" id="update">还原</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">关闭</button>
		</td>
	</tr>
					</table>
			</form>
		
	</body>
</html>
<script type="text/javascript">
	function updateRecycleCustomerStatus(){
			frm.submit();
	}


</script>
<%@ include file="/public/footInc.jsp"%>