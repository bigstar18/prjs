<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>


<!--ȡ�ò���-->
<html>
  <head>
	<title></title>
</head>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
  	<db:update table="v_curcommodity"
    tradepartition="${param.tradePartition}"
    lpflag="${param.lpFlag}"
    modifytime="<%=sqlDate%>"
    where="code='${param.code}'"
    />
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("��ǰ��Ʒ��Ϣ�޸ĳɹ���");
//window.close();
//-->
</SCRIPT>
</c:if>
<body>
<form name=frm id=frm method="post" action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>�޸ĵ�ǰ������Ʒ��Ϣ</legend>
		<BR>
		<span>
		<db:select var="row" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${param.code}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35">
                <td align="right"> ��ĺ� ��</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 180px;" value="${row.code}" readonly>
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> ���װ�� ��</td>
                <td align="left">
                	<select name="tradePartition">
                		 <option value="1">����</option>
                		 <option value="2">�Ϻ�</option>
                  </select>	
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> �Ƿ����� ��</td>
                <td align="left">
                	<select name="lpFlag">
                		 <option value="0">��</option>
                		 <option value="1">��</option>
                  </select>
                </td>
         </tr>
        	</table>
			<BR>
        </span>
		</db:select>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
        <input type="hidden" name="add">
			  <input type="submit" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{ if(Trim(frm.tradePartition.value) == "")
	{
		alert("����ģ�岻��Ϊ�գ�");
		frm.tradePartition.focus();
		return false;
	}
	else 
	{ frm.add.value="true";
		return true;
	}
}

//-->
<!--
function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}
//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>