<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 

<%
   	Date sysdate = new Date();
	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
	String date = df.format(sysdate);
	System.out.println("date:"+date);
%>

<html xmlns:MEBS>
<head>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>
<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar2.htc"/>">

<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
function window_onload(){
	highlightFormElements();
	document.forms[0].zDate.value = '<%=date%>';
}
</script>
<script language="javascript" for="document" event="onkeydown">
<!--
  if(event.keyCode==13)
     query();
-->
</script>

</head>

<body  onload="return window_onload()">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>ÿ�����������</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
      <tr>
        <td align="right">��ѯ���ڣ�</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
      </tr>

	
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<input name="Submit22" type="button" class="button" value="�鿴" onclick="return query('pdf')">
	     		</td>
	     		<td>
				<input name="Submit22" type="button" class="button" value="����Ϊexcel" onclick="return query('excel')">
	     		</td>
		</tr>
	</table>
	</fieldset>
	</td>
	</tr>
</table>
</FORM>
</body>

</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function query(exportTo)
{

	var zDate=document.forms[0].zDate.value;
	
	if (zDate == "") {
		alert("��ѯ���ڲ���Ϊ�գ�");
		return false;
	}
	if(!isDateFomat(zDate))
    {
        alert("��ѯ���ڸ�ʽ����ȷ��\n�磺" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
	
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=dayHQ&cleardate=" + zDate + "&title=ÿ�����������");
		else 
	window.showModalDialog("reportContainer.jsp?sign=dayHQ" + "&cleardate=" + zDate + "&title=ÿ�����������","", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>