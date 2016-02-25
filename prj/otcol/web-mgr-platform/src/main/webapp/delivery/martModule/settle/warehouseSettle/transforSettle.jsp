<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>货权转移</title>
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
			<legend class="common"><b>货权转移</b></legend>
			<span>
			<table class="common" align="center" width="100%">
				<tr class="common">
					<td align="right" width="50%">买方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">卖方交易商代码：</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">交收数量：</td>
					<td align="left" width="50%">${settleMatch.weight }
					  <c:if test="${commodity.countType != null}">
					   (${commodity.countType })
					  </c:if> 
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">已转数量：</td>
					<td align="left" width="50%">${qu } 
					  <c:if test="${commodity.countType != null}">
					   (${commodity.countType })
					  </c:if> 
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">本次转数量：</td>
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
						<input type="button" name="subbtn" class="button" value="提交" onclick="thisTransfor('本次获权转移数量为：','<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleTransfer&dd=');">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="返回" onclick="window.close();">
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
			alert("请输入一个非0正数！");
	        document.getElementById(obj).value = "";
	        frm.quality.focus();
		}
		else
		{
			var qu = frm.quality.value;
			var total=frm.totalQuality.value;
			if(parseFloat(qu)>parseFloat(total)){
				alert("输入数量不合法！");
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
	     	if(confirm(msg+""+quality+"？"))
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
			alert("数量不能为空！");
			frm.quality.focus();
			return;
		}
     	
	}
	</script>
</html>