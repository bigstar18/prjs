<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资金划转</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
		var flag = '${flag}';
	    jQuery(document).ready(function() {
	    	$("#subfrm").validationEngine("attach");
			$("#performBtn").click(function(){<%//执行转账%>
				if($("#subfrm").validationEngine('validate')){
					if(affirm("您确认要执行转账吗？")){
						if($("#inoutMoney").val()=='0'&& $("#bankID").val()=='66'){
							subfrm.action="${basePath}/bank/money/moneyTransferGFB.action";
						}else if(($("#cardType").val()=='8'||$("#cardType").val()=='9')&& $("#bankID").val()=='12'){
							window.open("https://ebs.boc.cn/BocnetClient/LoginFrame3.do?_locale=zh_CN&theAction=ETokenLogin.do");
							subfrm.action="${basePath}/bank/money/gotoMoneyPage.action";
						}
						$("#subfrm").submit();
					}
				}
			});
	    });
		function showPasswordTr(){<%//通过银行编号确认是否要显示密码%>
			var bankIDs='';
			if($("#inoutMoney").val()=='0'){
				bankIDs='${inMoneyNeedPasswordBankID}';
			}else if($("#inoutMoney").val()=='1'){
				bankIDs='${outMoneyNeedPasswordBankID}';
			}
			if($("#inoutMoney").val()=='0'&& $("#bankID").val()=='17'){
				document.getElementById("fie").style.display = "block";
				chengeStyle01();
			}
			else
			{
				var aa=document.getElementById("fie");
				if(aa!=null){
					document.getElementById("fie").style.display = "none";
				}

			}

			var bb=document.getElementById("RadioGroup1_0");
			if(bb!=null){
				document.getElementById("RadioGroup1_0").checked = "checked";
			}


			var bankID = $("#bankID").val();
			if(bankIDs && bankID && bankCheckPwd(bankIDs,bankID)){
				// document.getElementById("passwordtr").style.display="inline";
				document.getElementById("passwordth").innerText="银行密码：";
				document.getElementById("password").className="validate[required,custom[onlyNumberSp],minSize[6],maxSize[6]]";
			}else{//如果未选定银行信息
				if(${isCheckMarketPassword}){
					// document.getElementById("passwordtr").style.display="inline";
					document.getElementById("passwordth").innerText="交易所密码：";
					document.getElementById("password").className="validate[required,custom[password],maxSize[16]]";
				}else{
					document.getElementById("passwordtr").style.display="none";
					document.getElementById("password").className="";
				}
			}
	    	$("#subfrm").validationEngine("attach");
		}
		function check(obj){

		if(obj.value == ''){
			alert("非本行入金汇款信息不能为空");
		}
	}
	var InOutStartValue = '0';
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
		subfrm.PersonName.value = "";
		subfrm.AmoutDate.value = "";
		subfrm.BankName.value = "";
		if(obj == "4"){
			subfrm.OutAccount.value == "";
		}
	}
		function bankCheckPwd(bankIDs,bankID){<%//银行是否判断密码%>
			if(!(bankIDs && bankIDs.length>=bankID.length)){<%//如果未设置需要判断密码的银行，则认为银行不需要判断%>
				return false;
			}
			if(bankIDs==bankID){<%//如果两个相等，说明只有这个银行判断密码%>
				return true;
			}
			if(bankIDs.indexOf(bankID+",")==0){<%//如果设置信息以本银行信息开头，认为需要验证密码%>
				return true;
			}
			bankIDs += ",";
			if(bankIDs.indexOf(","+bankID+",")>=0){<%//如果设置信息中包含本银行认为需要验证%>
				return true;
			}
			return false;
		}

		function showMsgBoxCallbak(result,msg){<%//回掉函数%>
			//if(result==1){
				$("#frm").submit();
			//}else{
				//clearSubmitCount();
			//}
		}
		</script>
	</head>
	<body>
		<form id="frm" name="frm" action="${basePath}/bank/money/gotoMoneyPage.action"></form>
		<iframe style="display: none;" id="frame" name="frame"></iframe>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示:
				</div>
				<div class="content">在此您可以进行出/入金操作。</div>
			</div>
			<div class="clear"></div>
			<div id="message" class="form margin_10b">
				<form id="subfrm" name="subfrm" action="${basePath}/bank/money/moneyTransfer.action" method="post" target="frame">
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<tr>
							<th width="14%" align="center">出入金：</th>
							<td>
								<select id="inoutMoney" name="inoutMoney" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">请选择</option>
									<option value="0">入金</option>
									<option value="1">出金</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">选择银行： </th>
							<td>
								<select id="bankID" name="bankID" class="validate[required]" style="width: 127px;" onchange="showPasswordTr()">
									<option value="">请选择</option>

									<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">
										<option value="${firmIDAndAccount.bank.bankID}">${firmIDAndAccount.bank.bankName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">申请出入金金额：</th>
							<td>
								<input id="money" name="money" class="validate[required,custom[doubleCus],maxSize[9]]" style="width: 127px;"/>
								<c:forEach var="firmIDAndAccount" items="${pageInfo.result}">

									<input type="hidden" id="cardType"  name="cardType" value="${firmIDAndAccount.cardType}" />

								</c:forEach>
							</td>
						</tr>
						<tr id="passwordtr" <c:if test="${!isCheckMarketPassword}">style="display: none;"</c:if>>
							<th scope="row" id="passwordth">验证密码：</th>
							<td>
								<input id="password" type="password" name="password" class="validate[required,custom[password],maxSize[16]]" style="width: 127px;"/>
								<script type="">showPasswordTr();</script>
							</td>
						</tr>
					</table>
					<div class="page text_center">
						<label><span class="progressBar" id="pb1"></span></label>&nbsp;&nbsp;
						<label>
							<span id="pb2">

								<input type="button" id="performBtn"  value="确认" class="btbg"/>
							</span>
						</label>
					</div>
					<fieldset width='95%' id="fie" style="display:none;">
				<legend>入金登记申请</legend>
				<table border="0" cellspacing="0" cellpadding="0" style="width: 580px;" height="35">
					<tr height="35" id="tr01">
						<td width="20%" align="right">入金方式&nbsp;</td>
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
						<td width="20%" align="right">汇款人姓名&nbsp;</td>
						<td align="left">
							<input  name="PersonName" id="PersonNameID" value="" type=text  class="validate[required]" style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr03">
					<td  width="30%" align="right">汇款日期&nbsp;</td>
					<td align="left">

						<input name="AmoutDate" id="AmoutDateID" class="validate[required]"  onFocus="WdatePicker({el:this,dateFmt:'yyyyMMdd',skin:'whyGreen'})"/>
					</td>
				</tr>
					<tr height="35" id="tr04">
						<td width="20%" align="right">汇款银行&nbsp;</td>
						<td align="left">
							<input  name="BankName" id="BankNameID" value="" type=text  class="validate[required]" style="width: 100px" onblur="check(this)">
						</td>
					</tr>
					<tr height="35" id="tr05">
						<td width="20%" align="right">汇款账号&nbsp;</td>
						<td align="left">
							<input  name="OutAccount" id="OutAccountID" value="" type=text  class="validate[required]" style="width: 100px" onblur="check(this)" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
						</td>
					</tr>
				</table>
				</fieldset>

				</form>
			</div>
		</div>
	</body>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>