<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
	String resultToBankDate = (String)request.getSession().getAttribute("resultToBankDate");
	BankDAO dao = BankDAOFactory.getDAO();

	int pageSize = PAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex = Tool.strToInt(request.getParameter("pageIndex"));
	if (pageIndex < 0)
		pageIndex = 1;
	ObjSet obj =null;
	
	boolean checkSuccess = false;
	String checkMsg = "";
	boolean marketStutas = dao.getTraderStatus();
	Vector<CompareResult> compareResultList = null;
	if(resultToBankDate!=null)
	{
		if(marketStutas)
		{
			java.util.Date result2BankDate = Tool.strToDate(resultToBankDate);
			compareResultList = cp.checkMoney(result2BankDate);	
			if(compareResultList.size() == 0)
			{
				checkSuccess = true;
			}
			else 
			{
				checkSuccess = false;
				checkMsg = "ϵͳ������ϣ����˲�ƽ��";
			}
		}
		else
		{
			checkSuccess = false;
			checkMsg = "ϵͳ��δ���㣡";
		}
	}
	else
	{
		checkMsg = "δ���˻����ʧ�ܣ���ʱ���ܷ��ͽ������ݣ�";
	}
		
	String filter = " 1=1 ";
	String firmID = Tool.delNull(request.getParameter("firmID"));
	if(firmID==null||"".equals(firmID))
	{
		firmID="-1";
	}
	String moduleId = Tool.delNull(request.getParameter("moduleid"));
	int moduleid = -1;
	if(!"".equals(moduleId.trim()))
	{
		moduleid = Integer.parseInt(moduleId);
	}
	if (!firmID.trim().equals("")&&!"-1".equals(firmID)) {
		filter += " and f.firmID='" + firmID + "' ";
	}
	String s_time = (String)request.getAttribute("s_time");
	if(s_time==null)
	{
		s_time = Tool.fmtDate(new java.util.Date()) ;
		filter += " and f.b_date>=to_date('" + s_time+ "','yyyy-mm-dd')";
	}
	String e_time = (String)request.getAttribute("e_time");
	if(e_time==null)
	{
		e_time = Tool.fmtDate(new java.util.Date()) ;
		filter += " and f.b_date<=to_date('" + e_time+ "','yyyy-mm-dd')";
	}
	List list = dao.getTradeDataInList(filter,moduleid);
	obj = ObjSet.getInstance(list, pageSize, pageIndex);
	
	String oprValue = request.getParameter("opt");
	//�˶Գɹ�,�����ݵ����У����ؽ���ɹ����г��ܶ���ж���
	int resultToBank = 0;//�����ݵ����еĲ������
	String showMsg = "";
	boolean showSendMsg = false;
	if(checkSuccess && oprValue!=null && "DataToBank".equals(oprValue.trim()))
	{
		Hashtable ht = dao.getTradeDataInHashTable(filter,moduleid);
		resultToBank = 0;//cp.setMoneyInfoByHashtable(null,ht);
		if(resultToBank==0)
		{
			showMsg = "�����ݵ����гɹ���";
			request.getSession().setAttribute("resultToBank","OK");
		}
		else if(resultToBank==1)
		{
		showMsg = "û���ҵ���Ӧ����������";
		}
		else if(resultToBank==3)
		{
		showMsg = "�쳣��";
		}
		else if(resultToBank>100)
		{
		showMsg = "�����������"+(resultToBank-100)+"�����������ִ���";
		}
		showSendMsg = true;
		
		LogValue  = new LogValue(AclCtrl.getLogonID(request),"���������ݵ�����");
		lv.setIp(computerIP);
		dao.log(lv);
	}
%>

<html xmlns:MEBS>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=GBK">
		<IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
		<title></title>
	</head>

	<body>
		<form id="frm" name="frm" action="" method="post">
			<fieldset width="95%">
				<legend>��������</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
					<td align="right"> �����̴���&nbsp; </td>
						<td align="left">
							<select id="firmID" name="firmID" class="normal" style="width: 100px">
							<OPTION value="-1">��ѡ��</OPTION>
							<%
							Vector firmList = dao.getFirmList("");
							for(int i=0;i<firmList.size();i++)
							{
								FirmValue bv = (FirmValue)firmList.get(i);
								%>
								<option value="<%=bv.firmID%>"><%=bv.name%></option>		
								<%
							}
							%>							
						</select>
						<script type="text/javascript">
							frm.firmID.value='<%=firmID %>';
						</script>
						</td>
						<td align="right"> ���װ��&nbsp; </td>
						<td align="left">
							<select name="moduleid" class="normal" style="width: 50px">
								<OPTION value="-1">ȫ��</OPTION>
								<option value="2">Զ��</option>
								<option value="3">�ֻ�</option>
								<option value="4">����</option>
							</select>
						</td>
						<td align="right">
							����&nbsp;
						</td>
						<td align="left">
							<MEBS:calendar eltName="s_time" eltStyle="width:80px"
								eltImgPath="../skin/default/images/" eltValue="<%=s_time%>" />
							&nbsp;��&nbsp;
							<MEBS:calendar eltName="e_time" eltStyle="width:80px"
								eltImgPath="../skin/default/images/" eltValue="<%=e_time%>" />
						</td>
						<td align="left">
							<button <%if(!checkSuccess){out.print(" disabled='disabled' ");} %> type="button" class="smlbtn" onclick="doQuery();">
								��ѯ
							</button>
							&nbsp;
							<button type="button" class="smlbtn" onclick="frm.reset();">
								����
							</button>
							&nbsp;
						</td>
					</tr>
				</table>
			</fieldset><br>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="400">
				<tHead>
					<tr height="25" align=center>
						<td class="panel_tHead_LB"> &nbsp; </td>
						<td class="panel_tHead_MB"> �г���ˮ�� </td>
						<td class="panel_tHead_MB"> �����̴��� </td>
						<td class="panel_tHead_MB"> ���д��� </td>
						<td class="panel_tHead_MB"> �����˺� </td>
						<td class="panel_tHead_MB"> ҵ����� </td>
						<td class="panel_tHead_MB"> ���׷����� </td>
						<td class="panel_tHead_MB"> �ʽ���� </td>
						<td class="panel_tHead_MB"> �����ʽ�� </td>
						<td class="panel_tHead_MB"> �������� </td>
						<td class="panel_tHead_RB"> &nbsp; </td>
					</tr>
				</tHead>
				
				<%if(!checkSuccess&&!showSendMsg){ %>
				<tBody>
					<tr height="22" align=center  onclick="selectTr();">
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine" colspan="9"><font style="color: red"><%=checkMsg %></font></td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
				</tBody>
				<%}else if(checkSuccess&&!showSendMsg){ %>
				<tBody>
					<tr height="22" align=center onclick="selectTr();">
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine" colspan="9"><%=showMsg %></td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
				</tBody>
				<%}else{ %>
				<tBody>
					<%
							for (int i = 0; i < obj.getCurNum(); i++) {
							TradeResultValue trv = (TradeResultValue) obj.get(i);
					%>
					<tr height="22" align=center>
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine"><%=trv.fundFlowId%> &nbsp; </td>
						<td class="underLine"><%=trv.firmid%> &nbsp; </td>
						<td class="underLine"><%=trv.bankid%> &nbsp; </td>
						<td class="underLine"><%=trv.account%> &nbsp; </td>
						<td class="underLine"><%=trv.oprCode%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.amount%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.balance%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.appendAmount%> &nbsp; </td>
						<td class="underLine"><%=trv.date%> &nbsp; </td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
					<%
					}
					%>
				</tBody>
				<%} %>
				<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td colspan="9">&nbsp;</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="pager" colspan="9" align=right>
							��
							<%=pageIndex%>
							/
							<%=obj.getPageCount()%>
							ҳ &nbsp;&nbsp;��
							<%=obj.getTotalCount()%>
							�� &nbsp;&nbsp;ÿҳ
							
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">
							�� &nbsp;&nbsp;
							<%
							if (pageIndex != 1) {
							%>
							<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span>
							&nbsp;&nbsp;
							<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span>
							&nbsp;&nbsp;
							<%
							} else {
							%>
							��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;
							<%
								}

								if (pageIndex != obj.getPageCount()) {
							%>
							<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span>
							&nbsp;&nbsp;
							<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span>
							&nbsp;&nbsp;
							<%
							} else {
							%>
							��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;
							<%
							}
							%>
							��
							<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">
							ҳ
							<input name=pageIndex type=hidden value="<%=pageIndex%>">
						</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">&nbsp;</td>
						<td class="panel_tFoot_MB" colspan="9"></td>
						<td class="panel_tFoot_RB">&nbsp;</td>
					</tr>
				</tFoot>
			</table>
			<TABLE width=100%>
				<TR align=center>
					<TD>
					<%if(checkSuccess){ %>
						<input type="button" class="bigbtn" value="���������ݵ�����" onclick="sendDataToBank()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%} %>
					</TD>
				</TR>
			</TABLE>
			<input type="hidden" name="opt">
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
	var bankid = frm.firmID.value;
	//var s_time = frm.s_time.value;
	//var e_time = frm.s_time.value;
	if(bankid == -1)
	{
		alert("��ѡ�����̣�");
	}
	else
	{
		frm.pageIndex.value = 1;
		frm.opt.value="do";
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
function sendDataToBank()
{
	frm.opt.value="DataToBank";
	frm.submit();
}
//-->
</SCRIPT>
