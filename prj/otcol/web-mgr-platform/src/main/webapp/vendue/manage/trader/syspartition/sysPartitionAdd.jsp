<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--�û�Ȩ���ж�-->
<!--�ж��û�������İ�����Ƿ����ظ�-->
<c:set var="addFlag" value="true"/>
<%/*
<fun:acl userid="loginID" subject="10"/>*/%>
<!--ȡ�ò���-->
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
%>
<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid=${param.partitionID}">
	<c:set var="addFlag" value="false"/>
</db:select>
<c:choose>
<c:when test="${addFlag=='true'}">
<db:insert table="v_syspartition"
  partitionid="${param.partitionID}"
  engineclass="${param.engineClass}"
  quotationclass="${param.quotationClass}"
  submitactionclass="${param.submitActionClass}"
  validflag="${param.validFlag}"
  description="${param.description}"
/>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("ϵͳ���������ӳɹ���");
gotoUrl("sysPartitionList.jsp");
//-->
</SCRIPT>
</c:when>
<c:otherwise>
<SCRIPT LANGUAGE="JavaScript">
<!--
alert("������İ�����Ѿ�����,����������.");
gotoUrl("sysPartitionAdd.jsp");
//-->
</SCRIPT>
</c:otherwise>
</c:choose>
</c:if>
  <head>
	<title>��Ӱ��</title>
</head>
<body>
<form name=frm id=frm>
		<fieldset width="100%">
		<legend>ϵͳ�������</legend>
		<BR>
		<span>
<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
            	<td align="right"> ����� ��</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'�����')">
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> �����������javaʵ��������� ��</td>
                <td align="left">
                	<input name="engineClass" type="text" class="text" style="width: 180px;">
                </td>
        </tr>
        <tr height="35">
                <td align="right"> �������javaʵ��������� ��</td>
                <td align="left">
                	<input name="quotationClass" type="text" class="text" style="width: 180px;">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> ί����Ӧjavaʵ��������� ��</td>
                <td align="left">
                	<input name="submitActionClass" type="text" class="text" style="width: 180px;">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �Ƿ���ش����������� ��</td>
                <td align="left">
                	<select name="validFlag">
                		 <option value="1">��</option>
                		 <option value="0">��</option>
                  </select>	
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ������� ��</td>
                <td align="left">
                	<input name="description" type="text" class="text" style="width: 180px;">
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
			  <input name="add" type="submit" onclick="return frmChk()" class="btn" value="ȷ��">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="javascript:history.back();" class="btn" value="����">&nbsp;&nbsp;
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
		alert("����Ų���Ϊ�գ�");
		frm.partitionID.focus();
		return false;
	}else if(Trim(frm.engineClass.value) == "")
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
	{
		return true;
	}
}

//-->
</SCRIPT>