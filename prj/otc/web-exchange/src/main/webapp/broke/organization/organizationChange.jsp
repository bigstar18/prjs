<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/organizationChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scromid">
				<table border="0" height="300" width="90%" align="center" >
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;����ת��
							</div>
							<table width="100%" border="0" class="st_bor">
						<input type="hidden" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }">
						<!--  
						<tr height="35">
							<td align="right">
								��������:
							</td>
							<td colspan="3">
								<input type="text" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }" style="width: 300" readonly="readonly">
							</td>
							
						</tr>
						-->
						<tr height="35">
							<td align="right">
								��������:
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
								<select name="obj.organizationNoChange" id="organizationChangeNo">
									<option  value="">��ѡ��</option>
									<c:forEach items="${resultList}" var="organ" >
										<option value="${organ[0] }">${organ[1] }</option>
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
						<button class="btn_sec" onClick="updateOrganization()" id="update">
							ת�ƻ���
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
		function updateOrganization(){
	    	if(frm.organizationChangeNo.value==""){
	    		alert('��ѡ����Ҫת�ƵĻ�����');
	    		return false;
	    	}
			frm.submit();
		}
	</script>
<%@ include file="/public/footInc.jsp"%>