<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员状态修改</title>
	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/account/memberStatus/update.action"
			method="post" targetType="hidden">
			<div class="div_scromax">
			<fieldset width="50%" height="60%">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;会员状态查看</div>
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
							 <select id="status" name="obj.compMember.status">
									<c:forEach items="${memberStatusMap}" var="result">
										<option value="${result.key}" <c:if test="${result.key==obj.compMember.status}">selected="selected"</c:if>>${result.value}</option>
									</c:forEach>
								</select>
						</td>
					</tr>
				</table>
			</fieldset>
			</div>
			
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="tab_pad">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateMemberStatus()" id="update">提交</button>
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