<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա״̬�޸�</title>
	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/account/memberFreezeStatus/updateFreezeStatus.action"
			method="post" targetType="hidden">
			<div class="div_scromin">
			<table border="0" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��Ա״̬�鿴</div>
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right">
							��Ա���:
						</td>
						<td>
							<input type="text" class="input_text" id="id" name="obj.id" value="${obj.id}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա����:
						</td>
						<td>
							<input type="text" class="input_text" id="name" name="obj.name" value="${obj.name}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա״̬:
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
					</td>
				</tr>
			</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="tab_pad">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateMemberStatus()" id="update">����</button>
							</td>
							<td align="center">
							<button  class="btn_sec" onClick="window.close()">�ر�</button>
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