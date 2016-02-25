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
	//�޸�ע����Ϣ��������������ʡ�ݿ���ֱ�Ӹ�������
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
			msg = "�޸Ľ����������ʺųɹ��������̴��룺"+firmID+"���������˺ţ�"+account+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert("�����������ʺ��޸ĳɹ���");
				window.location.href="accountMng.jsp?firmID=<%=firmID%>";
				//window.close();
				//-->
				</SCRIPT>	
			<%
			return;
		}
		else
		{
			msg = "�����������ʺ��޸�ʧ�ܣ������̴��룺"+firmID+"���������˺ţ�"+account+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
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
    <title>�޸Ľ����������ʺ�</title>
	<script language="javascript">
	var req;
	function Change_Select(){//����һ���������ѡ����ı�ʱ���øú���
      var provinceCode = document.getElementById('bankProvince').value;
      if(provinceCode == 0){
		alert("��ѡ��ʡ��");
	  }
	  var url = "./ajaxAddCorr.jsp?provinceCode=" + provinceCode;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);         
	  	req.onreadystatechange = callback;//ָ���ص�����Ϊcallback
	  	req.send(null);
	  }
    }
    //�ص�����
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//����XML�ĵ�
        }else{
          alert("���ܵõ�������Ϣ:" + req.statusText);
        }
      }
    }
    //��������xml�ķ���
    function parseMessage(){	  
	  var res = req.responseText;
		if(!(res.indexOf('select')==-1)){//ȡ������ʱ
	     	document.getElementById("bankCity").innerHTML = res;
	     }else{//û��ȡ������ʱ
	     	alert("ȡ�ö�Ӧ����ʧ��");
	  	return;
	     }
    }
  </script>
	
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>�޸Ľ����������ʺ�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���У�&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account2" value="<%=replaceNull(account)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text class="text" maxlength="30"><span class=star>*</span>
						<input name="account" value="<%=replaceNull(account)%>"type="hidden" class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������˻�����&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=replaceNull(corr.accountName)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select>
					</td>
					<SCRIPT LANGUAGE="JavaScript">
						//frm.cardType.value='<%=replaceNull(corr.cardType)%>';
					</SCRIPT>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card2" value="<%=replaceNull(corr.card)%>" <%=(corr.isOpen==1) ? "readonly" : ""%> type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<select name="bankName" id="bankName" style="width: 150px">
							<option value="0">--��ѡ��--</option>
							<option value="NBCB" <%if(corr.bankName!=null && corr.bankName.equals("NBCB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="BOCOMM" <%if(corr.bankName!=null && corr.bankName.equals("BOCOMM")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��ͨ����</option>
							<option value="CIB" <%if(corr.bankName!=null && corr.bankName.equals("CIB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��ҵ����</option>
							<option value="BOC" <%if(corr.bankName!=null && corr.bankName.equals("BOC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>�й�����</option>
							<option value="CEB" <%if(corr.bankName!=null && corr.bankName.equals("CEB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>�������</option>
							<option value="CCB" <%if(corr.bankName!=null && corr.bankName.equals("CCB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="ABC" <%if(corr.bankName!=null && corr.bankName.equals("ABC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>ũҵ����</option>
							<option value="ICBC" <%if(corr.bankName!=null && corr.bankName.equals("ICBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="CMB" <%if(corr.bankName!=null && corr.bankName.equals("CMB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="CMBC" <%if(corr.bankName!=null && corr.bankName.equals("CMBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="CITIC" <%if(corr.bankName!=null && corr.bankName.equals("CITIC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="SPDB" <%if(corr.bankName!=null && corr.bankName.equals("SPDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>�ַ�����</option>
							<option value="UNION" <%if(corr.bankName!=null && corr.bankName.equals("UNION")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>����</option>
							<option value="SDB" <%if(corr.bankName!=null && corr.bankName.equals("SDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>���ڷ�չ����</option>
							<option value="PINGANBANK" <%if(corr.bankName!=null && corr.bankName.equals("PINGANBANK")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>ƽ������</option>
							<option value="HXB" <%if(corr.bankName!=null && corr.bankName.equals("HXB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>��������</option>
							<option value="GDB" <%if(corr.bankName!=null && corr.bankName.equals("GDB")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>������Ͽ����</option>
							<option value="CQRCB" <%if(corr.bankName!=null && corr.bankName.equals("CQRC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>����ũ����ҵ����</option>
							<option value="PSBC" <%if(corr.bankName!=null && corr.bankName.equals("PSBC")){out.print("selected");}else if(corr.isOpen==1){out.print("disabled");}%>>������������</option>
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
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<select name="bankProvince" id="bankProvince" style="width: 150px" onChange="Change_Select()">
							<option value="0">--��ѡ��--</option>
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
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<div id="bankCity">
							<select name="bankCityName" id="bankCityName" style="width: 150px">
							<option value="0">--��ѡ��--</option>
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
					<td align="right">�绰��&nbsp;</td>
					<td align="left">
						<input name="mobile" value="<%=replaceNull(corr.mobile)%>" type=text  class="text" maxlength="20">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="Email" value="<%=replaceNull(corr.email)%>" type=text  class="text" maxlength="30">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">�޸�</button>&nbsp;
						<button type="button" class="smlbtn" onclick="javaScript:window.location.href='accountMng.jsp?firmID=<%=firmID %>';">����</button>&nbsp;
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
		alert("��ѡ��֤������");
		frm.cardType.focus();
	}
	else if(trim(frm.card2.value) == "")
	{
		alert("������֤������");
		frm.card2.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("�����������˻�");
		frm.account.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankName.value) == 0)
	{
		alert("��ѡ�񿪻�������");
		frm.bankName.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankProvince.value) == 0)
	{
		alert("��ѡ�񿪻���ʡ��");
		frm.bankProvince.focus();
	}
	else if(frm.bankID.value == "79" && trim(frm.bankCityName.value) == 0)
	{
		alert("��ѡ�񿪻��г���");
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