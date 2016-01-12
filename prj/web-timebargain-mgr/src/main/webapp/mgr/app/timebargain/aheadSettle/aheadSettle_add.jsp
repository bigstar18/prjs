<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>提交交收添加</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			if(${result!=''} && ${result!=null}){
				alert('${result }');
			}
			$(function(){
				<c:if test="${not empty json}">
					var cities =${json};
					$("#customerId_S").change().autocomplete(cities);
					$("#customerId_B").change().autocomplete(cities);
				</c:if>
			});
			
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//添加按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var qty = frm.quantity.value;
						var ids = document.getElementsByName("ids");
						var idsString ="";	
						var sum = 0;
						for(var i= 0; i< ids.length; i++){
							if(ids[i].checked){
								idsString = idsString + ids[i].value.split('_')[0] + ",";
								var num = ids[i].value.split('_')[1];
								if((num*1)%1 !=0){
									alert("所选仓单数量不能为小数,请重新选择");
									return;
								}
								sum = (sum*1) + (num*1);
							}
						}
						if(qty==sum){
							var addUrl = document.getElementById('add').action;
							//获取完整跳转URL
							frm.action = "${basePath}"+addUrl+"?ids=" + idsString;
							frm.submit();
						}else{
							alert("所选仓单数量不等于交收数量,请重新选择");
						}
					}
				});
				
				
				$("#search").click(function(){
					//验证信息
					if(frm.commodityId.value==""){
						alert("商品代码不能为空");
						return;
					}
					if(frm.customerId_S.value==""){
						alert("卖方二级代码不能为空");
						return;
					}
					if(frm.quantity.value==""){
						alert("交收数量不能为空");
						return;
					}
					if(isNaN(frm.quantity.value)){
						alert("交收数量必须为数字");
						return;
					}
					frm.submit();
				});
			});
			
			function onload(){
				var priceType = frm.priceType.value;
				if(priceType==1){
					document.getElementById("priceDivContext").style.display = "inline";
					document.getElementById("priceDivInput").style.display = "inline";
				}
			
			}			
			
			function showSettlePrice(commodityId){
				if(commodityId!=""){
					var oldAjaxAsync = $.ajaxSettings.async;
					var url = "${basePath}/ajaxcheck/aheadSettle/getSettlePriceType.action?commodityId=" + commodityId;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function call(result){
						if(result==1){
							document.getElementById("priceDivContext").style.display = "inline";
							document.getElementById("priceDivInput").style.display = "inline";
							frm.priceType.value = "1";
						}else if(result==0){
							frm.price.value = "0";
							document.getElementById("priceDivContext").style.display = "none";
							document.getElementById("priceDivInput").style.display = "none";
							frm.priceType.value = "0";
						}else{
							alert("商品代码不存在");
							frm.price.value = "0";
							document.getElementById("priceDivContext").style.display = "none";
							document.getElementById("priceDivInput").style.display = "none";
							frm.priceType.value = "";
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}		
			}	
		</script>
	</head>
	<body onload="onload();">
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :填写商品代码、卖方二级代码和交收数量查询出仓单，然后勾选仓单后填写买方二级代码、价格和备注等信息后提交。
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											提前交收申请
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div>
										<form id="frm" method="post" enctype="multipart/form-data" action="${basePath}/timebargain/aheadSettle/getBillList.action">
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														商品代码：
													</td>
													<td  width="15%">
														<select name="entity.commodityId" id="commodityId"  onchange="showSettlePrice(this.value)"
															class="input_text_pwdmin">
															<option value="">请选择</option>
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" <c:if test="${commodityId==props['COMMODITYID']}">selected</c:if>>
																	${props['COMMODITYID']}
																</option>
															</c:forEach>
														</select>	
														<input type="hidden" id="priceType" name="priceType" value="${priceType }"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														卖方二级代码：
													</td>
													<td width="15%">
														<input type="text" id="customerId_S" name="entity.customerId_S" value="${customerId_S }"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text datepicker"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														交收数量：
													</td>
													<td width="15%">
														<input type="text" id="quantity" name="entity.quantity" value="${quantity }"
															class="validate[required,custom[integer],maxSize[10]] input_text datepicker"/>
													</td>
													<td width="10%">
														<rightButton:rightButton name="查询" onclick="" className="btn_sec" action="/timebargain/aheadSettle/getBillList.action" id="search"></rightButton:rightButton>
													</td>
												</tr>
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														买方二级代码：
													</td>
													<td  width="15%">
														<input type="text" id="customerId_B" name="entity.customerId_B" value="${customerId_B }"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text datepicker"/>
													</td>
													<td align="right" width="15%" style="display: none;" id="priceDivContext">
														<span class="required">*</span>
														价格：
													</td>
													<td width="15%" style="display: none;" id="priceDivInput">
														<input type="text" id="price" name="entity.price" value="${price }"
															class="validate[required,maxSize[10]] input_text datepicker"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														备注：
													</td>
													<td width="15%">
														<input type="text" id="remark1" name="entity.remark1" value="${remark1 }"
															class="validate[required,maxSize[126]] input_text datepicker"/>
													</td>
												</tr>
											</table>
										</form>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										<tr>
											<td>
												<ec:table items="pageInfo.result" var="bill"
													action="${basePath}/timebargain/aheadSettle/getBillList.action"											
													autoIncludeParameters="${empty param.autoInc}"
													xlsFileName="demo.xls" csvFileName="demo.csv"
													showPrint="true" listWidth="100%"
													minHeight="345"  style="table-layout:fixed;"
													toolbarContent="status" sortable="false"
													rowsDisplayed="${size}">
													<ec:row>
														<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${bill.STOCKID}_${bill.STOCKNUM}" width="5%" viewsAllowed="html" />
														<ec:column property="STOCKID"  alias="remarkHdd" width="15%" title="仓单号" style="text-align:center;" />
														<ec:column property="WAREHOUSEID" title="仓库编号" width="15%" style="text-align:center;"/>
														<ec:column property="BREEDNAME" title="品种名称" width="15%" style="text-align:center;"/>
														<ec:column property="QTYUNIT" title="商品数量" width="15%" style="text-align:center;">
														${bill.QUANTITY}&nbsp;${bill.UNIT}
														</ec:column>
														<ec:column property="STOCKNUM" title="仓单数量" width="15%" style="text-align:center;"/>
														<ec:column property="LASTTIME" title="最后变更时间" width="20%" style="text-align:center;">
														  <fmt:formatDate value="${bill.LASTTIME }" pattern="yyyy-MM-dd HH:mm:ss" />
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
					</td>
				</tr>
				<tr>
					<td align="right">
						<rightButton:rightButton name="添加" onclick="" className="btn_sec" action="/timebargain/aheadSettle/addAheadSettle.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">关闭</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>