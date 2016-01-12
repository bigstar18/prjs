<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>加盟商区域设置列表</title>
<script type="text/javascript">
<!--
	//添加信息跳转
	function addForward(){
		//获取配置权限的 URL
		var addUrl=document.getElementById('add').action;
		//获取完整跳转URL
		var url = "${basePath}"+addUrl;

		if(showDialog(url, "", 500, 350)){
			//如果添加成功，则刷新列表
			ECSideUtil.reload("ec");
		}
		
	}
	//修改信息跳转
	function detailForward(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerAreaforward.action?entity.areaId=";
		//获取完整跳转URL
		var url = detailUrl + id;
		//弹出修改页面
		if(showDialog(url, "", 500, 350)){
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

// -->
</script>
</head>
<body>
	<div id="main_body">
	    
	    <div class="div_gn">
			<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerAreaforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBrokerArea.action" id="delete"></rightButton:rightButton>
		</div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="brokerArea"
							action="${basePath}/broker/brokerManagement/listBrokerArea.action?sortColumns=order+by+areaId+asc"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${brokerArea.areaId}" width="5%" viewsAllowed="html" />
								<ec:column property="areaId" width="20%" title="区域代码" style="text-align:center;">
								    <a href="#" class="blank_a" onclick="detailForward('${brokerArea.areaId}')" title="修改"><font color="#880000">${brokerArea.areaId}</font></a> 	
								</ec:column>
								
								<ec:column property="name" title="区域名称" width="20%" style="text-align:center;"/>
								
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
