<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>������ΥԼ����Ϣ</title>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
	
		window.returnValue="2221";
	
		window.close();
	</script>
</c:if>
  
  </head>
  <body>
	<form name="frm" method="post">
		<input type="hidden" name="matchId" value="${settleMatch.matchId }">	
		<br>	
		<fieldset>
			<legend class="common"><b>������ΥԼ����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr>
					<td align="right" width="50%">���������̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��Ʒ���룺</td>
					<td align="left" width="50%">${settleMatch.commodityId }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td>
					<td align="left" width="50%">${settleMatch.weight }</td>
				</tr>
				
				<tr class="common">	
					<td align="right" width="50%">������ΥԼ��</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="incomeFromSeller1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
			</table>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function incomeFromSeller1()
	{
		var incomeFromSeller = frm.thisPayMent.value;
		if(incomeFromSeller.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(incomeFromSeller) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ��������ΥԼ��Ϊ��"+incomeFromSeller+"��")){
	     		submark = true;
	     		frm.opt.value="incomeFromSeller";
	     		frm.subbtn.disabled = true;
	     	}
	     	if(submark){
	     		frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomeFromSeller&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>