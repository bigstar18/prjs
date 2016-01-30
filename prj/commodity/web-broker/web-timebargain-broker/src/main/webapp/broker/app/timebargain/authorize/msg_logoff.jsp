<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<%
String mkName = (String)request.getAttribute("mkName");
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
<script language="javascript">
	var mkName = "<%=mkName%>";
    alert("<%=prompt %>");
    parent.ListFrame.location.href = "${basePath}/timebargain/authorize/chkLogin.action?mkName=" + mkName;
</script>
<%
}
%>