<%@page contentType="application/vnd.ms-excel;charset=GBK" %>
<html>
    <%
    response.setHeader("Content-disposition","inline; filename=report.xls");
    response.setContentType("application/vnd.ms-excel;charset=GBK");
    response.setCharacterEncoding("GBK");
    //���������趨���͵�ǰ�������ʱ�ĵ���ΪtotalBalance.xls
    //���ǿ���һ�У���ǰ���������Ϊ���յ�һ��excel�� 
    %>
	    
	<head>
	</head>
	<body>
	   <%@ include file="specialMemberTradeReport.jsp"%>
	</body>
</html>
