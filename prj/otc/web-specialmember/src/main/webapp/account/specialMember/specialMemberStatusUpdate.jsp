<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员状态修改</title>
	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/account/specialMemberStatus/updateStatus.action"
			method="post" targetType="hidden">
			<input type="hidden" name="obj.id" value="${obj.id }">
				<input type="hidden" name="obj.name" value="${obj.name }">
			<div class="div_scromin">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;会员状态查看</div>
			<table border="0" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right">
							会员编号:
						</td>
						<td>
							<input class="from" type="text" id="id" name="obj.memberId" value="${obj.id}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员名称:
						</td>
						<td>
							<input class="from" type="text" id="name" name="obj.memberName" value="${obj.name}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员状态:
						</td>
						<td>
							 <select id="status" name="obj.specialMemberStatus.status">
									<c:forEach items="${firmStatusMap}" var="result">
										<option value="${result.key}" <c:if test="${result.key==obj.specialMemberStatus.status}">selected="selected"</c:if>>${result.value}</option>
									</c:forEach>
								</select>
						</td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="tab_pad">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateMemberStatus()" id="update">修改</button>
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
	function updateMemberStatus(){
			frm.submit();
	}


</script>
<%@ include file="/public/footInc.jsp"%>