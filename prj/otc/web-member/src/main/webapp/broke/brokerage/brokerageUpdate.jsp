<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�Ӽ���Ϣ�޸�</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/brokerage/update.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:395px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�Ӽ���Ϣ�޸�
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="updateBrokerage()" id="update">
							����
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
	document.getElementById("getOrgTree").href="#";
	frm.brokerageNo.readOnly=true;
		function updateBrokerage(){
		 if( !myblur("all") ){return false;}
	   	var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
		}
		function checkBrokerageId(){
			return true;
		}
	</script>
<%@ include file="/public/footInc.jsp"%>