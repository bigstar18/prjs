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
		String id = request.getParameter("id");
		pageContext.setAttribute("date",date);
		pageContext.setAttribute("code",code);
		pageContext.setAttribute("id",id);
	%>
<frameset rows="170,*,0" border="0">    
	<frame name="TopFrame" src="<c:url value="/timebargain/deduct/deductKeepFirm_form.jsp?code=${code}&date=${date}&id=${id}"/>" application="yes">
  <frame name="ListFrame" src="<c:url value="/timebargain/deduct/deduct.do?funcflg=deductKeepFirmList&code=${code}&date=${date}&id=${id}"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
