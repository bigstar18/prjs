<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>会员持仓数量</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm" action="${basePath}/commodity/holdQuantily/update.action" method="post" targetType="hidden">
			<div>
			<%@ include file="/commodity/holdQuantily/holdQuantilyUpdateTable.jsp"%>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%" align="center">
					<tr>
						<td  align="center">
								<button  class="btn_sec" onClick="updateHoldQuantily()" id="update">修改</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onClick="window.close()">关闭</button>
							</td>
						</tr>
				</table>
				</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function updateHoldQuantily(){
		if(frm.oneMaxOrderQty.value==""){
			alert('单笔最大下单量不允许为空');
			frm.oneMaxOrderQty.focus();
			return false;
		}else if((isNaN(frm.oneMaxOrderQty.value))){
			alert("单笔最大下单量必须为数字");
			frm.oneMaxOrderQty.value="";
			frm.oneMaxOrderQty.focus();
			return false;
		}
		if(frm.oneMinOrderQty.value==""){
			alert('单笔最小下单量不允许为空');
			frm.oneMinOrderQty.focus();
			return false;
		}else if((isNaN(frm.oneMinOrderQty.value))){
			alert("单笔最小下单量必须位数字");
			frm.oneMinOrderQty.value="";
			frm.oneMinOrderQty.focus();
			return false;
		}
		if(frm.maxCleanQty.value==""){
			alert('最大净持仓量不允许为空');
			frm.maxCleanQty.focus();
			return false;
		}else if((isNaN(frm.maxCleanQty.value))){
			alert("最大净持仓量必须位数字");
			frm.maxCleanQty.value="";
			frm.maxCleanQty.focus();
			return false;
		}
		if(frm.maxHoldQty.value==""){
			alert('最大持仓量不允许为空');
			frm.maxHoldQty.focus();
			return false;
		}else if((isNaN(frm.maxHoldQty.value))){
			alert("最大持仓量必须位数字");
			frm.maxHoldQty.value="";
			frm.maxHoldQty.focus();
			return false;
		}
		var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}
</script>
<%@ include file="/public/footInc.jsp"%>