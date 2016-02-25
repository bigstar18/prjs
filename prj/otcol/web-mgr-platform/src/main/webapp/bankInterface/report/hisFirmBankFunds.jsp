<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " ";

String firmID = Tool.delNull(request.getParameter("firmID"));
if(!firmID.trim().equals(""))
{
	filter += " and firmid='"+ firmID +"'";
}

String bankID = Tool.delNull(request.getParameter("bankID"));
if(!bankID.trim().equals("")&&!bankID.trim().equals("-1"))
{
	filter += " and bankID='"+ bankID +"'";
}

String primaryBankFlag = Tool.delNull(request.getParameter("primaryBankFlag"));
if(!primaryBankFlag.trim().equals("")&&!primaryBankFlag.trim().equals("-1"))
{
	filter += " and primaryBankFlag="+ primaryBankFlag +"";
}

String s_time = Tool.delNull(request.getParameter("s_time"));
if(s_time == null || "".equals(s_time)){
	s_time = Tool.fmtDate(new java.util.Date());
}
if(!s_time.trim().equals(""))
{
	filter += " and b_date = to_date('"+s_time+"','yyyy-mm-dd')";
}

filter +=" order by b_date desc";

Vector moneyList = dao.getFirmBankFundsHis(filter);

int maxpage = moneyList.size()%pageSize==0 ? moneyList.size()/pageSize : moneyList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>��ʷ�ʽ��ѯ</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>��ʷ�ʽ��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">ƽ̨����&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">���д���&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width: 100px">
							<OPTION value="-1">ȫ��</OPTION>
							<%
								for(int i=0;i<vecBankList.size();i++){
									BankValue bankv = vecBankList.get(i);
									%><option value="<%=bankv.bankID%>" <%=bankv.bankID.equals(bankID)?"selected":""%>><%=bankv.bankID%>|<%=bankv.bankName%></option><%
								}
							%>
						</select>
					</td>
					<td align="right">���ν�����&nbsp;</td>
					<td align="left">
						<select name="primaryBankFlag" class="normal" style="width: 100px">
							<OPTION value="-1">ȫ��</OPTION>
							<option value="1" <%="1".equals(primaryBankFlag)?"selected":""%>>��������</option>
              				<option value="2" <%="2".equals(primaryBankFlag)?"selected":""%>>�ν�����</option>
						</select>
					</td>
					<td align="right">��������&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">ƽ̨����</td>
				<td class="panel_tHead_MB" align="center">���б��</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_MB" align="center">���</td>
				<td class="panel_tHead_MB" align="center">���𶳽�</td>
				<td class="panel_tHead_MB" align="center">�������</td>
				<td class="panel_tHead_MB" align="center">Ȩ�涳��</td>
				<td class="panel_tHead_MB" align="center">Ȩ��</td>
				<td class="panel_tHead_MB" align="center">������</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				FirmBankFunds money = (FirmBankFunds)obj.get(i);
				%>
				<tr height="22" align=center >
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=money.firmID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.bankID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.bankName%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.balance)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.outMoneyFrozenFunds)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.OutInMoney)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.RIGHTSFROZENFUNDS)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.RIGHTS)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.FIRMFEE)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.primaryBankFlag==1?"��������":"�ν�����"%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.b_date%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11" align=right>
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
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
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