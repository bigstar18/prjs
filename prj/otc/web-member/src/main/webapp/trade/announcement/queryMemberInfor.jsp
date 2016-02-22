<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<script type="text/javascript">
	function sub(){
		frm.action="${basePath}/tradeManage/memberInfor/updateMemberInfor.action";
		if(confirm('确定更新?')){
			frm.submit();
		}
	}
</script>
<html>
	<head>
		<title>查看会员信息</title>
	</head>
	<body style="overflow-y: hidden">
		<form action="${basePath}/tradeManage/memberInfor/queryMemberInfor.action" method="post" name="frm" id ="frm">
		<br/><br/><br/><br/><br/><br/><br/>
			<table align="center">
				<tr class="table3_style" >
					<td>会员编号：</td>
					<td><input type="text" class="input_text"  name="memberInfor.memberNo" value="${memberNo}" readonly="readonly" style="width:220px; height=20px;"/></td>
				</tr>
				
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr class="table3_style" > 
					<td>会员介绍：</td>
					<td><textarea rows="5" cols="90" name="memberInfor.notes">${memberInfor.notes}</textarea></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr class="table3_style" >
					<td>模拟连接地址：</td>
					<td><input type="text" class="input_text" style="width:610px; height=20px;" name="memberInfor.hrefaddress" value="${memberInfor.hrefaddress}"></td>
				</tr>
				<tr class="table3_style" >
					<td>正式连接地址：</td>
					<td><input type="text" class="input_text" style="width:610px; height=20px;" name="memberInfor.zshrefaddress" value="${memberInfor.zshrefaddress}"></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="更新" class="btn_sec" onclick="sub()"/></td>
				</tr>
			</table>
		</form>
	</body>

</html>

<%@ include file="/public/footInc.jsp"%>

