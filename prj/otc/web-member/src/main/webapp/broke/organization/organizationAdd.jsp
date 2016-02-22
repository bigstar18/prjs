<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>机构信息添加</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/organization/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:370px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;机构信息添加
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="addOrganization()" id="add">
							添加
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
		document.getElementById("organizationNo").value="${organizationNO}";
		//document.getElementById("organizationNoSpan").innerHTML="${organizationNO}";
		function addOrganization(){
	    
	    if(frm.organizationNo.value!="" && frm.organizationNo.value==frm.parentNo.value){
	    	alert('上级机构不能为自己');
	    }
	    if(!myblur("all")){
	    	return false;
	    }
		var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
		function checkOrganizationId(){
 			var id=document.getElementById("organizationNo").value;
		checksAction.existOrganizationId(id,function(isExist){
			if(isExist){
				document.getElementById("organizationNo").value="";
				alert('机构代码已存在，请重新添加');
				//document.getElementById("organizationNo").value="";
				document.getElementById("organizationNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>