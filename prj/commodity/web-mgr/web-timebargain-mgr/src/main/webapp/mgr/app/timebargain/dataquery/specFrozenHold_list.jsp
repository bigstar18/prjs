<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  
<head>
   <title>ָ��ת��ί��</title>
</head> 
<body>
<table width="100%">
  <tr>
    <td>
	<ec:table items="pageInfo.result" var="specFrozenHold"
											action="${basePath}/timebargain/dataquery/listSpecFrozenHold.action?sortColumns=order+by+id+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="ordersModel.consignerId" width="10%" title="��Ϊί��Ա����" style="text-align:center;"/>
												<ec:column property="holdNo" title="��������" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.orderNo" title="ί�е���" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.commodityId" title="��Ʒ����" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.price" title="ί�м۸�" width="10%" style="text-align:center;">
												     <fmt:formatNumber value="${specFrozenHold.ordersModel.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenQty" title="��������" width="10%" style="text-align:center;"/>
											</ec:row>
										</ec:table>
  </td>
</tr>
</table>	

</body>
</html>