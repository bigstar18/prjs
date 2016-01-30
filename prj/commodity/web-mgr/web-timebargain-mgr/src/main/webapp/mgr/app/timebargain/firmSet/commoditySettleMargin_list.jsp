<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>特殊商品交收保证金列表</title>
    <script type="text/javascript">

      // 添加信息跳转
	  function forwardAdd(){
			// 获取配置权限的 URL
			var addUrl=document.getElementById('add').action;
			// 获取完整跳转URL
			var url = "${basePath}"+addUrl;
			// 弹出添加页面
			if(showDialog(url, "", 800, 600)){
				// 如果添加成功，则刷新列表
				ECSideUtil.reload("ec");
			}
	  }

	  // 批量删除信息
	  function deleteSettleMargin(){
		  
			// 获取配置权限的 URL
			var deleteUrl = document.getElementById('delete').action;
			// 获取完整跳转URL
			var url = "${basePath}"+deleteUrl;
			// 执行删除操作
			updateRMIEcside(ec.ids,url);
	  }
   
	  // 详细信息跳转
	  function viewById(firmID, commodityID){
			// 获取配置权限的 URL
			var viewUrl = "/timebargain/firmSet/commoditySpecial/viewSettleMargin.action";
			// 获取完整跳转URL
			var url = "${basePath}"+viewUrl;
			// 给 URL 添加参数
			url += "?entity.firmID="+firmID + "&entity.commodityID=" + commodityID;
			// 弹出修改页面
			if(showDialog(url, "", 800, 600)){
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
		  
		    <div class="div_gn">
			  <rightButton:rightButton name="添加" onclick="forwardAdd();" className="anniu_btn" action="/timebargain/firmSet/commoditySpecial/addSettleMarginFoward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="删除" onclick="deleteSettleMargin();" className="anniu_btn" action="/timebargain/firmSet/commoditySpecial/deleteSettleMargin.action" id="delete"></rightButton:rightButton>
			</div>     
		       
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="commoditySettleMargin"  
							  action="${basePath}/timebargain/firmSet/commoditySpecial/listSettleMargin.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="commoditySettleMargin.xls" csvFileName="commoditySettleMargin.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${commoditySettleMargin.firmID},${commoditySettleMargin.commodityID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    
					    <ec:column property="_1" title="修改" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="viewById('<c:out value="${commoditySettleMargin.firmID}"/>','<c:out value="${commoditySettleMargin.commodityID}"/>')"><img title="进入修改信息" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a>--%>
                          <rightHyperlink:rightHyperlink text="<img title='进入修改信息' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="viewById('${commoditySettleMargin.firmID}','${commoditySettleMargin.commodityID}')" action="/timebargain/firmSet/commoditySpecial/viewSettleMargin.action"/> 
                        </ec:column>
						<ec:column property="firmID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="commodityID" title="商品代码" width="10%" style="text-align:center;" />
						<ec:column property="settleMarginAlgr_B" title="买交收保证金算法" width="10%" style="text-align:center;">
					      ${algrMap[commoditySettleMargin.settleMarginAlgr_B] }
						</ec:column>
						<ec:column property="settleMarginAlgr_S" title="卖交收保证金算法" width="10%" style="text-align:center;">
					      ${algrMap[commoditySettleMargin.settleMarginAlgr_S] }
						</ec:column>
						<ec:column property="payoutAlgr" title="交收货款算法" width="10%" style="text-align:center;">
					      ${algrMap[commoditySettleMargin.payoutAlgr] }
						</ec:column>
						<ec:column property="modifyTime" title="修改时间" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${commoditySettleMargin.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
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
