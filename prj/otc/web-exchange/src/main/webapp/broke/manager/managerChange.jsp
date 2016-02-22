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
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;客户代表转移
							</div>
							<table width="100%" border="0" class="st_bor">
						<tr height="35">
							<td align="right">
								客户代表代码:
							</td>
							<td colspan="3">
								<input type="text" id="managerNo" name="obj.managerNo" value="${obj.managerNo }" style="width: 300" readonly=true >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								客户代表名称:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									style="width: 300" readonly="readonly">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								转移机构名称:
							</td>
							<td colspan="3">
								<select name="obj.parentOrganizationNO" id="parentOrganizationNO">
									<option  value="">请选择</option>
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
							转移
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							关闭
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
	    		alert('请选择所要转移到的机构！');
	    		return false;
	    	}else if(frm.parentOrganizationNO.value==parentNo){
	    		alert('不能转移给此机构，请重新选择！');
	    		return false;
	    	}
			frm.submit();
		}
	</script>
<%@ include file="/public/footInc.jsp"%>