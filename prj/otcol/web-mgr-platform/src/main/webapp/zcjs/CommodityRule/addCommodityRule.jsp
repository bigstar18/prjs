<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  

<body>
    <form id="formNew" name="frm" method="POST" action="commodityRuleController.zcjs?funcflg=add" >
		<fieldset width="100%">
		<legend>�����Ʒ������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
					<td align="right" width="40%" id="sel">Ʒ��&nbsp;</td>
					<td align="left" width="60%">
						<select name="breedId" class="normal" id="breedId" style="width: 68px" >
						<OPTION value="-1">��ѡ��</OPTION>
							<c:forEach items="${breedList}" var="result">
							<option value="${result.breedid }">${result.breedName }</option>
						 	</c:forEach>
				 </select>
					</td>
					</tr>	                
	         <tr>
                <td align="right" width="40%">������&nbsp;</td>
                <td align="left" width="60%">
                	<select name="CommodityRuleFirmId" class="normal" id="CommodityRuleFirmId" style="width: 68px" >
						<OPTION value="-1">��ѡ��</OPTION>
							<c:forEach items="${firmPermissionList}" var="result">
							<option value="${result.firmId }">${result.firmId}</option>
						 	</c:forEach>
				 </select>
                </td>
			  </tr>
			  
			   <tr height="30">
                <td align="right" width="40%">��������&nbsp;</td>
                <td align="left" width="60%">
                 <select id="commodityRuleBusinessDirection" name="commodityRuleBusinessDirection" class="normal" style="width: 68px">
					<OPTION value="">��ѡ��</OPTION>
	                <option value="B">��</option>
	      			<option value="S">��</option>
				 </select>
                </td>
			  </tr>
			  <tr height="30">
                <td align="right" width="40%">��֤��&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="bail" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							��֤��ʽ:
						</td>
						<td align="left">
							<select name="bailMode" id="bailMode">
								<option value="1">�ٷֱ�
								</option>
								<option value="2">����ֵ
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						</td>
					</tr>
			  <tr height="30">
                <td align="right" width="40%">����������&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="tradePoundage" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
						<td align="left">
						
						<select name="tradePoundageMode" id="tradePoundageMode">
								<option value="1">�ٷֱ�
								</option>
								<option value="2">����ֵ
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						</td>
					</tr>
			  <tr height="30">
                <td align="right" width="40%">����������&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="deliveryPoundage" value="" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
						
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
								<option value="1">�ٷֱ�
								</option>
								<option value="2">����ֵ
								</option>
							</select>&nbsp;&nbsp;<font color="red">*</font>&nbsp;
						
						</td>
					</tr>
			   <tr height="30">
                <td align="right" width="40%">��߼�&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="maxPrice">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">��ͼ�&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="minPrice">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <input type="hidden" id=""commodityRuleStatus"" name="commodityRuleStatus" value="1">
			  </table>
                
             
              <tr height="30">
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">���</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn();">����</button>
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
		alert('������Ʒ��');
		frm.breedId.focus();
		return false;
	}
	if(frm.CommodityRuleFirmId.value == "")
	{
		alert('�����뽻����');
		frm.CommodityRuleFirmId.focus();
		return false;
	}	
	if(frm.commodityRuleBusinessDirection.value == "")
	{
		alert('��������������');
		frm.commodityRuleBusinessDirection.focus();
		return false;
	}	
	if(frm.bail.value == "")
	{
		alert('�����뱣֤��');
		frm.bail.focus();
		return false;
	}else if(isNaN(frm.bail.value)){
		alert('��֤�����Ϊ���֣�');
		frm.bail.focus();
		return false;
	}
	if(frm.tradePoundage.value == "")
	{
		alert('�����뽻��������');
		frm.tradePoundage.focus();
		return false;
	}else if(isNaN(frm.tradePoundage.value)){
		alert('���������ѱ���Ϊ���֣�');
		frm.tradePoundage.focus();
		return false;
	}
	if(frm.deliveryPoundage.value == "")
	{
		
		alert('�����뽻��������');
		frm.deliveryPoundage.focus();
		return false;
	}else if(isNaN(frm.deliveryPoundage.value)){
		alert('���������ѱ���Ϊ���֣�');
		frm.deliveryPoundage.focus();
		return false;
	}
	if(frm.maxPrice.value == "")
	{
		alert('��Ʒ������Ƽ۲���Ϊ��');
		frm.maxPrice.focus();
		return false;
	}else if(isNaN(frm.maxPrice.value)){
		alert('��Ʒ������Ƽ۱���Ϊ���֣�');
		frm.maxPrice.focus();
		return false;
	}else if(parseFloat(frm.maxPrice.value) < parseFloat(frm.minPrice.value)) {
			alert("��߼۲��ܵ�����ͼۣ�");
			frm.maxprice.focus();
			return false;
	}
	if(frm.maxPrice.value == "")
	{
		alert('��Ʒ������Ƽ۲���Ϊ��');
		frm.maxPrice.focus();
		return false;
	}else if(isNaN(frm.maxPrice.value)){
		alert('��Ʒ������Ƽ۱���Ϊ���֣�');
		frm.maxPrice.focus();
		return false;
	}	
	if(frm.minPrice.value == "")
	{
		alert('��Ʒ������Ƽ۲���Ϊ��');
		frm.minPrice.focus();
		return false;
	}else if(isNaN(frm.minPrice.value)){
		alert('��Ʒ������Ƽ۱���Ϊ���֣�');
		frm.minPrice.focus();
		return false;
	}		
	if(confirm("ȷ�����������Ʒ��������ô?")){
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
