<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
var firmID = '<%=request.getParameter("firmID").toString()%>';
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
	if (prompt.equals("��׺����ʽ����ȷ��")) {
%>
	alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>"); 
	document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + firmID
<%
	} else {
%>
	alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>"); 
<%
	}
}
else
{
%>
    alert("�����ɹ���"); 
	document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + firmID
<%
}
%> 
</script>
