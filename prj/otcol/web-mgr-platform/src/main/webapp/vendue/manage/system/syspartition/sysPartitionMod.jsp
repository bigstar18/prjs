<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--ȡ�ò���-->
  <head>
	<title></title>
</head>
<c:if test="${not empty param.add}">
<%
  java.util.Date curdate = new java.util.Date();
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
   <db:update table="v_syspartition"
    engineclass="${param.engineClass}"
    quotationclass="${param.quotationClass}"
    submitactionclass="${param.submitActionClass}"
    validflag="${param.validFlag}"
    where="partitionid=${param.partitionID}"
    />
	 <SCRIPT LANGUAGE="JavaScript">
	  alert("�����Ϣ�޸ĳɹ���");
	  window.returnValue="1";
	  window.close();
     </script>
</c:if>
<body>
<form name=frm method="post" id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>�޸İ��������Ϣ</legend>
		<BR>
		<span>
		<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid='${param.id}'">
			<input name="engineClass" type="hidden" value="${row.engineclass}">
			<input name="quotationClass" type="hidden" value="${row.quotationclass}">
			<input name="submitActionClass" type="hidden" value="${row.submitactionclass}">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
            <input name="partitionID" type="hidden" value="${row.partitionid}">
        	<!-- 
        	<tr height="35">
              <td align="right"> ������� ��</td>
              <td align="left">
              <input name="description" type="text" class="text" style="width: 180px;" value="${row.description}">
              </td>
              </tr>
			  <tr height="35">
              <td align="right"> ����ģʽ ��</td>
              <td align="left">
              <select name="tradeMode">
              <option value="0" <c:if test="${row.trademode==0}"><c:out value="selected"/></c:if>>����</option>
              <option value="1" <c:if test="${row.trademode==1}"><c:out value="selected"/></c:if>>����</option>
			  <option value="2" <c:if test="${row.trademode==2}"><c:out value="selected"/></c:if>>�б�</option>
              </select>
              </td>
              </tr>
               -->
              <tr height="35">
              <td align="right" width="57%"> �����������ݣ�</td>
              <td align="left">
              <select name="validFlag">
              <option value="1" <c:if test="${row.validflag==1}"><c:out value="selected"/></c:if>>��</option>
              <option value="0" <c:if test="${row.validflag==0}"><c:out value="selected"/></c:if>>��</option>
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
			  <input type="submit" onclick="return frmChk()" class="btn" value="ȷ��">&nbsp;&nbsp;
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
{ 
	if(Trim(frm.partitionID.value) == "")
	{
		alert("���Ų���Ϊ�գ�");
		frm.partitionID.focus();
		return false;
	}
	else if(Trim(frm.engineClass.value) == "")
	{
		alert("������������javaʵ��������Ʋ���Ϊ�գ�");
		frm.engineClass.focus();
		return false;
	}else if(Trim(frm.quotationClass.value) == "")
	{
		alert("��������javaʵ��������Ʋ���Ϊ�գ�");
		frm.quotationClass.focus();
		return false;
	}else if(Trim(frm.submitActionClass.value) == "")
	{
		alert("ί����Ӧ��javaʵ��������Ʋ���Ϊ�գ�");
		frm.submitActionClass.focus();
		return false;
	}else if(Trim(frm.validFlag.value) == "")
	{
		alert("�Ƿ�����������ݲ���Ϊ�գ�");
		frm.validFlag.focus();
		return false;
	}
	else 
	{ frm.add.value="true";
		return true;
	}
}

//-->
</SCRIPT>
<%@ include file="../../public/footInc.jsp" %>