<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%@include file="ajax.jsp"%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>�����</title>
	<%
	BankDAO dao = BankDAOFactory.getDAO();
	Vector<FirmFundsBankValue> firmFundsBanks = dao.getFirmFundsBank((String)session.getAttribute("REGISTERID"));
	FirmFundsValue firmFunds = dao.getFirmFunds((String)session.getAttribute("REGISTERID"));
	%>
	<script language="JavaScript">
		function doSubmit() {
			var sub = true;
			if(frm.bank.value == -1) {
				sub = false;
				alert("��ѡ�����У�");
			} else if(frm.money.value == "" || isNaN(frm.money.value) || frm.money.value<=0) {
				sub = false;
				alert("��������ȷ�Ľ�");
			} else if(frm.password.value == ""){
				sub = false;
				alert("���������룡");
			}
			if(sub){
				var frozenmoney = document.getElementById("frozen"+frm.bank.value).value;
				if(frozenmoney!=0 && frm.inoutMoney.value == <%=ProcConstants.outMoneyType%>) {
					if(!confirm("�������ʽ�["+frozenmoney+"Ԫ]������;״̬��\n\t�Ƿ��������?")){
						sub = false;
					}
				}
			}
			 if(sub) {
				var str = frm.money.value;
				var str2 = convertCurrency(str,true);
				if(str2 != ""){
					var inout = "";
					if(frm.inoutMoney.value == <%=ProcConstants.inMoneyType%>){
						inout = "��";
					}else if(frm.inoutMoney.value == <%=ProcConstants.outMoneyType%>){
						inout = "��";
					}
					str = "��"+commafy(str)+"("+str2+")?";
					str = "��ȷ������"+inout+"����Ϊ��\n\n   "+str
					if(confirm(str))
					{
						document.getElementById("sub_btn").disabled = 'disabled';
						frm.submit();
					}
				}
			 }
		}
		
	function commafy(num) {
		var  re=/(-?\d+)(\d{3})/  
		while(re.test(num)) {  
			num=num.replace(re,"$1,$2")  
		}  
		return  num;  
	}
	function showPwdBox() {
		var inoutMoneyValue = frm.inoutMoney.value;
		var bankid = frm.bank.value;
		if(bankid != -1){
			if(showPasswordBank(inoutMoneyValue,bankid,"1")){
				document.getElementById('pawdname').innerText="��������  ";
			}else{
				document.getElementById('pawdname').innerText="<%=marketpwd%>  ";
			}
		}else{
			document.getElementById('pawdname').innerText="<%=marketpwd%>  ";
		}
	}
	function changeBank(){
		showPwdBox();
		if(frm.bank.value != -1){
			validate(frm.bank.value,'<%=(String)session.getAttribute("REGISTERID")%>');
		}else{
			//frm.bankTime.value="";
			document.getElementById("bankTime").innerHTML="";
		}
	}
	</script>
  </head>
  <body>
	<table border="0" cellspacing="0" class="table1_style" cellpadding="0">
		<tr>
			<td>
				<form id="frm" action="MoneyAction.jsp" method="post"  onkeypress="javascript:return NoSubmit(event);">
					<div class="div_tj">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr height="35">
								<td width="30%" align="right">�����&nbsp;</td>
								<td align="left">
									<select  name="inoutMoney" class="normal" style="width: 80px" onchange="showPwdBox()">
										<option value="<%=ProcConstants.inMoneyType%>">���</option>
										<option value="<%=ProcConstants.outMoneyType%>">����</option>
									</select>
								</td>
								<td width="60%">&nbsp;</td>
							</tr>
							<tr height="35">
								<td name="bankid" align="right">ѡ������&nbsp;</td>
								<td align="left">
									<select  name="bank" class="normal" onchange="changeBank()">
										<OPTION value="-1">��ѡ��</OPTION>
										<% 
										if(firmFundsBanks != null && firmFundsBanks.size()>0){
											for(int i=0;i<firmFundsBanks.size();i++) {
												FirmFundsBankValue ffb=(FirmFundsBankValue)firmFundsBanks.get(i);
										%>
											<option value="<%=ffb.bankID%>"><%=ffb.bankName%></option>
										<%
											}
										}
										%>
									</select><!-- <input type="text" name="bankTime" disabled='disabled' readonly value=""> -->
								</td>
								<td name="bankTime" id="bankTime"></td>
							</tr>
							<tr height="35">
								<td width="30%" align="right">����������&nbsp;</td>
								<td align="left">
									<input  name="money2" value="" type=text  maxlength=13 class="input_text" width="80px" onblur="checkNum()">
									<input  name="money" value="" type=hidden >
								</td>
								<td>����λ��Ԫ   ���ȣ��֣�&nbsp;</td>
							</tr>
							<tr height="35">
								<td width="30%" align="right" id="pawdname"><%=marketpwd%>&nbsp;</td>
								<td align="left">
									<input name="password" value="" class="input_text" maxlength=6 type="password" width="80px">
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr height="35">
								<td align="right" colspan=2>
									<input id="sub_btn" type="button" class="btn_sec" value="ȷ��" onclick="doSubmit();">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="btn_cz" value="����" onclick="frm.reset();">
									</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</div>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<fieldset width='95%'>
			<legend>�г��ʽ���Ϣ</legend>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0" style="width: 650px;">
			<tHead>
			<tr  height="20">
				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center" colspan="3">�����˺�</td>
				<td class="panel_tHead_MB" align="center">�ڳ�Ȩ��</td>
				<td class="panel_tHead_MB" align="center">���ճ����</td>
				<td class="panel_tHead_MB" align="center">��ǰ�ɳ����</td>
				<td class="panel_tHead_MB_last" align="center">���ñ�֤��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
			</tHead>
		 	<tBody>
			<%
				if(firmFunds != null){
			%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="center" colspan="3"><%=Tool.delNull(firmFunds.contact)%></td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(firmFunds.lastBalance)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(firmFunds.inOutFunds)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(firmFunds.canOutFunds)%>&nbsp;</td>
				<td class="underLine_last" align="right"><%=Tool.fmtMoney(firmFunds.firmFunds)%>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%
				}
			if(firmFundsBanks != null && firmFundsBanks.size()>0){
			%>
			<tr  height="25">
				<td class="panel_tHead_MB" colspan="2" align="center">�����˺�</td>
				<td class="panel_tHead_MB" align="center">����</td>
				<td class="panel_tHead_MB" align="center">�˻�����</td>
				<td class="panel_tHead_MB" align="center">�ڳ�Ȩ��</td>
				<td class="panel_tHead_MB" align="center">���ճ����</td>
				<td class="panel_tHead_MB" align="center">��ǰ���ɳ����</td>
				<td class="panel_tHead_MB_last" colspan="2" align="center" TITLE="��;�ʽ�����������ͨ���쳣δ�յ�������ִʱ�������ʽ�����;״̬����;�ʽ����ÿ������ʱ�����к˶ԣ�ȷ��ת��״̬������ʱ�޷�ʹ����;�ʽ�">��;�ʽ�</td>
			</tr>
			<%
				double lastBalance=0;double inOutMoney=0;double frozenFunds=0;
					for(FirmFundsBankValue ffb : firmFundsBanks){
						lastBalance += ffb.lastBalance;
						inOutMoney += ffb.inOutMoney;
						frozenFunds += ffb.frozenFunds;
			%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="center"><%
				String outaccount = null;
				if(("005".equals(ffb.bankID) && "999999999999999999".equals(ffb.account)) || "006".equals(ffb.bankID.trim())){
					outaccount = ffb.account1;
				}else{
					outaccount = ffb.account;
				}
				String enter = "";
				if(outaccount==null){outaccount="";}
				for(int i=outaccount.length();i<8;i++){
					enter += "&nbsp;";
				}
				out.print(outaccount+enter);
				%></td>
				<td class="underLine" align="center"><%=Tool.delNull(ffb.bankName)%></td>
				<td class="underLine" align="center"><%
					if(ffb.mainBank != null && ffb.mainBank.trim().equals(ffb.bankID)){
						out.print("���˻�");
					}else{
						out.print("���˻�");
					}
				%></td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.lastBalance)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.inOutMoney)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.canOutFunds)%>&nbsp;</td>
				<td class="underLine_last" align="right" TITLE="��;�ʽ�����������ͨ���쳣δ�յ�������ִʱ�������ʽ�����;״̬����;�ʽ����ÿ������ʱ�����к˶ԣ�ȷ��ת��״̬������ʱ�޷�ʹ����;�ʽ�"><%
					if(ffb.frozenFunds==0){
						out.print(Tool.fmtMoney(ffb.frozenFunds));
					}else{
						out.print("<font color='#FF0000'>"+Tool.fmtMoney(ffb.frozenFunds)+"</font>");
					}
				out.print("<input type='hidden' name='frozen"+ffb.bankID+"' value='"+Tool.fmtMoney(ffb.frozenFunds)+"'>");
				%>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%}%>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="left" colspan="3">�ϼƣ�</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(lastBalance)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(inOutMoney)%>&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine_last" align="right"><%=Tool.fmtMoney(frozenFunds)%>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%}%>
			</tBody>
			<tFoot>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
			</table>
		</fieldset>
			</td>
		</tr>
	</table>
  </body>
</html>
<script>
/**�ж����س����ֵ*/
function NoSubmit(ev) {
	if( ev.keyCode == 13 ) {
		return false;
	}
	return true;
}
/**��֤�����ȷ��*/
function checkNum() {
	if(frm.money2.value!='') {
	  var txt = frm.money2.value;//��У���ֵ
	  txt = txt.replace(/,/gi,"");
		var pattern= /^\+?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/;
		if(!pattern.exec(txt)) {
			alert("������Ϸ����������ֵ!");
			frm.money.value="0";
			frm.money2.value="";
		}else if(txt>=10000000000){
			alert("���ʳ����Χ��������");
			frm.money.value="0";
			frm.money2.value="";
		}else{
			frm.money.value = txt;
		}
	}
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
