<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>特殊品种订货量列表</title>
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
	  function deleteQty(){
			// 获取配置权限的 URL
			var deleteUrl = document.getElementById('delete').action;
			// 获取完整跳转URL
			var url = "${basePath}"+deleteUrl;
			// 执行删除操作
			updateRMIEcside(ec.ids,url);
			
	  }

	  // 详细信息跳转
	  function viewById(firmID, breedID){
			//获取配置权限的 URL
			var viewUrl = "/timebargain/firmSet/breedSpecial/viewMaxHoldQty.action";
			//获取完整跳转URL
			var url = "${basePath}"+viewUrl;
			//给 URL 添加参数
			url += "?entity.firmID="+firmID + "&entity.breedID=" + breedID;
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
			  <rightButton:rightButton name="添加" onclick="forwardAdd();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/addMaxHoldQtyFoward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="删除" onclick="deleteQty();" className="anniu_btn" action="/timebargain/firmSet/breedSpecial/deleteMaxHoldQty.action" id="delete"></rightButton:rightButton>
			</div>     
		       
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="breedQty"
							  action="${basePath}/timebargain/firmSet/breedSpecial/listMaxHoldQty.action?sortColumns=order+by+modifyTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="breedQty.xls" csvFileName="breedQty.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${breedQty.firmID},${breedQty.breedID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    
					    <ec:column property="_1" title="修改" width="10%" style="text-align:center;" sortable="false" filterable="false">
                         <%--  <a href="#" onclick="viewById('<c:out value="${breedQty.firmID}"/>','<c:out value="${breedQty.breedID}"/>')"><img title="进入修改信息" src="<c:url value="${skinPath}/image/app/timebargain/fly.gif"/>"/></a> --%>
                          <rightHyperlink:rightHyperlink text="<img title='进入修改信息' src='${skinPath}/image/app/timebargain/fly.gif'/>" href="#" onclick="viewById('${breedQty.firmID}','${breedQty.breedID}')" action="/timebargain/firmSet/breedSpecial/viewMaxHoldQty.action"/>
                        </ec:column>
						<ec:column property="firmID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="breedID" title="品种代码" width="10%" style="text-align:center;" />
						<ec:column property="maxHoldQty" title="最大订货量" width="10%" style="text-align:center;">
					      <c:if test="${breedQty.maxHoldQty == -1}">
					                    无限制
					      </c:if>
					      <c:if test="${breedQty.maxHoldQty != -1}">
					          ${breedQty.maxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="cleanMaxHoldQty" title="最大净订货量" width="10%" style="text-align:center;">
					      <c:if test="${breedQty.cleanMaxHoldQty == -1}">
					                    无限制
					      </c:if>
					      <c:if test="${breedQty.cleanMaxHoldQty != -1}">
					          ${breedQty.cleanMaxHoldQty}
					      </c:if>
						</ec:column>
						<ec:column property="modifyTime" title="修改时间" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${breedQty.modifyTime }" pattern="yyyy-MM-dd HH:mm:SS" />
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
