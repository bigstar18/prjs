<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../../../public/session.jsp"%>
<body>
	<form name="frm" method="post" >
	<input type="hidden" name="opt">
	<fieldset>
	<legend>货款处理</legend>
	<br><br>
	<span style="height: 300">
	<table align="center" width="100%" border="0">
		<tr align="center">
			<td align="center" colspan="2">
				<ol><b>说明</b></ol>
				<ol>1、按交收设置中的收货款比例，收取买方货款。</ol>
				<ol>2、按交收设置中的付货款比例，支付卖方货款。</ol>
			</td>
		</tr>
		<tr align="center">
			<td align="right">执行处理：</td>
			<td align="left">
				<input type="button" class="mdlbtn" value="货款处理" onclick="balance()">
			</td>
		</tr>
		<c:if test="${result!=null }">
		<tr align="center">
			<td align="right">执行结果：</td>
			<td align="left">
				<font color="red">${result }</font>
			</td>
		</tr>
		</c:if>
	</table>
	</span>
	<br><br>
	</fieldset>
	</form>
</body>

<script type="text/javascript">
	function balance()
	{
		frm.opt.value="balance";
		if(confirm("确认执行货款处理吗？")){
			frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleBalance";
			frm.submit();
		}
	}
</script>