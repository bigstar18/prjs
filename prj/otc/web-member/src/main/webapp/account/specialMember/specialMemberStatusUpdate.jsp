<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա״̬�޸�</title>


	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/account/specialMemberStatus/updateStatus.action"
			method="post" targetType="hidden">
			<fieldset width="50%" height="60%">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;��Ա״̬�鿴</div>
				<input type="hidden" name="obj.id" value="${obj.id }">
				<input type="hidden" name="obj.name" value="${obj.name }">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr height="35">
						<td align="right">
							��Ա���:
						</td>
						<td>
							<input class="from" type="text" id="id" name="obj.memberId" value="${obj.id}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա����:
						</td>
						<td>
							<input class="from" type="text" id="name" name="obj.memberName" value="${obj.name}" readonly="readonly" style="background-color: #bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա״̬:
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
			</fieldset>
			
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<div class="st_anbor"><a href="#" id="updateMember" onclick="updateMemberStatus()" class="st_an">�ύ</a></div>
							</td>
							<td align="center">
								<div class="st_anbor"><a href="#" onclick="window.close()" class="st_an">�ر�</a></div>
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