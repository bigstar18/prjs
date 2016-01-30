<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%--������Ʒ�Ƿ�˰ ������������ֱ��ʹ�� 1Ϊ����˰ 0Ϊ��˰ --%>
<c:set value="${entity.taxIncluesive}" var="WT" scope="page"/>
<html>
<head>
<title>������������Ϣ</title>

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
		//��������ˮ�����
		var sellAll = frm.totalMoney.value;
		//�Ѹ���������(��Ϊ�������ʱ �Ѿ��չ�˰ �����㶮��)
		var sellIncome = ${entity.sellIncome+entity.buyTax};
		//Ӧ���������
		var percent = parseFloat((((sellAll - sellIncome) / sellAll) * 100)
				.toFixed(2));
		//�û�����ı���С�ڵ��ڼ�������ı���
		var userPercent = parseFloat(((frm.percent.value * 100) / 100)
				.toFixed(2));
		//����ǵ�һ�ν����ҳ��(��Ĭ��80%)
		if (isFirst == 1) 
		{
			if(percent>=80.00)
			{
				//Ĭ�ϰٷ�֮80
				frm.percent.value=80.00;
				var percentMoney = (frm.totalMoney.value) * 0.01*80.00;
				frm.thisPayMent.value = percentMoney.toFixed(2);
			}
			else
			{
				//ʣ�µ�Ϊβ�����
				frm.percent.value=percent;
				var percentMoney = (frm.totalMoney.value) * 0.01*percent;
				frm.thisPayMent.value = percentMoney.toFixed(2);
			}
		} 
		else 
		{
			//�û�����ı���С�ڵ��ڼ�����ı���
			if (frm.percent.value != '' && userPercent <= percent
				&& Number(frm.percent.value)) 
			{
				var percentMoney = (frm.totalMoney.value) * 0.01* (frm.percent.value);
				frm.thisPayMent.value = percentMoney.toFixed(2);
			} else 
			{
				return "* �ٷֱ�����Ϊ0~" + percent + "%";
			}
		}
	}

	//�ж�С�����Ƿ�Ϊ��2λ
	function checkPoint(Obj) {
		var val = Obj[0].value;
		if (val.indexOf(".") != -1
				&& val.substring(val.indexOf(".") + 1, val.length).length > 2) {
			return "* С�������Ϊ2λ";
		}
		;
	}
	
	//���û��ֶ�����ֵʱ���Ϸ��ٷֱ����
	function hidden() {
		frm.percent.value='';
		
	}

	jQuery(document).ready(function() {
		$("#frm").validationEngine('attach');

		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				if (Number(frm.thisPayMent.value) == 0) {
					alert("����������");
				} else {
					var vaild = affirm("��ȷ��Ҫ������");
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
							<div class="content">��ܰ��ʾ :������ԡ�${entity.matchID}��������������Ϣ</div>
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
										<div class="div_cxtjC">������������Ϣ</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="8" width="100%"
											align="center" class="table2_style">
											<tr>
												<td align="right">���������̴��룺</td>
												<td>${entity.firmID_S }&nbsp;</td>
											</tr>
											<tr>
												<td align="right">��Ʒ���룺</td>
												<td>${entity.commodityID}&nbsp;</td>
											</tr>
											<tr>
												<td align="right">����������</td>
												<td>${entity.quantity }&nbsp;</td>
											</tr>
											<tr>
												<td align="right">�������ռۣ�</td>
												<td><fmt:formatNumber value="${entity.sellPrice }"
														pattern="#,##0.00" />&nbsp;</td>
											</tr>
											<tr>
												<td align="right">������׼���</td>
												<td><fmt:formatNumber
														value="${entity.sellIncome_Ref }" pattern="#,##0.00" />&nbsp;
												</td>
											</tr>
											<tr>
					  						<td align="right">˰�ѣ�</td>
											<td>
											${entity.buyTax }&nbsp;
											</td>
					 						 </tr>
											<tr>
												<td align="right"><b>��������ˮ����</b></td>
												<td>
												  <fmt:formatNumber value="${entity.sellIncome_Ref + entity.HL_Amount }" pattern="#,##0.00"/>
<%-- 												  <fmt:formatNumber value="${entity.sellIncome_Ref + entity.HL_Amount +entity.buyTax }" pattern="#,##0.00"/> --%>
												&nbsp;
												</td>
											</tr>
											<tr>
												<td align="right"><b>�Ѹ��������</b></td>
												<td>
						  					<fmt:formatNumber value="${entity.sellIncome}" pattern="#,##0.00"/>
<%-- 						  					<fmt:formatNumber value="${entity.sellIncome+entity.buyTax }" pattern="#,##0.00"/> --%>
												&nbsp;
												</td>
											</tr>
											<tr>
												<td align="right">������ˮ�����ٷֱȣ�</td>
												<td><input
													class="validate[custom[number],funcCall[checkNum]]"
													id="percent" name="percent" size="12"/><font
													color="red">%</font> <input type="hidden" name="totalMoney"
													value="${entity.sellIncome_Ref+entity.HL_Amount}">
<%-- 													value="${entity.sellIncome_Ref+entity.HL_Amount+entity.buyTax }"> --%>
												</td>
											</tr>
											<tr>
												<td align="right"><span class='required'>*</span>���θ����</td>
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
					<td><rightButton:rightButton name="����" onclick=""
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