<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/brokerage/add.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scro">
				<table border="0" height="300" width="90%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;�Ӽ���Ϣ���
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="addBrokerage()" id="add">
							���
						</button>
					</td>
					<td align="center">
						<button class="btn_sec" onClick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
		document.getElementById("brokerageNo").value="${brokerageNo}";
		document.getElementById("brokerageNoSpan").innerHTML="${brokerageNo}";
		function addBrokerage(){
	    if(frm.brokerageNo.value==""){
	        alert('�Ӽ���벻����Ϊ��');
			frm.brokerageNo.focus();
			return false;
	    }
	   	if(frm.brokerageName.value==""){
	        alert('�Ӽ����Ʋ�����Ϊ��');
			frm.brokerageName.focus();
			return false;
	    }

		var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
		 function checkBrokerageId(){
 			var id=document.getElementById("brokerageNo").value;
		checksAction.existBokerageId(id,function(isExist){
			if(isExist){
				alert('�Ӽ����Ѵ��ڣ����������');
				document.getElementById("brokerageNo").value="";
				document.getElementById("brokerageNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>