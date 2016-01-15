<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>借方结算日报表</title>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
            <div class="div_list">
			  <table border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td> 

					<ec:table items="pageInfo.result" var="debit"
							  action="${basePath}/finance/financialQuery/listDebitBalanceDayReport.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="debit.xls" csvFileName="debit.csv"
							  showPrint="false" listWidth="100%"
							  minHeight="240"  style="table-layout:fixed;"
						      toolbarContent="refresh|extend"
							  >
					  <ec:row>
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;"/>
					   <%--
					    <ec:column property="bdate" title="结算日期" width="10%" style="text-align:center;" sortable="false">
					      <fmt:formatDate value="${credit.bdate }" pattern="yyyy-MM-dd" />
					    </ec:column>
					     --%>
						<ec:column property="code" title="科目代码" width="10%" style="text-align:center;"  />
					    <ec:column property="account.name" title="科目名称" width="10%" style="text-align:center;" ellipsis="true"  />
						<ec:column property="lastDayBalance" title="期初余额" width="10%" style="text-align:right;" format="#,##0.00"  cell="number"  calc="total" calcTitle= "借方合计" />
						<ec:column property="debitAmount" title="借方发生额" width="10%" style="text-align:right;" format="#,##0.00" cell="number"  calc="total" />
						<ec:column property="creditAmount" title="贷方发生额" width="10%" style="text-align:right;" format="#,##0.00" cell="number"  calc="total" />		  
						<ec:column property="todayBalance" title="期末余额" width="10%"  style="text-align:right;"  format="#,##0.00" cell="number"  calc="total"  />						
					  </ec:row>
					</ec:table>
	
				  </td>
				</tr>
				
			  </table>
            </div>
	
	      </td>
	    </tr>
      </table>
    </div>
  </body>
</html>
