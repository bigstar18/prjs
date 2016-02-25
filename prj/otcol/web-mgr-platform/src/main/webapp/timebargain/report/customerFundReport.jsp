<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.ReportManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.QueryConditions"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%

ReportManager mgr = (ReportManager) SysData.getBean("reportManager");
QueryConditions qc = QueryUtil.getQueryConditionsFromRequest(request);
java.util.List lst = mgr.getFunds(qc);
System.out.println("lst:" + lst.size());
request.setAttribute("fundList", lst);
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<title>
		</title>
		
	</head>

<body leftmargin="0" topmargin="0">

		<c:forEach items="${fundList}" var="fund">
			<table border="1" style="BORDER-RIGHT: 1px; BORDER-TOP: 1px; MARGIN: 1px; BORDER-LEFT: 1px; WIDTH: 400px; COLOR: blue; BORDER-BOTTOM: 1px; BORDER-COLLAPSE: collapse; TEXT-ALIGN: center" borderColor="#000000" align="center" cellSpacing="0" cellPadding="0" class="common">
			<caption><b><font size="2">�ʽ�����</font></b></caption>
			
			<tr>
				<td class="right">
					��������
				</td>
				<td>
				<fmt:formatDate value="${fund.ClearDate}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td class="right">
					�ͻ�ID
				</td>
				<td>
					<c:out value="${fund.CustomerID}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					�ʽ����
				</td>
				<td>
					<c:out value="${fund.Balance}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					���㱣֤��
				</td>
				<td>
					<c:out value="${fund.ClearMargin}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					���㸡��
				</td>
				<td>
					<c:out value="${fund.ClearFL}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					����������
				</td>
				<td>
					<c:out value="${fund.TradeFee}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					ƽ��ӯ��
				</td>
				<td>
					<c:out value="${fund.Close_PL}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					��ͽ��㱣֤��
				</td>
				<td>
					<c:out value="${fund.MinClearDeposit}"/>
				</td>
			</tr>
			<tr>
				<td class="right">
					Ӧ׷���ʽ�
				</td>
				<td>
					<c:out value="${fund.shouldAddFund}"/>
				</td>
			</tr>
		</table>
		</c:forEach>
		
		

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
