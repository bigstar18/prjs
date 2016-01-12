<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>
<title>手续费套餐列表</title>
<script type="text/javascript">
<!--
//添加信息跳转
function addForward(){
	//获取配置权限的 URL
	var addUrl=document.getElementById('add').action;
	//获取完整跳转URL
	var url = "${basePath}"+addUrl;

	var selTariff=document.getElementById("selTariffID");
	
	var selTariffid=selTariff.value;

	var selTariffName=selTariff.options[selTariff.selectedIndex].text;
	
    document.location.href = url + "?tariffID=" + selTariffid + "&tariffName=" + selTariffName;	

}
//修改信息跳转
function detailForward(id){
	//获取配置权限的 URL
	var detailUrl = "${basePath}/timebargain/tradeparams/detailTariff.action?entity.sectionID=";
	//获取完整跳转URL
	var url = detailUrl + id;
	//弹出修改页面
	if(showDialog(url, "", 800, 550)){
		//如果修改成功，则刷新列表
		ECSideUtil.reload("ec");
	};
}
//批量删除信息
function deleteList(){
	//获取配置权限的 URL
	var deleteUrl = document.getElementById('delete').action;
	//获取完整跳转URL
	var url = "${basePath}"+deleteUrl;
	//执行删除操作
	updateRMIEcside(ec.ids,url);
}
//执行查询列表
function dolistquery() {
	frm.submit();
}
// -->
</script>
</head>
<body>
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			    <div class="div_cx">
					<form name="frm" action="${basePath}/timebargain/tradeparams/tariffList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="table3_td_1" align="right">
												套餐代码:&nbsp;
												<input id="tariffID" name="${GNNT_}primary.tariffID" type="text" value="${oldParams['primary.tariffID'] }" class="input_text"/>
												</td>
												<!-- <td align="center">套餐状态：
													<select id="oldDate" name="oldDate" style="width:80" >											    
													<option value="0" <c:if test="${oldStatus==0 }">selected</c:if>>正常套餐</option>
													<option value="1" <c:if test="${oldStatus==1 }">selected</c:if>>过期套餐</option>
											  	    </select> 
												</td>
												-->
												<td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
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
				<br />
			    <div class="div_gn">
			        <select id="selTariffID" class="normal" style="height:40px; width: 80px">
					  <c:forEach items="${allMap}" var="map">
						  <option value="${map.key }">${map.value }</option>
					  </c:forEach>
					</select>
					<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addTariff.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;
					<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteTariff.action" id="delete"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="tariff"
									action="${basePath}/timebargain/tradeparams/tariffList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column cell="checkbox" headerCell="checkbox" alias="ids" value="${tariff.tariffID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:3%;"/>				            	
							            <ec:column property="tariffID" title="套餐代码" width="20%" style="text-align:center;">
							               <rightHyperlink:rightHyperlink text="${tariff.tariffID}" className="blank_a" href="<c:out value='${basePath}'/>/timebargain/tradeparams/detailTariff.action?tariffID=${tariff.tariffID}&sortColumns=order+by+commodityID+asc" title="查看" action="/timebargain/tradeparams/detailTariff.action" />
							              <%-- <a href="<c:out value="${basePath}"/>/timebargain/tradeparams/detailTariff.action?tariffID=${tariff.tariffID}&sortColumns=order+by+commodityID+asc" title="查看"><c:out value="${tariff.tariffID}"/></a> --%>
							            </ec:column>
							            <ec:column property="tariffName" title="套餐名称" width="20%" style="text-align:center;"/>
							            <ec:column property="createTime" title="创建时间" width="30%" style="text-align:center;">
							            <fmt:formatDate pattern="yyyy-MM-dd" value="${tariff.createTime}"/>
							            </ec:column>
							            <ec:column property="createUser" title="创建人" width="20%" style="text-align:center;"/>  		
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
		
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
</body>

</html>
  
