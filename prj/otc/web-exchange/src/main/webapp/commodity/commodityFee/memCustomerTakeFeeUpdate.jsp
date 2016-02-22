<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员管辖客户收取手续费</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}//commodity/memCustomerTakeFee/update.action" method="post" targetType="hidden">	
			<div>
			 <%@ include file="/commodity/commodityFee/memCustomerTakeFeeUpdateTable.jsp"%>
				</div>
				<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateTakeFeeStatus()" id="update">修改</button>
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
	function updateTakeFeeStatus(){
		if(parseFloat(frm.feeRate.value)<parseFloat(frm.mkt_FeeRate.value))
	   {
		    alert("交易所收取手续费必须小于手续费系数");
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