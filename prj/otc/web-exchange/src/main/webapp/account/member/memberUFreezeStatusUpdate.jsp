<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա�ⶳ</title>
	</head>
	<body class="st_body" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/account/memberUFreezeStatus/updateUFreezeStatus.action"
			method="post" targetType="hidden">
		  <div class="div_scromin1">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��Ա�ⶳ</div>
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right" width="38%">
							��Ա���:
						</td>
						<td>
							<input type="text" class="input_text_pwd" id="id" name="obj.id" value="${obj.id}" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա����:
						</td>
						<td>
							<input type="text" class="input_text_pwd" id="name" name="obj.name" value="${obj.name}" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա״̬:
						</td>
						<td>
						<input type="hidden" name="obj.compMember.status" value="N">
							 <input type="text" id="status" name="status" value="${memberStatusMap[obj.compMember.status]}" class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
				</table>
					</td>
				</tr>
			</table> 
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateMemberStatus()" id="update">�ⶳ</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onClick="window.close()">�ر�</button>
							</td>
						</tr>
					</table>
					</div>
			</form>
		
	</body>
</html>
<script type="text/javascript">
	function updateMemberStatus(){
	var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}


</script>
<%@ include file="/public/footInc.jsp"%>