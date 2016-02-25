<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
Vector bankList = dao.getBankList(" where validFlag=0 ");
Vector<gnnt.trade.bank.vo.BankCode> bankcodes = dao.getBankCode(" ");
Map<String,String> bankCodeMap = new HashMap<String,String>();
for(int i=0;i<bankcodes.size();i++){
	bankCodeMap.put(bankcodes.get(i).bankID,bankcodes.get(i).bankCode);
}
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));

if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " and 1=1 ";

String firmID = Tool.delNull(request.getParameter("firmID"));
if(!firmID.trim().equals(""))
{
	filter += " and fbfs.firmid='"+ firmID +"'";
}

String SysFirmID = Tool.delNull(request.getParameter("SysFirmID"));
if(!SysFirmID.trim().equals(""))
{
	filter += " and fbfs.SysFirmID='"+ SysFirmID +"'";
}

String systemID = Tool.delNull(request.getParameter("systemID"));
if(!systemID.trim().equals(""))
{
	filter += " and fbfs.systemID='" + systemID + "'";
}

String bankCode = Tool.delNull(request.getParameter("bankCode"));
if(!bankCode.trim().equals(""))
{
	filter += " and fbfs.bankID='" + bankCode + "'";
}

System.out.println("filter=[" + filter + "]");

Vector moneyList = dao.getfirmIDMgs(filter);
int maxpage = moneyList.size()%pageSize==0 ? moneyList.size()/pageSize : moneyList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);

boolean secondAudit = "true".equalsIgnoreCase(Tool.getConfig("secondMoneyAudit"));
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ƾ֤�б�</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>�����̶�Ӧ��ϵ��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">ƽ̨��&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">�����̴���&nbsp;</td>
					<td align="left">
						<input name="SysFirmID" value="<%=SysFirmID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">����ϵͳ���&nbsp;</td>
					<td align="left">
						<input name="systemID" value="<%=systemID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">����&nbsp;</td>
					<td align="left">
						<select name="bankCode" class="normal" onchange="changeBank();" style="width:120px">
							<OPTION value="">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bv = (BankValue)bankList.get(i);
								if(!noAdapterBank(bv.bankID)){
								%>
								<option value="<%=bv.bankID%>" <%=bankCode.equals(bankCodeMap.get(bv.bankID))?"selected":""%>><%=bv.bankName%></option>		
								<%
								}
							}
							%>
						</select>
					</td>
					<td align="right">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="frmReset();">����</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<!--button type="button" class="smlbtn" onclick="merger();">�ϲ�=></button-->&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="left">&nbsp;</td>
				<td class="panel_tHead_MB" align="left">ƽ̨��</td>
				<td class="panel_tHead_MB" align="left">�����̴���</td>
				<td class="panel_tHead_MB" align="left">����ϵͳ���</td>
				<td class="panel_tHead_MB" align="left">����ϵͳ����</td>
				<td class="panel_tHead_MB" align="left">ǩԼ����</td>
				<td class="panel_tHead_MB" align="left">�����˻���</td>
				<td class="panel_tHead_MB" align="left">�����˻���</td>
				<td class="panel_tHead_MB" align="left">֤������</td>
				<td class="panel_tHead_MB" align="left">֤����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				FirmID2SysFirmID money = (FirmID2SysFirmID)obj.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="left"><input name="ck" type="radio" value='<%=money.firmID%>,<%=money.sysFirmID%>,<%=money.systemID%>'&nbsp;</td>
					<td class="underLine" align="left"><%=money.firmID%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.sysFirmID%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.systemID%>&nbsp;</td>
					<td class="underLine" align="left"><%=(systemsMap.get(money.systemID) == null ? "--" : systemsMap.get(money.systemID).systemName)%>&nbsp;</td>
					<td class="underLine" align="left"><%=(banksMap.get(money.bankID) == null ? "--" : banksMap.get(money.bankID).bankName)%>&nbsp;</td>
					<td class="underLine" align=left><%=money.account%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.accountName%>&nbsp;</td>
					<td class="underLine" align="left"><%=(CardType.cardTypeMap.get(money.cardType) == null ? "����֤��" : CardType.cardTypeMap.get(money.cardType))%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.card%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" align=center>&nbsp;</td>
				<td class="pager" colspan="9" align=right>
				��<%=pageIndex%>/<%=obj.getPageCount()%>ҳ &nbsp;&nbsp;��<%=obj.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageIndex != 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(pageIndex != obj.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function merger(){
	var ck = null;
	var radios = document.getElementsByName("ck");
	for(var i=0;i<radios.length;i++){
		if(radios[i].checked == true){
			ck = radios[i].value;
		}
	}
	if(ck == null){
		alert("��ѡ������");
		return;
	}
	var pram = ck.split(',');
	var firmID = pram[0];
	var sysFirmID = pram[1];
	var systemID = pram[2];
	var newFirmID = window.showModalDialog("mergerPlatformNum.jsp?firmID="+"","","dialogWidth=520px; dialogHeight=520px; status=yes;scroll=no;help=no;");
	if(newFirmID == null){
		alert("Ҫ�ϲ�����ƽ̨��ѡ��ʧ��");
		return;
	}
	window.location = "mergerPlatformNum2.jsp?oldFirmID=" + firmID + "&sysFirmID=" + sysFirmID + "&systemID=" + systemID + "&newFirmID=" + newFirmID;
}

function pgTurn(i)
{
	if(i == 0)
	{
		frm.pageIndex.value = 1;
		frm.submit();
	}
	else if(i == 1)
	{		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	}
	else if(i == 2)
	{
		frm.pageIndex.value = <%=obj.getPageCount()%>;
		frm.submit();
	}
	else if(i == -1)
	{
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function doQuery()
{
	frm.pageIndex.value = 1;
	frm.submit();
}

function frmReset()
{
	frm.firmID.value = "";
	frm.SysFirmID.value = "";
	frm.systemID.value = "";
	frm.bankCode.value = "";
}

function pgJumpChk()
{
	if(event.keyCode == 13)
	{
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
		{
			alert("������1 - <%=obj.getPageCount()%>������֣�");
		}
		else
		{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}


//-->
</SCRIPT>