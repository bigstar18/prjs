<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>����ת��</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/organizationChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center" >
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;�����ͻ�ת��
							</div>
							<table width="100%" border="0" class="st_bor">
						<input type="hidden" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }">  
						<!-- 
						<tr height="35">
							<td align="right" width="25%">
								��������:
							</td>
							<td colspan="3">
								<input type="text" id="organizationNo" name="obj.organizationNO" value="${obj.organizationNO }" class="input_textdmax" readonly="readonly">
							</td>
							
						</tr>
						-->
						<tr height="35">
							<td align="right">
								��������:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									class="input_textdmax" readonly="readonly">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								ת�Ƶ���������:
							</td>
							<td colspan="3">
								<select name="obj.organizationNoChange" id="organizationChangeNo" class="select_widmid" style="width: 300px;>
									<option  value="">��ѡ��</option>
									<c:forEach items="${resultList}" var="organ" >
										<option value="${organ[0] }" title='${organ[1] }'>${organ[1] }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>
							</td>
						</tr>						
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_secmid" onClick="updateOrganization()" id="update">
							����ת��
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
		var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
		}
	</script>
<%@ include file="/public/footInc.jsp"%>