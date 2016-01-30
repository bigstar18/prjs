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

	function checkprice(){
		var price = document.getElementById("price").value;
		if(!flote(price,2)){
			return "*委托价格小数后面为2位";
			}
		
	}

	
	jQuery(document).ready(function() {
		
		  $("#frm").validationEngine('attach');
			
			$("#add").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						$("#frm").validationEngine('attach');
						//$('#frm').attr('action', 'action');
					    $("#frm").submit();
					    document.getElementById("add").disabled=true;
				}
			}})
	});
</script>
	<head>
		<title>强制委托录入</title>
	</head>
	<body>
		<form id="frm"  action="${basePath}/timebargain/deduct/addDeductOrder.action" method="post" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="50%" align="left"  class="table_left_style">
					<tr>
						<td>
							<table border="0"  width="80%" align="left">
								<tr>
									<td>
										<div class="warning">
											<div class="content">
												温馨提示 :强制委托录入
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												强制委托录入基本信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_tj">
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" height="150px" align="center" class="table2_style">
												<tr height="40">
													<td  align="right" width="20%" >
														交易客户代码:&nbsp;
													</td>
													<td  align="left" width="30%">
															<label>
																<select name="customerId" id="customerId" class="input_text_pwdmin">
																	<c:forEach items="${customer}" var="cus">
																		<option value="${cus.customerId}" >
																			${cus.customerId}
																		</option>
																	</c:forEach>
																</select>
															</label>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														商品代码:
													</td>
													<td align="left" width="30%">
														<select name="commodityId" id="com" 
															class="input_text_pwdmin">
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" >
																	${props['NAME']}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr height="40">
													<td  align="right" width="20%" >
														买卖标志:&nbsp;
													</td>
													<td align="left" width="30%">
														<label>
															<input type="radio" name="buyOrSell" value="1" checked="checked" style="border:0px;">买
															<input type="radio" name="buyOrSell" value="2" style="border:0px;">卖
														</label>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>委托价格:
													</td>
													<td align="left" width="30%">
														<input id="price" name="price"  style="width: 120px"
															class="validate[required,custom[number],funcCall[checkprice]] "></input>
													</td>
												</tr>
												<tr height="40">
													<td align="right" width="20%">
														<span style="color: red">*&nbsp;</span>委托数量:
													</td>
													<td align="left" width="30%">
														<input id="quantity" name="quantity"  style="width: 120px"
															class="validate[required,custom[integer]] "></input>
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
							action="/timebargain/deduct/addDeductOrder.action" id="add" ></rightButton:rightButton>
					&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
