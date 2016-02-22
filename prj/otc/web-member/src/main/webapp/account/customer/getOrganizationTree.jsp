<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>机构设置</title>
	</head>
	<link type="text/css" rel="stylesheet"
		href="${basePath}/tree1/checktree.css" />
	<script src="${basePath}/tree1/jquery-1.2.6.min.js"
		type="text/javascript">
</script>
	<script src="${basePath}/tree1/jquery.checktree2.js"
		type="text/javascript">
</script>
	<script>
$(document).ready(function() {
	$("ul.tree").checkTree( {});
});
</script>
	<body style="overflow-x: hidden;overflow-y: hidden;">
		<form action="" method="post" name="frm" targetType="hidden">
			<div style="overflow:auto;height:420px;border: 1px solid #bfbfbf;">
				<ul class="tree" style="margin-left: 15px;">
					${tree}
				</ul>
			</div>
			</form>
			<div >
				<table border="0" cellspacing="0" cellpadding="0" width="400"
					align="center" style="padding-bottom:10px;">
					<tr><td height="30">&nbsp;</td></tr>
					<tr height="35">
					    
						<td align="center" id="tdId">
								<button class="btn_sec" onClick="sub()" id="update">
									确定
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
		</div>
	</body>
</html>

<script>

var checks = document.getElementsByName("checks");
var flag=false;
for(var i=0;i<checks.length;i++){
   	if(checks[i].value=='${parentOrgNo}'){
   		flag=true;
		checks[i].checked=true;
    }
}
var checkM = document.getElementById("checkM");
if(!flag){
	if(checkM){
		checkM.checked=true;
	}
}
function sub() {
	var checks = document.getElementsByName("checks");
	var checkValue="";
	var checkM = document.getElementById("checkM");
	var isMemberNo=false;
	if(checkM&&checkM.checked){
			checkValue=checkM.value;
			isMemberNo=true;
	}else{
		for(var i=0;i<checks.length;i++){
	     	if(checks[i].checked){
				checkValue=checks[i].value;
	     	}
		}
	}
	var lableId = checkValue + '_organization';
	if(isMemberNo){
		lableId=checkValue+'_memberInfoTree';	
	}
	var returnName= document.getElementById(lableId).innerHTML;
	var parWin = window.dialogArguments;
	parWin.document.getElementById('parentOrganizationNo').value = checkValue;
	parWin.document.getElementById('parentOrgName').innerHTML = returnName;
	var returnString=checkValue;
	if(isMemberNo&&(ORGANIZATIONID==null||ORGANIZATIONID=='')){
		parWin.document.getElementById('parentOrganizationNo').value = "";
		parWin.document.getElementById('parentOrgName').innerHTML ="会员直属客户";
		returnString="";
	}
	window.returnValue = returnString;
	window.close();
}
</script>
<%@ include file="/public/footInc.jsp"%>