<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<link href="bg.css" rel="stylesheet" type="text/css">
<%
Calendar cld = Calendar.getInstance();
int h = cld.get(Calendar.HOUR_OF_DAY);
int m = cld.get(Calendar.MINUTE);
int s = cld.get(Calendar.SECOND);
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function adjust()
{
	if(window.parent.frames('main').clock != null)
	window.parent.frames('main').clock.innerHTML = '<%=h<10?"0"+h:""+h%>:<%=m<10?"0"+m:""+m%>:<%=s<10?"0"+s:""+s%>';
}
-->
</SCRIPT>


<META HTTP-EQUIV=Refresh CONTENT="60;URL=">
