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
		<legend>�޸���Ʒ������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr height="30">
						<td align="right" width="40%">Ʒ��&nbsp;</td>
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
                <td align="right" width="40%">������&nbsp;</td>
                <td align="left" width="60%">
						<input name="commodityRuleFirmId" class="normal" id="commodityRuleFirmId" style="width: 68px" value="${commodityRule.commodityRuleFirmId }" readonly="true" />
			  </tr>
			  
			   <tr height="30">
                <td align="right" width="40%">��������&nbsp;</td>
                <td align="left" width="60%">
                 <input type="hidden" id="commodityRuleBusinessDirection" name="commodityRuleBusinessDirection" class="normal" style="width: 68px" value="${commodityRule.commodityRuleBusinessDirection}">
					<c:if test="${commodityRule.commodityRuleBusinessDirection eq 'B'}">
                 	 <input type="text" id="commodityRuleBusinessD" value="��" disabled="disabled"></c:if>
                	<c:if test="${commodityRule.commodityRuleBusinessDirection eq 'S'}">
                	<input type="text" id="commodityRuleBusinessD" value="��" disabled="disabled"></c:if>
                </td>
			  </tr>
			  <tr height="30">
                <td align="right" width="40%">��֤��&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="bail" value="${commodityRule.bail }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  
			  <tr>
						<td align="right">
							��֤��ʽ:
						</td>
						<td align="left">
						<select name="bailMode" id="bailMode">				
			                	<option value="1" selected>�ٷֱ�</option>
			                	<option value="2" selected>����ֵ</option>
							</select>		
						</td>
					</tr>
					 <script>
						frm.bailMode.value = "<c:out value='${commodityRule.bailMode}'/>"
				</script>
			  <tr height="30">
                <td align="right" width="40%">����������&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="tradePoundage" value="${commodityRule.tradePoundage }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
						<td align="left">
							<select name="tradePoundageMode" id="tradePoundageMode">
			                	<option value="1" selected>�ٷֱ�</option>
			                	<option value="2" selected>����ֵ</option>
							</select>
						</td>
					</tr>
					 <script>
						frm.tradePoundageMode.value = "<c:out value='${commodityRule.tradePoundageMode}'/>"
				</script>
			  <tr height="30">
                <td align="right" width="40%">����������&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="deliveryPoundage" value="${commodityRule.deliveryPoundage }" style="width: 150px;">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
			                	<option value="1" selected>�ٷֱ�</option>
			                	<option value="2" selected>����ֵ</option>
							</select>
						</td>
					</tr>
					<script>
						frm.deliveryPoundageMode.value = "<c:out value='${commodityRule.deliveryPoundageMode}'/>"
				</script>
			   <tr height="30">
                <td align="right" width="40%">��߼�&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="maxPrice" value="${commodityRule.maxPrice }">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">��ͼ�&nbsp;</td>
                <td align="left" width="60%">
                	<input class="text" name="minPrice" value="${commodityRule.minPrice}">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr height="30">
				<td align="right">��Ʒ״̬&nbsp;</td>
                <td align="left">
                	<select id="commodityRuleStatus" name="commodityRuleStatus" class="normal" style="width: 68px">
			                	<option value="1" selected>����</option>
			                	<option value="2" selected>��ֹ</option>
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
				  <button class="smlbtn" type="button" onclick="doSubmit();">�޸�</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn();">����</button>
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
	if(confirm("ȷ���޸�������Ʒ��������ô?")){
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
