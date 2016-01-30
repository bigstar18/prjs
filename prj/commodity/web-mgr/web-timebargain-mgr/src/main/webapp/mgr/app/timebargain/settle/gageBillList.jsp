<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	
		<style type="text/css">
		a:link {text-decoration:none;} 
		a:active:{text-decoration:none;} 
		a:visited {text-decoration:none;}
		a:hover { text-decoration:none;}
		
		</style>
		<script type="text/javascript">
			$(function(){
				$("#frm").validationEngine({
					promptPosition:'bottomRight'
				}); 
				//添加信息跳转
				$("#add").click(function(){
					var addUrl = $("#add").attr('action');
					var url = "${basePath}"+addUrl;
					if(showDialog(url, "", 800, 550)){
						//如果添加成功，则刷新列表
						ECSideUtil.reload("ec");
					}
				});

				//$("a").click(function(){
					//viewById('<c:out value="${bill['ID']}"/>', '<c:out value="${bill['COMMODITYID']}"/>')
					//var gageBillID = $(this).attr("billId");
					//var commodityID = $(this).attr("commodityId");
					//var viewUrl = "/timebargain/bill/gageBillDetail.action";
					//var url = "${basePath}"+viewUrl + "?gageBillID="+gageBillID + "&commodityID="+commodityID;
					// 弹出修改页面
					//showDialog(url, "", 800, 550);
				//});
			});
			
			// 详细信息跳转
			  function viewById(gageBillID, commodityID){
					//获取配置权限的 URL
					var viewUrl = "/timebargain/bill/gageBillDetail.action";
					//获取完整跳转URL
					var url = "${basePath}"+viewUrl;
					//给 URL 添加参数
					url += "?gageBillID="+gageBillID + "&commodityID=" + commodityID;
					// 弹出修改页面
					// 弹出修改页面
					showDialog(url, "", 800, 550);
			  }
		</script>
		<script type="text/javascript">
		</script>
		</head>
		<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					    <div class="div_cx">
							<form id = "frm" name="frm" action="${basePath}/timebargain/bill/gageBillList.action" method="post">
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
						<br />
					    <div class="div_gn">
							<rightButton:rightButton name="添加" onclick="" className="anniu_btn" action="/timebargain/bill/getCommodity.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="bill"
											action="${basePath}/timebargain/bill/gageBillList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
									            <ec:column property="FIRMID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;"/>
									            
									            <ec:column property="COMMODITYID" title="商品代码" width="10%" style="text-align:center;"/>
									            <ec:column property="QUANTITY" title="仓单数量" width="10%" style="text-align:center;"/>
									            <ec:column property="CREATETIME" title="创建时间" width="15%" style="text-align:center;"> 
									            	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:SS" value="${bill['CREATETIME']}"/> 		
									            </ec:column>
									            <ec:column property="MODIFYTIME" title="最后修改时间" width="15%" style="text-align:center;">  		
									            	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:SS" value="${bill['MODIFYTIME']}"/> 		
									            </ec:column>
									            <ec:column property="REMARK" title="备注" width="20%" style="text-align:center;"/>  
									            <ec:column property="_1" title="详细信息" width="10%" style="text-align:center;">
									            	<%-- <a name = "detail" href="#" billId = "${bill['ID']}" commodityId = "${bill['COMMODITYID']}">
									            		<img title="点击查看详细信息" src="<c:url value="${skinPath}/image/app/timebargain/commodity.gif"/>"/>
									            	</a>--%>
									            	<rightHyperlink:rightHyperlink text="<img title='点击查看详细信息' src='${skinPath }/image/app/timebargain/commodity.gif'/>" onclick="viewById('${bill['ID']}','${bill['COMMODITYID']}')" action="/timebargain/bill/gageBillDetail.action"/>
									            </ec:column>  
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
