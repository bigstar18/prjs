<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>����Ա�б�</title>
    <script type="text/javascript">
      // ���ؽ������б�
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //��ȡ������תURL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }

	
	  // ��ת������ԱȨ��
	  function updateForwardPrivilege(id) {
		  //��ȡ����Ȩ�޵� URL
		  var updateUrl = "/timebargain/firmSet/tradePrivilege/listTraderPrivilege.action";
		  //��ȡ������תURL
		  var url = "${basePath}"+updateUrl;
		  //�� URL ��Ӳ���
		  url += "?traderID="+id + "&firmID=" + '${firmID}';

		  document.location.href = url;
	  }

	  // ��ת�����ͻ���Ϣ
	  function viewCode(id){
		
		//��ȡ����Ȩ�޵� URL
		var updateUrl = "${basePath}/timebargain/firmSet/tradePrivilege/viewCode.action";
		//��ȡ������תURL
		var url = updateUrl;
		//�� URL ��Ӳ���
		url += "?traderID="+id + "&firmID=" + '${firmID}';

		document.location.href = url;
	  }

    </script>
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
				    <ec:table items="pageInfo.result" var="trader"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listTrader.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="trader.xls" csvFileName="trader.csv"
							  showPrint="true" listWidth="100%" title="������ ���룺${firmID}"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
				        <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="traderID" title="����Ա����" ellipsis="true" width="20%" style="text-align:center;" >
						</ec:column>
						<ec:column property="name" title="����Ա����" width="20%" style="text-align:center;" >
						</ec:column>
						<ec:column property="prop" title="���������ͻ�" width="10%" ellipsis="true" style="text-align:center;" sortable="false"  tipTitle="��������ͻ���Ϣ" >
						 <%--  <a href="#" onclick="viewCode('<c:out value="${trader.traderID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/man.gif"/>"></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/man.gif'/>" href="#" onclick="viewCode('${trader.traderID}')" action="/timebargain/firmSet/tradePrivilege/viewCode.action"/>
						</ec:column>
						<ec:column property="prop" title="����ԱȨ��" width="10%" style="text-align:center;" sortable="false"  tipTitle="�鿴Ȩ��" >
						 <%--  <a href="#" onclick="updateForwardPrivilege('<c:out value="${trader.traderID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${trader.traderID}')" action="/timebargain/firmSet/tradePrivilege/listTraderPrivilege.action"/>
						</ec:column>
						
					  </ec:row>
					  
					  <ec:extend >
  			            <%-- <a href="#" onclick="goback()">���ؽ������б�</a>--%>
  			            <rightHyperlink:rightHyperlink href="#" text="���ؽ������б�" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/tradePrivilege.action" />
  		              </ec:extend>
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
