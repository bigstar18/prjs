<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/public/session.jsp" %>
<html>
	<head>
		<title>机构设置</title>
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
	//$("ul.tree").checkTree( {});
});
</script>
	<body style="overflow-x: hidden;overflow-y: hidden;">
		<form action="" method="post" name="frm" targetType="hidden">
		<div style="overflow:auto;height:420px;border: 1px solid #bfbfbf;">
			<ul class="tree" style="margin-left: 15px;">
				${treeString}
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
								关联
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
	</body>
</html>

<script>

var reg =/^\w*DEFAULT_+\w*$/; 
var id='${user.type}';
var userId='${user.userId}';
if(id.match(reg)!=null||userId==currenUserId){
	var tableTd=document.getElementById("tdId");
	tableTd.style.display="none";
}


var checks = document.getElementsByName("checks");
var flag=false;
for(var i=0;i<checks.length;i++){
   	if(checks[i].value=='${user.organization.organizationNO}'){
   		flag=true;
		checks[i].checked=true;
    }
}
var checkM = document.getElementById("checkM");
if(!flag){
	checkM.checked=true;
}
function sub() {
	var checks = document.getElementsByName("checks");
	var check="";
	var checkM = document.getElementById("checkM");
	if(checkM){
		checkM.checked=false;
	}
	for(var i=0;i<checks.length;i++){
     	if(checks[i].checked){
			check=checks[i].value;
     	}
	}
	var vaild = affirm("您确定要操作吗？");
	if(vaild==true){
		frm.action = "<%=basePath%>/user/organizationUpdate.action?organizationNo="+check+"&userId="+'${user.userId}';
		frm.submit();
	 }else{
	     return false;
	}
}

function resetAll(){
	var checks = document.getElementsByName("checks");
	for(var i=0;i<checks.length;i++){
     	if(checks[i].checked){
		checks[i].checked=false;
     	}
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>