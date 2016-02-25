<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@page import="gnnt.MEBS.member.broker.services.BrokerService"%>
<%@page import="gnnt.MEBS.member.broker.model.Broker"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gnnt.MEBS.member.broker.util.SysData"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="gnnt.MEBS.base.query.QueryConditions"%>
<%@page import="gnnt.MEBS.base.query.QueryHelper"%>
<%@page import="gnnt.MEBS.base.query.PageInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
	String chuLiMethod=request.getParameter("chuLiMethod");
	QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
	PageInfo pageInfo = null;
	if (pageInfo == null) {
		pageInfo = new PageInfo(1, Integer.MAX_VALUE, "brokerId", false);
	}
	BrokerService brokerService = (BrokerService) SysData
			.getBean("m_brokerService");
	List<Broker> list = new ArrayList<Broker>();
	
	String c = request.getParameter("qc"); 
	if(c != null){
		if(chuLiMethod.equals("1")){
			list = brokerService.getBrokerLists(qc, null);  // ������ѯ
		}else{
			list = brokerService.getBrokerLists(qc, pageInfo);  // ������ѯ
		}
	}else{
		if(chuLiMethod.equals("1")){
			list = brokerService.getBrokerList(qc, null);
		}else{
			list = brokerService.getBrokerList(qc, pageInfo);
		}
	}
	
	request.setAttribute("resultList", list);
	
	String reportName = null;
 	SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd HHmm");
	String datetime = tempDate.format(new java.util.Date());

	reportName = URLEncoder.encode("�������б�","UTF-8");
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition","attachment; filename="+ reportName +datetime +".xls");
%>
<html>
  <head>
	<title>ϵͳ�û����</title>
</head>
<body>
<form method='post' name=frm_query>
		<br>
		<table>
			<tr>
				<td colspan="11" align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="11" align="center"><h1 align="center">��������Ϣ�б�</h1></td>
			</tr>
			<tr>
				<td colspan="11" align="center">&nbsp;</td>
			</tr>
		</table>
		<table id="tb" border="1" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  			    <td class="panel_tHead_LB">&nbsp;</td>
			        <td class="panel_tHead_MB" align=left>�����̱�� </td>
			        <td class="panel_tHead_MB" align=left>�������˺� </td>
					<td class="panel_tHead_MB" align=left>���� </td>
			        <td class="panel_tHead_MB" align=left>�绰</td>
			        <td class="panel_tHead_MB" align=left>�ֻ�</td>
			        <td class="panel_tHead_MB" align=left>�����ʼ�</td>
				    <td class="panel_tHead_MB" align=left>��ַ</td>
				    <td class="panel_tHead_MB" align=left>��������</td>
				    <td class="panel_tHead_MB" align=left>�г�������Ա </td>
				    <td class="panel_tHead_MB" align=left>����ʡ��</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:forEach items="${resultList}" var="result">
					<tr onclick="selectTr();" align=center height="25">
							<td class="panel_tBody_LB">&nbsp;</td>
							<td class="underLine" align=left>&nbsp;${result.brokerid}</td>
							<td class="underLine" align=left>&nbsp;${result.firmId}</td>
							<td class="underLine" align=left>&nbsp;${result.name}</td>
							<td class="underLine" align=left>&nbsp;${result.telephone}</td>
							<td class="underLine" align=left>&nbsp;${result.mobile}</td>
							<td class="underLine" align=left>&nbsp;${result.email}</td>
							<td class="underLine" align=left>&nbsp;${result.address}</td>
							<td class="underLine" align=left>&nbsp;
							<c:if test="${result.createDate!=null}">
							<fmt:formatDate value="${result.createDate}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							</td>
							<td class="underLine" align=left>&nbsp;${result.marketManager}</td>
							<td class="underLine" align=left>&nbsp;${result.locationProvince}</td>
							<td class="panel_tBody_RB">&nbsp;</td>
						</tr>
							</c:forEach>
		  	</tBody>			
		</table>
</form>
</body>
</html>