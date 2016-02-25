<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>
<base target="_self">
<c:if test="${not empty resultMsg }">
	<script language="JavaScript">
		window.returnValue="1";
		window.close();
	</script>
</c:if>
<script>
function saveNote()
{
	formNew.submit();
}
</script>
<body>
        <form name="formNew" action="<%=basePath%>/brokerController.mem?funcflg=brokerRefuseFirm" method="POST">
		<fieldset width="100%">
		<legend>驳回原因</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="left">
                	<textarea name="note" rows="5" cols="50"></textarea>
					<font color="#ff0000">*</font>
                </td>
                </tr>
                <tr>
                <td align="center">
                <button class="smlbtn" onclick="saveNote()">提交</button>&nbsp;&nbsp;
                <button class="smlbtn" onclick="javascript:window.close();">关闭</button>
                </td>
                </tr>
          </table>
		</fieldset>
		<input type="hidden" name="firmIds" value="${firmIds }">
        </form>
</body>