<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>������Ϣ���</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/organization/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:370px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;������Ϣ���
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
							���
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
		document.getElementById("organizationNo").value="${organizationNO}";
		//document.getElementById("organizationNoSpan").innerHTML="${organizationNO}";
		function addOrganization(){
	    
	    if(frm.organizationNo.value!="" && frm.organizationNo.value==frm.parentNo.value){
	    	alert('�ϼ���������Ϊ�Լ�');
	    }
	    if(!myblur("all")){
	    	return false;
	    }
		var vaild = window.confirm("��ȷ��Ҫ������");
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
				alert('���������Ѵ��ڣ����������');
				//document.getElementById("organizationNo").value="";
				document.getElementById("organizationNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>