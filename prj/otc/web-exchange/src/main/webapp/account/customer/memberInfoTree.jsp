<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type='text/javascript' src='${basePath}/dwr/engine.js'>
</script>
<script type='text/javascript' src='${basePath}/dwr/util.js'>
</script>
<script type="text/javascript"
	src='${basePath}/dwr/interface/memberLoad.js' />
</script>
<html>
	<head>
		<title>客户归属</title>
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
	<body style="overflow-x: hidden;overflow-y: hidden;width:350px;">
		<form action="" method="post" name="frm">
		<input type="checkbox" id="isRelated" checked="checked"/>是否关联子项
		<script>
			if('${isRelated}'=='false'){
				document.getElementById("isRelated").checked=false;
			}
		</script>
		<div style="overflow:auto;height:480px;border: 1px solid #bfbfbf;">
			<ul class="tree" style="margin-left: 15px;font-size:12px;">
				<div id="tree">
				</div>
			</ul>
			<span id="aaaaa" name="aaaaa"></span>
		</div>
		<script>
		var parWin = window.dialogArguments;
			var tree = parWin.parent.frames["topFrame1"].document.getElementById("tree").value;
			var treeString="";
			if(tree!=null&&tree!=""){
				document.getElementById("tree").innerHTML=tree;
			}else{
				document.getElementById("tree").innerHTML='${treeString}';
			}
			$("ul.tree").checkTree( {});
		</script>
		</form>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="350"
				align="center">
				<tr><td height="5">&nbsp;</td></tr>
				<tr height="35">
					<td align="center">
							<button class="btn_sec" onClick="sub()" >
								确定
							</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

function clickMember(memberNo){
	var liId=memberNo+"_li";
	var liDoc=document.getElementById(liId);
	var name=document.getElementById(memberNo+"_mem").value;
	var memberInfoIds = frm.memberInfo;
	var checkFlag=false;
	var memberChecked=null;
	//////////////////////////////////
	if (memberInfoIds) {
				for (i = 0; i < memberInfoIds.length; i++) {
						//alert(memberInfoIds[i].value+'    '+memberNo+'    '+memberInfoIds[i].checked);
					if (memberInfoIds[i].value==memberNo)
					{						
						//alert('进来了1');
						//alert(memberInfoIds[i]);
						if(memberInfoIds[i].checked)
						{
							memberChecked=memberInfoIds[i];
							checkFlag = true;
							break;
						}
					}
				}
	}
	//}


	if(name=='false'){
		memberLoad.treeForMemberInfo(memberNo, function(returnString) {
			
			liDoc.innerHTML=liDoc.innerHTML+returnString;
			$("ul.tree").checkTree( {});
			document.getElementById(memberNo+"_mem").value="true";
			
			//var memberInfoIds = frm.memberInfo;
			if (checkFlag) {
				if(memberChecked){
					
					//memberChecked.click();
					memberChecked.checked = true;
					
				}
			}
			if(checkFlag&&document.getElementById("isRelated").checked){
				var docs=document.getElementById(memberNo+"_li").getElementsByTagName('input');
				if(docs&&docs.length>0){
					for(var i=0;i<docs.length;i++){
						docs[i].checked=true;
					}
				}
			}
			$("ul.tree").checkTree( {});
		})
	}
}


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
		parWin.document.getElementById('memberNames').value = returnNames;
	}
	var brokerageIds = frm.brokerage;
	var brokerageString = "";
	var flag = false;
	if (brokerageIds) {
		if( brokerageIds.length){
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
		}else{
			if (memberInfoIds.checked) {
				flag = true;
				memberInfoString += "'" + memberInfoIds.value + "',";
			}
			if (flag) {
				memberInfoString = memberInfoString.substring(0,
						memberInfoString.length - 1);
			}
			parWin.document.getElementById('memberInfoIds').value = memberInfoString;
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
			managerString = managerString.substring(0, managerString.length - 1);
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
	parWin.parent.frames["topFrame1"].document.getElementById('tree').value=document.getElementById("tree").innerHTML;
	window.close();
}

var parWin = window.dialogArguments;
var brokerageIds = parWin.document.getElementById('brokerageIds').value;
var brokerageId = brokerageIds.split(",");


var organizationIds = parWin.document.getElementById('organizationIds').value;
var organizationId = organizationIds.split(",");;

var managerIds = parWin.document.getElementById('managerIds').value;
var managerId = managerIds.split(",");;

var memberInfoIds = parWin.document.getElementById('memberInfoIds').value;
var memberInfoId = memberInfoIds.split(",");

var thisMemberInfoIds = frm.memberInfo;
if (thisMemberInfoIds) {
	for ( var j = 0; j < memberInfoId.length; j++) {
		memberInfoId[j] = memberInfoId[j].substring(1,
				memberInfoId[j].length - 1);
		var flag = false;
		if(thisMemberInfoIds.length){
			for ( var i = 0; i < thisMemberInfoIds.length; i++) {
				if (!flag) {
					if (memberInfoId[j] == thisMemberInfoIds[i].value) {
						thisMemberInfoIds[i].checked = true;
						flag = true;
					}
				}
			}
		}else{
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