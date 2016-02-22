<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/brokerageChange/update.action" name="frm"
			method="post" targetType="hidden">
			<div class="div_scro">
				<table border="0" height="300" width="90%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;居间转移
							</div>
							<table width="90%" border="0" class="st_bor">
						<tr height="35">
							<td align="right">
								居间代码:
							</td>
							<td colspan="3">
								<input type="text" id="brokerageNo" name="obj.brokerageNo" value="${obj.brokerageNo }" style="width: 300" readonly=true >
							</td>
							
						</tr>
						<tr height="35">
							<td align="right">
								居间名:
							</td>
							<td colspan="3">
								<input type="text" name="obj.name" id="name" value="${obj.name}"
									style="width: 300">
							</td>
						</tr>
												<tr height="35">
							<td align="right">
								转移居间名称:
							</td>
							<td colspan="3">
								<select name="obj.brokerageNoChange" id="brokerageNoChange">
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
			<table cellspacing="0" cellpadding="0" border="0" width="90%"
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
			frm.submit();
		}
	</script>
<%@ include file="/public/footInc.jsp"%>