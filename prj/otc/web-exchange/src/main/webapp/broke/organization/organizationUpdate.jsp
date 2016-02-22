<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>机构信息查看</title>
	</head>
	
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden" >
		<form action="${basePath}/broke/organization/update.action" name="frm" method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
						&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;机构信息查看
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="updateOrganization()" id="update">
							保存
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
		function updateOrganization(){
	    if(frm.organizationNo.value==""){
	        alert('机构代码不允许为空');
			frm.organizationNo.focus();
			return false;
	    }
	    if(frm.organizationNo.value==frm.parentNo.value){
	    	alert('上级机构代码不能为自己');
			frm.parentNo.focus();
			return false;
	    }
	   	if(frm.name.value==""){
	        alert('机构名称不允许为空');
			frm.name.focus();
			return false;
	    }
	    var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
		}
		function checkOrganizationId(){}
		
	</script>
<%@ include file="/public/footInc.jsp"%>