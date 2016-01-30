<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>交易商权限</title>
    <script type="text/javascript">
      // 返回交易商列表
	  function goback() {
		 var backUrl = "/timebargain/firmSet/tradePrivilege/tradePrivilege.action";
		 //获取完整跳转URL
	     var url = "${basePath}"+backUrl;

	     document.location.href = url;
	  }
		
	  // 详细信息跳转
	  function viewById(id){
			//获取配置权限的 URL
			var updateUrl = "/timebargain/firmSet/tradePrivilege/viewFirmPrivilege.action";
			//获取完整跳转URL
			var url = "${basePath}"+updateUrl;
			//给 URL 添加参数
			url += "?entity.ID="+id;
			//弹出修改页面
			if(showDialog(url, "", 600, 450)){
				//如果修改成功，则刷新列表
				ECSideUtil.reload("ec");
			};
	  }

	  //添加信息跳转
	  function addForward(){
			//获取配置权限的 URL
			var addUrl=document.getElementById('add').action;
			//获取完整跳转URL
			var url = "${basePath}"+addUrl;
			//给 URL 添加参数
			url += "?typeID=" + '${firmID}';
			//弹出添加页面
			if(showDialog(url, "", 600, 450)){
				//如果添加成功，则刷新列表
				ECSideUtil.reload("ec");
			}
	  }
	  
	  //批量删除信息
	  function deletePrivilege(){
			//获取配置权限的 URL
			var deleteUrl = document.getElementById('delete').action;
			//获取完整跳转URL
			var url = "${basePath}"+deleteUrl;
			//执行删除操作
			updateRMIEcside(ec.ids,url);
	  }

    </script>
  </head>
  
  <body>
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
	       
		    <div class="div_gn">
			  <rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/addFirmPrivilegeForward.action" id="add"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <rightButton:rightButton name="删除" onclick="deletePrivilege();" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/deleteFirmPrivilege.action" id="delete"></rightButton:rightButton>
			</div>      
		          
            <div class="div_list"> 
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				    <ec:table items="pageInfo.result" var="firmPrivilege"
							  action="${basePath}/timebargain/firmSet/tradePrivilege/listFirmPrivilege.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="firmPrivilege.xls" csvFileName="firmPrivilege.csv"
							  showPrint="true" listWidth="100%" title="交易商 ${firmID}交易权限维护"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${firmPrivilege.ID}" width="5%" viewsAllowed="html" />
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    <ec:column property="KIND" title="品种/商品" width="10%" style="text-align:center;">
					       <c:if test="${firmPrivilege.KIND == 1}">品种</c:if>
						   <c:if test="${firmPrivilege.KIND == 2}">商品</c:if>	
						</ec:column>
						<ec:column property="KINDID" title="上市品种/商品代码" width="20%" style="text-align:center;" >
						 <%--  <a href="#" class="blank_a" onclick="viewById('<c:out value="${firmPrivilege.ID}"/>')">
						    <font color="#880000">${firmPrivilege.KINDID }</font>
						  </a>--%>
						  <rightHyperlink:rightHyperlink href="#" className="blank_a" text="<font color='#880000'>${firmPrivilege.KINDID}</font>"  onclick="viewById('${firmPrivilege.ID}');" action="/timebargain/firmSet/tradePrivilege/viewFirmPrivilege.action" ></rightHyperlink:rightHyperlink>

						</ec:column>
						<ec:column property="kindName" title="上市品种/商品" width="20%" style="text-align:center;" sortable="false">
						    <c:if test="${firmPrivilege.KIND == 1}">
						     <font color='#880000'>${firmPrivilege.BREEDNAME}</font>
						    </c:if>
						    <c:if test="${firmPrivilege.KIND == 2}">
						     <font color='#880000'>${firmPrivilege.COMMODITYNAME}</font>
						    </c:if>	
						</ec:column>
						<ec:column property="PRIVILEGECODE_B" title="买方权限" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_B" mappingItem="FIRMPRIVILEGE_B" />
						<ec:column property="PRIVILEGECODE_S" title="卖方权限" width="20%" style="text-align:center;" editTemplate="ecs_privilegeCode_S" mappingItem="FIRMPRIVILEGE_S" />	
					  </ec:row>
					  
					  <ec:extend >
  			           <%--  <a href="#" onclick="goback()">返回交易商列表</a>--%>
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
      
      <!-- 编辑买方权限所用模板 -->
	  <textarea id="ecs_privilegeCode_B" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_B" >
			<ec:options items="FIRMPRIVILEGE_B" />
		</select>
	  </textarea>
	  <!-- 编辑卖方权限所用模板 -->
	  <textarea id="ecs_privilegeCode_S" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_S" >
			<ec:options items="FIRMPRIVILEGE_S" />
		</select>
	  </textarea>		
      
    </div>
  </body>
</html>
