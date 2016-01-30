<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	
	<head>
		<SCRIPT type="text/javascript">
		jQuery(document).ready(function() {

				//ajax验证交易商代码是否存在
				jQuery("#frm").validationEngine( {
					ajaxFormValidation : true,
					ajaxFormValidationURL : "../..//ajaxCheck/deduct/formCheckDeductKeepByIds.action",
					onAjaxFormComplete : ajaxValidationCallback,
					onBeforeAjaxFormValidation : beforeCall
				});
	
				//提交前事件
				function beforeCall(form, options) {
					return true;
				}
	
				//提交后事件
				function ajaxValidationCallback(status, form, json, options) {
					//如果返回成功
					if (status === true) {
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var addDemoUrl = $("#add").attr("action");
							//全 URL 路径
							var url = "${basePath}"+addDemoUrl;
							$("#frm").attr("action",url);
							frm.submit();
							document.getElementById("add").disabled=true;
						}
					}
				}
	
				//添加按钮注册点击事件
				$("#add").click(function(){
					//验证信息
					if ($("#frm").validationEngine('validateform')) {
					}
				});
	
	
				$("#delete").click(function(check) {
					
					//获取配置权限的 URL
					var delateUrl = document.getElementById('delete').action;
					//获取完整跳转URL
					var url = "${basePath}"+delateUrl;
					//执行删除操作
					updateRMIEcside(ec.ids,url);
					});
	
	
				$("#back").click(function(check) {
					
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						 $("#fm").submit();
				}
				});
	
				$("#next").click(function(check) {
					var url="${basePath}"+document.getElementById("next").action;
							var vaild = affirm("您确定要操作吗？");
							if (vaild == true) {
								$('#fm').attr('action', url);
							    $("#fm").submit();
							    document.getElementById("next").disabled=true;
						}
				});
			
			});
		
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												强减保留交易商设置
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
						<div class="div_cx">
							<form name="frm" id="frm"  action="${basePath}/timebargain/deduct/addKeepFirm.action"  method="post" targetType="hidden">
							<input id="deductId" name="entity.deductId" value="${deduct.deductId }" type="hidden"/>
							<input id="commodityId" name="commodityId" value="${deduct.commodityId}" type="hidden"/>
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															强减日期:&nbsp;
															<label>
																<fmt:formatDate value="${deduct.deductDate}" pattern="yyyy-MM-dd"/>
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															商品代码:&nbsp;
															<label>
																${deduct.commodityId}
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															交易客户代码:&nbsp;
															<label>
																<select name="entity.customerId" id="customerId" class="input_text_pwdmin">
																	<c:forEach items="${customer}" var="cus">
																		<option value="${cus.customerId}" >
																			${cus.customerId}
																		</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left" style="width: 300px">
															买卖标志:&nbsp;
															<label>
																<input type="radio" id="bs_Flag1" name="entity.bs_Flag" value="1" checked="checked" style="border:0px;">买
																<input type="radio" id="bs_Flag2" name="entity.bs_Flag" value="2" style="border:0px;">卖
															</label>
														</td>
														<td class="table3_td_1" align="left" style="width: 300px">
															<span style="color: red">*&nbsp;</span>保留数量:
															<label>
																<input id="keepQty" name="entity.keepQty" value="" style="width: 120px"
																class="validate[required,maxSize[10],custom[number]] "></input>
															</label>
														</td>
														
														<td align="center">
															<rightButton:rightButton name="保存" onclick="" className="btn_sec"
																action="/timebargain/deduct/addKeepFirm.action" id="add" ></rightButton:rightButton>
														&nbsp;&nbsp;&nbsp;
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
						<form name="fm" id="fm"  action="${basePath}/timebargain/deduct/updateDeductPositionForward.action"  method="post" targetType="hidden">
							<input id="deductId1" name="entity.deductId" value="${deduct.deductId }" type="hidden"/>&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="" className="anniu_btn" action="/timebargain/deduct/deleteDeductKeep.action" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="下一步" onclick="" className="anniu_btn" action="/timebargain/deduct/operateDeductDetail.action" id="next"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="返回上一步" onclick="" className="anniu_btn" action="/timebargain/deduct/updateDeductPositionForward.action" id="back"></rightButton:rightButton>
						</form>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="deductKeep"
											action="${basePath}/timebargain/deduct/deductKeepFirmForward.action?deductId=${deduct.deductId}"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deductKeep.xls" csvFileName="deductKeep.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${deductKeep.deductId},${deductKeep.customerId},${deductKeep.bs_Flag}" width="5%" viewsAllowed="html" />
												<ec:column property="customerId" width="33%" title="交易客户代码" ellipsis="true" style="text-align:center;"/>
												<ec:column property="_" title="买卖标志" width="33%" style="text-align:center;" >
												<c:if test="${deductKeep.bs_Flag==1}">买</c:if><c:if test="${deductKeep.bs_Flag==2}">卖</c:if>
												</ec:column>
												<ec:column property="keepQty" title="保留数量" width="33%" style="text-align:center;"/>
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
