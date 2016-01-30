<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>仓单列表</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/stock/list/stockList.action?sortColumns=order+by+stockId"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" >
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top" style="margin-left: 0px">
												<table class="table3_style" border="0" cellspacing="0" 
													cellpadding="0" width="100%">
													<tr >
														<td class="table3_td_1" align="right">
															所属交易商：
															<label>
																<input id="id" type="text" class="input_text"  style="width:100px;"
																maxLength="<fmt:message key='ownerFirm_q' bundle='${PropsFieldLength}'/>" 
																	name="${GNNT_}primary.ownerFirm[allLike]"
																	value="${oldParams['primary.ownerFirm[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															仓单号：
															<label>
																<input type="text" class="input_text"  style="width:100px;"
																maxLength="<fmt:message key='stockId_q' bundle='${PropsFieldLength}'/>" 
																	name="${GNNT_}primary.stockId[=][String]"
																	value="${oldParams['primary.stockId[=][String]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															仓库原始凭证号：
															<label>
																<input type="text" class="input_text"  style="width:90px;"
																maxLength="30"
																	name="${GNNT_}primary.realStockCode[allLike]"
																	value="${oldParams['primary.realStockCode[allLike]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															<button class="btn_sec" onclick=select1();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																	重置
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="div_gn">
							<rightButton:rightButton name="录入仓单" onclick="addStock();" className="btn_sec" action="/stock/list/addStockForward.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="stock"
											action="${basePath}/stock/list/stockList.action?sortColumns=order+by+stockId"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="stock.xls" csvFileName="stock.csv" 
											showPrint="true" listWidth="100%" minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="7%" property="_0" title="序号" style="text-align:center;"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="stockId" width="8%" title="Stock.stockId"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" id="detail" action="/stock/list/stockDetails.action" onclick="details(${stock.stockId})"  text="<font color='#880000'>${stock.stockId}</font> "/>
												</ec:column>
												<ec:column property="realStockCode" width="8%" title="仓库原始凭证号"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="breed.breedName" width="8%" title="Stock.breed"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="quantity" width="12%" title="Stock.quantity" style="text-align:right; " ellipsis="true"><fmt:formatNumber value="${stock.quantity}" pattern="#,##0.00" />(${stock.unit})
												</ec:column>
												<ec:column property="ownerFirm" width="10%" title="Stock.ownerFirm"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="lastTime" width="14%" title="Stock.lastTime"
													style="text-align:center; ">
													<fmt:formatDate value="${stock.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="createTime" width="14%" title="Stock.createTime"
													style="text-align:center; ">
													<fmt:formatDate value="${stock.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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


			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" /></textarea>

<SCRIPT type="text/javascript">

	function register(stockId){
		var result = confirm("确认注册该仓单吗？");
		if(result){
			var a=document.getElementById("register").action;
			frm.action = "${basePath}"+a+"?stockId=" + stockId;
			frm.submit();
			//alert(${ReturnValue.result});
		}
	}
	
	function addStock(){
			var a=document.getElementById("add").action;
			var url="${basePath}"+a;
			if(showDialog(url, "", 700, 470)){
				frm.submit();
			}

		}
		//出库
	function stockOut(stockId){
	var haveWarehouse = '${haveWarehouse}';
		if(haveWarehouse==''){
			alert("仓单交易核心未能正常启动：仓单出库操作暂时无法进行操作，请稍后重试！")
		}else{
			if(haveWarehouse==1){
				var a=document.getElementById("stockOut").action;
				var url="${basePath}"+a+"?entity.stockId=" + stockId;
				if(showDialog(url, "", 500, 300)){
					frm.submit();
				}
			}else{
				var result = confirm("确认出库？");
				if(result){
					frm.action = "${basePath}/stock/list/stockOut.action?stockId=" + stockId;
					frm.submit();
				}
			}
		}
	}
	
	function dismantleStock(stockId){
		var a=document.getElementById("dismantleStock").action;
		var url="${basePath}"+a+"?entity.stockId=" + stockId;
		if(showDialog(url, "", 700, 400)){
			frm.submit();
		}
	}
	
	function select1() {
		frm.submit();
	}
	
	function details(stockid){
		var a = document.getElementById("detail").action;
		var url="${basePath}"+a+"?entity.stockId="+stockid;
		var result =  showDialogRes(url, '', 700, 400);
	}
</SCRIPT>
	</body>
</html>