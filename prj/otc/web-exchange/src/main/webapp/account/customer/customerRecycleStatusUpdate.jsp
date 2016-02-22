<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户还原</title>
	</head>
	<body style="overflow-y:hidden">
		<form name="frm" action="${basePath}/account/customerRecycle/update.action"
			method="post" targetType="hidden">
			<div class="div_scromin1">
			<table border="0" width="80%" align="center" valign="top">
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="30" height="28" align="absmiddle" />&nbsp;&nbsp;客户还原</div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right" width="35%">
							交易账号:
						</td>
						<td>
							<input type="text" id="id" name="obj.customerNo" value="${obj.customerNo}"  class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="${obj.name}" class="input_text_pwd" readonly="readonly"">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							客户状态:
						</td>
						<td>
							<input type="hidden" name="obj.customerStatus.status" value="C">
							<!--  <select id="status" style="background-color: #bebebe" name="customerstatus" disabled="disabled">
									<c:forEach items="${firmStatusMap}" var="result">
										<option value="${result.key}" <c:if test="${result.key==obj.customerStatus.status}">selected="selected"</c:if>>${result.value}</option>
									</c:forEach>
							</select>  -->
						<input type="text" id="customerStatus" name="customerStatus" value="${firmStatusMap[obj.customerStatus.status]}" class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" >	
		<td align="center">
			<button  class="btn_sec" onClick="updateRecycleCustomerStatus()" id="update">还原</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">关闭</button>
		</td>
	</tr>
					</table>
					</div>
			</form>
		
	</body>
</html>
<script type="text/javascript">
	function updateRecycleCustomerStatus(){
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}


</script>
<%@ include file="/public/footInc.jsp"%>