<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../../../public/session.jsp"%>
<body>
	<form name="frm" method="post" >
	<input type="hidden" name="opt">
	<fieldset>
	<legend>�����</legend>
	<br><br>
	<span style="height: 300">
	<table align="center" width="100%" border="0">
		<tr align="center">
			<td align="center" colspan="2">
				<ol><b>˵��</b></ol>
				<ol>1�������������е��ջ����������ȡ�򷽻��</ol>
				<ol>2�������������еĸ����������֧���������</ol>
			</td>
		</tr>
		<tr align="center">
			<td align="right">ִ�д���</td>
			<td align="left">
				<input type="button" class="mdlbtn" value="�����" onclick="balance()">
			</td>
		</tr>
		<c:if test="${result!=null }">
		<tr align="center">
			<td align="right">ִ�н����</td>
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
		if(confirm("ȷ��ִ�л������")){
			frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleBalance";
			frm.submit();
		}
	}
</script>