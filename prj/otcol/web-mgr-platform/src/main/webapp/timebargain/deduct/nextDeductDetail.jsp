<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>

	<%
		String date = request.getParameter("date");
		String code = request.getParameter("code");
		String qty = request.getParameter("qty");
		String id = request.getParameter("id");
		if (qty == null && "".equals(qty)) {
			qty = "0";
		}
		pageContext.setAttribute("date", date);
		pageContext.setAttribute("code", code);
		pageContext.setAttribute("qty", qty);
		pageContext.setAttribute("id",id);
	%>
<frameset rows="90,*,0" border="0">    
	<frame name="TopFrame" src="<c:url value="/timebargain/deduct/topDeductDetail.jsp?date=${date}&code=${code}&qty=${qty}&id=${id}"/>" application="yes">
  <frame name="ListFrame" src="<c:url value="/timebargain/deduct/deduct.do?funcflg=nextDeductDetail&date=${date}&code=${code}&id=${id}"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
