<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>������Ϣ�鿴</title>
	</head>
	
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden" >
		<form action="${basePath}/broke/organization/update.action" name="frm" method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
						&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;������Ϣ�鿴
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
							����
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
	    if(frm.organizationNo.value==""){
	        alert('�������벻����Ϊ��');
			frm.organizationNo.focus();
			return false;
	    }
	    if(frm.organizationNo.value==frm.parentNo.value){
	    	alert('�ϼ��������벻��Ϊ�Լ�');
			frm.parentNo.focus();
			return false;
	    }
	   	if(frm.name.value==""){
	        alert('�������Ʋ�����Ϊ��');
			frm.name.focus();
			return false;
	    }
	    var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
		}
		function checkOrganizationId(){}
		
	</script>
<%@ include file="/public/footInc.jsp"%>