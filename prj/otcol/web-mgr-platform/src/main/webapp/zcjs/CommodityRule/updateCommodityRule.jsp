<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
	<title></title>
</head>

<body>
        <form id="frm" name="frm" method="POST"  action ="<%=basePath%>commodityRuleController.zcjs?funcflg=update">
        <input type="hidden" name="commodityRuleId" value="${commodityRule.commodityRuleId }" />
        <input type="hidden" name="stamp" value="particularity" />
		<fieldset width="100%">
		<legend>修改商品规则信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td align="right" width="40%">品种&nbsp;</td>
						<td align="left" width="60%">
						<input type="hidden" name="breedId" id="breedId" value="${commodityRule.breedId }"/>
						<c:forEach items="${breedList}" var="breed">
							<c:if test="${commodityRule.breedId eq breed.breedId}">
								<input type="text" name="breedName" value="${breed.breedName }" disabled="disabled"/>
							</c:if>
						</c:forEach>
					</td>
					</tr>	                
	         <tr>
                <td align="right" width="40%">交易商&nbsp;</td>
                <td align="left" width="60%">
						<input name="commodityRuleFirmId" class="normal" id="commodityRuleFirmId" style="width: 68px" value="${commodityRule.commodityRuleFirmId }" readonly="true" />
			  </tr>
			  
			   <tr height="30">
                <td align="right" width="40%">买卖方向&nbsp;</td>
                <td align="left" width="60%">
                 <input type="hidden" id="commodityRuleBusinessDirection" name="commodityRuleBusinessDirection" class="normal" style="width: 68px" value="${commodityRule.commodityRuleBusinessDirection}">
					<c:if test="${commodityRule.commodityRuleBusinessDirection eq 'B'}">
                 	 <input type="text" id="commodityRuleBusinessD" value="买" disabled="disabled"></c:if>
                	<c:if test="${commodityRule.commodityRuleBusinessDirection eq 'S'}">
                	<input type="text" id="commodityRuleBusinessD" value="卖" disabled="disabled"></c:if>
                </td>
			  </tr>
			  <tr height="30">
                <td align="right" width="40%">保证金&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="bail" value="${commodityRule.bail }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  
			  <tr>
						<td align="right">
							保证金方式:
						</td>
						<td align="left">
						<select name="bailMode" id="bailMode">				
			                	<option value="1" selected>百分比</option>
			                	<option value="2" selected>绝对值</option>
							</select>		
						</td>
					</tr>
					 <script>
						frm.bailMode.value = "<c:out value='${commodityRule.bailMode}'/>"
				</script>
			  <tr height="30">
                <td align="right" width="40%">交易手续费&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="tradePoundage" value="${commodityRule.tradePoundage }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr>
						<td align="right">
							交易手续费方式:
						</td>
						<td align="left">
							<select name="tradePoundageMode" id="tradePoundageMode">
			                	<option value="1" selected>百分比</option>
			                	<option value="2" selected>绝对值</option>
							</select>
						</td>
					</tr>
					 <script>
						frm.tradePoundageMode.value = "<c:out value='${commodityRule.tradePoundageMode}'/>"
				</script>
			  <tr height="30">
                <td align="right" width="40%">交收手续费&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="deliveryPoundage" value="${commodityRule.deliveryPoundage }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr>
						<td align="right">
							交收手续费方式:
						</td>
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
			                	<option value="1" selected>百分比</option>
			                	<option value="2" selected>绝对值</option>
							</select>
						</td>
					</tr>
					<script>
						frm.deliveryPoundageMode.value = "<c:out value='${commodityRule.deliveryPoundageMode}'/>"
				</script>
			   <tr height="30">
                <td align="right" width="40%">最高价&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="maxPrice" value="${commodityRule.maxPrice }">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">最低价&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="minPrice" value="${commodityRule.minPrice}">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr height="30">
				<td align="right">商品状态&nbsp;</td>
                <td align="left">
                	<select id="commodityRuleStatus" name="commodityRuleStatus" class="normal" style="width: 68px">
			                	<option value="1" selected>正常</option>
			                	<option value="2" selected>禁止</option>
					 </select>
				 <font color="red">*</font>&nbsp;
                </td>
                <script>
						frm.commodityRuleStatus.value = "<c:out value='${commodityRule.commodityRuleStatus}'/>"
				</script>
			  </tr>
			  </table>
              <tr height="30">
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">修改</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn();">返回</button>
                </div></td>
              </tr>
		</fieldset>
        </form>
</body>
</html>
<script type="text/javascript">
	function doSubmit(){
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
			frm.maxprice.focus();
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
	if(confirm("确定修改特殊商品质量配置么?")){
		frm.submit();
	}
	
}
	function freturn(){
		frm.action = "<%=basePath%>commodityRuleController.zcjs?funcflg=getList";
		frm.submit();
	}
	function   isDigit(s)  
      {  
          var   patrn=/^[0-9]{1,20}$/;  
          if   (!patrn.test(s))   return   false;  
          return   true;  
      }   
</script> 
