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
	<script>
	function checkRate1(){
		var rate1 = document.getElementById("profitLvl1").value*1;
		if(!flote(rate1,1)){
			return "*分级比例率小数后面为1位";
			}
		else if(rate1<0){
			return "*盈利分级比例率1必须大于0";
			}
	}
	function checkRate2(){
		var rate1 = document.getElementById("profitLvl1").value*1;
		var rate2 = document.getElementById("profitLvl2").value*1;
		if(!flote(rate2,1)){
			return "*分级比例率小数后面为1位";
			}
		else if(rate2>rate1||rate2<0){
			return "*盈利分级比例率2必须大于0且小于等于盈利分级比例1";
			}
	}
	function checkRate3(){
		var rate2 = document.getElementById("profitLvl2").value*1;
		var rate3 = document.getElementById("profitLvl3").value*1;
		if(!flote(rate3,1)){
			return "*分级比例率小数后面为1位";
			}
		else if(rate3>rate2||rate3<0){
			return "*盈利分级比例率3必须大于0且小于等于盈利分级比例2";
			}
	}
	function checkRate4(){
		var rate3 = document.getElementById("profitLvl3").value*1;
		var rate4 = document.getElementById("profitLvl4").value*1;
		if(!flote(rate4,1)){
			return "*分级比例率小数后面为1位";
			}
		else if(rate4>rate3||rate4<0){
			return "*盈利分级比例率4必须大于0且小于等于盈利分级比例3";
			}
	}
	function checkRate5(){
		var rate4 = document.getElementById("profitLvl4").value*1;
		var rate5 = document.getElementById("profitLvl5").value*1;
		if(!flote(rate5,1)){
			return "*分级比例率小数后面为1位";
			}
		else if(rate5>rate4||rate5<0){
			return "*盈利分级比例率5必须大于0且小于等于盈利分级比例4";
			}
	}

	function checkprice(){
		var deductPrice = document.getElementById("deductPrice").value;
		if(!flote(deductPrice,2)){
			return "*强减价格小数后面为2位";
			}
		
	}

	//控制自我对冲隐现
	function selfCounteract_onclick(){
		if (document.getElementById("mode").value == "2") {
			document.getElementById("selfCounteract").style.display = "block";
		}else {
			document.getElementById("selfCounteract").style.display = "none";
		}
	}
	
	jQuery(document).ready(function() {
		
		  $("#frm").validationEngine('attach');
			
			$("#update").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						$("#frm").validationEngine('attach');
						//$('#frm').attr('action', 'action');
					    $("#frm").submit();
					    document.getElementById("update").disabled=true;
				}
			}})
	});
</script>
	<head>
		<title>强制减仓配置</title>
	</head>
	<body onload="selfCounteract_onclick();">
		<form id="frm" enctype="multipart/form-data" action="${basePath}/timebargain/deduct/updateDeductPosition.action" method="post" targetType="hidden">
		<input id="deductId" name="entity.deductId" value="${entity.deductId }" type="hidden"/>
			<div class="div_cx">
				<table border="0" width="50%" align="left"  class="table_left_style">
					<tr>
						<td>
							<table border="0"  width="80%" align="left">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :强制减仓配置<br><font color="red">注意：盈利分级比例 P1>=P2>=P3>=P4>=P5</font>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												强制减仓基本信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="150px" align="center" class="table2_style">
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>强减日期:
													</td>
													<td align="left" width="30%">
														<input type="text" style="width: 120px" id="deductDate"
																	class="wdate;validate[required]" maxlength="10"
																	name="entity.deductDate"
																	value='<fmt:formatDate value='${entity.deductDate}' pattern="yyyy-MM-dd"/>'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
													</td>
													<td align="right" width="20%">
														商品代码:
													</td>
													<td align="left" width="30%">
														<select name="entity.commodityId" id="com" 
															class="input_text_pwdmin">
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" <c:if test="${entity.commodityId==props['COMMODITYID']}">selected</c:if>>
																	${props['NAME']}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>盈利分级比例1:
													</td>
													<td align="left" width="30%">
														<input id="profitLvl1" name="entity.profitLvl1" value="<fmt:formatNumber value='${entity.profitLvl1 }' />" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkRate1]]"></input></input><span >%</span>
													</td>
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>强减价格:
													</td>
													<td align="left" width="30%">
														<input id="deductPrice" name="entity.deductPrice" value="${entity.deductPrice }" style="width: 120px"
															class="validate[required,custom[number],funcCall[checkprice]] "></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>盈利分级比例2:
													</td>
													<td align="left" width="30%">
														<input id="profitLvl2" name="entity.profitLvl2" value="<fmt:formatNumber value='${entity.profitLvl2 }' />" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkRate2]] "></input></input><span >%</span>
													</td>
													<td align="right" width="20%">
														亏损方买卖标志:
													</td>
													<td align="left" width="30%">
														<select name="entity.loserBSFlag" id="loser" 
															class="input_text_pwdmin">
															<c:forEach items="${loserBSFlagMap}" var="props">
																<option value="${props.key}" title='${props.value}'
																	<c:if test="${loser.propsValue==props.key }">selected="selected"</c:if>>
																	${props.value}
																</option>
															</c:forEach>
														</select>
															<script type="text/javascript">document.getElementById("loser").value=${entity.loserBSFlag };</script>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>盈利分级比例3:
													</td>
													<td align="left" width="30%">
														<input id="profitLvl3" name="entity.profitLvl3" value="<fmt:formatNumber value='${entity.profitLvl3 }' />" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkRate3]] "></input></input><span >%</span>
													</td>
													<td align="right" width="20%">
														亏损方强减模式:
													</td>
													<td align="left" width="30%">
														<select name="entity.loserMode" id="mode" onchange="selfCounteract_onclick()"
															class="input_text_pwdmin">
															<c:forEach items="${loserModeMap}" var="props">
																<option value="${props.key}" title='${props.value}'
																	<c:if test="${mode.propsValue==props.key }">selected="selected"</c:if>>
																	${props.value}
																</option>
															</c:forEach>
														</select>
																<script type="text/javascript">document.getElementById("mode").value=${entity.loserMode };</script>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>盈利分级比例4:
													</td>
													<td align="left" width="30%">
														<input id="profitLvl4" name="entity.profitLvl4"  style="width: 120px" value="<fmt:formatNumber value='${entity.profitLvl4 }' />" 
															class="validate[required,custom[integer],funcCall[checkRate4]] "></input></input><span >%</span>
													</td>
													<td align="right" width="20%">
														是否对冲:
													</td>
													<td align="left" width="30%">
														<input type="radio" name="entity.selfCounteract" value="0" <c:if test="${entity.selfCounteract==0 }" >checked="checked"</c:if> style="border:0px;">否
														<input type="radio" name="entity.selfCounteract" value="1" <c:if test="${entity.selfCounteract==1 }" >checked="checked"</c:if> style="border:0px;">所有会员双向订货对冲
														<div id="selfCounteract" style="display: none"><input type="radio" name="entity.selfCounteract" value="2" <c:if test="${entity.selfCounteract==2 }" >checked="checked"</c:if> style="border:0px;">超净订货申报对冲</div>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>盈利分级比例5:
													</td>
													<td align="left" width="30%">
														<input id="profitLvl5" name="entity.profitLvl5" value="<fmt:formatNumber value='${entity.profitLvl5 }' />" style="width: 120px"
															class="validate[required,custom[integer],funcCall[checkRate5]] "></input><span >%</span>
													</td>
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>亏损比例:
													</td>
													<td align="left" width="30%">
														<input id="lossRate" name="entity.lossRate" value="<fmt:formatNumber value='${entity.lossRate }' />" style="width: 120px"
															class="validate[required,custom[integer]] "></input><span >%</span>
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
			<table border="0" cellspacing="0" cellpadding="4" width="600px"
				align="left">
				<tr>
					<td align="center">
						<rightButton:rightButton name="保存" onclick="" className="btn_sec"
							action="/timebargain/deduct/updateDeductPosition.action" id="update" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
