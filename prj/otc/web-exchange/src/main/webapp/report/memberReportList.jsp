<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�ۺϻ�Ա����</title>
	</head>

	<body leftmargin="0" topmargin="0" onload="sele()" style="overflow-y:hidden">
			<div class="st_title">&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;ѡ���Ա��ѯ</div>
			<div class="div_scromid" style="overflow-x:hidden;position:absolute;top:50;z-index:1;">
			<form action="" name="frm" method="POST">
			<table border="0" width="100%" align="center" valign="top">
				<tr>
					<td>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="left" class="st_bor" valign="top">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<c:forEach items="${memberInfoList}" var="result">
						<tr>
							<td align="left" style="padding-left: 20px;">
								<input type="checkbox" name="checkId" 
									value="${result.id }" />&nbsp;
								<span id="${result.id}">(${result.id})${result.name }</span>
							</td>
						</tr>
					</c:forEach>
				</table>
				</td>
				</tr>
				</table>
				</form>			
				</div>
				<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
	<tr>
		<td align="center">
			<button  class="btn_sec" onClick="sub()">ȷ��</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="resetMem()">�ر�</button>
		</td>
	</tr>
</table>	
				</div>
		
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