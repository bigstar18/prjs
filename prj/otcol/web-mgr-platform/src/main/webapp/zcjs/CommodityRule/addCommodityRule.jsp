<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  

<body>
    <form id="formNew" name="frm" method="POST" action="commodityRuleController.zcjs?funcflg=add" >
		<fieldset width="100%">
		<legend>添加商品规则信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
					<td align="right" width="40%" id="sel">品种&nbsp;</td>
					<td align="left" width="60%">
						<select name="breedId" class="normal" id="breedId" style="width: 68px" >
						<OPTION value="-1">请选择</OPTION>
							<c:forEach items="${breedList}" var="result">
							<option value="${result.breedid }">${result.breedName }</option>
						 	</c:forEach>
				 </select>
					</td>
					</tr>	                
	         <tr>
                <td align="right" width="40%">交易商&nbsp;</td>
                <td align="left" width="60%">
                	<select name="CommodityRuleFirmId" class="normal" id="CommodityRuleFirmId" style="width: 68px" >
						<OPTION value="-1">请选择</OPTION>
							<c:forEach items="${firmPermissionList}" var="result">
							<option value="${result.firmId }">${result.firmId}</option>
						 	</c:forEach>
				 </select>
                </td>
			  </tr>
			  
			   <tr height="30">
                <td align="right" width="40%">买卖方向&nbsp;</td>
                <td align="left" width="60%">
                 <select id="commodityRuleBusinessDirection" name="commodityRuleBusinessDirection" class="normal" style="width: 68px">
					<OPTION value="">请选择</OPTION>
	                <option value="B">买</option>
	      			<option value="S">卖</option>
				 </select>
                </td>
			  </tr>
			  <tr height="30">
                <td align="right" width="40%">保证金&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="bail" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							保证金方式:
						</td>
						<td align="left">
							<select name="bailMode" id="bailMode">
								<option value="1">百分比
								</option>
								<option value="2">绝对值
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						</td>
					</tr>
			  <tr height="30">
                <td align="right" width="40%">交易手续费&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="tradePoundage" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							交易手续费方式:
						</td>
						<td align="left">
						
						<select name="tradePoundageMode" id="tradePoundageMode">
								<option value="1">百分比
								</option>
								<option value="2">绝对值
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						</td>
					</tr>
			  <tr height="30">
                <td align="right" width="40%">交收手续费&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="deliveryPoundage" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							交收手续费方式:
						</td>
						
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
								<option value="1">百分比
								</option>
								<option value="2">绝对值
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						
						</td>
					</tr>
			   <tr height="30">
                <td align="right" width="40%">最高价&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="maxPrice">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">最低价&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="minPrice">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <input type="hidden" id=""commodityRuleStatus"" name="commodityRuleStatus" value="1">
			  </table>
                
             
              <tr height="30">
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn();">返回</button>
                </div></td>
              </tr>
		</fieldset>
   </form>
</body>
</html>
<script type="text/javascript">
	function doSubmit(){
	if(frm.breedId.value == "")
	{
		alert('请输入品种');
		frm.breedId.focus();
		return false;
	}
	if(frm.CommodityRuleFirmId.value == "")
	{
		alert('请输入交易商');
		frm.CommodityRuleFirmId.focus();
		return false;
	}	
	if(frm.commodityRuleBusinessDirection.value == "")
	{
		alert('请输入买卖方向');
		frm.commodityRuleBusinessDirection.focus();
		return false;
	}	
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
	if(confirm("确定添加特殊商品质量配置么?")){
		frm.submit();
	}
	}
	function freturn(){
		frm.action="commodityRuleController.zcjs?funcflg=getList";
		frm.submit();
	}
	  function   isDigit(s)  
      {  
          var   patrn=/^[0-9]{1,20}$/;  
          if   (!patrn.test(s))   return   false;  
          return   true;  
      }   
</script> 
