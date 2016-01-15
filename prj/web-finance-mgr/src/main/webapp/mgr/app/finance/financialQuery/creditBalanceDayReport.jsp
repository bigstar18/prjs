<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>���������ձ���</title>
    
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
				    <ec:table items="pageInfo.result" var="credit" 
							  action="${basePath}/finance/financialQuery/listCreditBalanceDayReport.action"											
							  autoIncludeParameters="${empty param.autoInc}"	
							  xlsFileName="credit.xls" csvFileName="credit.csv"	 
							  showPrint="true" listWidth="100%"
							  minHeight="240"  style="table-layout:fixed;"
							  toolbarContent="refresh|extend"
							  >
					  <ec:row>
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;"/>
					    <%--
					    <ec:column property="bdate" title="��������" width="10%" style="text-align:center;" sortable="false">
					      <fmt:formatDate value="${credit.bdate }" pattern="yyyy-MM-dd" />
					    </ec:column>
					     --%>
						<ec:column property="code" title="��Ŀ����" width="10%" style="text-align:center;" />
					    <ec:column property="account.name" title="��Ŀ����" width="10%" style="text-align:center;" ellipsis="true" />
						<ec:column property="lastDayBalance" title="�ڳ����" width="10%" style="text-align:right;" format="#,##0.00"  cell="number"  calc="total" calcTitle= "�����ϼ�" />
						<ec:column property="debitAmount" title="�跽������" width="10%" style="text-align:right;" format="#,##0.00" cell="number"  calc="total" />
						<ec:column property="creditAmount" title="����������" width="10%" style="text-align:right;" format="#,##0.00" cell="number"  calc="total" />		  
						<ec:column property="todayBalance" title="��ĩ���" width="10%"  style="text-align:right;" format="#,##0.00" cell="number"  calc="total" />									
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
