<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա��Ͻ�ͻ���ȡ������</title>
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
								<button  class="btn_sec" onClick="updateTakeFeeStatus()" id="update">�޸�</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onClick="window.close()">�ر�</button>
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
		    alert("��������ȡ�����ѱ���С��������ϵ��");
		    return false;
	   }
	   var vaild = window.confirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }else{
           return false;
	    }
	}


</script>
<%@ include file="/public/footInc.jsp"%>