<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%--设置商品是否含税 后续操作可以直接使用 1为不含税 0为含税 --%>
<c:set value="${entity.taxIncluesive}" var="WT" scope="page"/>
<html>
<head>
<title>付卖方货款信息</title>

<link rel="stylesheet"
	href="${skinPath }/css/validationengine/validationEngine.jquery.css"
	type="text/css" />
<link rel="stylesheet"
	href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script
	src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="GBK"></script>
<script
	src="${publicPath }/js/validationengine/jquery.validationEngine.js"
	type="text/javascript" charset="GBK"></script>
<script type="text/javascript">
	function checkNum(isFirst) {
		//卖方升贴水后货款
		var sellAll = frm.totalMoney.value;
		//已付卖方货款(因为交收配对时 已经收过税 所以你懂的)
		var sellIncome = ${entity.sellIncome+entity.buyTax};
		//应付货款比例
		var percent = parseFloat((((sellAll - sellIncome) / sellAll) * 100)
				.toFixed(2));
		//用户输入的比例小于等于计算出来的比例
		var userPercent = parseFloat(((frm.percent.value * 100) / 100)
				.toFixed(2));
		//如果是第一次进入此页面(既默认80%)
		if (isFirst == 1) 
		{
			if(percent>=80.00)
			{
				//默认百分之80
				frm.percent.value=80.00;
				var percentMoney = (frm.totalMoney.value) * 0.01*80.00;
				frm.thisPayMent.value = percentMoney.toFixed(2);
			}
			else
			{
				//剩下的为尾款比例
				frm.percent.value=percent;
				var percentMoney = (frm.totalMoney.value) * 0.01*percent;
				frm.thisPayMent.value = percentMoney.toFixed(2);
			}
		} 
		else 
		{
			//用户输入的比例小于等于计算出的比例
			if (frm.percent.value != '' && userPercent <= percent
				&& Number(frm.percent.value)) 
			{
				var percentMoney = (frm.totalMoney.value) * 0.01* (frm.percent.value);
				frm.thisPayMent.value = percentMoney.toFixed(2);
			} else 
			{
				return "* 百分比区间为0~" + percent + "%";
			}
		}
	}

	//判断小数点是否为后2位
	function checkPoint(Obj) {
		var val = Obj[0].value;
		if (val.indexOf(".") != -1
				&& val.substring(val.indexOf(".") + 1, val.length).length > 2) {
			return "* 小数点后面为2位";
		}
		;
	}
	
	//当用户手动输入值时，上方百分比清空
	function hidden() {
		frm.percent.value='';
		
	}

	jQuery(document).ready(function() {
		$("#frm").validationEngine('attach');

		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				if (Number(frm.thisPayMent.value) == 0) {
					alert("请输入非零金额！");
				} else {
					var vaild = affirm("您确定要操作吗？");
					if (vaild == true) {
						$("#frm").validationEngine('attach');
						//$('#frm').attr('action', 'action');
						$("#frm").submit();
						//document.getElementById("update").disabled=true;
					}
				}
			}
		})
	});
</script>

</head>
<body onload="checkNum(1);">
	<form id="frm" enctype="multipart/form-data" method="post"
		targetType="hidden"
		action="${basePath}/timebargain/settle/matchDispose/payPayout.action?entity.matchID=${entity.matchID}">
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">温馨提示 :交收配对【${entity.matchID}】付卖方货款信息</div>
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
										<div class="div_cxtjC">付卖方货款信息</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="8" width="100%"
											align="center" class="table2_style">
											<tr>
												<td align="right">卖方交易商代码：</td>
												<td>${entity.firmID_S }&nbsp;</td>
											</tr>
											<tr>
												<td align="right">商品代码：</td>
												<td>${entity.commodityID}&nbsp;</td>
											</tr>
											<tr>
												<td align="right">交收数量：</td>
												<td>${entity.quantity }&nbsp;</td>
											</tr>
											<tr>
												<td align="right">卖方交收价：</td>
												<td><fmt:formatNumber value="${entity.sellPrice }"
														pattern="#,##0.00" />&nbsp;</td>
											</tr>
											<tr>
												<td align="right">卖方基准货款：</td>
												<td><fmt:formatNumber
														value="${entity.sellIncome_Ref }" pattern="#,##0.00" />&nbsp;
												</td>
											</tr>
											<tr>
					  						<td align="right">税费：</td>
											<td>
											${entity.buyTax }&nbsp;
											</td>
					 						 </tr>
											<tr>
												<td align="right"><b>卖方升贴水后货款：</b></td>
												<td>
												  <fmt:formatNumber value="${entity.sellIncome_Ref + entity.HL_Amount }" pattern="#,##0.00"/>
<%-- 												  <fmt:formatNumber value="${entity.sellIncome_Ref + entity.HL_Amount +entity.buyTax }" pattern="#,##0.00"/> --%>
												&nbsp;
												</td>
											</tr>
											<tr>
												<td align="right"><b>已付卖方货款：</b></td>
												<td>
						  					<fmt:formatNumber value="${entity.sellIncome}" pattern="#,##0.00"/>
<%-- 						  					<fmt:formatNumber value="${entity.sellIncome+entity.buyTax }" pattern="#,##0.00"/> --%>
												&nbsp;
												</td>
											</tr>
											<tr>
												<td align="right">付升贴水后货款百分比：</td>
												<td><input
													class="validate[custom[number],funcCall[checkNum]]"
													id="percent" name="percent" size="12"/><font
													color="red">%</font> <input type="hidden" name="totalMoney"
													value="${entity.sellIncome_Ref+entity.HL_Amount}">
<%-- 													value="${entity.sellIncome_Ref+entity.HL_Amount+entity.buyTax }"> --%>
												</td>
											</tr>
											<tr>
												<td align="right"><span class='required'>*</span>本次付货款：</td>
												<td><input
													class="validate[required,custom[number],funcCall[checkPoint],funcCall[hidden]]"
													id="thisPayMent" name="thisPayMent" size="12" /></td>
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

		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="10" width="100%"
				align="center">
				<tr align="center">
					<td><rightButton:rightButton name="保存" onclick=""
							className="btn_sec"
							action="/timebargain/settle/matchDispose/payPayout.action"
							id="update"></rightButton:rightButton></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>