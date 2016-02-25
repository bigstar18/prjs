<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		System.out.print(cp.testRmi());
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	BankDAO dao = BankDAOFactory.getDAO();
	
	String firmID = Tool.delNull(request.getParameter("firmID")).trim();
	
	String provinceCode = dao.getCorrespondList("where firmID='" + firmID + "'").get(0).bankProvince;
	
	String filterForGetCitys = "where parentID='0'";
	Vector<CityValue> cityValues = dao.getCityNames(filterForGetCitys);
	//修改注册信息————不更改省份可以直接更改县市
	//String provinceCode = request.getParameter("bkProvince");
	CityValue cvl = new CityValue();
	Vector<CityValue> cityValuesForCity = new Vector<CityValue>();
	if(provinceCode != null && !"null".equals(provinceCode) && !"".equals(provinceCode) && !"0".equals(provinceCode)){
		String ft = "where cityCode='" + provinceCode + "'";
		cityValuesForCity = dao.getCityNames(ft);
		if(cityValuesForCity.size() > 0){
			cvl = cityValuesForCity.get(0);
			String filterForSubCity = "where parentID='" + cvl.getCityID() + "'";
			cityValuesForCity = dao.getCityNames(filterForSubCity);
		}
	}
	
	CityValue cityValue = new CityValue();
	
	String bankID = Tool.delNull(request.getParameter("bankID")).trim();
	String account = Tool.delNull(request.getParameter("account")).trim();
	String account1 = Tool.delNull(request.getParameter("account1")).trim();
	CorrespondValue corr = dao.getCorrespond(bankID,firmID,account);
	String tellBank = "";
	if(("79".equals(bankID)) && (corr.isOpen == 1 || corr.isOpen == 2)){
		tellBank = "1";
	}else{
		tellBank = "0";
	}
	CorrespondValue corrOld = new CorrespondValue();
	
	corrOld.bankID = corr.bankID;
	corrOld.firmID = corr.firmID;
	corrOld.account = corr.account;
	corrOld.account1 = corr.account1;
	corrOld.accountName = corr.accountName;
	corrOld.bankName = corr.bankName;
	corrOld.bankProvince = corr.bankProvince;
	corrOld.bankCity = corr.bankCity;
	corrOld.mobile = corr.mobile;
	corrOld.email = corr.email;
	corrOld.status = corr.status;
	corrOld.isOpen = corr.isOpen;
	corrOld.cardType = corr.cardType;
	corrOld.card = corr.card;
	corrOld.frozenFuns = corr.frozenFuns;
	corrOld.inMarketCode = corr.inMarketCode;
	corrOld.opentime = corr.opentime;
	corrOld.deltime = corr.deltime;
	corrOld.Linkman = corr.Linkman;
	corrOld.Phone = corr.Phone;
	corrOld.addr = corr.addr;
	corrOld.LawName = corr.LawName;
	corrOld.NoteFlag = corr.NoteFlag;
	corrOld.NotePhone = corr.NotePhone;
	corrOld.zipCode = corr.zipCode;
	corrOld.checkFlag = corr.checkFlag;
	
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		String bankName = Tool.delNull(request.getParameter("bankName")).trim();
		String bankProvince = Tool.delNull(request.getParameter("bankProvince")).trim();
		String bankCity = Tool.delNull(request.getParameter("bankCityName")).trim();
		String mobile = Tool.delNull(request.getParameter("mobile")).trim();
		String email = Tool.delNull(request.getParameter("Email")).trim();
		String accountName = Tool.delNull(request.getParameter("accountName")).trim();
		String account2 = Tool.delNull(request.getParameter("account2")).trim();
		String card2 = Tool.delNull(request.getParameter("card2")).trim();
		String cardType = Tool.delNull(request.getParameter("cardType")).trim();

		corr.bankCity = bankCity;
		corr.bankName = bankName;
		corr.bankProvince = bankProvince;
		corr.email = email;
		corr.mobile = mobile;
		corr.accountName=accountName;
		corr.account=account2;
		corr.card=card2;
		if(checkAccount(bankID)){
			if(corr.isOpen !=1){
				corr.account = Tool.getConfig("DefaultAccount");
			}
		}
		corr.cardType=cardType;
		
		int result = 0;
		if("0".equals(tellBank)){
			try
			{
				result = dao.modCorrespond(corr);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = -1;
			}
		}else if("1".equals(tellBank)){
			try
			{
				ReturnValue retV = cp.modAccount(corrOld, corr, bankID);
				result = (int)retV.result;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = -1;
			}
		}
		String msg = "";
		if(result == 0)
		{
			msg = "修改交易商银行帐号成功，交易商代码："+firmID+"，新银行账号："+account+"时间："+Tool.fmtTime(new java.util.Date());
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("交易商银行帐号修改成功！");
				window.location.href="accountMng.jsp?firmID=<%=firmID%>";
				//window.close();
				//-->
				</SCRIPT>	
			<%
			return;
		}
		else
		{
			msg = "交易商银行帐号修改失败，交易商代码："+firmID+"，新银行账号："+account+"时间："+Tool.fmtTime(new java.util.Date());
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//-->
				</SCRIPT>	
			<%
		}
		lv.setLogcontent(msg);
		dao.log(lv);
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>修改交易商银行帐号</title>
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
  </script>
	
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>修改交易商银行帐号</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行：&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">出入金帐号：&nbsp;</td>
					<td align="left">
						<input name="account2" value="<%=replaceNull(account)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text class="text" maxlength="30"><span class=star>*</span>
						<input name="account" value="<%=replaceNull(account)%>"type="hidden" class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">出入金账户名：&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=replaceNull(corr.accountName)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">请选择</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select>
					</td>
					<SCRIPT LANGUAGE="JavaScript">
						//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
					</SCRIPT>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card2" value="<%=replaceNull(corr.card)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行名称：&nbsp;</td>
					<td align="left">
						<select name="bankName" id="bankName" style="width: 150px">
							<option value="0">--请选择--</option>
							<option value="NBCB" <%if(corr.bankName!=null && corr.bankName.equals("NBCB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>宁波银行</option>
							<option value="BOCOMM" <%if(corr.bankName!=null && corr.bankName.equals("BOCOMM")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>交通银行</option>
							<option value="CIB" <%if(corr.bankName!=null && corr.bankName.equals("CIB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>兴业银行</option>
							<option value="BOC" <%if(corr.bankName!=null && corr.bankName.equals("BOC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>中国银行</option>
							<option value="CEB" <%if(corr.bankName!=null && corr.bankName.equals("CEB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>光大银行</option>
							<option value="CCB" <%if(corr.bankName!=null && corr.bankName.equals("CCB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>建设银行</option>
							<option value="ABC" <%if(corr.bankName!=null && corr.bankName.equals("ABC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>农业银行</option>
							<option value="ICBC" <%if(corr.bankName!=null && corr.bankName.equals("ICBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>工商银行</option>
							<option value="CMB" <%if(corr.bankName!=null && corr.bankName.equals("CMB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>招商银行</option>
							<option value="CMBC" <%if(corr.bankName!=null && corr.bankName.equals("CMBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>民生银行</option>
							<option value="CITIC" <%if(corr.bankName!=null && corr.bankName.equals("CITIC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>中信银行</option>
							<option value="SPDB" <%if(corr.bankName!=null && corr.bankName.equals("SPDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>浦发银行</option>
							<option value="UNION" <%if(corr.bankName!=null && corr.bankName.equals("UNION")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>银联</option>
							<option value="SDB" <%if(corr.bankName!=null && corr.bankName.equals("SDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>深圳发展银行</option>
							<option value="PINGANBANK" <%if(corr.bankName!=null && corr.bankName.equals("PINGANBANK")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>平安银行</option>
							<option value="HXB" <%if(corr.bankName!=null && corr.bankName.equals("HXB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>华夏银行</option>
							<option value="GDB" <%if(corr.bankName!=null && corr.bankName.equals("GDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>重庆三峡银行</option>
							<option value="CQRCB" <%if(corr.bankName!=null && corr.bankName.equals("CQRC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>重庆农村商业银行</option>
							<option value="PSBC" <%if(corr.bankName!=null && corr.bankName.equals("PSBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>邮政储蓄银行</option>
							<%
							if(corr.bankName != null && BankCode.codeForBank.get(corr.bankName) == null){
								if(!"null".equalsIgnoreCase(corr.bankName)){
									out.print("<option value='");
									out.print(corr.bankName);
									out.print("' selected>");
									out.print(corr.bankName);
									out.print("</option>");
								}
							}else if(corr.isOpen == 1){
								if(!"null".equalsIgnoreCase(corr.bankName)){
									out.print("<option value='");
									out.print(corr.bankName);
									out.print("' disabled>");
									out.print(corr.bankName);
									out.print("</option>");
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行省份：&nbsp;</td>
					<td align="left">
						<select name="bankProvince" id="bankProvince" style="width: 150px" onChange="Change_Select()">
							<option value="0">--请选择--</option>
							<%
							if(cityValues.size() > 0){
								for(int i=0;i<cityValues.size();i++){
									cityValue = cityValues.get(i);
							%>
							<option value='<%=cityValue.getCityCode()%>' <%if(corr.bankProvince!=null && corr.bankProvince.equals(cityValue.getCityCode())){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>><%=cityValue.getCityName()%></option>
							<%		
								}
							}
							if(corr.bankProvince != null && map.get(corr.bankProvince) == null){
								if(!"null".equalsIgnoreCase(corr.bankProvince)){
									out.print("<option value='");
									out.print(corr.bankProvince);
									out.print("' selected>");
									out.print(corr.bankProvince);	
									out.print("</option>");
								}
							}else if(corr.isOpen == 1){
								if(!"null".equalsIgnoreCase(corr.bankName)){
									out.print("<option value='");
									out.print(corr.bankName);
									out.print("' disabled>");
									out.print(corr.bankName);
									out.print("</option>");
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">开户行所在市：&nbsp;</td>
					<td align="left">
						<div id="bankCity">
							<select name="bankCityName" id="bankCityName" style="width: 150px">
							<option value="0">--请选择--</option>
							<%
							if(cityValuesForCity.size() <= 0 || cityValuesForCity == null || "".equals(cityValuesForCity) || "null".equals(cityValuesForCity)){
								
							}else if(cityValuesForCity.size() > 0){
								for(int i=0;i<cityValuesForCity.size();i++){
									cityValue = cityValuesForCity.get(i);
							%>
							<option value='<%=cityValue.getCityCode()%>' <%
							if(corr.bankCity!=null && corr.bankCity.equals(cityValue.getCityCode())){
								out.print("selected");
							}else if(corr.isOpen==1){
								out.print("disabled");
							}
							%>><%=cityValue.getCityName()%></option>
							<%		
								}
							}
							if(corr.bankCity != null && map.get(corr.bankCity) == null){
								if(!"null".equalsIgnoreCase(corr.bankCity)){
									out.print("<option value='");
									out.print(corr.bankCity);
									out.print("' selected>");
									out.print(corr.bankCity);
									out.print("</option>");
								}
							}else if(corr.isOpen == 1){
								if(!"null".equalsIgnoreCase(corr.bankName)){
									out.print("<option value='");
									out.print(corr.bankName);
									out.print("' disabled>");
									out.print(corr.bankName);
									out.print("</option>");
								}
							}
							%>
						</select>
						</div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">电话：&nbsp;</td>
					<td align="left">
						<input name="mobile" value="<%=replaceNull(corr.mobile)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">电子邮件：&nbsp;</td>
					<td align="left">
						<input name="Email" value="<%=replaceNull(corr.email)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">修改</button>&nbsp;
						<button type="button" class="smlbtn" onclick="javaScript:window.location.href='accountMng.jsp?firmID=<%=firmID %>';">返回</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doAdd()
{	
	if(trim(frm.cardType.value) == "")
	{
		alert("请选择证件类型");
		frm.cardType.focus();
	}
	else if(trim(frm.card2.value) == "")
	{
		alert("请输入证件号码");
		frm.card2.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名");
		frm.accountName.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("请输入银行账户");
		frm.account.focus();
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
		
		//if(<%=(corr.isOpen==1)%>){
			//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
		//}
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>