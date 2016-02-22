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
	<script src="${basePath}/tree1/jquery.checktree.js"
		type="text/javascript">
</script>
	<script>
$(document).ready(function() {
	$("ul.tree").checkTree( {});
});
</script>
	<body style="overflow-x: hidden;overflow-y: hidden;width:400px;">
		<form action="" method="post" name="frm">
		<input type="checkbox" id="isRelated" checked="checked"/>是否关联子项
		<script>
			var relate='${isRelated}';
			if(relate==''){
				relate=window.dialogArguments.document.getElementById("isRelated").value;
			}
			if(relate=='false'){
				document.getElementById("isRelated").checked=false;
			}
		</script>
		<div style="overflow:auto;height:500px;border: 1px solid #bfbfbf;">
			<ul class="tree" style="margin-left: 15px;">
				${treeString}
			</ul>
			</div>
		</form>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="400"
				align="center">
				<tr><td height="5">&nbsp;</td></tr>
				<tr height="35">
					<td>
						<div align="center">
							<button class="btn_sec" onClick="sub()">
								确定
							</button>
					</td>
					<td>
						<div align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
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
	if (checks) {
		for ( var j = 0; j < checks.length; j++) {
			if (checks[j].checked) {
				var checkName = checks[j].id;
				var checkValue = checks[j].value;
				var lableId = checkValue + '_' + checkName;
				returnNames += document.getElementById(lableId).innerHTML + ',';
			}
		}
		parWin.document.getElementById('selectIds').value = returnNames;
	}
	var brokerageIds = frm.brokerage;
	var brokerageString = "";
	var flag = false;
	if (brokerageIds) {
		if(brokerageIds.length){
			for (i = 0; i < brokerageIds.length; i++) {
				if (brokerageIds[i].checked) {
					flag = true;
					brokerageString += "'" + brokerageIds[i].value + "',";
				}
			}
			if (flag) {
				brokerageString = brokerageString.substring(0,
						brokerageString.length - 1);
			}
			parWin.document.getElementById('brokerageIds').value = brokerageString;
		}else{
			if (brokerageIds.checked) {
					flag = true;
					brokerageString += "'" + brokerageIds.value + "',";
			}
			if (flag) {
				brokerageString = brokerageString.substring(0,
						brokerageString.length - 1);
			}
			parWin.document.getElementById('brokerageIds').value = brokerageString;
		}
	}

	var memberInfoIds = frm.memberInfo;
	var memberInfoString = "";
	var flag = false;
	if (memberInfoIds) {
		if (memberInfoIds.checked) {
			flag = true;
			memberInfoString += "'" + memberInfoIds.value + "',";
		}
		if (flag) {
			memberInfoString = memberInfoString.substring(0,
					memberInfoString.length - 1);
		}
		parWin.document.getElementById('memberIds').value = memberInfoString;
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
		}else{
			if (organizationIds.checked) {
				flag = true;
				organizationString += "'" + organizationIds.value + "',";
			}
			if (flag) {
				organizationString = organizationString.substring(0,
						organizationString.length - 1);
			}
			parWin.document.getElementById('organizationIds').value = organizationString;
		}
	}

	var managerIds = frm.manager;
	var managerString = "";
	var flag = false;
	if (managerIds) {
		for (i = 0; i < managerIds.length; i++) {
			if (managerIds[i].checked) {
				flag = true;
				managerString += "'" + managerIds[i].value + "',";
			}
		}
		if (flag) {
			managerString = managerString.substring(0, organizationString - 1);
		}
		parWin.document.getElementById('managerIds').value = managerString;
	}
	
	var Related=document.getElementById('isRelated');
	if(Related.checked){
		isRelated='true';
	}else{
		isRelated='false';
	}
	parWin.document.getElementById('isRelated').value = isRelated;
	
	window.close();
}

var parWin = window.dialogArguments;
var brokerageIds = parWin.document.getElementById('brokerageIds').value;
var brokerageId = brokerageIds.split(",");

var organizationIds = parWin.document.getElementById('organizationIds').value;
var organizationId = organizationIds.split(",");;

var managerIds = parWin.document.getElementById('managerIds').value;
var managerId = managerIds.split(",");;

var memberInfoIds1 = parWin.document.getElementById('memberIds').value;
var memberInfoId = memberInfoIds1.split(",");

var thisMemberInfoIds =  document.getElementById("memberInfo");
if (thisMemberInfoIds) {
	for ( var j = 0; j < memberInfoId.length; j++) {
		memberInfoId[j] = memberInfoId[j].substring(1,
				memberInfoId[j].length - 1);
		var flag = false;
		if (!flag) {
			if (memberInfoId[j] == thisMemberInfoIds.value) {
				thisMemberInfoIds.checked = true;
				flag = true;
			}
		}
	}
}



var thisBrokerageIds = frm.brokerage;
if (thisBrokerageIds) {
	for ( var j = 0; j < brokerageId.length; j++) {
		brokerageId[j] = brokerageId[j].substring(1, brokerageId[j].length - 1);
		var flag = false;
		if(thisBrokerageIds.length){
			for ( var i = 0; i < thisBrokerageIds.length; i++) {
				if (!flag) {
					if (brokerageId[j] == thisBrokerageIds[i].value) {
						thisBrokerageIds[i].checked = true;
						flag = true;
					}
				}
			}
		}else{
			if (brokerageId[j] == thisBrokerageIds.value) {
				thisBrokerageIds.checked = true;
				flag = true;
			}
		}
	}
}

var thisOrganizationIds = frm.organization;
if (thisOrganizationIds) {
	for ( var j = 0; j < organizationId.length; j++) {
		organizationId[j] = organizationId[j].substring(1,
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
		}else{
			if (organizationId[j] == thisOrganizationIds.value) {
				thisOrganizationIds.checked = true;
				flag = true;
			}
		}
	}
}

var thisManagerIds = frm.manager;
if (thisManagerIds) {
	for ( var j = 0; j < managerId.length; j++) {
		managerId[j] = managerId[j].substring(1, managerId[j].length - 1);
		var flag = false;
		for ( var i = 0; i < thisManagerIds.length; i++) {
			if (!flag) {
				if (managerId[j] == thisManagerIds[i].value) {
					thisManagerIds[i].checked = true;
					flag = true;
				}
			}
		}
	}
}
</script>