<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
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
				</div>
				<br />		
				<!-- 
				<div class="div_gn">
					<rightButton:rightButton name="返回" onclick="back();" className="anniu_btn" action="/timebargain/tradeparams/tariffList.action" id="back"></rightButton:rightButton>
				</div> -->
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="730">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="tariff"
									action="${basePath}/firm/tariff/detailTariffCommodity.action?tariffid=${tariffID}"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="730" sortable="false" filterable="false"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column property="COMMODITYID" title="商品代码" width="25%" style="text-align:center;"/>
            
							            <ec:column property="NAME" title="商品名称" width="25%" style="text-align:center;"/>
							            <ec:column property="OLDFEERATE" title="交易所费率" width="25%"  style="text-align:center;"/> 
							            <ec:column property="NEWFEERATE" title="套餐费率" width="25%"  style="text-align:center;"/> 
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
