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
					ϵͳĬ����Ʒ����
				</legend>
				<input type="hidden" name="commodityRuleId" value="${commodityRule.commodityRuleId }">
				<input type="hidden" name="commodityRuleStatus" value="${commodityRule.commodityRuleStatus }">
				<input type="hidden" name="breedId" value="${commodityRule.breedId}">
				<input type="hidden" name="stamp" value="sys">
				<table align="center">
					
					<tr>
						<td align="right">
							��֤��ʽ:
						</td>
						<td align="left">
							<select name="bailMode" id="bailMode">
								<option value="1" <c:if test='${commodityRule.bailMode==1 }'>selected</c:if>>�ٷֱ�
									
								</option>
								<option value="2" <c:if test='${commodityRule.bailMode==2 }'>selected</c:if>>����ֵ
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							��֤��:
						</td>
						<td align="center">
							<input type="text" name="bail" id="bail" value="${commodityRule.bail }"/>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
						<td align="left">
						
						<select name="tradePoundageMode" id="tradePoundageMode">
								<option value="1" <c:if test='${commodityRule.tradePoundageMode==1 }'>selected</c:if>>�ٷֱ�
								</option>
								<option value="2" <c:if test='${commodityRule.tradePoundageMode==2 }'>selected</c:if>>����ֵ
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							����������:
						</td>
						<td align="center">
							<input type="text" name="tradePoundage" id="tradePoundage"
								value="${commodityRule.tradePoundage }" />
						</td>
					</tr>
					
					<tr>
						<td align="right">
							���������ѷ�ʽ:
						</td>
						
							<td align="left">
							<select name="deliveryPoundageMode" id="deliveryPoundageMode">
								<option value="1" <c:if test='${commodityRule.deliveryPoundageMode==1 }'>selected</c:if>>�ٷֱ�
								</option>
								<option value="2" <c:if test='${commodityRule.deliveryPoundageMode==2 }'>selected</c:if>>����ֵ
								</option>
							</select>
						
						</td>
					</tr>
					<tr>
						<td align="right">
							����������:
						</td>
						<td align="center">
							<input type="text" name="deliveryPoundage" id="deliveryPoundage" value="${commodityRule.deliveryPoundage }"  />
						</td>
					</tr>
					<tr>
						<td align="right">
							��Ʒ������Ƽ�:
						</td>
						<td align="center">
							<input type="text" name="maxPrice" id="maxPrice" value="<fmt:parseNumber type="number" value="${commodityRule.maxPrice }" />" />
						</td>
					</tr>
					<tr>
						<td align="right">
							��Ʒ��ͼ�����:
						</td>
						<td align="center">
							<input type="text" name="minPrice" id="minPrice" value="${commodityRule.minPrice }" />
						</td>
						</tr>
				</table>
				<table align="center">
					<tr>
						<td align="center">
							<input type="button" value="�޸�" onclick="update()" />
						</td>
						<td>
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						</td>
						<td align="center">
							<input type="button" value="����" onclick="freturn()" />
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
			frm.maxPrice.focus();
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
	if(confirm("ȷ���޸�ϵͳ��Ʒ��������ô?")){
		frm.submit();
	}
	
}
function freturn(){
		frm.action = "<%=basePath%>commodityRuleController.zcjs?funcflg=getSysCommodityRule";
		frm.submit();
	}
</script>


