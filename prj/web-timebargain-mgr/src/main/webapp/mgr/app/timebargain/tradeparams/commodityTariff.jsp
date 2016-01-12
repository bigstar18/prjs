<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>

<title>
套餐详情
</title>
<script type="text/javascript">
function back(){
	//获取配置权限的 URL
	var addUrl=document.getElementById('back').action;
	//获取完整跳转URL
	var url = "${basePath}"+addUrl;
	
    document.location.href = url;	

}
</script>
</head>
<body>
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
												${tariffID}
												</td>
												<td class="table3_td_1" align="right">
												套餐名称:&nbsp;${tariffName}</td>
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
					<rightButton:rightButton name="返回" onclick="back();" className="anniu_btn" action="/timebargain/tradeparams/tariffList.action" id="back"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="tariff"
									action="${basePath}/timebargain/tradeparams/detailTariff.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%" sortable="false" filterable="false"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column property="commodityID" title="商品代码" width="100" style="text-align:center;"/>
							            <ec:column property="name" title="商品名称" width="110" style="text-align:center;"/>
							            <ec:column property="oldFeeRate_B" title="交易所买订立费率" width="130"  style="text-align:center;"/> 
							            <ec:column property="newFeeRate_B" title="套餐买订立费率" width="130"  style="text-align:center;"/>  
							            <ec:column property="oldFeeRate_S" title="交易所卖订立费率" width="130"  style="text-align:center;"/> 
							            <ec:column property="newFeeRate_S" title="套餐卖订立费率" width="130"  style="text-align:center;"/>  
							            <ec:column property="oldTodayCloseFeeRate_B" title="交易所买转让今订货费率" width="160"  style="text-align:center;"/> 
							            <ec:column property="newTodayCloseFeeRate_B" title="套餐买转让今订货费率" width="160"  style="text-align:center;"/>  
							            <ec:column property="oldTodayCloseFeeRate_S" title="交易所卖转让今订货费率" width="160"  style="text-align:center;"/> 
							            <ec:column property="newTodayCloseFeeRate_S" title="套餐卖转让今订货费率" width="160"  style="text-align:center;"/>  
							            <ec:column property="oldHistoryCloseFeeRate_B" title="交易所买转让历史订货费率" width="170"  style="text-align:center;"/> 
							            <ec:column property="newHistoryCloseFeeRate_B" title="套餐买转让历史订货费率" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldHistoryCloseFeeRate_S" title="交易所卖转让历史订货费率" width="170"  style="text-align:center;"/> 
							            <ec:column property="newHistoryCloseFeeRate_S" title="套餐卖转让历史订货费率" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldForceCloseFeeRate_B" title="交易所买强制转让费率" width="160"  style="text-align:center;"/> 
							            <ec:column property="newForceCloseFeeRate_B" title="套餐买强制转让费率" width="160"  style="text-align:center;"/>   
							            <ec:column property="oldForceCloseFeeRate_S" title="交易所卖强制转让费率" width="160"  style="text-align:center;"/> 
							            <ec:column property="newForceCloseFeeRate_S" title="套餐卖强制转让费率" width="160"  style="text-align:center;"/>                        
							            <ec:column property="modifyTime" title="修改时间" width="160" cell="date" format="date" style="text-align:center;"/>
							            <ec:column property="createUser" title="修改人" width="100" style="text-align:center;"/>
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
