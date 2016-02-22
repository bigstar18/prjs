<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>机构信息</title>
	</head>
	
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden" >
		<form action="${basePath}/broke/organization/update.action" name="frm" method="post" targetType="hidden">
			<div style="overflow:auto;height:370px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;机构信息
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
					<td align="center" id="tdId">
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
	if(${obj.organizationNO}=='${parentOrgNo}'){
		var tableTd=document.getElementById("tdId");
		tableTd.style.display="none";
		var regexTests = document.getElementsByTagName("input");
		var len = regexTests.length;
		for (i = 0; i < len; i++) {
			regexTests[i].readOnly = 'readOnly';
		}
		var regexselect = document.getElementsByTagName("select");
		var len2 = regexselect.length;
		for (i = 0; i < len2; i++) {
			regexselect[i].disabled='disabled';
		}
		var regexText = document.getElementsByTagName("a");
		var len3 = regexText.length;
		for (i = 0; i < len3; i++) {
			regexText[i].href="#";
		}
	}
		function updateOrganization(){
	    if(frm.organizationNo.value==frm.parentNo.value){
	    	alert('上级机构不能为自己');
			return false;
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
		function checkOrganizationId(){}
	</script>
<%@ include file="/public/footInc.jsp"%>