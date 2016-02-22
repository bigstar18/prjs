<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<link type="text/css" rel="stylesheet"
		href="${basePath}/tree1/checktree.css" />
	<script src="${basePath}/tree1/jquery-1.2.6.min.js"
		type="text/javascript">
</script>
	<script src="${basePath}/tree1/jquery.checktree1.js"
		type="text/javascript">
</script>
	<script>
$(document).ready(function() {
	$("ul.tree").checkTree( {});
});
</script>
	<body style="overflow-x: hidden;overflow-y: hidden;width:350px;">
		<form action="" method="post" name="frm">
		<div style="overflow:auto;height:500px;border: 1px solid #bfbfbf;">
			<ul class="tree" style="margin-left: 15px;">
				${treeString}
			</ul>
			<span id="aaaaa" name="aaaaa"></span>
		</div>
		</form>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="350"
				align="center">
				<tr><td height="5">&nbsp;</td></tr>
				<tr height="35">
					<td align="center">
							<button class="btn_sec" onClick="sub()">
								ȷ��
							</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close()">
								�ر�
							</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

<script>
function sub() {
	var parWin = window.dialogArguments;
	var checks = document.getElementsByName("checks");
	var returnNames = "";
	var showNames = "";
	if (checks) {
		for ( var j = 0; j < checks.length; j++) {
			if (checks[j].checked) {
				var checkName = checks[j].id;
				var checkValue = checks[j].value;
				var lableId = checkValue + '_' + checkName;
				returnNames += document.getElementById(lableId).value + ',';
				showNames += document.getElementById(lableId).innerHTML + ',';
			}
		}
		parWin.document.getElementById('memberInfo').value = returnNames;
		if(showNames.length > 0){
			parWin.document.getElementById('managerShow').value = showNames.substring(0, showNames.length - 1);
		} else {
			parWin.document.getElementById('managerShow').value = "";
		}
	}
	var memberInfoIds = frm.memberInfo;
	var memberInfoString = "";
	var flag = false;
	if (memberInfoIds) {
		if(memberInfoIds.length){
			for (i = 0; i < memberInfoIds.length; i++) {
				if (memberInfoIds[i].checked) {
					flag = true;
					memberInfoString += "'" + memberInfoIds[i].value + "',";
				}
			}
			if (flag) {
				memberInfoString = memberInfoString.substring(0,
						memberInfoString.length - 1);
			}
			parWin.document.getElementById('memberInfoIds').value = memberInfoString;
		} else {
			parWin.document.getElementById('memberInfoIds').value = memberInfoIds.value;
		}
	}

	var organizationIds = frm.organization;
	var organizationString = "";
	var flag = false;
	if (organizationIds) {
		if(organizationIds.length){
			for (i = 0; i < organizationIds.length; i++) {
				if (organizationIds[i].checked) {
					flag = true;
					organizationString += "'" + organizationIds[i].value + "',";
				}
			}
			if (flag) {
				organizationString = organizationString.substring(0,
						organizationString.length - 1);
			}
			parWin.document.getElementById('organizationIds').value = organizationString;
		} else {
			parWin.document.getElementById('organizationIds').value = "'" + organizationIds.value + "'";
		}
	}
	window.close();
}

var parWin = window.dialogArguments;

if(parWin.document.getElementById('memberInfoIds')){
	var memberInfoIds = parWin.document.getElementById('memberInfoIds').value;
	var memberInfoId = memberInfoIds.split(",");
	var thisMemberInfoIds = frm.memberInfo;
	if (thisMemberInfoIds) {
		if(thisMemberInfoIds.length){
			for ( var j = 0; j < memberInfoId.length; j++) {
				memberInfoId[j] = memberInfoId[j].substring(1,
						memberInfoId[j].length - 1);
				var flag = false;
				for ( var i = 0; i < thisMemberInfoIds.length; i++) {
					if (!flag) {
						if (memberInfoId[j] == thisMemberInfoIds[i].value) {
							thisMemberInfoIds[i].checked = true;
							flag = true;
						}
					}
				}
			}
		} else {
			for ( var j = 0; j < memberInfoId.length; j++) {
				if(memberInfoId.length > 1){
					memberInfoId[j] = memberInfoId[j].substring(1, memberInfoId[j].length - 1);
				}
				var flag = false;
				if (!flag) {
					if (memberInfoId[j] == thisMemberInfoIds.value) {
						thisMemberInfoIds.checked = true;
						flag = true;
					}
				}
			}
		}
	}
}

if(parWin.document.getElementById('organizationIds')){
	var organizationIds = parWin.document.getElementById('organizationIds').value;
	var organizationId = organizationIds.split(",");
	var thisOrganizationIds = frm.organization;
	if (thisOrganizationIds) {
		for ( var j = 0; j < organizationId.length; j++) {
			organizationId[j] = organizationId[j].substring(0,
					organizationId[j].length - 1);
			var flag = false;
			if(thisOrganizationIds.length){
				for ( var i = 0; i < thisOrganizationIds.length; i++) {
					if (!flag) {
						if (organizationId[j] == thisOrganizationIds[i].value) {
							thisOrganizationIds[i].checked = true;
							flag = true;
						}
					}
				}
			} else {
				if (!flag) {
					if (organizationId[j] == thisOrganizationIds.value) {
						thisOrganizationIds.checked = true;
						flag = true;
					}
				}
			}
		}
	}
}


</script>