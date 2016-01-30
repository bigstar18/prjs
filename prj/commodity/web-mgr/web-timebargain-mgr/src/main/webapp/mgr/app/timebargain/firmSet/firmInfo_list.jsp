<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交易商信息</title>
    <script type="text/javascript">
      //执行查询列表
	  function dolistquery() {
		frm.submit();
	  }

      // 修改状态
	  function updateStatus(){
			var status = document.getElementById("sta").value;
			var updateStatusUrl = "/timebargain/firmSet/tradePrivilege/updateStatusFirm.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateStatusUrl+"?status="+status;
			
			updateRMIEcside(ec.ids,url);
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
	  }

      // 跳转交易商权限
	  function updateForwardPrivilege(id) {
			//获取配置权限的 URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateUrl;
			//给 URL 添加参数
			url += "?firmID="+id;

			parent.location.href = url;
	  }

	  // 跳转交易客户
	  function updateForwardCustomer(id) {
		  
			//获取配置权限的 URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listCustomer.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateUrl;
			//给 URL 添加参数
			url += "?firmID="+id;

			parent.location.href = url;
	  }

	  // 跳转交易员
	  function updateForwardTrader(id) {
			//获取配置权限的 URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/listTrader.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateUrl;
			//给 URL 添加参数
			url += "?firmID="+id;

			parent.location.href = url;
	  }
	  //批量修改权限
	  function batchSet(){
		    //pTop("<c:url value="/mgr/app/timebargain/firmSet/batchSetPower.jsp"/>",700,500);
		    //var url = "/mgr/app/timebargain/firmSet/batchSetPower.jsp";
		    //location.href = "${basePath}"+url;

		   //获取完整跳转URL
			var url = "${basePath}"+"/timebargain/firmSet/tradePrivilege/addPrivilegeForward.action";
			//弹出添加页面
			if(showDialog(url, "", 600, 550)){
				//如果添加成功，则刷新列表
				ECSideUtil.reload("ec");
			}
	  }
	  function pTop(location, width, height)
	  {
		    return showModalDialog(location,window,'dialogWidth:'+width+'px;dialogHeight:'+height+'px;dialogLeft:'+(screen.width-width)/2+'px;dialogTop:'+(screen.height-height)/2+'px;center:yes;help:no;resizable:no;status:no;scrollbars:no');
	  }
    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmInfo.action?sortColumns=order+by+createTime+desc" method="post">
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
							         交易商名称:&nbsp;
								<label>
								  <input type="text" class="input_text" id="firmName" name="${GNNT_}primary.firmName[like]" value="${oldParams['primary.firmName[like]']}" />
							    </label>
							  </td>
                              <td class="table3_td_1" align="right">
								状态:&nbsp;
								<label>									
								  <select id="status" name="${GNNT_}primary.status[=][int]">
								    <option value="">全部</option>
			                        <c:forEach items="${firm_statusMap}" var="map">
					                  <option value="${map.key}">${map.value}</option>
				                    </c:forEach>		
			                      </select>
								</label>
								<script >
								  frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
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
            
            <div class="div_list">
           
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="firmInfo"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmInfo.action?sortColumns=order+by+createTime+desc"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firmInfo.xls" csvFileName="firmInfo.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firmInfo.firmID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="firmID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
						<ec:column property="firmName" title="交易商名称" width="10%" style="text-align:center;" />
						<ec:column property="status" title="状态" width="10%" style="text-align:center;">
					      ${firm_statusMap[firmInfo.status] }
						</ec:column>
						<ec:column property="customerCounts" title="二级客户数量" width="10%" style="text-align:center;">
						<c:set var="status" value="${firm.status}"></c:set>
					      <c:choose>
                            <c:when test="${firmInfo.customerCounts > 0}">
                              <%-- <a href="#" onclick="updateForwardCustomer('<c:out value="${firmInfo.firmID}"/>')"><c:out value="${firmInfo.customerCounts}"/>&nbsp;&nbsp;<img title="修改二级客户" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>"></a> --%>
                              <rightHyperlink:rightHyperlink text=" ${firmInfo.customerCounts}&nbsp;&nbsp;<img title='修改二级客户' src='${skinPath}/image/app/timebargain/customer.gif'/>" href="#" onclick="updateForwardCustomer('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listCustomer.action"/>
                            </c:when>
                            <c:otherwise>
                              0&nbsp;<img title="修改交易客户" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>">
                           </c:otherwise>
                         </c:choose>
                       	
						</ec:column>
						<ec:column property="tcounts" title="交易员数量" width="10%" style="text-align:center;">
											      
					       <c:choose>
                            <c:when test="${firmInfo.tcounts > 0}">
                              <%-- <a href="#" onclick="updateForwardTrader('<c:out value="${firmInfo.firmID}"/>')"><c:out value="${firmInfo.tcounts}"/>&nbsp;&nbsp;<img title="修改交易员" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>"></a> --%>
                              <rightHyperlink:rightHyperlink text=" ${firmInfo.tcounts}&nbsp;&nbsp;<img title='修改交易员' src='${skinPath}/image/app/timebargain/customer.gif'/>" href="#" onclick="updateForwardTrader('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listTrader.action"/>
                            </c:when>
                            <c:otherwise>
                              0&nbsp;<img title="修改交易员" src="<c:url value="${skinPath}/image/app/timebargain/customer.gif"/>">
                           </c:otherwise>
                         </c:choose>
					      
						</ec:column>
						<ec:column property="createTime" title="创建时间" width="10%" style="text-align:center;" >
						  <fmt:formatDate value="${firmInfo.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>
						<ec:column property="prop" title="交易商权限" width="5%" style="text-align:center;" sortable="false"  tipTitle="查看权限" >
						  <%-- <a href="#" onclick="updateForwardPrivilege('<c:out value="${firmInfo.firmID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${firmInfo.firmID}')" action="/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action"/>
						</ec:column>
								
					  </ec:row>
					</ec:table>
				  </td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
		          <td>
			        <select id="sta">
			          <c:forEach items="${firm_statusMap}" var="map">
					    <option value="${map.key}">${map.value}</option>
				      </c:forEach>		
			        </select>
			        <input type="button" class="anniu_btn" value="修改状态"  onclick="updateStatus()">
			        <input type="button" class="anniu_btn" value="批量操作"  onclick="batchSet()">
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
