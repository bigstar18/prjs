<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>居间客户转移</title>
	</head>
	<body leftmargin="0" topmargin="0">
		<form action="${basePath}/broke/brokerageChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;居间客户转移
							</div>
							<table width="90%" border="0" class="st_bor">
						<tr height="35">
							<td align="right" width="22%">
								居间代码:
							</td>
							<td colspan="3">
								<input type="text" id="brokerageNo" name="obj.brokerageNo" value="${obj.brokerageNo }" class="input_textdmax" readonly=true >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								居间名:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									class="input_textdmax">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								转移居间名称:
							</td>
							<td colspan="3">
								<select name="obj.brokerageNoChange" id="brokerageNoChange" class="select_widmid">
									<option  value="">请选择</option>
									<c:forEach items="${resultList}" var="brokerage" >
										<option value="${brokerage.brokerageNo }">${brokerage.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
				</table>
			</div>
			<div class="tab_pad">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onClick="changeBrokerage()" id="update">
							转移
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
		function changeBrokerage(){
	    	if(frm.brokerageNoChange.value==""){
	    		alert('请选择所要转移的居间！');
	    		return false;
	    	}else if(frm.brokerageNoChange.value==frm.brokerageNo.value){
	    		alert('不能转移给自己，请重新选择！');
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