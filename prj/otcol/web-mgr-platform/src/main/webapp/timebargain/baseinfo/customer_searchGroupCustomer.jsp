<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>

<title>
��ͻ�
</title>

</head>
<body >
<table width="600">
  <tr><td>
	<ec:table items="customerList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=searchGroupCustomer"	
			autoIncludeParameters="${empty param.autoInc}"
			height="500px" 
			xlsFileName="customer.xls" 
			csvFileName="customer.csv"
			showPrint="true" 
			listWidth="100%"
			pageSizeList="max:2000,5,15,30,50,100,1000,all"
			rowsDisplayed="15" 
			title="�������б�"	
	>
		<ec:row>							
            <ec:column property="firmID" title="������ID" width="100"/>
            <ec:column property="firmName" title="����������" width="130"/>			
			<ec:column property="status" title="customerForm.Status" width="100" editTemplate="ecs_t_status" mappingItem="CUSTOMER_STATUS"/>							
			<ec:column property="groupName" title="customerGroupForm.groupName" width="130"/>
		</ec:row>
		<c:if test="${param['mkName'] != 'customer'}">	
		<ec:extend>			
			<a href="<c:url value="/timebargain/baseinfo/customerGroup.do"/>">�������б�</a>
		</ec:extend>
		</c:if>			
	</ec:table>
</td></tr>
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	

<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
