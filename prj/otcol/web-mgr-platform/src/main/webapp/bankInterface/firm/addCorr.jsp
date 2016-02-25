<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	
	String filterForGetCitys = "where parentID='0'";
	Vector<CityValue> cityValues = dao.getCityNames(filterForGetCitys);
	CityValue cityValue = new CityValue();
	
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	String firmID = request.getParameter("firmID");
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		String bankID = request.getParameter("bankID");
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String account = request.getParameter("account").trim();
		String accountName = Tool.delNull(request.getParameter("accountName")).trim();
		String card = request.getParameter("card").trim();
		String bankName = Tool.delNull(request.getParameter("bankName")).trim();
		String bankProvince = Tool.delNull(request.getParameter("bankProvince")).trim();
		String bankCity = Tool.delNull(request.getParameter("bankCityName")).trim();
		String mobile = Tool.delNull(request.getParameter("mobile")).trim();
		String email = Tool.delNull(request.getParameter("Email")).trim();
		String cardType = Tool.delNull(request.getParameter("cardType")).trim();
		String account1 = Tool.delNull(request.getParameter("account1")).trim();
		
		if (checkAccount(bankID)){//不需录入真实银行卡号的银行
			account = Tool.getConfig("DefaultAccount");
		}
		
		CorrespondValue corr = new CorrespondValue();
		corr.account = account;
		corr.account1=account1;
		corr.accountName = accountName;
		corr.card = card;
		corr.bankCity = bankCity;
		corr.bankID = bankID;
		corr.bankName = bankName;
		corr.bankProvince = bankProvince;
		corr.email = email;
		corr.firmID = firmID;
		corr.mobile = mobile;
		corr.status = 1;
		corr.cardType=cardType;
		corr.isOpen = 0;
		long result = 0;
		if(noAdapterBank(bankID)){
			corr.isOpen = 1;
			corr.status = 0;
		}else if(checkAccount(bankID)){
			corr.account = Tool.getConfig("DefaultAccount");
		}
		try
		{
			if(dao.getCorrespond(bankID,firmID,account)!=null)
			{
				result = -2;
			}
			else
			{
				result = cp.rgstAccount(corr);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = -1;
		}
		String msg = "";
		if(result == 0)
		{
			msg = "交易商银行帐号注册成功！";
		}
		else if(result == -2)
		{
			msg = "交易商银行帐号对应关系已存在！";
		}
		else
		{
			msg = "交易商银行帐号注册失败！";
		}	

		%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//window.opener.location.reload();
				window.returnValue="1";
				window.close();
				//-->
				</SCRIPT>	
			<%
		lv.setLogcontent(msg+" 银行代码："+bankID+"交易商代码："+firmID);
		dao.log(lv);
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>注册交易商银行帐号</title>
	<script language="javascript">
	var req;
	function Change_Select(){//当第一个下拉框的选项发生改变时调用该函数
      var provinceCode = document.getElementById('bankProvince').value;
      if(provinceCode == 0){
		alert("请选择省份");
	  }
	  var url = "./ajaxAddCorr.jsp?provinceCode=" + provinceCode;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);         
	  	req.onreadystatechange = callback;//指定回调函数为callback
	  	req.send(null);
	  }
    }
    //回调函数
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
    //解析返回xml的方法
    function parseMessage(){	  
	  var res = req.responseText;
		if(!(res.indexOf('select')==-1)){//取得数据时
	     	document.getElementById("bankCity").innerHTML = res;
	     }else{//没有取得数据时
	     	alert("取得对应县市失败");
	  	return;
	     }
    }
	function init(){
		document.getElementById("bankCity").innerHTML = "<select style='width: 140px'><option value='0' selected>--请选择--</option></select>";
	}
  </script>
	
  </head>
  
  <body onload="init()">
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>注册交易商银行帐号</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行：&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(" where validFlag=0 ");
						%>
						<select name="bankID" class="normal" style="width: 140px">
							<OPTION value="-1">请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bank = (BankValue)bankList.get(i);
								%>
								<option value="<%=bank.bankID%>"><%=bank.bankName%></option>	
								<%
							}
							%>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行帐号：&nbsp;</td>
					<td align="left">
						<input name="account" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">重复银行帐号：&nbsp;</td>
					<td align="left">
						<input name="reaccount" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" style="display: none;">
					<td align="right">银行内部帐号：&nbsp;</td>
					<td align="left">
						<input name="account1" value="" type=text  class="text" maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">账户名：&nbsp;</td>
					<td align="left">
						<input name="accountName" value="" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">请选择</option>
							<option value="1">身份证</option>
							<!--<option value="2">军官证</option>
							<option value="3">国内护照</option>
							<option value="4">户口本</option>
							<option value="5">学员证</option>
							<option value="6">退休证</option>
							<option value="7">临时身份证</option>-->
							<option value="8">组织机构代码</option>
							<option value="9">营业执照</option>
							<option value="a">法人代码证</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card" value="" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行名称：&nbsp;</td>
					<td align="left">
						<!--input name="bankName" value="" type=text  class="text" maxlength="50" style="width: 140px"-->
						<select name="bankName" id="bankName" style="width: 140px">
							<option value="0">--请选择--</option>
							<option value="NBCB">宁波银行</option>
							<option value="BOCOMM">交通银行</option>
							<option value="CIB">兴业银行</option>
							<option value="BOC">中国银行</option>
							<option value="CEB">光大银行</option>
							<option value="CCB">建设银行</option>
							<option value="ABC">农业银行</option>
							<option value="ICBC">工商银行</option>
							<option value="CMB">招商银行</option>
							<option value="CMBC">民生银行</option>
							<option value="CITIC">中信银行</option>
							<option value="SPDB">浦发银行</option>
							<option value="UNION">银联</option>
							<option value="SDB">深圳发展银行</option>
							<option value="PINGANBANK">平安银行</option>
							<option value="HXB">华夏银行</option>
							<option value="GDB">重庆三峡银行</option>
							<option value="CQRCB">重庆农村商业银行</option>
							<option value="PSBC">邮政储蓄银行</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行省份：&nbsp;</td>
					<td align="left">
						<!--input name="bankProvince" value="" type=text  class="text" maxlength="20" style="width: 140px"-->
						<select name="bankProvince" id="bankProvince" style="width: 140px" onChange="Change_Select()">
							<option value="0">--请选择--</option>
							<%
							if(cityValues.size() > 0){
								for(int i=0;i<cityValues.size();i++){
									cityValue = cityValues.get(i);
							%>
							<option value='<%=cityValue.getCityCode()%>'><%=cityValue.getCityName()%></option>
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
						<div id="bankCity"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">电话：&nbsp;</td>
					<td align="left">
						<input name="mobile" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="Email" value="" type=text  class="text" maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">添加</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">取消</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doAdd()
{
	if(frm.bankID.value == -1)
	{
		alert("请选择银行！");
		frm.bankID.focus();
	}else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.account.value) == "")
	{
		alert("请输入银行帐号！");
		frm.account.focus();
	}
	else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.reaccount.value) == "")
	{
		alert("请重复银行帐号！");
		frm.account.focus();
	}
	else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.reaccount.value) != trim(frm.account.value))
	{
		alert("银行账号与重复银行帐号不一致！");
		frm.reaccount.value="";
		frm.account.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名");
		frm.accountName.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("请选择证件类型");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("请输入证件号码");
		frm.card.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankName.value) == 0)
	{
		alert("请选择开户行名称");
		frm.bankName.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankProvince.value) == 0)
	{
		alert("请选择开户行省份");
		frm.bankProvince.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankCityName.value) == 0)
	{
		alert("请选择开户行城市");
		frm.bankCityName.focus();
	}
	else
	{
		if(frm.bankID.value == "01" && frm.bankID.value != "14" ){
			frm.account.value="<%=Tool.getConfig("DefaultAccount")%>";
		}
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>