<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.util.Tool"%>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ include file="ajax.jsp"%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	
    <title>�����</title>
	<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	
	String AmoutDateID = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	
	CapitalProcessorRMI cp = null;
	try{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	Vector<CorrespondValue> cvs = cp.getCorrespondValue("  and firmId='"+((String)session.getAttribute("REGISTERID"))+"'");//�����̵����а��б�
	long RequestID = cp.getMktActionID();
	BankDAO dao = BankDAOFactory.getDAO();
	Vector<FirmFundsBankValue> firmFundsBanks = dao.getFirmFundsBank((String)session.getAttribute("REGISTERID"));
	FirmFundsValue firmFunds = dao.getFirmFunds((String)session.getAttribute("REGISTERID"));
	%>
	<script language="JavaScript">
		function doSubmit(remark) {
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
			}else if(frm.bank.value == "006" && InOutStartValue != "0" ){
				if(frm.PersonName.value == ""){
					sub = false;
					alert("����������������");
				} else if(frm.AmoutDate.value == ""){
					sub = false;
					alert("�����������ڣ�");
				} else if(frm.BankName.value == ""){
					sub = false;
					alert("�����������У�");
				} else if(InOutStartValue == "2"){
					if(frm.OutAccount.value == ""){
						sub = false;
						alert("���������˺ţ�");
					}
				}
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
						frm.falg.value = remark;
						
						if(frm.bank.value =='031' &&frm.pay.value =='1'){
						var result = window.showModalDialog("BankCaptailProcess/cap_031_up/moneyToAccount.jsp?inoutMoney="+frm.inoutMoney.value+"&money="+		frm.money.value+"&password="+frm.password.value+"","dialogWidth=150px; dialogHeight=440px; status=yes;scroll=yes;help=no;");
							//if(result){
							frm.action="Money.jsp";	
							frm.submit();							
						//}
						}else{
						
						frm.submit();
						}
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
			
			if(inoutMoneyValue == 0 && bankid == 79){
				document.getElementById("payChannelTR").style.display = "block";
			}else{
				document.getElementById("payChannelTR").style.display = "none";
			}
			if( bankid ==31){
			
				document.getElementById("payType").style.display = "block";
			}else{
				document.getElementById("payType").style.display = "none";
			}
			
			if("0"==inoutMoneyValue && isPasswordBank(bankid)){
				document.getElementById('bankPwd').style.display="block";
			}else{
				document.getElementById('bankPwd').style.display="none";
			}
			if("25" == bankid){
				document.getElementById('bankPawdname').innerText="���в�ѯ����  ";
			}else{
				document.getElementById('bankPawdname').innerText="��������  ";
			}
			if("17"==bankid && "0"==inoutMoneyValue){
				document.getElementById("fie").style.display = "block";
				chengeStyle01();
			}else{
				document.getElementById("fie").style.display = "none";
			}
			if(bankid=="016"){
				document.getElementById("moneyToAccountTr").style.display = "";
			}else{
				document.getElementById("moneyToAccountTr").style.display = "none";
			}
		}
	function changeBank(){
		showPwdBox();
		if(frm.bank.value != -1){
			//validate(frm.bank.value,'<%=(String)session.getAttribute("REGISTERID")%>');
		}
		checkBank();
		
	}
	function check(obj){
		//showPwdBox();
		
		if(obj.value == ''){
			alert("�Ǳ����������Ϣ����Ϊ��");
		}
	}
	function checkBank(){
		if(frm.bank.value !=-1 ){
			if(frm.inoutMoney.value == 0 && (frm.bank.value == 17||frm.bank.value == 31||frm.bank.value == 006)){
				document.getElementById("fie").style.display = "block";
				//document.getElementById('tel1').style.display='none';
				chengeStyle01();
			}
			else
			{
				//if(frm.bank.value=='005'&&frm.inoutMoney.value == 0)
				//{
				//	if(document.getElementById('tel1'))
				//	{
				//		document.getElementById('tel1').style.display='';
				//	}
				//}else {
				//	document.getElementById('tel1').style.display='none';
				//}
				document.getElementById("fie").style.display = "none";
				reset("4");
				InOutStartValue = '0';
			}
			//validate(frm.bank.value,'<%=(String)session.getAttribute("REGISTERID")%>');
			if(frm.inoutMoney.value == 0&&frm.bank.value=='027'){
				alert("ͨ���˻�֧�������������");
				document.getElementById("sub_btn").disabled = 'disabled';
			}else if(frm.inoutMoney.value == 1&&frm.bank.value=='028'){
				alert("ͨ������֧��������������");
				document.getElementById("sub_btn").disabled = 'disabled';
			}else{
				document.getElementById("sub_btn").disabled = '';
			}
		}else{
			//frm.bankTime.value="";
			//document.getElementById("bankTime").innerHTML="";
			document.getElementById("fie").style.display = "none";
			//document.getElementById('tel1').style.display='none';
		}
		document.getElementById("RadioGroup1_0").checked = "checked";
	}
	function chengeStyle01(){
		InOutStartValue = '0';
		document.getElementById("tr02").style.display = "none";
		document.getElementById("tr03").style.display = "none";
		document.getElementById("tr04").style.display = "none";
		document.getElementById("tr05").style.display = "none";
	}
	function changeStyle02(){
		InOutStartValue = '1';
		document.getElementById("tr02").style.display = "block";
		document.getElementById("tr03").style.display = "block";
		document.getElementById("tr04").style.display = "block";
		document.getElementById("tr05").style.display = "none";
		reset("3");
	}
	function changeStyle03(){
		InOutStartValue = '2';
		document.getElementById("tr02").style.display = "block";
		document.getElementById("tr03").style.display = "block";
		document.getElementById("tr04").style.display = "block";
		document.getElementById("tr05").style.display = "block";
		reset("4");
	}
	function reset(obj){
		frm.PersonName.value == "";
		//document.getElementById("PersonNameID").innerHTML="";
		frm.AmoutDate.value == "";
		//document.getElementById("AmoutDateID").innerHTML="";
		frm.BankName.value == "";
		//document.getElementById("BankName").innerHTML="";
		if(obj == "4"){
			frm.OutAccount.value == "";
			//document.getElementById("OutAccountID").innerHTML="";
			
		}
	}
	function moneyToAccount(){
		
		var result = window.showModalDialog("BankCaptailProcess/cap_016_citic/moneyToAccount.jsp","dialogWidth=150px; dialogHeight=440px; status=yes;scroll=yes;help=no;");
		if(result){
			frm.action="Money.jsp";
			frm.submit();
		}
	}
	</script>

  </head>
  <body oncontextmenu="return false">
  <div id="main_body">
  	<form id="frm" action="MoneyAction.jsp" method="post"  onkeypress="javascript:return NoSubmit(event);">
	<table border="0" cellspacing="0" class="table1_style" cellpadding="0">
		<tr>
		<td>
		<fieldset width="95%">
			<legend>ת�˲���</legend>
					<div class="div_tj">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				<tr height="35">
								<td width="30%" align="right">�����&nbsp;</td>
								<td align="left">
						<select  name="inoutMoney" class="normal" style="width: 80px" onchange="changeBank()">
							<option value="<%=ProcConstants.inMoneyType%>">���</option>
							<option value="<%=ProcConstants.outMoneyType%>">����</option>
						</select>
						<%
							for(int i=0;i<cvs.size();i++)
							{
								CorrespondValue corr = (CorrespondValue)cvs.get(i);
						%>
										<input name="RequestID" type="hidden" value="<%=RequestID%>" id="requestID">
										<input name="BuyCustName" type="hidden" value="<%=delNull(corr.accountName)%>"></td>
										<input name="BuyCustNo" type="hidden" value="<%=delNull(corr.firmID)%>" id="Customer">
						<%
							}
						%>	
						<input type="hidden" value="start" name="falg" >
					</td>
					<td width="60%"></td>
				</tr>
				<tr height="35">
					<td name="bankid" align="right">ѡ������:&nbsp;</td>
					<td align="left">
						<select  name="bank" class="normal" style="width: 80px" onchange="changeBank()">
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
						</select>
					</td>
					<td id="bankTime" name="bnakTime">
					<div id="moneyToAccountTr" style="display: none;" >���ӹ��ܣ�&nbsp;
					<a href="#" onclick="moneyToAccount();">���ֵ����п�</a>&nbsp;
					<a href="BankCaptailProcess/cap_016_citic/moneyToAccountList.jsp" >������ˮ��¼</a>
					</div>
					</td>
				</tr>
				
				<tr height="35" id="payChannelTR" style="display:none;">
					<td width="30%" align="right">֧������&nbsp;</td>
					<td align="left">
						<input type="radio" name="payChannel" value="0000"/>����
						<input type="radio" name="payChannel" value="0001" checked="checked"/>����
						<input type="radio" name="payChannel" value="0002"/>����
						<input type="radio" name="payChannel" value="0003"/>֤��
					</td>
				</tr>
				<tr height="35" id="payType" style="display:none;">
					<td width="30%" align="right">֧������&nbsp;</td>
					<td align="left">
						<select  name="pay" class="normal" style="width: 80px" >
							<option value="0" checked="checked">ǩԼ�˺�</option>
							<!-- <option value="1">�����˺�</option> -->
						</select>
						
						
					</td>
				</tr>
				<tr height="35">
								<td width="30%" align="right">����������&nbsp;</td>
					<td align="left" >
						<input  name="money2" value="" type=text  class="text" maxlength="13" style="width: 100px" onblur="checkNum()">
						<input  name="money" value="" type=hidden >
					</td>
					<td>����λ��Ԫ   ���ȣ��֣�</td>
				</tr>
					<tr height="35" id="tel1">
						<td width="30%" align="right" id="pawdname">�ʽ�����&nbsp;</td>
						<td align="left">
							<input  name="password" value="" type="password"  class="text">
						</td>
					</tr>
					<tr height="35" id="bankPwd" style="display: none;">
						<td width="30%" align="right" id="bankPawdname">��������&nbsp;</td>
						<td align="left">
							<input  name="bankPassword" value="" type="password"  class="text">
						</td>
					</tr>
				<tr height="35">
					<td align="right" colspan=2>
						<input id="sub_btn" type="button" class="btn_sec" value="ȷ��" onclick="doSubmit('sh');">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn_cz" value="����" onclick="frm.reset();">
					</td>
					<td></td>
				</tr>
			</table>
					</div>
			</td>
		</tr>
		
		<!-- �����������ҳ��start -->
		<tr>
			<td>
		<fieldset width='95%' id="fie" style="display:none;">
				<legend>���Ǽ�����</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
					<tr height="35" id="tr01">
						<td width="30%" align="right">���ʽ&nbsp;</td>
						<td align="left"><p>
						<label>
							<input type="radio" name="InOutStart" value="0" id="RadioGroup1_0" onClick="chengeStyle01();" checked = "checked">
							�������</label>
						&nbsp;
						<label>
							<input type="radio" name="InOutStart" value="1" id="RadioGroup1_1" onClick="changeStyle02();">
							�����ֽ����</label>
						&nbsp;
						<label>
							<input type="radio" name="InOutStart" value="2" id="RadioGroup1_2" onClick="changeStyle03();">
							����ת�����</label>
						<br></p>
						</td>
					</tr>
					<tr height="35" id="tr02">
						<td width="30%" align="right">���������&nbsp;</td>
						<td align="left">
							<input  name="PersonName" id="PersonNameID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr03">
						<td name="bankid" width="30%" align="right">�������&nbsp;</td>
						<td align="left">
							<!--input  name="AmoutDate" id="AmoutDateID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">�������� ���磺һ��������ʮһ��һ�գ�19701101��-->
							<input  name="AmoutDate" id="AmoutDateID" value="<%=AmoutDateID%>" type=text  class="text" style="width: 100px" onblur="check(this)" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
						</td>
					</tr>
					<tr height="35" id="tr04">
						<td width="30%" align="right">�������&nbsp;</td>
						<td align="left">
							<input  name="BankName" id="BankNameID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr05">
						<td width="30%" align="right">����˺�&nbsp;</td>
						<td align="left">
							<input  name="OutAccount" id="OutAccountID" value="" type=text  class="text"style="width: 100px" onblur="check(this)" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
						</td>
					</tr>
				</table>
				</fieldset>
			</td>
		</tr>
		<!-- �����������ҳ��end -->		
		
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
			<%} %>
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
	</form>	
	</div>
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
