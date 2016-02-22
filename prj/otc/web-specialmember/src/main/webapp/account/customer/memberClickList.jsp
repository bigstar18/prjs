<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0" onload="sele()">
		<form action="" name="frm" method="POST">
			<fieldset>
				<legend>
					<b>选择会员查询</b>
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<c:forEach items="${memberInfoList}" var="result">
						<tr>
							<td align="right" width="50%">
								<input type="checkbox" name="checkId" 
									value="${result.id }" />
							</td>
							</td>
							<td align="left" width="50%">
								<span id="${result.id}">${result.name }</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
			<button  class="btn_sec" onClick="sub()">修改</button>
			&nbsp;&nbsp;
			<button  class="btn_sec" onClick="resetMem()">返回</button>
		</form>
	</body>
</html>
<script type="text/javascript">
function sele() {
	var relIds = "${oldMemberIds}";
	var relId = relIds.split(",");
	var ids = frm.checkId;
	for (j = 0; j < relId.length; j++) {
	relId[j]=relId[j].substring(1,relId[j].length-1);
		for (i = 0; i < ids.length; i++) {
			if (relId[j] == ids[i].value) {
				ids[i].checked = true;
			}
		}
	}
}

function sub(){
	var returnString="";
	var ids = frm.checkId;
	var flag=false;
		for (i = 0; i < ids.length; i++) {
			if (ids[i].checked) {
			flag=true;
				returnString+="'"+ids[i].value+"',";
			}
		}
		if(flag){
			returnString=returnString.substring(0,returnString.length-1);
		}
		returnString+='####';
		for (i = 0; i < ids.length; i++) {
			var spanId=ids[i].value;
			var spanObj=document.getElementById(spanId);
			if (ids[i].checked) {
				returnString+=spanObj.innerHTML+',';
			}
		}
		if(flag){
			returnString=returnString.substring(0,returnString.length-1);
		}
	window.returnValue = returnString;
	window.close();
}

function resetMem(){
	window.close();
}
</script>