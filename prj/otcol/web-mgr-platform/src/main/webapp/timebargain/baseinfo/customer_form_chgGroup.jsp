<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");

%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");     
    //parent.TopFrame.query_onclick();
    <%if("delete_KH".equals(request.getAttribute("customer_Type"))){
	%>
	var firmID = '<%=request.getParameter("firmID").toString()%>';
	document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + firmID
	<%}else{%>
    document.location.href = "<c:url value="/timebargain/menu/Firm.do"/>";
	<%}%>
</script>
