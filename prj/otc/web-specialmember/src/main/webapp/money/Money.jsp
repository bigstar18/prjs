<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.util.Tool"%>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ include file="ajax.jsp"%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	
    <title>出入金</title>
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
	
	Vector<CorrespondValue> cvs = cp.getCorrespondValue("  and firmId='"+((String)session.getAttribute("REGISTERID"))+"'");//交易商的银行绑定列表
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
				alert("请选择银行！");
			} else if(frm.money.value == "" || isNaN(frm.money.value) || frm.money.value<=0) {
				sub = false;
				alert("请输入正确的金额！");
			} else if(frm.password.value == ""){
				sub = false;
				alert("请输入密码！");
			}else if(frm.bank.value == "006" && InOutStartValue != "0" ){
				if(frm.PersonName.value == ""){
					sub = false;
					alert("请输入汇款人姓名！");
				} else if(frm.AmoutDate.value == ""){
					sub = false;
					alert("请输入汇款日期！");
				} else if(frm.BankName.value == ""){
					sub = false;
					alert("请输入汇款银行！");
				} else if(InOutStartValue == "2"){
					if(frm.OutAccount.value == ""){
						sub = false;
						alert("请输入汇款账号！");
					}
				}
			}
			if(sub){
				var frozenmoney = document.getElementById("frozen"+frm.bank.value).value;
				if(frozenmoney!=0 && frm.inoutMoney.value == <%=ProcConstants.outMoneyType%>) {
					if(!confirm("您已有资金["+frozenmoney+"元]处于在途状态，\n\t是否继续出金?")){
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
						inout = "入";
					}else if(frm.inoutMoney.value == <%=ProcConstants.outMoneyType%>){
						inout = "出";
						
					}
					str = "￥"+commafy(str)+"("+str2+")?";
					str = "您确认您的"+inout+"金金额为：\n\n   "+str
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
				document.getElementById('bankPawdname').innerText="银行查询密码  ";
			}else{
				document.getElementById('bankPawdname').innerText="银行密码  ";
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
			alert("非本行入金汇款信息不能为空");
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
				alert("通联账户支付不允许做入金");
				document.getElementById("sub_btn").disabled = 'disabled';
			}else if(frm.inoutMoney.value == 1&&frm.bank.value=='028'){
				alert("通联网关支付不允许做出金");
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
			<legend>转账操作</legend>
					<div class="div_tj">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				<tr height="35">
								<td width="30%" align="right">出入金&nbsp;</td>
								<td align="left">
						<select  name="inoutMoney" class="normal" style="width: 80px" onchange="changeBank()">
							<option value="<%=ProcConstants.inMoneyType%>">入金</option>
							<option value="<%=ProcConstants.outMoneyType%>">出金</option>
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
					<td name="bankid" align="right">选择银行:&nbsp;</td>
					<td align="left">
						<select  name="bank" class="normal" style="width: 80px" onchange="changeBank()">
							<OPTION value="-1">请选择</OPTION>
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
					<div id="moneyToAccountTr" style="display: none;" >附加功能：&nbsp;
					<a href="#" onclick="moneyToAccount();">提现到银行卡</a>&nbsp;
					<a href="BankCaptailProcess/cap_016_citic/moneyToAccountList.jsp" >提现流水记录</a>
					</div>
					</td>
				</tr>
				
				<tr height="35" id="payChannelTR" style="display:none;">
					<td width="30%" align="right">支付渠道&nbsp;</td>
					<td align="left">
						<input type="radio" name="payChannel" value="0000"/>其他
						<input type="radio" name="payChannel" value="0001" checked="checked"/>代扣
						<input type="radio" name="payChannel" value="0002"/>网银
						<input type="radio" name="payChannel" value="0003"/>证书
					</td>
				</tr>
				<tr height="35" id="payType" style="display:none;">
					<td width="30%" align="right">支付渠道&nbsp;</td>
					<td align="left">
						<select  name="pay" class="normal" style="width: 80px" >
							<option value="0" checked="checked">签约账号</option>
							<!-- <option value="1">其他账号</option> -->
						</select>
						
						
					</td>
				</tr>
				<tr height="35">
								<td width="30%" align="right">申请出入金金额&nbsp;</td>
					<td align="left" >
						<input  name="money2" value="" type=text  class="text" maxlength="13" style="width: 100px" onblur="checkNum()">
						<input  name="money" value="" type=hidden >
					</td>
					<td>（单位：元   精度：分）</td>
				</tr>
					<tr height="35" id="tel1">
						<td width="30%" align="right" id="pawdname">资金密码&nbsp;</td>
						<td align="left">
							<input  name="password" value="" type="password"  class="text">
						</td>
					</tr>
					<tr height="35" id="bankPwd" style="display: none;">
						<td width="30%" align="right" id="bankPawdname">银行密码&nbsp;</td>
						<td align="left">
							<input  name="bankPassword" value="" type="password"  class="text">
						</td>
					</tr>
				<tr height="35">
					<td align="right" colspan=2>
						<input id="sub_btn" type="button" class="btn_sec" value="确认" onclick="doSubmit('sh');">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn_cz" value="重置" onclick="frm.reset();">
					</td>
					<td></td>
				</tr>
			</table>
					</div>
			</td>
		</tr>
		
		<!-- 华夏它行入金页面start -->
		<tr>
			<td>
		<fieldset width='95%' id="fie" style="display:none;">
				<legend>入金登记申请</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
					<tr height="35" id="tr01">
						<td width="30%" align="right">入金方式&nbsp;</td>
						<td align="left"><p>
						<label>
							<input type="radio" name="InOutStart" value="0" id="RadioGroup1_0" onClick="chengeStyle01();" checked = "checked">
							本行入金</label>
						&nbsp;
						<label>
							<input type="radio" name="InOutStart" value="1" id="RadioGroup1_1" onClick="changeStyle02();">
							他行现金入金</label>
						&nbsp;
						<label>
							<input type="radio" name="InOutStart" value="2" id="RadioGroup1_2" onClick="changeStyle03();">
							他行转账入金</label>
						<br></p>
						</td>
					</tr>
					<tr height="35" id="tr02">
						<td width="30%" align="right">汇款人姓名&nbsp;</td>
						<td align="left">
							<input  name="PersonName" id="PersonNameID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr03">
						<td name="bankid" width="30%" align="right">汇款日期&nbsp;</td>
						<td align="left">
							<!--input  name="AmoutDate" id="AmoutDateID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">（年月日 例如：一九七零年十一月一日：19701101）-->
							<input  name="AmoutDate" id="AmoutDateID" value="<%=AmoutDateID%>" type=text  class="text" style="width: 100px" onblur="check(this)" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
						</td>
					</tr>
					<tr height="35" id="tr04">
						<td width="30%" align="right">汇款银行&nbsp;</td>
						<td align="left">
							<input  name="BankName" id="BankNameID" value="" type=text  class="text"style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr05">
						<td width="30%" align="right">汇款账号&nbsp;</td>
						<td align="left">
							<input  name="OutAccount" id="OutAccountID" value="" type=text  class="text"style="width: 100px" onblur="check(this)" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
						</td>
					</tr>
				</table>
				</fieldset>
			</td>
		</tr>
		<!-- 华夏它行入金页面end -->		
		
		<tr>
			<td>
		<fieldset width='95%'>
			<legend>市场资金信息</legend>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0" style="width: 650px;">
			<tHead>
			<tr  height="20">
				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center" colspan="3">交易账号</td>
				<td class="panel_tHead_MB" align="center">期初权益</td>
				<td class="panel_tHead_MB" align="center">当日出入金</td>
				<td class="panel_tHead_MB" align="center">当前可出金额</td>
				<td class="panel_tHead_MB_last" align="center">可用保证金</td>
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
				<td class="panel_tHead_MB" colspan="2" align="center">银行账号</td>
				<td class="panel_tHead_MB" align="center">银行</td>
				<td class="panel_tHead_MB" align="center">账户属性</td>
				<td class="panel_tHead_MB" align="center">期初权益</td>
				<td class="panel_tHead_MB" align="center">当日出入金</td>
				<td class="panel_tHead_MB" align="center">当前最大可出金额</td>
				<td class="panel_tHead_MB_last" colspan="2" align="center" TITLE="在途资金是因与银行通信异常未收到立即回执时，导致资金处于在途状态。在途资金会在每天日终时与银行核对，确定转账状态。您暂时无法使用在途资金。">在途资金</td>
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
						out.print("主账户");
					}else{
						out.print("次账户");
					}
				%></td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.lastBalance)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.inOutMoney)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(ffb.canOutFunds)%>&nbsp;</td>
				<td class="underLine_last" align="right" TITLE="在途资金是因与银行通信异常未收到立即回执时，导致资金处于在途状态。在途资金会在每天日终时与银行核对，确定转账状态。您暂时无法使用在途资金。"><%
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
				<td class="underLine" align="left" colspan="3">合计：</td>
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
/**判定按回车后的值*/
function NoSubmit(ev) {
	if( ev.keyCode == 13 ) {
		return false;
	}
	return true;
}
/**验证金额正确性*/
function checkNum() {
	if(frm.money2.value!='') {
	  var txt = frm.money2.value;//被校验的值
	  txt = txt.replace(/,/gi,"");
		var pattern= /^\+?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/;
		if(!pattern.exec(txt)) {
			alert("请输入合法的人民币数值!");
			frm.money.value="0";
			frm.money2.value="";
		}else if(txt>=10000000000){
			alert("单笔出入金范围超出限制");
			frm.money.value="0";
			frm.money2.value="";
		}else{
			frm.money.value = txt;
		}
	}
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
