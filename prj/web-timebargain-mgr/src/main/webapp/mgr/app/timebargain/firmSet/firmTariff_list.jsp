<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>手续费套餐选择列表</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		frm.submit();
	  }

      // 修改信息跳转
	  function updateForward(id){
		
		//获取配置权限的 URL
		var updateUrl = "/timebargain/firmSet/firmTariff/updateFirmTariffForward.action";
		//获取完整跳转URL
		var url = "${basePath}" + updateUrl;
		//给 URL 添加参数
		url += "?entity.firmID="+id;
		//弹出修改页面
		if(showDialog(url, "", 600, 450)){
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		};
	  }

	   // 批量取消套餐信息
	   function closeTariff(){
			//获取配置权限的 URL
			var updateUrl = document.getElementById('update').action;
			//获取完整跳转URL
			var url = "${basePath}"+updateUrl;
			//执行修改操作
			updateRMIEcside(ec.ids,url);
		}
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/firmTariff/firmTariffList.action?sortColumns=order+by+modifyTime+desc" method="post">
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
							  <td class="table3_td_1" align="right">
							         套餐代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="tariffID" name="${GNNT_}primary.tariffID[like]" value="${oldParams['primary.tariffID[like]']}" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							         是否设置套餐:&nbsp;
								<label>
								  <select id="isTariff" name="isTariff"  class="normal" >
									<option value="">全部</option>
									<option value="Y">是</option>							
									<option value="N">否</option>								
								  </select>
							    </label>
							     <script >
									frm.isTariff.value = "${isTariff}";
					  			 </script>
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
            
            <div class="div_gn">		
				<rightButton:rightButton name="取消套餐" onclick="closeTariff();" className="anniu_btn" action="/timebargain/firmSet/firmTariff/updateFirmTariffClose.action" id="update"></rightButton:rightButton>
			</div>
            
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="firm"
							  action="${basePath}/timebargain/firmSet/firmTariff/firmTariffList.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firm.xls" csvFileName="firm.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firm.firmID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />

						<ec:column property="firmID" title="交易商代码" width="20%" ellipsis="true" style="text-align:center;" />
						<ec:column property="status" title="状态" width="20%" style="text-align:center;">
					      ${firm_statusMap[firm.status] }
						</ec:column>
						<ec:column property="modifyTime" title="修改时间" width="20%" style="text-align:center;" >
						  <fmt:formatDate value="${firm.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>		
						 <ec:column property="tariffI" title="套餐代码" width="20%" style="text-align:center;" sortable="false" filterable="false">
						  <c:if test="${firm.tariffID != 'none' }">
						   <%--  <a href="<c:out value="${basePath}"/>/timebargain/firmSet/firmTariff/viewTariff.action?tariffID=${firm.tariffID }&sortColumns=order+by+commodityID+asc" title="查看套餐">
						      <c:out value="${firm.tariffID }"/>
						    </a> --%>
						    <rightHyperlink:rightHyperlink text="${firm.tariffID }" href="${basePath}/timebargain/firmSet/firmTariff/viewTariff.action?tariffID=${firm.tariffID }&sortColumns=order+by+commodityID+asc" title="查看套餐" action="/timebargain/firmSet/firmTariff/viewTariff.action"/>
						  </c:if>
						</ec:column>	
						<ec:column property="_1" title="设置套餐" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="updateForward('<c:out value="${firm.firmID}"/>')"><img title="设置套餐" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a> --%>
                          <rightHyperlink:rightHyperlink text="<img title='设置套餐' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="updateForward('${firm.firmID}')" action="/timebargain/firmSet/firmTariff/updateFirmTariffForward.action"/>
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
