<%@page contentType="application/vnd.ms-excel;charset=GBK" %>

</head>
 <%@ include file="../public/headInc.jsp" %>
<%
	
    response.setHeader("Content-disposition","inline; filename=listTrader.xls");
    response.setContentType("application/vnd.ms-excel;charset=GBK");
    response.setCharacterEncoding("GBK");
    //���������趨���͵�ǰ�������ʱ�ĵ���ΪlistTrader.xls
    //���ǿ���һ�У���ǰ���������Ϊ���յ�һ��excel�� 
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
    <%@ include file="listTraderTable.jsp" %>
</body>