<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>������¼�б�</title>
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
				    <ec:table items="pageInfo.result" var="log"
							  action="${basePath}/timebargain/settle/matchDispose/settleMatchLogList.action?sortColumns=order+by+id"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="settleMatchLogList.xls" csvFileName="settleMatchLogList.csv"
							  showPrint="true" listWidth="100%" 
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column property="operator" title="����Ա" width="10%" style="text-align:center;" />
						<ec:column property="updateTime" title="����ʱ��" width="15%" style="text-align:center;">
						  <fmt:formatDate value="${log.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</ec:column>
						<ec:column property="operateLog" title="�������" width="50%" style="text-align:center;" ellipsis="true" />
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
