<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
  
<head>
   <title>指定转让委托</title>
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
												<ec:column property="ordersModel.consignerId" width="10%" title="代为委托员代码" style="text-align:center;"/>
												<ec:column property="holdNo" title="订货单号" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.orderNo" title="委托单号" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.commodityId" title="商品代码" width="10%" style="text-align:center;"/>
												<ec:column property="ordersModel.price" title="委托价格" width="10%" style="text-align:center;">
												     <fmt:formatNumber value="${specFrozenHold.ordersModel.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenQty" title="冻结数量" width="10%" style="text-align:center;"/>
											</ec:row>
										</ec:table>
  </td>
</tr>
</table>	

</body>
</html>