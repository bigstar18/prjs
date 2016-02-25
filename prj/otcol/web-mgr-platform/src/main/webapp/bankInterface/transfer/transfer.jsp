<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%

	String payBankId = Tool.delNull(request.getParameter("payBankId"));	
	String payAcc = Tool.delNull(request.getParameter("payAcc"));	
	String recBankId = Tool.delNull(request.getParameter("recBankId"));	
	String recAcc = Tool.delNull(request.getParameter("recAcc"));	
	String amount = Tool.delNull(request.getParameter("amount"));
	String postscript = Tool.delNull(request.getParameter("postscript"));	
	String recFirmId = Tool.delNull(request.getParameter("recFirmId"));	

	BankDAO dao = BankDAOFactory.getDAO();
	Vector bankList = dao.getBankList(" where validFlag=0 ");
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>资金划转</title>
  </head>
 <script language="text/javascript" src="./lib/prototype-1.6.0.3.js"></script>
  <SCRIPT LANGUAGE="JavaScript">
	<!--
	function dosubmit(){
		if(check()==true){
			frm.submit();
		}
	}
	//检查表单
	function check(){
		if(frm.payBankId.value == -1){
			alert("请选择付款行");
			frm.payBankId.focus();
			return false;
		}else if(frm.recBankId.value == -1){
			alert("请选择收款行");
			frm.recBankId.focus();
			return false;
		}else if(frm.amount.value == ""){
			alert("请填写转账金额");
			frm.amount.focus();
			return false;
		}else if(frm.amount.value == "" || isNaN(frm.amount.value) || frm.amount.value<=0){
			alert("请填写正确的转账金额");
			frm.amount.focus();
			return false;
		}else{
			return true;
		}
	}
	function showFirm(){
		
		if(frm.recAcc.value == '295'){			
			document.getElementById("firm").style.display = "inline";
		}else{			
			document.getElementById("firm").style.display = "none";
		}
	}
	function back(){
		document.location = "transferList.jsp";
	}
	function resetFrm(){
		frm.reset();
		document.getElementById("firm").style.display = "none";
		frm.payBankId.focus();
	}
	function decType(value){
		if(value==3){
		    document.getElementById("trandType").style.display = "";
			document.getElementById("typeMess").style.display = "";
            document.getElementById("payBankId").attachEvent("onchange",syncValue); 
			loadEmp("1%","payBankId");
            syncValue();
		}else{
		    document.getElementById("trandType").style.display = "none";
			document.getElementById("typeMess").style.display = "none";
			document.getElementById("payBankId").detachEvent("onchange",syncValue); 
			loadEmp("1%","payBankId");
            loadEmp("01%","recBankId");
		}
		
	}
	
	function loadEmp(tradeType,id) {
		var req = new XMLHttpRequest();
		req.open('GET','getBank.jsp?tradeType='+tradeType,true);
		req.onreadystatechange = function() {
			if(req.readyState==4) {
				var jsonTxt = req.responseText;
				var length = jsonTxt.slice(0,3).replace(" ","");
				var list = jsonTxt.slice(3,jsonTxt.length).split(",",length);
				fillEmp(list,id);
			}
		};
		req.send(null);
	}
	function fillEmp(emps,id) {
	    var templateId = document.getElementById(id);
		templateId.options.length=0; 
         templateId.options.add(new Option("请选择","-1"));		
		var array ;
		for(var i=0;i<emps.length;i++){
			array = emps[i].split(":",2);
			templateId.options.add(new Option(array[1],array[0]));
		}
	}

	function syncValue(){
		var name = null;
        var value = null;
	    var payBankId = document.getElementById("payBankId");
		for(var i=0;i<payBankId.length;i++){
		   if(payBankId[i].selected==true){
				name=payBankId[i].innerText;
				value = payBankId[i].value;
		   }
		}
		var templateId = document.getElementById("recBankId");
		templateId.options.length=0;  
        templateId.options.add(new Option(name,value));	
        
	}
	//-->
	windows.load
	</SCRIPT>

  <body>
  	<form id="frm" name="frm" action="confirm.jsp" method="post">
		<fieldset width="95%">
			<legend>资金划转录入</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" >
			<tr height="35">
					<td align="left" width="15%"><B>交易模式：</B>&nbsp;</td>
					<td align="left" width="5%">
					    <select id="type" name="type" class="normal" style="width: 140px" onchange="decType(this.value)">
							<OPTION value="-1">请选择</OPTION>
							<OPTION value="3">市场出金</OPTION>
							<OPTION value="6">跨行转账</OPTION>
						</select><span class=star>*</span>
					</td>
					<td align="left" width="5%">&nbsp;</td>
					<td align="left" width="15%"><div id="typeMess" style="display: none"><B>交易类型：</B></div></td>
					<td align="left" width="15%"><div id="trandType" style="display: none"><select  name="trandType" class="normal" style="width: 140px">
							<OPTION value="-1">请选择</OPTION>
							<OPTION value="00">欠款入金</OPTION>
							<OPTION value="01">欠款出金</OPTION>
							<OPTION value="02">佣金出金</OPTION>
							<OPTION value="03">税金出金</OPTION>
						</select><span class=star>*</span></div></td>
					<td align="left" ></td>
				</tr>
				<tr height="35">
					<td align="left" width="15%"><B>付款方：</B>&nbsp;</td>
					<td align="left" width="5%"></td>
					<td align="left" width="15%"></td>
					<td align="left" width="5%"></td>
					<td align="left" width="15%"></td>
					<td align="left" ></td>
				</tr>

				<tr height="35">
					<td align="right">
					银行：
					</td>
					<td align="left">						
						<select name="payBankId" class="normal" style="width: 140px">
							<OPTION value="-1">请选择</OPTION>	
						</select><span class=star>*</span>
					</td>
					<td align="left"></td>
					<td align="left"></td>
				</tr>

				<tr height="35">
					<td align="left" width="10%"><div id="recBankMess"><B>收款方：</B>&nbsp;</div></td>
					<td align="left" width="5%">						
					</td>
					<td align="left" >						
					</td>
					<td align="left">						
					</td>
					<td align="left"></td>
					<td align="left"></td>
				</tr>
				<tr height="35">
					<td align="right">
					<div id="recBank1">
					银行：
					</div>
					</td>
					<td align="left">
						<select id="recBankId" name="recBankId" class="normal" style="width: 140px">
							<OPTION value="-1">请选择</OPTION>	
						</select><span class=star>*</span>
					</td>
				</tr>

				<tr height="35">
					<td align="left">
					<B>交易金额及相关信息：</B>
					</td>
					<td align="left" colspan="5">						
					</td>					
				</tr>

				<tr height="35">
					<td align="right">交易金额：&nbsp;</td>
					<td align="left" colspan="5">
						<input name="amount" value="<%=amount%>" type=text  class="text" maxlength="30" style="width: 140px">(单位：元)<span class=star>*</span>
					</td>
				</tr>
				<!--
				<tr height="35">
					<td align="right">大写：&nbsp;</td>
					<td align="left" colspan="3">
						<input name="upperAmount" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				-->
				<tr height="35">
					<td align="right">附言：&nbsp;</td>
					<td align="left" colspan="5">
						<input name="postscript" value="<%=postscript%>" type=text  class="text" maxlength="30" style="width: 140px">
					</td>
				</tr>

				<tr height="35">					
					<td align="center" colspan=6>
						<button type="button" class="smlbtn" onclick="dosubmit()">下一步</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetFrm()">清空</button>&nbsp;		
						<button type="button" class="smlbtn" onclick="back()">返回</button>&nbsp;
					</td>
				</tr>


			</table>
		</fieldset>	  
	</form>
  </body>
</html>

