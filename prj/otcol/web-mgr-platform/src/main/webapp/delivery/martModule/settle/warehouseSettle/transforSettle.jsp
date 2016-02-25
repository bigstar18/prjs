<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>��Ȩת��</title>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue="-1";
		window.close();
	</script>
</c:if>
  </head>
  <body>
	<form name="frm" method="post">
		<input type="hidden" name="matchId" value="${settleMatch.matchId }">
		<input type="hidden" name="moduleId" value="${settleMatch.moduleId }">
		<fieldset>
			<br>
			<legend class="common"><b>��Ȩת��</b></legend>
			<span>
			<table class="common" align="center" width="100%">
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">���������̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td>
					<td align="left" width="50%">${settleMatch.weight }
					  <c:if test="${commodity.countType != null}">
					   (${commodity.countType })
					  </c:if> 
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��ת������</td>
					<td align="left" width="50%">${qu } 
					  <c:if test="${commodity.countType != null}">
					   (${commodity.countType })
					  </c:if> 
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">����ת������</td>
					<td align="left" width="50%">
						<input type="text" id="quality" name="quality" style="width: 100px" onblur="checkTranForSettle('quality')">
						<font color="red">*</font>
						<input type="hidden" name="totalQuality" value="${total}">
						<c:if test="${commodity.countType != null}">
					     (${commodity.countType })
					    </c:if> 
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="thisTransfor('���λ�Ȩת������Ϊ��','<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleTransfer&dd=');">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
			</table>
				<br>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script language="javascript">
   function checkTranForSettle(obj,msg)
	{
		var quality = document.getElementById(obj).value;
		if(quality!='' &&(quality.search("^\\d+(\\.\\d+)?$")!=0 || parseFloat(quality) == parseFloat(0)))
		{
			alert("������һ����0������");
	        document.getElementById(obj).value = "";
	        frm.quality.focus();
		}
		else
		{
			var qu = frm.quality.value;
			var total=frm.totalQuality.value;
			if(parseFloat(qu)>parseFloat(total)){
				alert("�����������Ϸ���");
				document.getElementById(obj).value = "";
				frm.quality.focus();
			}
		}
	}

	function thisTransfor(msg,forwardUrl)
	{
		var quality = frm.quality.value;
		if(quality!=""){
			var submark = false;
	     	if(confirm(msg+""+quality+"��"))
	     	{
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="submit";
	     		submark = true;
	     	}
	     	if(submark)
	     	{
	     		frm.action=forwardUrl+Date();
	     		frm.submit();
	     	}
		}else{
			alert("��������Ϊ�գ�");
			frm.quality.focus();
			return;
		}
     	
	}
	</script>
</html>