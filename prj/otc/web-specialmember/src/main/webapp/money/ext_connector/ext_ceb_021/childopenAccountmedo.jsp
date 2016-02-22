<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
    //request.setCharacterEncoding("GBK");
	String message = request.getParameter("submitFlag");
	String contact = request.getParameter("contact");
	String bankID = request.getParameter("bankID");
	String firmID = request.getParameter("firmID");
	System.out.println("firmID-------------"+firmID+bankID+contact);
	CEBBankDao dao = BankDAOFactory.getCEBDAO();
	//光大银行他行签约定制>>>>>>>>>>>>取得开户行所在省份--start<<<<<<<<<<<
	//HXBBankDAO hxDao = BankDAOFactory.getHXBDAO();
	String filterForGetCitys = "where parentID='0'";
	Vector<CitysValue> cityValues = dao.getCityOfBank(filterForGetCitys);
	CitysValue cityValue = new CitysValue();
	//光大银行他行签约定制>>>>>>>>>>>>取得开户行所在省份--end<<<<<<<<<<<
	CEBCapitalProcessorRMI cp = null;
	try{
		cp = getCEBBankUrl(bankID);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	if(contact == null){
		contact = "''";
	}
	FirmUserValue firmUserValue = dao.getFirmUserValue(contact);
	
	if(message != null && message.equals("do")){
		LogValue lv = new LogValue();
		lv.setLogtime(new Date());
		//lv.setIp(computerIP);
		
		String AccountProp = Tool.delNull(request.getParameter("AccountProp")).trim();
		String RelatingAcct = Tool.delNull(request.getParameter("RelatingAcct")).trim();
		String RelatingAcctName = Tool.delNull(request.getParameter("RelatingAcctName")).trim();
		
		String RelatingAcctBank = Tool.delNull(request.getParameter("acctBankName")).trim();//new String(request.getParameter("acctBankName").getBytes("ISO-8859-1"),"GBK");
		
		String RelatingAcctBankAddr = Tool.delNull(request.getParameter("RelatingAcctBankAddr")).trim();
		
		String RelatingAcctBankCode = Tool.delNull(request.getParameter("RelatingAcctBankCode")).trim();
		
		String Addr = Tool.delNull(request.getParameter("Addr")).trim();
		String LawName = Tool.delNull(request.getParameter("LawName")).trim();
		//String Addr = Tool.delNull(new String(request.getParameter("Addr").getBytes("ISO-8859-1"),"GBK"));
		//String LawName = Tool.delNull(new String(request.getParameter("LawName").getBytes("ISO-8859-1"),"GBK"));
		String cardType = Tool.delNull(request.getParameter("cardType")).trim();
		String card = Tool.delNull(request.getParameter("card")).trim();		
		String checkFlag = Tool.delNull(request.getParameter("CheckFlag")).trim();
		
		CorrespondValue corr = new CorrespondValue();
		corr.bankID = bankID;
		corr.firmID = firmID;
		corr.contact = contact;
		corr.account = RelatingAcct;
		corr.accountName = RelatingAcctName;
		corr.bankName = RelatingAcctBank;
		corr.bankCity = RelatingAcctBankAddr;		
		corr.status = 0;
		corr.isOpen = 1;
		corr.cardType = cardType;
		corr.card = card;
		corr.OpenBankCode = RelatingAcctBankCode;
		corr.checkFlag = checkFlag;
		corr.isCrossline = "1";//判断是否是他行签约的字段信息
		
		ReturnValue retuenValue=new ReturnValue();
		try
		{
			retuenValue = cp.synchroAccount(corr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			retuenValue.result = -1;
		}
		String msg = "";
		if(retuenValue.result < 0)
		{
			
			msg = "交易商银行帐号签约失败！"+retuenValue.remark;
		}
		else
		{
			msg = "交易商银行帐号签约成功！";
			
		}	

		%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg%>');
				window.returnValue="1";
				window.close();
			</SCRIPT>
		<%
		lv.setLogcontent(msg+": 银行代码："+bankID+"交易商代码："+contact);
		dao.log(lv);
	}
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
    <title>发起账户签约</title>
	<script language="javascript">
	function changeBank(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--请选择--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var city = frm.bankCityName.value;
		var county = frm.bankCountyName.value;
		if(city != "0" && county != ""){
			countyChange();
		}
	}
	function provinceChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--请选择--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var bankName = frm.RelatingAcctBanks.value;
		if(bankName == 0){
			alert("请先选择开户行");
			frm.bankProvince.value = 0;
			return;
		}
		document.getElementById("bankCounty").innerHTML = "<select name='bankCountyName' id='bankCountyName' style='width: 150px'><option value='0' selected>--请选择--</option></select><span>*</span>";
		var cityID = document.getElementById('bankProvince').value;
		if(cityID == 0){
			alert("请选择省");
		}
		var url = "./ajaxForProvince.jsp?cityID=" + cityID;
		Change_Select(url,"bankCity");
	}
	function cityChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--请选择--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var cityID = document.getElementById('bankCityName').value;
		if(cityID == ""){
			alert("请选择市");
		}
		var url = "./ajaxForCity.jsp?cityID=" + cityID;
		Change_Select(url,"bankCounty");
	}
	function countyChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--请选择--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var cityID = document.getElementById('bankCountyName').value;
		if(cityID == 0){
			alert("请选择县（区）");
		}
		var bank = frm.RelatingAcctBanks.value;
		var city = frm.bankCityName.value;
		var county = frm.bankCountyName.value;
		if(bank == 0){
			alert("请选择开户行");
			return;
		}
		if(city ==0 || county == 0){
			alert("请选择开户行所在城市");
			return;
		}
		var url = "./ajaxForBanks.jsp?bank=" + bank + "&city=" + city + "&county=" + county;
		Change_Select(url,"bankFullName");
	}
	function bankNameChange(){
		var request;
		var bankName = frm.acctBankName.value;
		if(bankName == ""){
			alert("请选择开户行名称");
			document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span>></td></tr></table>";
			return;
		}
		var url = "./ajaxForAddrNum.jsp?bankName=" + bankName;
		if(window.XMLHttpRequest){
			request = new XMLHttpRequest();
	    }else if(window.ActiveXObject){
			request = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    if(request){
			request.open("GET",url,true);
			request.onreadystatechange = function(){
				if(request.readyState ==4){
					if(request.status ==200){
						var res = request.responseText;
						document.getElementById("bankAddrAndNum").innerHTML = res;
					}else{
						alert("不能得到描述信息:" + request.statusText);
					}
				}
			}
			request.send(null);
	    }
	}
	var req;
	function Change_Select(url,element){//当第一个下拉框的选项发生改变时调用该函数
      //var province = document.getElementById('bankProvince').value;
      //if(province == 0){
	  //	alert("请选择省份");
	  //}
	  //var url = "./ajaxForProvince.jsp?province=" + province;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);
	  	//req.onreadystatechange = callback;//指定回调函数为callback
		req.onreadystatechange = function(){callback(element);}
	  	req.send(null);
	  }
    }
    //回调函数
    function callback(element){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage(element);//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
    //解析返回xml的方法
    function parseMessage(element){	  
	  var res = req.responseText;
	  if(!(res.indexOf('select')==-1)){//取得数据时
	     document.getElementById(element).innerHTML = res;
	  }else{//没有取得数据时
	     alert("取得对应县市失败");
	  	 return;
	  }
    }
	
	function AccountPropChange(){
		var AccountProp = document.getElementsByName("AccountProp");
		var strNew;
		for(var i=0;i<AccountProp.length;i++){
			if(AccountProp.item(i).checked){
				strNew = AccountProp.item(i).getAttribute("value");  
				break;
			}else{
				continue;
			}
		}
		
	}
	
	function init(){
		document.getElementById("bankCity").innerHTML = "<select name='bankCityName' id='bankCityName' style='width: 150px'><option value='0' selected>--请选择--</option></select>";
		document.getElementById("bankCounty").innerHTML = "<select name='bankCountyName' id='bankCountyName' style='width: 150px'><option value='' selected>--请选择--</option></select>";
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--请选择--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>开户行地址：</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>开户行行号：</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
	}
	
	function startLikeQuery(){
		//var RelatingAcctBanks = document.getElementById('RelatingAcctBanks').value;
		//if(RelatingAcctBanks == 0){
		//	alert("先选择开户行");
		//	document.getElementById('bankProvince').removeAttribute("disabled");
		//	document.getElementById('bankCityName').removeAttribute("disabled");
		//	document.getElementById('bankCountyName').removeAttribute("disabled");
		//	document.getElementById("likeFilter").disabled = "disabled";
		//	document.getElementById("queryButton").disabled = "disabled";
		//}
		if(document.getElementById('qiyong').checked){
			document.getElementById('likeFilter').removeAttribute("disabled");
			document.getElementById('queryButton').removeAttribute("disabled");
			document.getElementById("bankProvince").disabled = "disabled";
			document.getElementById("bankCityName").disabled = "disabled";
			document.getElementById("bankCountyName").disabled = "disabled";
		}else{
			document.getElementById('bankProvince').removeAttribute("disabled");
			document.getElementById('bankCityName').removeAttribute("disabled");
			document.getElementById('bankCountyName').removeAttribute("disabled");
			document.getElementById("likeFilter").disabled = "disabled";
			document.getElementById("queryButton").disabled = "disabled";
		}
	}
	
	function getBanks(){
		var RelatingAcctBanks = document.getElementById("RelatingAcctBanks").value;
		var likeFilter = document.getElementById("likeFilter").value;
		if(RelatingAcctBanks == 0){
			alert("请选择开户行");
			return;
		}
		if( likeFilter == ""){
			alert("请输入查询条件");
			frm.likeFilter.focus();
			return;
		}
		var url = "./ajaxForBanksByLikeQuery.jsp?bank=" + RelatingAcctBanks + "&filter=" + likeFilter;
		Change_Select(url,"bankFullName");
	}
	</script>
  </head>
  
  <body style="overflow-y: hidden" onload="init()">
  	<form id="frm" action="" method="post">
		
	<div style="overflow:auto;height:485;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title">&nbsp;(光大银行)交易所发起子账户签约</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
			<input type=hidden name="submitFlag" value="">
				<tr height="35">
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">
						<input name="firmID" type=hidden value="<%=firmID%>">
						<input name="contact" value="<%=Tool.delNull(contact)%>" readonly type=text  class="input_text" maxlength="10" style="width: 150px"><span class=star>*</span>
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">账户类型：&nbsp;</td>
					<td align="left">
						<input onclick="AccountPropChange()" type = "radio" value = "1" name="AccountProp" id="AccountProp" CHECKED/>个人
						<input onclick="AccountPropChange()" type = "radio" value = "0" name="AccountProp" id="AccountProp"/>企业
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">关联账号(卡号)：&nbsp;</td>
					<td align="left">
						<input name="RelatingAcct" value="<%=Tool.delNull(firmUserValue.account)%>" type=text  maxlength="32" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">关联账户名：&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctName" readonly value="<%=Tool.delNull(firmUserValue.accountname)%>" type=text  maxlength="30" style="width: 150px"><span>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBank" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="RelatingAcctBanks" id="RelatingAcctBanks" style="width: 150px" onChange="changeBank()">
							<option value="0" selected>--请选择--</option>
							<option value="人民银行">中国人民银行</option>
							<option value="工商银行">中国工商银行</option>
							<option value="农业银行">中国农业银行</option>
							<option value="中国银行">中国银行</option>
							<option value="建设银行">中国建设银行</option>
							<option value="农业发展">中国农业发展银行</option>
							<option value="交通银行">交通银行</option>
							<option value="中信银行">中信银行</option>
							<option value="光大银行">光大银行</option>
							<option value="民生银行">民生银行</option>
							<option value="广东发展">广东发展银行</option>
							<option value="深圳发展">深圳发展银行</option>
							<option value="招商银行">招商银行</option>
							<option value="兴业银行">兴业银行</option>
							<option value="浦东发展">上海浦东发展银行</option>
							<option value="北京银行">北京银行</option>
							<option value="农村商业">农村商业银行</option>
							<option value="农村信用">农村信用社</option>
							<option value="城市信用">城市信用社</option>
							<option value="邮政储蓄">中国邮政储蓄银行</option>
							<option value="华夏银行">中国华夏银行</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">
						<input id="qiYong" type="checkbox" onclick="startLikeQuery()">启用模糊查询&nbsp;
					</td>
					<td align="left">
						<input id="likeFilter" name="likeFilter" style="width: 230px" disabled="disabled">&nbsp;
						<input type="button" id="queryButton" name="queryButton" class="smlbtn" onclick="getBanks()" disabled="disabled" value="查询"></input>&nbsp;
						<!--<button id="queryButton" name="queryButton" class="smlbtn" onclick="getBanks()" disabled="disabled">查询</button>-->
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">开户行所在省：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankProvince" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="bankProvince" id="bankProvince" style="width: 150px" onChange="provinceChange()">
							<option value="0">--请选择--</option>
							<%
							if(cityValues.size() > 0){
								for(int i=0;i<cityValues.size();i++){
									cityValue = cityValues.get(i);
							%>
							<option value='<%=cityValue.ID%>'><%=cityValue.cityName%></option>
							<%		
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行所在市：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankCity" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<div id="bankCity"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行所在县（区）：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankCounty" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<div id="bankCounty"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行名称：&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBank" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<div id="bankFullName"></div>
					</td>
				</tr>
				<tr height="35">
					<td colspan="2">
						<!--table border="1" id="bankAddrAndNum"-->
							<div style="padding-left:87px" id="bankAddrAndNum"><div>
						<!--/table-->
					</td>
				</tr>
				<!--
				<tr>
					<td colspan="2">
						<div style="padding-left:46px" id="TrForCompany" style="display: none;">
							<table border="0">
								<tr height="35">
									<td align="right">联系人：&nbsp;</td>
									<td align="left">
										<input name="PersonName" type=text maxlength="50" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">办公电话：&nbsp;</td>
									<td align="left">
										<input name="OfficeTel" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right" >移动电话：&nbsp;</td>
									<td align="left">
										<input name="MobileTel" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">联系地址：&nbsp;</td>
									<td align="left">
										<input name="Addr" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">邮政编码：&nbsp;</td>
									<td align="left">
										<input name="ZipCode" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">法人姓名：&nbsp;</td>
									<td align="left">
										<input name="LawName" type=text maxlength="20" style="width: 150px"><font color="red">&nbsp;(账户类型为企业时必输)</font>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>-->
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">请选择</option>
							<option value="1"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(firmUserValue.card)%>" type=text maxlength="20" style="width: 150px"><span >*</span>
					</td>
				</tr>
				<!--<tr height="35">
					<td align="right">是否需要短信通知：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="NoteFlag" CHECKED/>需要
						<input type = "radio" value = "0" name="NoteFlag" />不需要
					</td>
				</tr>
				<tr height="35">
					<td align="right">短信通知手机号：&nbsp;</td>
					<td align="left">
						<input name="NotePhone" type=text maxlength="20" style="width: 150px"><span >*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="eMail" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">复核标识：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="CheckFlag" CHECKED/>需要
						<input type = "radio" value = "0" name="CheckFlag" />不需要
					</td>
				</tr>-->
				<tr height="35">					
					<td align="center" colspan=2>
					<input type="button" id="sub_btn" class="smlbtn" onclick="doAdd();" value="签约"></input>&nbsp;
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();" value="返回"></input>&nbsp;
					
					</td>
				</tr>
			</table>	
		</div>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">

function doAdd()
{

	if((frm.RelatingAcct.value) == "" || isNaN(frm.RelatingAcct.value))
	{
		alert("请输入关联银行帐号！");
		frm.RelatingAcct.focus();
	}
	else if((frm.RelatingAcctName.value) == "")
	{
		alert("请输入关联账户名！");
		frm.RelatingAcctName.focus();
	}
	else if((frm.acctBankName.value) == "")
	{
		alert("请选开户行名称！");
		frm.acctBankName.focus();
	}
	else if((frm.RelatingAcctBankAddr.value) == "")
	{
		alert("请输入开户行地址！");
		frm.RelatingAcctBankAddr.focus();
	}
	else if((frm.RelatingAcctBankCode.value) == "")
	{
		alert("请输入开户行系统行号！");
		frm.RelatingAcctBankCode.focus();
	}	
	
	else if((frm.cardType.value) == "")
	{
		alert("请选择证件类型！");
		frm.cardType.focus();
	}	
	else if((frm.card.value) == "")
	{
		alert("请输入证件号码！");
		frm.card.focus();
	}	
	
	else
	{
		frm.submitFlag.value = "do";
		frm.sub_btn.disabled = "disabled";
		frm.submit();
	}
}
</SCRIPT>