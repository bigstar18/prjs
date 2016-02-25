<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
BankDAO dao = BankDAOFactory.getDAO();

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("del"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] bankIDs = request.getParameterValues("ck");
	if(bankIDs != null)
	{
		String bankID = "";
		for(int i=0;i<bankIDs.length;i++)
		{
			try
			{
				dao.delBank(bankIDs[i]);
				bankID = bankID + bankIDs[i]+"��";
				String filter=" where userID='"+bankIDs[i]+"'";
				dao.delFeeInfo(filter);//ɾ�����е����������б�
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("ɾ�����У����д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
	}
	dao.log(lv);
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("forbid"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] bankIDs = request.getParameterValues("ck");
	if(bankIDs != null)
	{
		String bankID = "";
		for(int i=0;i<bankIDs.length;i++)
		{
			bankID = bankID + bankIDs[i]+"��";
			try
			{
				BankValue bank = dao.getBank(bankIDs[i]);
				bank.validFlag = 1;
				dao.modBank(bank);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("�������У����д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
	}
	dao.log(lv);
}

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("recover"))
{
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String[] bankIDs = request.getParameterValues("ck");
	if(bankIDs != null)
	{
		String bankID = "";
		for(int i=0;i<bankIDs.length;i++)
		{
			bankID = bankID + bankIDs[i]+"��";
			try
			{
				BankValue bank = dao.getBank(bankIDs[i]);
				bank.validFlag = 0;
				dao.modBank(bank);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		lv.setLogcontent("�ָ����У����д��룺"+bankID+"ʱ�䣺"+Tool.fmtTime(new java.util.Date()));
	}
	dao.log(lv);
}



int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " order by bankid ";

Vector dicList = dao.getBankList(filter);
int maxpage = dicList.size()%pageSize==0 ? dicList.size()/pageSize : dicList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(dicList, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ƾ֤�б�</title>
  </head>
  
  <body>
  	<form name="frm" id="frm" action="" method="post">
		<font style="font-size: 10pt;font-weight: bold;"></font>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1200px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'ck')"></td>
				<td class="panel_tHead_MB" align="left">���д���</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_MB" align="right">�������ת�˽��</td>
				<td class="panel_tHead_MB" align="right">ÿ�����ת�˽��</td>
				<td class="panel_tHead_MB" align="right">ÿ�����ת�˴���</td>
				<td class="panel_tHead_MB" align="left">������˶��</td>
				<!--td class="panel_tHead_MB" align="center">����������</td--><td  class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" align="left">ƽ̨�˻�����</td>
				<td class="panel_tHead_MB" align="left">״̬</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				BankValue bank = (BankValue)obj.get(i);
				int firmNum = dao.countFirmAccount(bank.bankID,"","","","");
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><input name="ck" type="checkbox" value='<%=bank.bankID%>'></td>
					<td class="underLine" align="left"><a href="#" onclick="bankInfo('<%=bank.bankID%>')"><%=bank.bankID%></a>&nbsp;</td>
					<td class="underLine" align="center"><%=bank.bankName%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtDouble2(bank.maxPerSglTransMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtDouble2(bank.maxPerTransMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=bank.maxPerTransCount%>&nbsp;</td>
					<td class="underLine" align="left"><%=Tool.fmtDouble2(bank.maxAuditMoney)%>&nbsp;</td>
					<!--td class="underLine" align="center"><a href="#" onclick="window.location='feeSet.jsp?bankID=<%=bank.bankID%>'">����</a>&nbsp;</td--><td class="underLine">&nbsp;</td>
					<td class="underLine" align="center"><a href="#" onclick="window.location='bankFirmList.jsp?bankID=<%=bank.bankID%>'"><%=firmNum%></a>&nbsp;</td>
					<td class="underLine" align="left">
					<%
						if(bank.validFlag == 0)
						{
							out.println("����");
						}
						else if(bank.validFlag == 1)
						{
							out.println("������");
						}
					%>
					&nbsp;</td>					
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
				<td class="pager" colspan="10" align=right>
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
	<TABLE width=100%>
	<TR align=center>
		<TD>
		<!--button type="button" class="smlbtn" onclick="goToAddBank()">��������</button>&nbsp;-->
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','del');">ɾ������</button>&nbsp;-->
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','forbid');">��������</button>&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','recover');">�ָ�����</button>&nbsp;
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function goToAddBank(){
	var result = window.showModalDialog("addBank.jsp","","dialogWidth=500px; dialogHeight=600px; status=yes;scroll=yes;help=no;");	
	if(result){
			window.location.reload();
		}
}
function bankInfo(v){
	var result = window.showModalDialog("modBank.jsp?bankID="+v+"","","dialogWidth=500px; dialogHeight=400px; status=yes;scroll=yes;help=no;");	
	if(result){
			window.location.reload();
		}
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