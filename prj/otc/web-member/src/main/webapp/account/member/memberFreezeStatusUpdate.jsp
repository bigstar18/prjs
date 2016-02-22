<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员冻结</title>


	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/account/memberFreezeStatus/updateFreezeStatus.action"
			method="post" targetType="hidden">
			<fieldset width="50%" height="60%">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;会员冻结</div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr height="35">
						<td align="right">
							会员编号:
						</td>
						<td>
							<input type="text" class="from" id="id" name="obj.id" value="${obj.id}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员名称:
						</td>
						<td>
							<input type="text" class="from" id="name" name="obj.name" value="${obj.name}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员状态:
						</td>
						<td>
						<input type="hidden" name="obj.compMember.status" value="F" >
							 <select id="status" name="status" disabled="disabled">
									<c:forEach items="${memberStatusMap}" var="result">
										<option value="${result.key}" <c:if test="${result.key==obj.compMember.status}">selected="selected"</c:if>>${result.value}</option>
									</c:forEach>
								</select>
						</td>
					</tr>
				</table>
			</fieldset>
			
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<div class="st_anbor"><a href="#" onclick="updateMemberStatus()" class="st_an">冻结</a></div>
							</td>
							<td align="center">
								<div class="st_anbor"><a href="#" onclick="window.close()" class="st_an">关闭</a></div>
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