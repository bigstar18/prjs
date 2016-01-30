<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>交易节管理列表</title>
<script type="text/javascript">
<!--
	//添加信息跳转
	function addForward(){
		//获取配置权限的 URL
		var addUrl=document.getElementById('add').action;
		//获取完整跳转URL
		var url = "${basePath}"+addUrl;

		if(showDialog(url, "", 800, 550)){
			//如果添加成功，则刷新列表
			ECSideUtil.reload("ec");
		}
		
	}
	//修改信息跳转
	function detailForward(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/timebargain/tradeparams/detailTradeTimeforward.action?entity.sectionID=";
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

// -->
</script>
</head>
<body>
	<div id="main_body">
	    <div class="warning">
			<div class="content">
				温馨提示 :添加、删除、修改交易节
			</div>
		</div>	
	    <div class="div_gn">
			<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addTradeTimeforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteTradeTime.action" id="delete"></rightButton:rightButton>
		</div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="tradeTime"
							action="${basePath}/timebargain/tradeparams/tradeTimeList.action?sortColumns=order+by+sectionID+asc"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${tradeTime.sectionID}" width="5%" viewsAllowed="html" />
								<ec:column property="sectionID" width="20%" title="编号" style="text-align:center;">
								   <rightHyperlink:rightHyperlink text="<font color='#880000'>${tradeTime.sectionID}</font>" className="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="修改" action="/timebargain/tradeparams/detailTradeTimeforward.action" />
								    <%-- <a href="#" class="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="修改"><font color="#880000">${tradeTime.sectionID}</font></a> 	--%>
								</ec:column>
								<ec:column property="name" title="交易节名称" width="20%" style="text-align:center;">
								    <rightHyperlink:rightHyperlink text="<font color='#880000'>${tradeTime.name}</font>" className="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="修改" action="/timebargain/tradeparams/detailTradeTimeforward.action" />
									<%-- <a href="#" class="blank_a" onclick="detailForward('${tradeTime.sectionID}')" title="修改"><font color="#880000">${tradeTime.name}</font></a> 	--%>
								</ec:column>
								<ec:column property="startTime" title="交易开始时间" width="20%" style="text-align:center;"/>
								<ec:column property="endTime" title="交易结束时间" width="20%" style="text-align:center;"/>
								<ec:column property="status" title="状态" width="20%" style="text-align:center;">${tradetime_statusMap[tradeTime.status]}</ec:column>
								
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
