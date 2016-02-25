<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title></title>
		<%@ include file="../public/session.jsp"%>
	</head>
	<body>
		<form name="frm" method="post"
			action="<%=basePath%>commodityRuleController.zcjs?funcflg=update">
			<fieldset width="60%">
				<legend> 
					系统默认商品规则
				</legend>
				<input type="hidden" name="commodityRuleId" value="${commodityRule.commodityRuleId }">
				<input type="hidden" name="commodityRuleStatus" value="${commodityRule.commodityRuleStatus }">
				<input type="hidden" name="breedId" value="${commodityRule.breedId}">
				<input type="hidden" name="stamp" value="sys">
				<table align="center">
					
					<tr>
						<td align="right">
							保证金方式:
						</td>
						<td align="left">
							<select name="bailMode" id="bailMode">
								<option value="1" <c:if test='${commodityRule.bailMode==1 }'>selected</c:if>>百分比
									
								</option>
								<option value="2" <c:if test='${commodityRule.bailMode==2 }'>selected</c:if>>绝对值
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							保证金:
						</td>
						<td align="center">
							<input type="text" name="bail" id="bail" value="${commodityRule.bail }"/>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							交易手续费方式:
						</td>
						<td align="left">
						
						<select name="tradePoundageMode" id="tradePoundageMode">
								<option value="1" <c:if test='${commodityRule.tradePoundageMode==1 }'>selected</c:if>>百分比
								</option>
								<option value="2" <c:if test='${commodityRule.tradePoundageMode==2 }'>selected</c:if>>绝对值
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							交易手续费:
						</td>
						<td align="center">
							<input type="text" name="tradePoundage" id="tradePoundage"
								value="${commodityRule.tradePoundage }" />
						</td>
					</tr>
					
					<tr>
						<td align="right">
							交收手续费方式:
						</td>
						
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
								<option value="1" <c:if test='${commodityRule.deliveryPoundageMode==1 }'>selected</c:if>>百分比
								</option>
								<option value="2" <c:if test='${commodityRule.deliveryPoundageMode==2 }'>selected</c:if>>绝对值
								</option>
							</select>
						
						</td>
					</tr>
					<tr>
						<td align="right">
							交收手续费:
						</td>
						<td align="center">
							<input type="text" name="deliveryPoundage" id="deliveryPoundage" value="${commodityRule.deliveryPoundage }"  />
						</td>
					</tr>
					<tr>
						<td align="right">
							商品最高限制价:
						</td>
						<td align="center">
							<input type="text" name="maxPrice" id="maxPrice" value="<fmt:parseNumber type="number" value="${commodityRule.maxPrice }" />" />
						</td>
					</tr>
					<tr>
						<td align="right">
							商品最低价限制:
						</td>
						<td align="center">
							<input type="text" name="minPrice" id="minPrice" value="${commodityRule.minPrice }" />
						</td>
						</tr>
				</table>
				<table align="center">
					<tr>
						<td align="center">
							<input type="button" value="修改" onclick="update()" />
						</td>
						<td>
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						</td>
						<td align="center">
							<input type="button" value="重置" onclick="freturn()" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>

	</body>
</html>
<script type="text/javascript">
function update(){
if(frm.bail.value == "")
	{
		alert('请输入保证金');
		frm.bail.focus();
		return false;
	}else if(isNaN(frm.bail.value)){
		alert('保证金必须为数字！');
		frm.bail.focus();
		return false;
	}
	if(frm.tradePoundage.value == "")
	{
		alert('请输入交易手续费');
		frm.tradePoundage.focus();
		return false;
	}else if(isNaN(frm.tradePoundage.value)){
		alert('交易手续费必须为数字！');
		frm.tradePoundage.focus();
		return false;
	}
	if(frm.deliveryPoundage.value == "")
	{
		
		alert('请输入交收手续费');
		frm.deliveryPoundage.focus();
		return false;
	}else if(isNaN(frm.deliveryPoundage.value)){
		alert('交收手续费必须为数字！');
		frm.deliveryPoundage.focus();
		return false;
	}
	if(frm.maxPrice.value == "")
	{
		alert('商品最高限制价不能为空');
		frm.maxPrice.focus();
		return false;
	}else if(isNaN(frm.maxPrice.value)){
		alert('商品最高限制价必须为数字！');
		frm.maxPrice.focus();
		return false;
	}else if(parseFloat(frm.maxPrice.value) < parseFloat(frm.minPrice.value)) {
			alert("最高价不能低于最低价！");
			frm.maxPrice.focus();
			return false;
	}
	if(frm.maxPrice.value == "")
	{
		alert('商品最高限制价不能为空');
		frm.maxPrice.focus();
		return false;
	}else if(isNaN(frm.maxPrice.value)){
		alert('商品最高限制价必须为数字！');
		frm.maxPrice.focus();
		return false;
	}	
	if(frm.minPrice.value == "")
	{
		alert('商品最低限制价不能为空');
		frm.minPrice.focus();
		return false;
	}else if(isNaN(frm.minPrice.value)){
		alert('商品最低限制价必须为数字！');
		frm.minPrice.focus();
		return false;
	}		
	if(confirm("确定修改系统商品质量配置么?")){
		frm.submit();
	}
	
}
function freturn(){
		frm.action = "<%=basePath%>commodityRuleController.zcjs?funcflg=getSysCommodityRule";
		frm.submit();
	}
</script>


