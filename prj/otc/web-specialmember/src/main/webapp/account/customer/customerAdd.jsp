<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y:hidden">
		<form action="${basePath}/account/customer/add.action" name="frm" method="post" targetType="hidden">
		<div class="div_scro">
			<table border="0" height="300" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
					    <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;�ͻ���Ϣ���</div>
						<%@include file="commonTable.jsp" %>
					</td>
				</tr>
			</table>
			</div>
			<table cellspacing="0" cellpadding="0" border="0" width="500" align="center" class="tab_padmax">
				<tr>
					<td  align="center">
						<button  class="btn_sec" onClick="addCustomer()" id="add">���</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onClick="window.close()">�ر�</button>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
	<script type="text/javascript">
		function addCustomer(){
		var obj=document.getElementById("memberNo").value;
	    if(!isNull(obj)){
	        alert('������Ա������Ϊ��');
			frm.memberNo.focus();
			return false;
	    }
	    	var obj=document.getElementById("papersType").value;
	        if(!isNull(obj)){
	            alert('֤�����Ͳ�����Ϊ��');
	            frm.papersType.focus();
			    return false;
	         }
		var id=document.getElementById("memberNo").value+document.getElementById("shortId").value;
		document.getElementById("customerId").value=id;
		checkAction.existMemberId(id,function(isExist){
			if(isExist){
				alert('�����˺��Ѵ��ڣ����������');
				document.getElementById("shortId").value="";
				document.getElementById("customerId").value="";
				document.getElementById("shortId").focus();
				return false;
			}
		});
			frm.submit();
		}
	function checkMemberNo(){
		var id=document.getElementById("memberNo").value+document.getElementById("shortId").value;
		document.getElementById("customerId").value=id;
		checkAction.existMemberId(id,function(isExist){
			if(isExist){
				alert('�����˺��Ѵ��ڣ����������');
				document.getElementById("shortId").value="";
				document.getElementById("customerId").value="";
				document.getElementById("shortId").focus();
			}
		});
	}
	</script>
<%@ include file="/public/footInc.jsp"%>