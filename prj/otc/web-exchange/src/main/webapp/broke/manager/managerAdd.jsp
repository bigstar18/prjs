<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checksAction.js'/></script>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/manager/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:380px;">
				<table border="0" height="300" width="90%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;客户代表信息添加
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div  class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="addManager()" id="add">
							添加
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
		function addManager(){
	    if(frm.managerNo.value==""){
	        alert('经纪人代码不允许为空');
			frm.managerNo.focus();
			return false;
	    }
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
		function checkManagerId(){
 			var id=document.getElementById("managerNo").value;
		checksAction.existManagerId(id,function(isExist){
			if(isExist){
				alert('经纪编号已存在，请重新添加');
				document.getElementById("managerNo").value="";
				document.getElementById("managerNo").focus();
			}
		});
 
 }
	</script>
<%@ include file="/public/footInc.jsp"%>