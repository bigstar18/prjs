<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>居间信息查看</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/brokerage/update.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scrominmid">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;居间信息查看
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
							保存
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
		</form>
	</body>
</html>
<script type="text/javascript">
		function updateBrokerage(){
	    if(frm.brokerageNo.value==""){
	        alert('居间代码不允许为空');
			frm.brokerageNo.focus();
			return false;
	    }
	   	if(frm.brokerageName.value==""){
	        alert('名称不允许为空');
			frm.brokerageName.focus();
			return false;
	    }
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
		function checkBrokerageId(){}
	</script>
<%@ include file="/public/footInc.jsp"%>