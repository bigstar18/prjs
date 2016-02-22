<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>会员延期费设置</title>
	</head>
	<body class="st_body">
		<form name="frm"
			action="${basePath}/commodity/memberDelayFee/add.action" method="post"
			targetType="hidden">
		   <div>
			       <%@ include file="/commodity/tcDelayFee/memberDelayFeeInfoTable.jsp" %>	
		   </div>
				<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center">
				<tr>
					<td align="center">
						<button  class="btn_sec" id="update" onclick="updateDelayFee()">添加</button>
					</td>
					<td align="center">
						<button  class="btn_sec" onclick="window.close()">关闭</button>
					</td>
				</tr>
			</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function updateDelayFee() {
	 if(!myblur("all")){
		 return false;
	 }
	var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }
}
</script>
<%@ include file="/public/footInc.jsp"%>