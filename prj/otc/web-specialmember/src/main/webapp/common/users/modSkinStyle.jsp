<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<body>
<form name="frm" id="frm" method ="post"  action="<%=commonUserControllerPath %>commonUserModStyle">
		<fieldset width="100%">
		<legend>�޸ķ��</legend>
		<BR>
		<span>		
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> �û����� ��</td>
            <td align="left">
            	<input name="userId" type="text" class="input_text_mid" value="${user.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> �û����� ��</td>
            <td align="left">
            	<input name="name" type="text" class="input_text_mid" value="${user.name }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
            <td align="right"> ѡ���� ��</td>
            <td align="left">
            	<select name="skinStyle">
            		<c:forEach items="${skinMap }" var="skin">
            			<option value="${skin.key }">${skin.value }</option>
            		</c:forEach>
            	</select>
            	<script type="text/javascript">
            		frm.skinStyle.value = "<c:out value='${user.skin }'/>";
            	</script>
            </td>
        </tr>
  	</table>
		<BR>
        </span>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
          	<input type="hidden" name="modMark">
			  <input type="button" name="btn" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close();" class="btn" value="�ر�">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{	
	if(Trim(frm.skinStyle.value) == ""){
		alert("Ĭ�Ϸ�����ã�");
		}
		frm.modMark.value="mod";
		frm.submit();
		//return true;
}
//-->
</SCRIPT>