<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�ͻ�������Ϣ�޸�</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden" onload="setIdReadOnly();">
		<form action="${basePath}/broke/manager/update.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:370px;">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;�ͻ�������Ϣ�޸�
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
						<button class="btn_sec" onClick="updateManager()" id="update">
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
	function setIdReadOnly(){
		frm.managerNo.readOnly=true;
	}
	
	function updateManager(){
		if(!myblur("all")){return false;}
		var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
		}
		function checkManagerId(){
			return true;
		}
	</script>
<%@ include file="/public/footInc.jsp"%>