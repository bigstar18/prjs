<%@page contentType="application/vnd.ms-excel;charset=GBK" %>

</head>
 <%@ include file="/public/session.jsp"%>
<%
	String fileName=(String)request.getAttribute("fileName");
    response.setHeader("Content-disposition","inline; filename="+fileName);
    response.setContentType("application/vnd.ms-excel;charset=GBK");
    response.setCharacterEncoding("GBK");
    //���������趨���͵�ǰ�������ʱ�ĵ���ΪtotalBalance.xls
    //���ǿ���һ�У���ǰ���������Ϊ���յ�һ��excel�� 
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
    <%@ include file="table.jsp" %>
</body>