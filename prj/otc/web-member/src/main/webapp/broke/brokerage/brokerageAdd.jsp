<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>居间信息添加</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/brokerage/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:395px;">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="st_title">
							&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;居间信息添加
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
						<button class="btn_sec" onClick="addBrokerage()" id="add">
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
		document.getElementById("brokerageNo").value="${brokerageNo}";
		document.getElementById("brokerageNoSpan").innerHTML="${brokerageNo}";
		function addBrokerage(){
		   if( !myblur("all") ){return false;}
			var vaild = window.confirm("您确定要操作吗？");
			if(vaild==true){
			    frm.submit();
		    }else{
	           return false;
		    }
		}

	</script>
<%@ include file="/public/footInc.jsp"%>