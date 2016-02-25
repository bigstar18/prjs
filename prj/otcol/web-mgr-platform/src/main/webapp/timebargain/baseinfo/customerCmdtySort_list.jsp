<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<link href="<c:url value="/timebargain/widgets/extremecomponents/extremecomponents.css"/>" type="text/css" rel="stylesheet">
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/cmdtySort.do?crud=create&funcflg=editCustomer"/>";
}
// -->
</script>
</head>
<body>
<div id="content">
	<ec:table items="customerSortHoldList" var="cmdtySort" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/cmdtySort.do?funcflg=searchCustomer"	
			  autoIncludeParameters="${empty param.autoInc}"
	>
		<ec:exportXls fileName="customerSortHoldList.xls" tooltip="ec.export.tooltip"/>
		<ec:row>
            <ec:column property="checkbox" title="button.choice" filterable="false" sortable="false" viewsAllowed="html" width="20">
                <input type="checkbox" name="itemlist" value="<c:out value="${cmdtySort.customerID}"/>:<c:out value="${cmdtySort.sortID}"/>"/>&nbsp;&nbsp;
            </ec:column>	
            <ec:column property="customerName" title="customerForm.CustomerName" style="text-align:right;">
              <a href="<c:out value="${ctx}"/>/baseinfo/cmdtySort.do?crud=update&method=editCustomer&sortID=<c:out value="${cmdtySort.sortID}"/>&customerID=<c:out value="${cmdtySort.customerID}"/>"><c:out value="${cmdtySort.customerName}"/></a> 
            </ec:column>
			<ec:column property="sortName" title="cmdtySortForm.sortName" style="text-align:right;"/>
			<ec:column property="maxHoldQty" title="cmdtySortForm.maxHoldQty" style="text-align:right;"/>
			<ec:column property="modifyTime" title="cmdtySortForm.modifyTime" cell="date" format="datetime" style="text-align:right;"/>			
		</ec:row>
	</ec:table>
</div>
<div style="text-align: center">
      <button id="addbutton" onclick="add_onclick()" ><fmt:message key="button.add"/></button>  &nbsp;&nbsp;    
      <button id="delbutton" onclick="javascript:batch_do('<fmt:message key="cmdtySortForm.delCustomerbutton"/>','<c:url value="/baseinfo/cmdtySort.do?method=deleteCustomer"/>');" ><fmt:message key="button.delete"/></button>
</div>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/ecchkbox.js"/>"></script>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
