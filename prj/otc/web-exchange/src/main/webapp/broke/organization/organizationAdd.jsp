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
								&nbsp;&nbsp;������Ϣ���
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
		function addOrganization(){
	    if(frm.organizationNo.value==""){
	        alert('�������벻����Ϊ��');
			frm.organizationNo.focus();
			return false;
	    }
	   	if(frm.name.value==""){
	        alert('�������Ʋ�����Ϊ��');
			frm.name.focus();
			return false;
	    }
	    if(frm.organizationNo.value==frm.parentNo.value){
	    	alert('�ϼ���������Ϊ�Լ�');
	    }
		var vaild = affirm("��ȷ��Ҫ������");
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
				alert('��������Ѵ��ڣ����������');
				document.getElementById("organizationNo").value="";
				document.getElementById("organizationNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>