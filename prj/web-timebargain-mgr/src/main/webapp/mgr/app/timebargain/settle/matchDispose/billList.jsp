<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>��Գֲ���Ϣ</title>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		    <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="bill"
							  action="${basePath}/timebargain/settle/matchDispose/billList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="billList.xls" csvFileName="billList.csv"
							  showPrint="true" listWidth="100%" 
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column property="STOCKID"  alias="remarkHdd" width="15%" title="�ֵ���" style="text-align:center;" />
						<ec:column property="WAREHOUSEID" title="�ֿ���" width="20%" style="text-align:center;"/>
						<ec:column property="BREEDNAME" title="Ʒ������" width="15%" style="text-align:center;"/>
						<ec:column property="QTYUNIT" title="��Ʒ����" width="15%" style="text-align:center;">
							${bill.QUANTITY }${bill.UNIT }
						</ec:column>
						<ec:column property="STOCKNUM" title="�ֵ�����" width="15%" style="text-align:center;"/>
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
