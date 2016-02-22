<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checksAction.js'/></script>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/organization/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:380px;">
				<table border="0" height="300" width="90%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;机构信息添加
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
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
		function addOrganization(){
	    if(frm.organizationNo.value==""){
	        alert('机构代码不允许为空');
			frm.organizationNo.focus();
			return false;
	    }
	   	if(frm.name.value==""){
	        alert('机构名称不允许为空');
			frm.name.focus();
			return false;
	    }
	    if(frm.organizationNo.value==frm.parentNo.value){
	    	alert('上级机构不能为自己');
	    }
		var vaild = affirm("您确定要操作吗？");
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
				alert('机构编号已存在，请重新添加');
				document.getElementById("organizationNo").value="";
				document.getElementById("organizationNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>