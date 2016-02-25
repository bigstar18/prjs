<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
String firmID = (String)request.getAttribute("firmID");
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    document.location.href = "${pageContext.request.contextPath}"+"/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="+"<%=firmID%>";
    window.close();
</script>
