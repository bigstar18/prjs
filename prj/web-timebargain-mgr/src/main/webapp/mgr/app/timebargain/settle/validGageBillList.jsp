<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self" />
		<title>查询仓单</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	
		<script type="text/javascript">
			$(function(){
				//交易商自动补全
				<c:if test="${not empty json}">
					var cities =${json};
					$("#firmID").change().autocomplete(cities);
				</c:if>
				//执行验证
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
							<form id="frm" name="frm" method="post" action="${basePath }/timebargain/bill/getValidGageBill.action">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
													<td align="right">
														交易商代码：
													</td>
													<td>
													   <input type="text" id="firmID"
															class="validate[custom[noSpecialChar]] input_text" name = "${GNNT_}stock.firmID" value="${oldParams['stock.firmID']}"/>
													</td>
													
													<td align="center">
														商品代码：
													</td>
													<td>
													   <select style="width:100" name = "${GNNT_}stock.commodityID">
													   		<option value="">
													   			全部
													   		</option>
															<c:forEach items="${list}" var="props">
																<c:choose>
												  					<c:when test = "${props['COMMODITYID'] == oldParams['stock.commodityID']}">
												  						<option value="${props['COMMODITYID']}" selected="selected">
																			${props['NAME']}
																		</option>
												  					</c:when>
												  					<c:otherwise>
												  						<option value="${props['COMMODITYID']}" >
																			${props['NAME']}
																		</option>
												  					</c:otherwise>
												  				</c:choose>
															</c:forEach>
							   							</select> 
													</td>
													<td align="right">
														<input id="query" class="btn_sec" type="submit" value = "查询"/>
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="bill"
										action="${basePath}/timebargain/bill/getValidGageBill.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="export.xls" csvFileName="export.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column property="FIRMID" title="交易商代码" width="15%" ellipsis="true" style="text-align:center;"/>
											<ec:column property="NAME" title="商品名称" width="20%" style="text-align:center;"/>
											<ec:column property="QUANTITY" title="仓单数量" width="20%" style="text-align:center;"/>
											<ec:column property="FROZENQTY" title="冻结数量" width="20%" style="text-align:center;"/>
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
