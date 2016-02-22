<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>特别会员延期费设置</title>
		<%request.setAttribute("applyType","commodity_specialMemberDelayFee"); %>
	</head>
	<body class="st_body">
		<form name="frm"
			action="${basePath}/commodity/specialMemberDelayFee/update.action" method="post"
			targetType="hidden">
		        <div>
				    <%@ include file="/commodity/tcDelayFee/specialMemberDelayFeeInfoUpdateTable.jsp" %>
				</div>
				<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center">
				<tr>
					<td align="center">
						<button  class="btn_sec" id="update" onclick="updateDelayFee()">修改</button>
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
	var flag = myblur("all");
		if(!flag){
			return false;
		}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
	var delayFee=document.getElementsByName("delayFee");
	for(var i=0;i<delayFee.length;i++){
		if(delayFee[i].value==''){
			var num=i+1;
			alert('第'+num+'阶梯值不允许为空');
			delayFee[i].focus();
			return false;
		}
		if(isNaN(delayFee[i].value)){
			var num=i+1;
			alert('第'+num+'阶梯值应为数字');
			delayFee[i].focus();
			return false;
		}
	}
		
	var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }
		}
</script>
<%@ include file="/public/footInc.jsp"%>