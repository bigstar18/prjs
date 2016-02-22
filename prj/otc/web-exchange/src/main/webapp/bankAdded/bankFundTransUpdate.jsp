<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�����ѻ�ת</title>
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
											&nbsp;&nbsp;�����ѻ�ת
										</div>
											<table width="421" border="0" class="table2_style">
												<tr height="35">
													<input type="hidden" name="obj.bankCode" value="${obj.bankCode}" />
													<td width="40%" align="right">
														��������:
													</td>
													<td>
														<input type="hidden" name="obj.bankName" value="${obj.bankName}" />
														${obj.bankName}
													</td>
												</tr>
												<tr height="35">
													<td align="right">
														���������:
													</td>
													<td>
														<input type="hidden" id="marketFeeBalance" name="obj.marketFeeBalance" value="${obj.marketFeeBalance}"/>
														<fmt:formatNumber value="${obj.marketFeeBalance}" type="currency" pattern="0.00" />
													</td>
												</tr>
												<tr height="35">
													<td align="right">
														��ת���:
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
							<span id="msg"><font color="red" size="4">��ǰʱ�䲻����ת</font></span>
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
								�ύ
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
								�ر�
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
		alert("��ת���������0��");
		return false;
	}
	var marketFeeBalance = document.getElementById('marketFeeBalance').value;
	var funds = document.getElementById('funds').value;
	if(marketFeeBalance < parseFloat(funds)){
		alert('��ת�����������������������룡');
		document.getElementById('funds').value = "";
		document.getElementById('funds').focus();
		return false;
	}
	var money = convertCurrency(document.getElementById('funds').value, false);
	var msg = "��${obj.bankName}��ת�������ѣ�" + money + "����ȷ����ת��"
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
/**ȡ�ô�д���*/
function convertCurrency(currencyDigits,flag) {
	// �������������
	var MAXIMUM_NUMBER = 99999999999.99;
	// ����ָ������
	var CN_ZERO = "��";
	var CN_ONE = "Ҽ";
	var CN_TWO = "��";
	var CN_THREE = "��";
	var CN_FOUR = "��";
	var CN_FIVE = "��";
	var CN_SIX = "½";
	var CN_SEVEN = "��";
	var CN_EIGHT = "��";
	var CN_NINE = "��";
	var CN_TEN = "ʰ";
	var CN_HUNDRED = "��";
	var CN_THOUSAND = "Ǫ";
	var CN_TEN_THOUSAND = "��";
	var CN_HUNDRED_MILLION = "��";
	var CN_SYMBOL = "����ң�";
	var CN_DOLLAR = "Ԫ";
	var CN_TEN_CENT = "��";
	var CN_CENT = "��";
	var CN_INTEGER = "��";

	// �洢��������
	var integral;
	// �洢С������
	var decimal;
	// �洢������Ϣ
	var outputCharacters;
	// �洢����С����ָ������
	var parts;
	// �洢 ���֡���ʮ��ǧ�������ڡ��Ƿ� ����
	var digits, radices, bigRadices, decimals;
	// �ж���ĳ�����������֮ǰ�Ƿ��н������� "0" ����
	var zeroCount;
	// i��ѭ��ȡ���ֵ���������;p��ĳ�����ֵ�λ��;d��ĳ�����ֵ�ֵ
	var i, p, d;
	// quotient���ж��Ƿ�Ϊ����ڵķ�λ;modulus���ж����Ϊʮ���١�ǧ����ʲô������
	var quotient, modulus;

	// ת����(null)Ϊ ��("")
	currencyDigits = currencyDigits.toString();
	//�ж��Ƿ�������Ϣ
	if (currencyDigits == "") {
		alert("��δ�����ʽ���Ϣ!");
		return "";
	}
	//�ж��Ƿ������ʽ���Ч
	if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("�����ʽ�����!");
		return "";
	}
	//�ж�����������Ƿ��ж��С����
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("��������ֵ�ų���!");
		return "";
	}
	// ȥ�� "," �ָ���
	currencyDigits = currencyDigits.replace(/,/g, "");
	// ȥ����ʼ�� "0"
	currencyDigits = currencyDigits.replace(/^0+/, "");
	// �ж�����������Ƿ񳬳���Χ
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("��������ֳ�����Χ!");
		return "";
	}
	// ��С����(.)������������
	parts = currencyDigits.split(".");
	//�ж����������Ƿ���С������
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// ȡС�����ߵ�ǰ��λ
		decimal = decimal.substr(0, 2);
	} else {
		integral = parts[0];
		decimal = "";
	}
	// ����
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	// ��ʮ��ǧ
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	// ������
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	// �Ƿ�
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// ��ʼ�������Ϣ
	outputCharacters = "";
	// �ж����������Ƿ������
	if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
			d = integral.substr(i, 1);//��ȡ�������ֵ�ֵ
			p = integral.length - i - 1;//��ȡ�������ֵ�λ��
			quotient = p / 4;//�ж�Ӧ������ʲô��λ��
			modulus = p % 4;//�ж��ڵ�����λ�ϵ�λ��
			if (d == "0") {//�������λ�ϵ�ֵΪ0
				zeroCount++;//��ֱ��ִ���¸�ѭ��
			} else {
				if (zeroCount > 0) {//����ڵ�����Ϊ0������ǰ�н�������0
					outputCharacters += digits[0];//�����д��0�Ķ�Ӧֵ
				}
				zeroCount = 0;//����0�ж���ɼ�
				outputCharacters += digits[Number(d)] + radices[modulus];//д�����ֺ�����Ӧ��ʮ��ǧ
			}
			if (modulus == 0 && zeroCount < 4) {//����ǵ������ڵĸ�λ������������ǰ����ĸ�������Ϊ0
				outputCharacters += bigRadices[quotient];//��������ڱ�־
			}
		}
		outputCharacters += CN_DOLLAR;//����ֵ���Ԫ��־
	}
	// ���С�����ֲ�Ϊ��
	if (decimal != "") {
		//ѭ��С������
		for (i = 0; i < decimal.length; i++) {
			//��ȡ��������
			d = decimal.substr(i, 1);
			//�������λ�ò�Ϊ0
			if (d != "0") {
				//д��С�����ֵ�ֵ
				outputCharacters += digits[Number(d)] + decimals[i];
			}
		}
	}
	// ��������Ҳ�����Ҫ�������Ϣ���򷵻�0Ԫ
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	//���û��С�����֣���С�����ֵ�ǰ�������ֶ�Ϊ0
	if (decimal == "" || decimal == "0" || decimal == "00") {
		//�������Ϣ����
		outputCharacters += CN_INTEGER;
	}
	if(flag){
		//���� "�����" ��Ϣ
		outputCharacters = CN_SYMBOL + outputCharacters;
	}
	//���ؽ��
	return outputCharacters;
}
</script>
<%@ include file="/public/footInc.jsp"%>
