<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>风控设置</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		frm.submit();
	  }

      // 详细信息跳转
	  function viewById(id){
		
		//获取配置权限的 URL
		var viewUrl = "/timebargain/firmSet/risk/viewFirm.action";
		//获取完整跳转URL
		var url = "${basePath}"+viewUrl;
		//给 URL 添加参数
		url += "?entity.firmID=" + id;
		// 弹出修改页面
		if(showDialog(url, "", 600, 450)){
			// 如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		};
	  }
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/risk/riskList.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							 <td class="table3_td_1" align="right">
							         交易商代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmID" name="${GNNT_}primary.firmID[like]" value="${oldParams['primary.firmID[like]']}" />
							    </label>
							  </td>

						    <td class="table3_td_anniu" align="left">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							  &nbsp;&nbsp;
							  <button class="btn_cz" onclick="myReset();">重置</button>
						    </td>
					      </tr>
					    </table>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
            </div>
            
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="risk"
							  action="${basePath}/timebargain/firmSet/risk/riskList.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="risk.xls" csvFileName="risk.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="firmID" title="交易商代码" width="20%" ellipsis="true" style="text-align:center;" >
						 <%--  <a href="#" class="blank_a" onclick="viewById('<c:out value="${risk.firmID}"/>')">
						    <font color="#880000">${risk.firmID}</font>
						  </a> --%>
						  <rightHyperlink:rightHyperlink href="#" text="<font color='#880000'>${risk.firmID}</font>" className="blank_a" onclick="viewById('${risk.firmID}');" action="/timebargain/firmSet/risk/viewFirm.action" />
						</ec:column>
						<ec:column property="maxHoldQty" title="最大订货总量" width="20%" style="text-align:center;">
					      <c:if test="${risk.maxHoldQty == -1}">
					                    无限制
					      </c:if>
					      <c:if test="${risk.maxHoldQty != -1}">
					          ${risk.maxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="minClearDeposit" title="最低结算准备金" width="20%" style="text-align:center;" >
						  <fmt:formatNumber value="${risk.minClearDeposit }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="maxOverdraft" title="质押资金" width="20%" style="text-align:center;" >
						  <fmt:formatNumber value="${risk.maxOverdraft }" pattern="#,##0.00" />
						</ec:column>
									
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
