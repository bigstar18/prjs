<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交易员列表</title>
    <script type="text/javascript">
      // 返回交易商列表
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //获取完整跳转URL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }

	
	  // 跳转交易商员权限
	  function updateForwardPrivilege(id) {
		  //获取配置权限的 URL
		  var updateUrl = "/timebargain/firmSet/tradePrivilege/listTraderPrivilege.action";
		  //获取完整跳转URL
		  var url = "${basePath}"+updateUrl;
		  //给 URL 添加参数
		  url += "?traderID="+id + "&firmID=" + '${firmID}';

		  document.location.href = url;
	  }

	  // 跳转二级客户信息
	  function viewCode(id){
		
		//获取配置权限的 URL
		var updateUrl = "${basePath}/timebargain/firmSet/tradePrivilege/viewCode.action";
		//获取完整跳转URL
		var url = updateUrl;
		//给 URL 添加参数
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
							  showPrint="true" listWidth="100%" title="交易商 代码：${firmID}"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
				        <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="traderID" title="交易员代码" ellipsis="true" width="20%" style="text-align:center;" >
						</ec:column>
						<ec:column property="name" title="交易员名称" width="20%" style="text-align:center;" >
						</ec:column>
						<ec:column property="prop" title="所属二级客户" width="10%" ellipsis="true" style="text-align:center;" sortable="false"  tipTitle="进入二级客户信息" >
						 <%--  <a href="#" onclick="viewCode('<c:out value="${trader.traderID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/man.gif"/>"></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/man.gif'/>" href="#" onclick="viewCode('${trader.traderID}')" action="/timebargain/firmSet/tradePrivilege/viewCode.action"/>
						</ec:column>
						<ec:column property="prop" title="交易员权限" width="10%" style="text-align:center;" sortable="false"  tipTitle="查看权限" >
						 <%--  <a href="#" onclick="updateForwardPrivilege('<c:out value="${trader.traderID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${trader.traderID}')" action="/timebargain/firmSet/tradePrivilege/listTraderPrivilege.action"/>
						</ec:column>
						
					  </ec:row>
					  
					  <ec:extend >
  			            <%-- <a href="#" onclick="goback()">返回交易商列表</a>--%>
  			            <rightHyperlink:rightHyperlink href="#" text="返回交易商列表" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/tradePrivilege.action" />
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
