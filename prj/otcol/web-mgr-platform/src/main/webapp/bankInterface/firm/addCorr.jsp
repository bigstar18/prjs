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
		
		if (checkAccount(bankID)){//����¼����ʵ���п��ŵ�����
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
			msg = "�����������ʺ�ע��ɹ���";
		}
		else if(result == -2)
		{
			msg = "�����������ʺŶ�Ӧ��ϵ�Ѵ��ڣ�";
		}
		else
		{
			msg = "�����������ʺ�ע��ʧ�ܣ�";
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
		lv.setLogcontent(msg+" ���д��룺"+bankID+"�����̴��룺"+firmID);
		dao.log(lv);
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ע�ύ���������ʺ�</title>
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
	function init(){
		document.getElementById("bankCity").innerHTML = "<select style='width: 140px'><option value='0' selected>--��ѡ��--</option></select>";
	}
  </script>
	
  </head>
  
  <body onload="init()">
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>ע�ύ���������ʺ�</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����̴��룺&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���У�&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(" where validFlag=0 ");
						%>
						<select name="bankID" class="normal" style="width: 140px">
							<OPTION value="-1">��ѡ��</OPTION>
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
					<td align="right">�����ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ظ������ʺţ�&nbsp;</td>
					<td align="left">
						<input name="reaccount" value="" type=text  class="text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" style="display: none;">
					<td align="right">�����ڲ��ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account1" value="" type=text  class="text" maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�˻�����&nbsp;</td>
					<td align="left">
						<input name="accountName" value="" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1">���֤</option>
							<!--<option value="2">����֤</option>
							<option value="3">���ڻ���</option>
							<option value="4">���ڱ�</option>
							<option value="5">ѧԱ֤</option>
							<option value="6">����֤</option>
							<option value="7">��ʱ���֤</option>-->
							<option value="8">��֯��������</option>
							<option value="9">Ӫҵִ��</option>
							<option value="a">���˴���֤</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="" type=text  class="text" maxlength="20" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<!--input name="bankName" value="" type=text  class="text" maxlength="50" style="width: 140px"-->
						<select name="bankName" id="bankName" style="width: 140px">
							<option value="0">--��ѡ��--</option>
							<option value="NBCB">��������</option>
							<option value="BOCOMM">��ͨ����</option>
							<option value="CIB">��ҵ����</option>
							<option value="BOC">�й�����</option>
							<option value="CEB">�������</option>
							<option value="CCB">��������</option>
							<option value="ABC">ũҵ����</option>
							<option value="ICBC">��������</option>
							<option value="CMB">��������</option>
							<option value="CMBC">��������</option>
							<option value="CITIC">��������</option>
							<option value="SPDB">�ַ�����</option>
							<option value="UNION">����</option>
							<option value="SDB">���ڷ�չ����</option>
							<option value="PINGANBANK">ƽ������</option>
							<option value="HXB">��������</option>
							<option value="GDB">������Ͽ����</option>
							<option value="CQRCB">����ũ����ҵ����</option>
							<option value="PSBC">������������</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<!--input name="bankProvince" value="" type=text  class="text" maxlength="20" style="width: 140px"-->
						<select name="bankProvince" id="bankProvince" style="width: 140px" onChange="Change_Select()">
							<option value="0">--��ѡ��--</option>
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
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<div id="bankCity"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�绰��&nbsp;</td>
					<td align="left">
						<input name="mobile" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʼ���&nbsp;</td>
					<td align="left">
						<input name="Email" value="" type=text  class="text" maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">���</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">ȡ��</button>&nbsp;
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
		alert("��ѡ�����У�");
		frm.bankID.focus();
	}else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.account.value) == "")
	{
		alert("�����������ʺţ�");
		frm.account.focus();
	}
	else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.reaccount.value) == "")
	{
		alert("���ظ������ʺţ�");
		frm.account.focus();
	}
	else if(frm.bankID.value != "14" && frm.bankID.value != "005" && frm.bankID.value != "66" && trim(frm.reaccount.value) != trim(frm.account.value))
	{
		alert("�����˺����ظ������ʺŲ�һ�£�");
		frm.reaccount.value="";
		frm.account.focus();
	}
	else if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("��ѡ��֤������");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("������֤������");
		frm.card.focus();
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
		if(frm.bankID.value == "01" && frm.bankID.value != "14" ){
			frm.account.value="<%=Tool.getConfig("DefaultAccount")%>";
		}
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>