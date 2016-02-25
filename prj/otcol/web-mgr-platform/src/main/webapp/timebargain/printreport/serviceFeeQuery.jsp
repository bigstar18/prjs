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
	document.forms[0].zEndDate.value = '<%=date%>';
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
		<b>手续费统计表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
      <tr>
        <td align="right">起始日期：</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
      </tr>

	  <tr>
        <td align="right">结束日期：</td>
        <td >
			<MEBS:calendar eltID="zEndDate" eltName="zEndDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
      </tr>
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<input name="Submit22" type="button" class="button" value="查看" onclick="return query('pdf')">
	     		</td>
	     		<td>
				<input name="Submit22" type="button" class="button" value="保存为excel" onclick="return query('excel')">
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
	var zEndDate=document.forms[0].zEndDate.value;
	if (zDate == "") {
		alert("起始日期不能为空！");
		return false;
	}
	if (zEndDate == "") {
		alert("结束日期不能为空！");
		return false;
	}
	if(!isDateFomat(zDate))
    {
        alert("起始日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
    if(!isDateFomat(zEndDate))
    {
        alert("结束日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zEndDate.value = "";
        return false;
    }
    if (zDate > zEndDate) {
		alert("起始日期不能大于结束日期！");
		return false;
	}
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=serviceFee" + "&startClearDate=" + zDate + "&endClearDate=" + zEndDate + "&title=手续费统计表");
		else
	window.showModalDialog("reportContainer.jsp?sign=serviceFee" + "&startClearDate=" + zDate + "&endClearDate=" + zEndDate + "&title=手续费统计表","", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>