<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/managerChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scromid">
				<table border="0" height="300" width="90%" align="center" >
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�ͻ�����ת��
							</div>
							<table width="100%" border="0" class="st_bor">
						<tr height="35">
							<td align="right">
								�ͻ��������:
							</td>
							<td colspan="3">
								<input type="text" id="managerNo" name="obj.managerNo" value="${obj.managerNo }" style="width: 300" readonly=true >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								�ͻ���������:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									style="width: 300" readonly="readonly">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								ת�ƻ�������:
							</td>
							<td colspan="3">
								<select name="obj.parentOrganizationNO" id="parentOrganizationNO">
									<option  value="">��ѡ��</option>
									<c:forEach items="${resultList}" var="organization" >
										<option value="${organization.organizationNO }">${organization.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="changeManager('${obj.parentOrganizationNO}')" id="update">
							ת��
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
		function changeManager(parentNo){
	    	if(frm.parentOrganizationNO.value==""){
	    		alert('��ѡ����Ҫת�Ƶ��Ļ�����');
	    		return false;
	    	}else if(frm.parentOrganizationNO.value==parentNo){
	    		alert('����ת�Ƹ��˻�����������ѡ��');
	    		return false;
	    	}
			frm.submit();
		}
	</script>
<%@ include file="/public/footInc.jsp"%>