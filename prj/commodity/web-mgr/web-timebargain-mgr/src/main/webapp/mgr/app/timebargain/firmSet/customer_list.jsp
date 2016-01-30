<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交易客户列表</title>
    <script type="text/javascript">
      // 返回交易商列表
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //获取完整跳转URL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }

	  // 修改状态
	  function updateStatus(){
			var status = document.getElementById("sta").value;
			var updateStatusUrl = "/timebargain/firmSet/tradePrivilege/updateStatusCustomer.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateStatusUrl+"?status="+status;
			
			updateRMIEcside(ec.ids,url);
	  }

	   //添加信息跳转
	   function addForward(){
			//获取配置权限的 URL
			var addUrl=document.getElementById('add').action;
			//获取完整跳转URL
			var url = "${basePath}"+addUrl;
			//给 URL 添加参数
			 url += "?firmID=" + "${firmID}";
			//弹出添加页面
			if(showDialog(url, "", 600, 400)){
				 //如果添加成功，则刷新列表
			     ECSideUtil.reload("ec");
			}
		}
		
		//批量删除信息
		function deleteCustomer(){
			//获取配置权限的 URL
			var delateUrl = document.getElementById('delete').action;
			//获取完整跳转URL
			var url = "${basePath}"+delateUrl;
			//执行删除操作
			updateRMIEcside(ec.ids,url);
		}

	    // 跳转交易客户权限
		function updateForwardPrivilege(id) {
				//获取配置权限的 URL
				var updateUrl = "/timebargain/firmSet/tradePrivilege/listCustomerPrivilege.action";
				//获取完整跳转URL
				var url = "${basePath}"+updateUrl;
				//给 URL 添加参数
				url += "?customerID="+id + "&firmID=" + '${firmID}';

				document.location.href = url;
		 }

    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		    
		    <div class="div_gn">
			  <rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/addCustomerForward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="删除" onclick="deleteCustomer();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/deleteCustomer.action?autoInc=false" id="delete"></rightButton:rightButton>
			</div>     
		      
            <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="customer"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listCustomer.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="customer.xls" csvFileName="customer.csv"
							  showPrint="true" listWidth="100%" title="交易商 代码：${firmID}"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
				        <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${customer.customerID}" width="5%" viewsAllowed="html" />
				         <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="customerID" title="二级代码" width="20%" style="text-align:center;" />
						
						<ec:column property="status" title="状态" width="20%" style="text-align:center;" >
						  ${firm_statusMap[customer.status] }
						</ec:column>
						<ec:column property="createTime" title="创建时间" width="20%" style="text-align:center;">
						  <fmt:formatDate value="${customer.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
						</ec:column>
						<ec:column property="prop" title="二级代码权限" width="10%" style="text-align:center;" sortable="false"  tipTitle="查看权限" >
						  <%-- <a href="#" onclick="updateForwardPrivilege('<c:out value="${customer.customerID}"/>')"><img src="<c:url value="${skinPath}/image/app/timebargain/wheel.gif"/>"/></a>--%>
						  <rightHyperlink:rightHyperlink text="<img src='${skinPath}/image/app/timebargain/wheel.gif'/>" href="#" onclick="updateForwardPrivilege('${customer.customerID}')" action="/timebargain/firmSet/tradePrivilege/listCustomerPrivilege"/>
						</ec:column>
					  </ec:row>
					  
					  <ec:extend >
  			           <%--  <a href="#" onclick="goback()">返回交易商列表</a>--%>
  			            <rightHyperlink:rightHyperlink href="#" text="返回交易商列表" onclick="goback()" action="/timebargain/firmSet/tradePrivilege/tradePrivilege.action" />
  		              </ec:extend>
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
