<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.base.util.SpringContextHelper"%>
<%@ include file="../public/common.jsp"%>

<base target="_self">
<%
	Map styleNameMap = (Map)SpringContextHelper.getBean("styleNameMap");
	request.setAttribute("styleNameMap",styleNameMap);
%>
<c:if test="${not empty modSuccess}">
	<script>
		window.returnValue = 1;
		window.close();
	</script>
</c:if>
<body>
<form name="frm" id="frm" action="<%=basePath %>/user/commonuserLogout.action">
	<BR>
	<table width="460px" align="center">	
		<tr>
			<td>
		<fieldset width="450px">
		<legend>��������</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" align="center">
			  <tr height="35">	
            	<td align="right"> �û����룺</td>
                <td align="left">
                	<input readonly="readonly" name="userId" type="text" class="text" style="width:80px;" value="${user.userId }">
                </td>
        </tr>       
			  <tr height="35">
            	<td align="right"> ѡ���� ��</td>
                <td align="left">
				<select name="skinstyle">
					<option value="">��ѡ��</option>
					<c:forEach items="${styleNameMap }" var="snMap">
						<option value="${snMap.key }">${snMap.value }</option>
					</c:forEach>
				</select>
				<script>
					frm.skinstyle.value = "<c:out value='${user.skin }'/>";				
				</script>
                </td> 
			  </tr>              
        	</table>
			<BR>
        </span>  
		</fieldset>		              
			  	</td>
			  </tr>
			</table>
		<br>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
			  	<td>&nbsp;</td>
				<td width="300px"><div align="center">
				  <input type="button" name="btn" onClick="return frmChk()" class="btn" value="����">
				  &nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="button" onClick="window.close();" class="btn" value="�ر�">
				</div></td>
				<td><input type="hidden" name="modMark">&nbsp;</td>
			  </tr>
		 </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{	
	if(Trim(frm.skinstyle.value) == ""){
		alert("Ĭ�Ϸ�����ã�");
		}
		frm.modMark.value="mod";
		frm.submit();
		//return true;
}
//-->
</SCRIPT>