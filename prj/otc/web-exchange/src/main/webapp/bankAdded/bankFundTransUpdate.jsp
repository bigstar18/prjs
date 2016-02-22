<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>手续费划转</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/bankFunds/poundageChange/update.action"
			method="post" targetType="hidden">
			<div class="div_scro">
				<table border="0" height="300" width="80%" align="center">
					<tr height="100"></tr>
					<tr>
						<td>
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="2">
								<tr>
									<td colspan="4">
										<div class="st_title">
											<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
											&nbsp;&nbsp;手续费划转
										</div>
											<table width="421" border="0" class="table2_style">
												<tr height="35">
													<input type="hidden" name="obj.bankCode" value="${obj.bankCode}" />
													<td width="40%" align="right">
														银行名称:
													</td>
													<td>
														<input type="hidden" name="obj.bankName" value="${obj.bankName}" />
														${obj.bankName}
													</td>
												</tr>
												<tr height="35">
													<td align="right">
														手续费余额:
													</td>
													<td>
														<input type="hidden" id="marketFeeBalance" name="obj.marketFeeBalance" value="${obj.marketFeeBalance}"/>
														<fmt:formatNumber value="${obj.marketFeeBalance}" type="currency" pattern="0.00" />
													</td>
												</tr>
												<tr height="35">
													<td align="right">
														划转金额:
													</td>
													<td>
														<input type="text" class="input_text" id="funds" name="funds" onkeypress="return regInput(this,/^\d*\.?\d{0,2}$/,String.fromCharCode(event.keyCode));" 
															value="<fmt:formatNumber value='${obj.marketFeeBalance}' type="currency" pattern="0.00" />"/>
															
													</td>
												</tr>
											</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center">
							<span id="msg"><font color="red" size="4">当前时间不允许划转</font></span>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="80%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateFunds()" id="update" />
								提交
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
if (${!isTradeDate}) {
	document.getElementById('update').disabled = 'disabled';
} else {
	document.getElementById('msg').style.display = "none";
}
function updateFunds() {
	if(document.getElementById('funds').value == 0 || document.getElementById('funds').value == ""){
		alert("划转金额必须大于0！");
		return false;
	}
	var marketFeeBalance = document.getElementById('marketFeeBalance').value;
	var funds = document.getElementById('funds').value;
	if(marketFeeBalance < parseFloat(funds)){
		alert('划转金额大于手续费余额，请重新输入！');
		document.getElementById('funds').value = "";
		document.getElementById('funds').focus();
		return false;
	}
	var money = convertCurrency(document.getElementById('funds').value, false);
	var msg = "从${obj.bankName}划转出手续费：" + money + "，您确定划转吗？"
	var vaild = window.confirm(msg);
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
	
}
function checkNumber(e, txt) { 
	var key = window.event ? e.keyCode : e.which; 
	var keychar = String.fromCharCode(key); 
	reg = /\d|\./; 
	var result = reg.test(keychar); 
	if(result) {
		if(e.keyCode==46) {
			result=!(txt.value.split('.').length>1);
		} else {
			result=!(txt.value.split('.').length>1&&txt.value.split('.')[1].length>1); 
		}
    } 
	if(!result) { 
		return false; 
    } else { 
		return true; 
	} 
}
function regInput(obj, reg, inputStr){
	var docSel = document.selection.createRange();
	if (docSel.parentElement().tagName != "INPUT") {
		return false;
	}
	oSel = docSel.duplicate();
	oSel.text = "";
	var srcRange = obj.createTextRange();
	oSel.setEndPoint("StartToStart", srcRange);
	var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length);
	return reg.test(str)
}
/**取得大写金额*/
function convertCurrency(currencyDigits,flag) {
	// 设置最大输入金额
	var MAXIMUM_NUMBER = 99999999999.99;
	// 设置指定参数
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_SYMBOL = "人民币：";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";

	// 存储整数部分
	var integral;
	// 存储小数部分
	var decimal;
	// 存储返回信息
	var outputCharacters;
	// 存储按照小数点分割的数组
	var parts;
	// 存储 数字、空十百千、空万亿、角分 数组
	var digits, radices, bigRadices, decimals;
	// 判断在某个非零的数字之前是否有紧邻它的 "0" 存在
	var zeroCount;
	// i：循环取数字的整数部分;p：某个数字的位置;d：某个数字的值
	var i, p, d;
	// quotient：判断是否为万或亿的分位;modulus：判断其后为十、百、千还是什么都不填
	var quotient, modulus;

	// 转换空(null)为 空("")
	currencyDigits = currencyDigits.toString();
	//判断是否输入信息
	if (currencyDigits == "") {
		alert("尚未输入资金信息!");
		return "";
	}
	//判断是否输入资金有效
	if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("输入资金有误!");
		return "";
	}
	//判断输入的数字是否含有多个小数点
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("输入的数字点号超限!");
		return "";
	}
	// 去掉 "," 分隔符
	currencyDigits = currencyDigits.replace(/,/g, "");
	// 去掉起始的 "0"
	currencyDigits = currencyDigits.replace(/^0+/, "");
	// 判断输入的数字是否超出范围
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("输入的数字超出范围!");
		return "";
	}
	// 按小数点(.)拆分输入的数字
	parts = currencyDigits.split(".");
	//判断输入数字是否有小数部分
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// 取小数点后边的前两位
		decimal = decimal.substr(0, 2);
	} else {
		integral = parts[0];
		decimal = "";
	}
	// 数字
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	// 空十百千
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	// 空万亿
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	// 角分
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// 初始化输出信息
	outputCharacters = "";
	// 判断整数部分是否大于零
	if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
			d = integral.substr(i, 1);//获取当个数字的值
			p = integral.length - i - 1;//获取当个数字的位置
			quotient = p / 4;//判断应该是在什么分位上
			modulus = p % 4;//判断在当个分位上的位置
			if (d == "0") {//如果当个位上的值为0
				zeroCount++;//则直接执行下个循环
			} else {
				if (zeroCount > 0) {//如果在当个不为0的数字前有紧邻它的0
					outputCharacters += digits[0];//结果中写入0的对应值
				}
				zeroCount = 0;//紧邻0判断设成假
				outputCharacters += digits[Number(d)] + radices[modulus];//写入数字和它对应的十百千
			}
			if (modulus == 0 && zeroCount < 4) {//如果是当个万、亿的个位，并且在它的前面的四个数不都为0
				outputCharacters += bigRadices[quotient];//添加入万亿标志
			}
		}
		outputCharacters += CN_DOLLAR;//整数值添加元标志
	}
	// 如果小数部分不为空
	if (decimal != "") {
		//循环小数部分
		for (i = 0; i < decimal.length; i++) {
			//获取当个数字
			d = decimal.substr(i, 1);
			//如果当个位置不为0
			if (d != "0") {
				//写入小数部分的值
				outputCharacters += digits[Number(d)] + decimals[i];
			}
		}
	}
	// 如果最终找不到需要输出的信息，则返回0元
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	//如果没有小数部分，或小数部分的前两个数字都为0
	if (decimal == "" || decimal == "0" || decimal == "00") {
		//则输出信息加整
		outputCharacters += CN_INTEGER;
	}
	if(flag){
		//加入 "人民币" 信息
		outputCharacters = CN_SYMBOL + outputCharacters;
	}
	//返回结果
	return outputCharacters;
}
</script>
<%@ include file="/public/footInc.jsp"%>
