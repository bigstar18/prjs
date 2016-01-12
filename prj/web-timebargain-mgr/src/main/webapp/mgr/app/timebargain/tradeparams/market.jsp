<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易市场参数</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 
		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				changeManner("${typeFloat}");

			});

			function changeManner(id){
				if (id == "1") {
					$("#floatingLossComputeType1").attr("disabled",false);
					var $f = $("#floatingLossComputeType2");
					$f.attr("disabled",true);
				}
				if (id == "2") {
					$("#floatingLossComputeType2").attr("disabled",false);
					var $f = $("#floatingLossComputeType1");
					$f.attr("disabled",true);
				}
			}

			function setFloatingLT(value) {
				if (value != "") {
					$("#floatingLossComputeType").val(value);
				}
				
			}
		
		//-->
		</SCRIPT>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改交易市场参数
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="900" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												交易市场参数
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														交易运行模式：
													</td>
													<td>
													    <input type="hidden" name="entity.marketcode" value="${entity.marketcode}" />
													    <input type="hidden" name="entity.shortName" value="${entity.shortName}" />
													    <input type="hidden" name="entity.tradePriceAlgr" value="0" />
														<select id="runMode" name="entity.runMode" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.runMode==0}">selected="selected"</c:if>>手动</option>
										                      <option value="1" <c:if test="${entity.runMode==1}">selected="selected"</c:if>>自动</option>
														</select>
													</td>
													
													<td align="right">
														<span class="required">*</span>
														抵顶模式：
													</td>
													<td>
													    <select id="gageMode" name="entity.gageMode" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.gageMode==0}">selected="selected"</c:if>>全抵顶</option>
										                      <option value="1" <c:if test="${entity.gageMode==1}">selected="selected"</c:if>>半抵顶</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														保证金调整时间：
													</td>
													<td>
														<select id="marginFBFlag" name="entity.marginFBFlag" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.marginFBFlag==0}">selected="selected"</c:if>>结算时调整</option>
										                      <option value="1" <c:if test="${entity.marginFBFlag==1}">selected="selected"</c:if>>开市时调整</option>
														</select>
													</td>
													
													<td align="right">
													    <span class="required">*</span>
														交易时间类型：
													</td>
													<td>
														<select id="tradeTimeType" name="entity.tradeTimeType" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.tradeTimeType==0}">selected="selected"</c:if>>同一天交易</option>
										                      <option value="1" <c:if test="${entity.tradeTimeType==1}">selected="selected"</c:if>>跨天交易</option>
														</select>
													</td>
												</tr>
												<!--  取消浮动盈亏是否扣税字段  duanbaodi 20150731 
												<tr><! -- 交收数据生成方式默认自动
													<td align="right">
														<span class="required">*</span>
														交收数据生成方式：
													</td>
													<td>
														<select id="settleMode" name="entity.settleMode" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.settleMode==0}">selected="selected"</c:if>>自动</option>
										                      <option value="1" <c:if test="${entity.settleMode==1}">selected="selected"</c:if>>手动</option>
														</select>
													</td> -- >
													
													<td align="right">
														<span class="required">*</span>
														浮动盈亏是否扣税：
													</td>
													<td colspan="3">
														<select id="floatingProfitSubTax" name="entity.floatingProfitSubTax" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.floatingProfitSubTax==0}">selected="selected"</c:if>>不扣税</option>
										                      <option value="1" <c:if test="${entity.floatingProfitSubTax==1}">selected="selected"</c:if>>扣税</option>
														</select>
													</td>
												
												</tr>   -->
												<c:choose>
												<c:when test="${useDelay == 'Y'}">
												<tr>
													<td align="right">
														<span class="required">*</span>
														延期交收行情显示类型：
													</td>
													<td>
														<select id="delayQuoShowType" name="entity.delayQuoShowType" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.delayQuoShowType==0}">selected="selected"</c:if>>交收和中立仓显示</option>
										                      <option value="1" <c:if test="${entity.delayQuoShowType==1}">selected="selected"</c:if>>实时显示</option>
														</select>
													</td>
													
													<td align="right">
													    <span class="required">*</span>
														中立仓交收手续费收取方式：
													</td>
													<td>
														<select id="neutralFeeWay" name="entity.neutralFeeWay" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.neutralFeeWay==0}">selected="selected"</c:if>>不收</option>
										                      <option value="1" <c:if test="${entity.neutralFeeWay==1}">selected="selected"</c:if>>收取</option>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">
														<span class="required">*</span>
														延期补偿金收取类型：
													</td>
													<td>
														<select id="chargeDelayFeeType" name="entity.chargeDelayFeeType" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.chargeDelayFeeType==0}">selected="selected"</c:if>>按净订货量收取</option>
										                      <option value="1" <c:if test="${entity.chargeDelayFeeType==1}">selected="selected"</c:if>>按单边订货总量收取</option>
														</select>
													</td>
													<td align="right">
													    <span class="required">*</span>
														交收申报是否按净订货量：
													</td>
													<td>
														<select id="delayOrderIsPure" name="entity.delayOrderIsPure" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.delayOrderIsPure==0}">selected="selected"</c:if>>否</option>
										                      <option value="1" <c:if test="${entity.delayOrderIsPure==1}">selected="selected"</c:if>>是</option>
														</select>
													</td>
												</tr>
												</c:when>
												<c:otherwise>
													<input type="hidden" name="entity.delayQuoShowType" value="${entity.delayQuoShowType }" />
													<input type="hidden" name="entity.neutralFeeWay" value="${entity.neutralFeeWay }" />
													<input type="hidden" name="entity.chargeDelayFeeType" value="${entity.chargeDelayFeeType }" />
													<input type="hidden" name="entity.delayOrderIsPure" value="${entity.delayOrderIsPure }" />
												</c:otherwise>
												</c:choose>
												<tr>
													<td align="right">
														<span class="required">*</span>
														提前交收是否收取保证金：
													</td>
													<td>
														<select id="asMarginType" name="entity.asMarginType" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.asMarginType==0}">selected="selected"</c:if>>不收取</option>
										                      <option value="1" <c:if test="${entity.asMarginType==1}">selected="selected"</c:if>>收取</option>
														</select>
													</td>
													<td align="right">
													    <span class="required">*</span>
														是否启用中立仓：
													</td>
													<td>
														<select id="neutralFlag" name="entity.neutralFlag" class="validate[required]" style="width:120">
															  <option value=""></option>
									                          <option value="0" <c:if test="${entity.neutralFlag==0}">selected="selected"</c:if>>否</option>
										                      <option value="1" <c:if test="${entity.neutralFlag==1}">selected="selected"</c:if>>是</option>
														</select>
													</td>
												</tr>
												<tr>
													<td colspan="4">
													<fieldset class="pickList">
														<legend class="common">
															<b>浮亏计算方式
															</b>
														</legend>

													<table border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
					
													<tr>
				
															<td>
																<input type="radio" name="type" onclick="changeManner(1);" <c:if test="${typeFloat=='1'}">checked</c:if> style="border:0px;">						
															</td>
											                <td>
																	盈亏对冲计亏不计盈：
															</td>
															<td>
																<select id="floatingLossComputeType1" class="validate[required]" onchange="setFloatingLT(this.value)">
																   <option value=""></option> 
										                           <option value="0" <c:if test="${entity.floatingLossComputeType==0}">selected="selected"</c:if>>同商品同方向盈亏对冲</option>
											                       <option value="1" <c:if test="${entity.floatingLossComputeType==1}">selected="selected"</c:if>>同商品盈亏对冲</option>
											                       <option value="2" <c:if test="${entity.floatingLossComputeType==2}">selected="selected"</c:if>>所有商品盈亏对冲</option>
									                            </select>
															</td>	
															
															<td>&nbsp;</td><td>&nbsp;</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<input type="hidden" id="floatingLossComputeType" name="entity.floatingLossComputeType" value="${entity.floatingLossComputeType }" />
															
															<td>
																<input type="radio" name="type" onclick="changeManner(2);" <c:if test="${typeFloat=='2'}">checked</c:if> style="border:0px;">
															</td>
											                <td>
																	无负债模式：
															</td>
															<td>
																<select id="floatingLossComputeType2" class="validate[required]" style="width:120" onchange="setFloatingLT(this.value)">
																   <option value=""></option> 
										                           <option value="3" <c:if test="${entity.floatingLossComputeType==3}">selected="selected"</c:if>>实时无负债</option>
											                       <option value="4" <c:if test="${entity.floatingLossComputeType==4}">selected="selected"</c:if>>每日无负债</option>
									                            </select> 
															</td>	
		
							        					</tr>					
													</table>
													</fieldset>
													</td>
												</tr>
												
												<tr>
													<td colspan="4" align="center">                                                  
														<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/timebargain/tradeparams/updatemarket.action" id="update"></rightButton:rightButton>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>