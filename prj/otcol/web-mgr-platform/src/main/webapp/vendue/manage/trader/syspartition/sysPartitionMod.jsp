<%@ page contentType="text/html;charset=GBK" %>

<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<!--�û�Ȩ���ж�-->
<%/*
<fun:acl userid="loginID" subject="10"/>*/%>
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
    description="${param.description}"
    where="partitionid=${param.partitionID}"
    />
	 <SCRIPT LANGUAGE="JavaScript">
	 	<!--
	  alert("�����Ϣ�޸ĳɹ���");
	  //-->
   </script>
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden" callback='closeRefreshDialog();'>
		<fieldset width="100%">
		<legend>�޸İ��������Ϣ</legend>
		<BR>
		<span>
		<db:select var="row" table="v_syspartition" columns="<%=COLS_SYSPARTITION%>" where="partitionid='${param.id}'">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td align="right"> ����� ��</td>
                <td align="left">
                	<input name="partitionID" type="text" class="text" style="width: 180px;" value="${row.partitionid}" readonly>
                </td>
        </tr>
				<tr height="35">
                <td align="right"> �����������javaʵ��������� ��</td>
                <td align="left">
                  <input name="engineClass" type="text" class="text" style="width: 180px;" value="${row.engineclass}">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> �������javaʵ��������� ��</td>
                <td align="left">
                	<input name="quotationClass" type="text" class="text" style="width: 180px;" value="${row.quotationclass}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ί����Ӧjavaʵ��������� ��</td>
                <td align="left">
                	<input name="submitActionClass" type="text" class="text" style="width: 180px;" value="${row.submitactionclass}">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �������� ��</td>
                <td align="left">
                	<select name="validFlag">
                		<option value="1" <c:if test="${row.validflag==1}"><c:out value="selected"/></c:if>>��</option>
                		<option value="0" <c:if test="${row.validflag==0}"><c:out value="selected"/></c:if>>��</option>
                	</select>
                </td>
               </tr>
               <tr height="35">
                <td align="right"> �������� ��</td>
                <td align="left">
                	<input name="description" type="text" class="text" style="width: 180px;" value="${row.description}">
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
	{ frm.add.value="true";
		return true;
	}
}

//-->
</SCRIPT>
<%@ include file="/vendue/manage/public/footInc.jsp" %>