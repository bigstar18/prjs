<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>抵顶数据查询</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript">
			$(function(){
				$("#frm").validationEngine({
					promptPosition:'bottomRight'
				}); 
			});
		</script>
	</head>
	
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form id = "frm" name="frm" action="${basePath}/timebargain/bill/gageDataQuery.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															交易商代码:&nbsp;
															<input id="firmID" name="${GNNT_}gageBill.firmId" type="text" value="${oldParams['gageBill.firmId'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
														<td align="center">商品代码：&nbsp;
															<input id="commodityID" name="${GNNT_}gageBill.commodityID" type="text" value="${oldParams['gageBill.commodityID'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
														
														<td class="table3_td_1" align="left">
															<input id="view" class="btn_sec" type="submit" value = "查询"/>
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
										<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/bill/gageDataQuery.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="FIRMID" width="16%" title="交易商代码" ellipsis="true" style="text-align:center;"/>
												<ec:column property="CUSTOMERID" title="二级代码" width="16%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="COMMODITYID" title="商品代码"  width="16%" style="text-align:center;"/>
											
												<ec:column property="HOLDQTY" title="订货数量" calc="total" calcTitle= "合计" width="16%" style="text-align:right;"/>
												<ec:column property="GAGEQTY" title="抵顶数量" calc="total" width="16%" style="text-align:right;"/>
											</ec:row>
											<ec:row>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>