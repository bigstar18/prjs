<%@page contentType="application/vnd.ms-excel;charset=GBK" %>
<html>
    <%
    response.setHeader("Content-disposition","inline; filename=report.xls");
    response.setContentType("application/vnd.ms-excel;charset=GBK");
    response.setCharacterEncoding("GBK");
    //以上这行设定传送到前端浏览器时的档名为totalBalance.xls
    //就是靠这一行，让前端浏览器以为接收到一个excel档 
    %>
	    
	<head>
	</head>
	<body>
	   <%@ include file="specialMemberTradeReport.jsp"%>
	</body>
</html>
