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
	//�����������ǩԼ����>>>>>>>>>>>>ȡ�ÿ���������ʡ��--start<<<<<<<<<<<
	//HXBBankDAO hxDao = BankDAOFactory.getHXBDAO();
	String filterForGetCitys = "where parentID='0'";
	Vector<CitysValue> cityValues = dao.getCityOfBank(filterForGetCitys);
	CitysValue cityValue = new CitysValue();
	//�����������ǩԼ����>>>>>>>>>>>>ȡ�ÿ���������ʡ��--end<<<<<<<<<<<
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
		corr.isCrossline = "1";//�ж��Ƿ�������ǩԼ���ֶ���Ϣ
		
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
			
			msg = "�����������ʺ�ǩԼʧ�ܣ�"+retuenValue.remark;
		}
		else
		{
			msg = "�����������ʺ�ǩԼ�ɹ���";
			
		}	

		%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg%>');
				window.returnValue="1";
				window.close();
			</SCRIPT>
		<%
		lv.setLogcontent(msg+": ���д��룺"+bankID+"�����̴��룺"+contact);
		dao.log(lv);
	}
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
    <title>�����˻�ǩԼ</title>
	<script language="javascript">
	function changeBank(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--��ѡ��--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var city = frm.bankCityName.value;
		var county = frm.bankCountyName.value;
		if(city != "0" && county != ""){
			countyChange();
		}
	}
	function provinceChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--��ѡ��--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var bankName = frm.RelatingAcctBanks.value;
		if(bankName == 0){
			alert("����ѡ�񿪻���");
			frm.bankProvince.value = 0;
			return;
		}
		document.getElementById("bankCounty").innerHTML = "<select name='bankCountyName' id='bankCountyName' style='width: 150px'><option value='0' selected>--��ѡ��--</option></select><span>*</span>";
		var cityID = document.getElementById('bankProvince').value;
		if(cityID == 0){
			alert("��ѡ��ʡ");
		}
		var url = "./ajaxForProvince.jsp?cityID=" + cityID;
		Change_Select(url,"bankCity");
	}
	function cityChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--��ѡ��--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var cityID = document.getElementById('bankCityName').value;
		if(cityID == ""){
			alert("��ѡ����");
		}
		var url = "./ajaxForCity.jsp?cityID=" + cityID;
		Change_Select(url,"bankCounty");
	}
	function countyChange(){
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--��ѡ��--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
		var cityID = document.getElementById('bankCountyName').value;
		if(cityID == 0){
			alert("��ѡ���أ�����");
		}
		var bank = frm.RelatingAcctBanks.value;
		var city = frm.bankCityName.value;
		var county = frm.bankCountyName.value;
		if(bank == 0){
			alert("��ѡ�񿪻���");
			return;
		}
		if(city ==0 || county == 0){
			alert("��ѡ�񿪻������ڳ���");
			return;
		}
		var url = "./ajaxForBanks.jsp?bank=" + bank + "&city=" + city + "&county=" + county;
		Change_Select(url,"bankFullName");
	}
	function bankNameChange(){
		var request;
		var bankName = frm.acctBankName.value;
		if(bankName == ""){
			alert("��ѡ�񿪻�������");
			document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span>></td></tr></table>";
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
						alert("���ܵõ�������Ϣ:" + request.statusText);
					}
				}
			}
			request.send(null);
	    }
	}
	var req;
	function Change_Select(url,element){//����һ���������ѡ����ı�ʱ���øú���
      //var province = document.getElementById('bankProvince').value;
      //if(province == 0){
	  //	alert("��ѡ��ʡ��");
	  //}
	  //var url = "./ajaxForProvince.jsp?province=" + province;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);
	  	//req.onreadystatechange = callback;//ָ���ص�����Ϊcallback
		req.onreadystatechange = function(){callback(element);}
	  	req.send(null);
	  }
    }
    //�ص�����
    function callback(element){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage(element);//����XML�ĵ�
        }else{
          alert("���ܵõ�������Ϣ:" + req.statusText);
        }
      }
    }
    //��������xml�ķ���
    function parseMessage(element){	  
	  var res = req.responseText;
	  if(!(res.indexOf('select')==-1)){//ȡ������ʱ
	     document.getElementById(element).innerHTML = res;
	  }else{//û��ȡ������ʱ
	     alert("ȡ�ö�Ӧ����ʧ��");
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
		document.getElementById("bankCity").innerHTML = "<select name='bankCityName' id='bankCityName' style='width: 150px'><option value='0' selected>--��ѡ��--</option></select>";
		document.getElementById("bankCounty").innerHTML = "<select name='bankCountyName' id='bankCountyName' style='width: 150px'><option value='' selected>--��ѡ��--</option></select>";
		document.getElementById("bankFullName").innerHTML = "<select name='acctBankName' id='acctBankName' style='width: 300px'><option value='-2' selected>--��ѡ��--</option></select><span>*</span>";
		document.getElementById("bankAddrAndNum").innerHTML = "<table border='0'><tr height='35'><td align='right'>�����е�ַ��</td><td align='left'><input name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'><span>*</span></td></tr><tr height='35'><td align='right'>�������кţ�</td><td align='left'><input name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 150px'><span>*</span></td></tr></table>";
	}
	
	function startLikeQuery(){
		//var RelatingAcctBanks = document.getElementById('RelatingAcctBanks').value;
		//if(RelatingAcctBanks == 0){
		//	alert("��ѡ�񿪻���");
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
			alert("��ѡ�񿪻���");
			return;
		}
		if( likeFilter == ""){
			alert("�������ѯ����");
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
			<div class="st_title">&nbsp;(�������)�������������˻�ǩԼ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
			<input type=hidden name="submitFlag" value="">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" type=hidden value="<%=firmID%>">
						<input name="contact" value="<%=Tool.delNull(contact)%>" readonly type=text  class="input_text" maxlength="10" style="width: 150px"><span class=star>*</span>
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�˻����ͣ�&nbsp;</td>
					<td align="left">
						<input onclick="AccountPropChange()" type = "radio" value = "1" name="AccountProp" id="AccountProp" CHECKED/>����
						<input onclick="AccountPropChange()" type = "radio" value = "0" name="AccountProp" id="AccountProp"/>��ҵ
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�����˺�(����)��&nbsp;</td>
					<td align="left">
						<input name="RelatingAcct" value="<%=Tool.delNull(firmUserValue.account)%>" type=text  maxlength="32" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�����˻�����&nbsp;</td>
					<td align="left">
						<input name="RelatingAcctName" readonly value="<%=Tool.delNull(firmUserValue.accountname)%>" type=text  maxlength="30" style="width: 150px"><span>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����У�&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBank" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="RelatingAcctBanks" id="RelatingAcctBanks" style="width: 150px" onChange="changeBank()">
							<option value="0" selected>--��ѡ��--</option>
							<option value="��������">�й���������</option>
							<option value="��������">�й���������</option>
							<option value="ũҵ����">�й�ũҵ����</option>
							<option value="�й�����">�й�����</option>
							<option value="��������">�й���������</option>
							<option value="ũҵ��չ">�й�ũҵ��չ����</option>
							<option value="��ͨ����">��ͨ����</option>
							<option value="��������">��������</option>
							<option value="�������">�������</option>
							<option value="��������">��������</option>
							<option value="�㶫��չ">�㶫��չ����</option>
							<option value="���ڷ�չ">���ڷ�չ����</option>
							<option value="��������">��������</option>
							<option value="��ҵ����">��ҵ����</option>
							<option value="�ֶ���չ">�Ϻ��ֶ���չ����</option>
							<option value="��������">��������</option>
							<option value="ũ����ҵ">ũ����ҵ����</option>
							<option value="ũ������">ũ��������</option>
							<option value="��������">����������</option>
							<option value="��������">�й�������������</option>
							<option value="��������">�й���������</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">
						<input id="qiYong" type="checkbox" onclick="startLikeQuery()">����ģ����ѯ&nbsp;
					</td>
					<td align="left">
						<input id="likeFilter" name="likeFilter" style="width: 230px" disabled="disabled">&nbsp;
						<input type="button" id="queryButton" name="queryButton" class="smlbtn" onclick="getBanks()" disabled="disabled" value="��ѯ"></input>&nbsp;
						<!--<button id="queryButton" name="queryButton" class="smlbtn" onclick="getBanks()" disabled="disabled">��ѯ</button>-->
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">����������ʡ��&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankProvince" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<select name="bankProvince" id="bankProvince" style="width: 150px" onChange="provinceChange()">
							<option value="0">--��ѡ��--</option>
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
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankCity" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<div id="bankCity"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����������أ�������&nbsp;</td>
					<td align="left">
						<!--input name="RelatingAcctBankCounty" value="" type=text  maxlength="30" style="width: 150px"><span>*</span-->
						<div id="bankCounty"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
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
									<td align="right">��ϵ�ˣ�&nbsp;</td>
									<td align="left">
										<input name="PersonName" type=text maxlength="50" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">�칫�绰��&nbsp;</td>
									<td align="left">
										<input name="OfficeTel" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right" >�ƶ��绰��&nbsp;</td>
									<td align="left">
										<input name="MobileTel" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">��ϵ��ַ��&nbsp;</td>
									<td align="left">
										<input name="Addr" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">�������룺&nbsp;</td>
									<td align="left">
										<input name="ZipCode" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
								<tr height="35">
									<td align="right">����������&nbsp;</td>
									<td align="left">
										<input name="LawName" type=text maxlength="20" style="width: 150px"><font color="red">&nbsp;(�˻�����Ϊ��ҵʱ����)</font>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>-->
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<option value="8"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a"<%=(firmUserValue.cardType!=null && firmUserValue.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(firmUserValue.card)%>" type=text maxlength="20" style="width: 150px"><span >*</span>
					</td>
				</tr>
				<!--<tr height="35">
					<td align="right">�Ƿ���Ҫ����֪ͨ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="NoteFlag" CHECKED/>��Ҫ
						<input type = "radio" value = "0" name="NoteFlag" />����Ҫ
					</td>
				</tr>
				<tr height="35">
					<td align="right">����֪ͨ�ֻ��ţ�&nbsp;</td>
					<td align="left">
						<input name="NotePhone" type=text maxlength="20" style="width: 150px"><span >*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="eMail" type=text  maxlength="20" style="width: 150px"><font color="red">&nbsp;</font>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���˱�ʶ��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "1" name="CheckFlag" CHECKED/>��Ҫ
						<input type = "radio" value = "0" name="CheckFlag" />����Ҫ
					</td>
				</tr>-->
				<tr height="35">					
					<td align="center" colspan=2>
					<input type="button" id="sub_btn" class="smlbtn" onclick="doAdd();" value="ǩԼ"></input>&nbsp;
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();" value="����"></input>&nbsp;
					
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
		alert("��������������ʺţ�");
		frm.RelatingAcct.focus();
	}
	else if((frm.RelatingAcctName.value) == "")
	{
		alert("����������˻�����");
		frm.RelatingAcctName.focus();
	}
	else if((frm.acctBankName.value) == "")
	{
		alert("��ѡ���������ƣ�");
		frm.acctBankName.focus();
	}
	else if((frm.RelatingAcctBankAddr.value) == "")
	{
		alert("�����뿪���е�ַ��");
		frm.RelatingAcctBankAddr.focus();
	}
	else if((frm.RelatingAcctBankCode.value) == "")
	{
		alert("�����뿪����ϵͳ�кţ�");
		frm.RelatingAcctBankCode.focus();
	}	
	
	else if((frm.cardType.value) == "")
	{
		alert("��ѡ��֤�����ͣ�");
		frm.cardType.focus();
	}	
	else if((frm.card.value) == "")
	{
		alert("������֤�����룡");
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