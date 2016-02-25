<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
BankDAO dao = BankDAOFactory.getDAO();
//��ȡƽ̨��������н���ϵͳ
Vector<SystemMessage> vecSys = null;
try{
	vecSys = dao.getSystemMessages("");
}catch(Exception e){
	e.printStackTrace();
}
if(vecSys == null || vecSys.size() < 0){
	vecSys = new Vector<SystemMessage>();
}
Map<String, SystemMessage> sysMap = new HashMap<String, SystemMessage>();
for(SystemMessage sm : vecSys){
	sysMap.put(sm.systemID, sm);
}
String submitFlag = request.getParameter("submitFlag");
String systemID = request.getParameter("systemID");
String tradeDate = request.getParameter("tradeDate");
if(tradeDate == null || "".equals(tradeDate) || "null".equals(tradeDate)){
	tradeDate = Tool.fmtDate(new java.util.Date());
}
Vector dataList = new Vector<RZQS>();
ReturnValue result = new ReturnValue();
PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
RequestMsg req = new RequestMsg();
req.setBankID("");
System.out.println("=========submitFlag[" + request.getParameter("submitFlag") + "]============");
if( "one".equals(request.getParameter("submitFlag"))){
	req.setMethodName("getRZDataBySystemID");
	req.setParams(new Object[]{Tool.strToDate(tradeDate), systemID});
	try{
		result = cp.doWork(req);//ִ���ʽ����
	}catch(Exception e){
		e.printStackTrace();
		result.result = -1;
	}
	if(result.result == 0){
		dataList = (Vector) result.msg[0];
	}
}else if("all".equals(request.getParameter("submitFlag"))){
	req.setMethodName("getRZData");
	req.setParams(new Object[]{Tool.strToDate(tradeDate)});
	try{
		result = cp.doWork(req);//ִ���ʽ����
	}catch(Exception e){
		e.printStackTrace();
		result.result = -1;
	}
	if(result.result == 0){
		dataList = (Vector) result.msg[0];
	}
}

int maxpage = dataList.size()%pageSize==0 ? dataList.size()/pageSize : dataList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(dataList, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>��ѯ��������</title>
  </head>
  
  <body>
  	<form id="frm" action="getRZData.jsp" method="POST">
		<fieldset width="100%">
		<legend>��ѯ��������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="800px">
				<tr height="35">
					<td width="50%" align="right">����ϵͳ��</td>
					<td align="left">
						<select name="systemID" style="width:120px;">
							<option value="-1">��ѡ��</option>
							<%for(SystemMessage sm : vecSys){%>
								<option value="<%=sm.systemID%>"><%=sm.systemID%>|<%=sm.systemName%></option>
							<%}%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">&nbsp;��&nbsp;�ڣ�</td>
					<td align="left">
						<MEBS:calendar eltName="tradeDate" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=tradeDate%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan="2">
						&nbsp;&nbsp;<input type="button" class="smlbtn" name="saveOne" value="��������" onclick="doCompare(1)"/>&nbsp;&nbsp;
					<!--/td>
					<td align="left"-->
						<!--input type="button" class="smlbtn" value="��������" name="saveAll" onclick="doCompare(2)"/-->
						<input type="hidden" name="submitFlag" value=""/>
					</td>
				</tr>
          </table>
		</fieldset>
		<fieldset width="100%" >
			<legend>�������ݲ������</legend>
			<table><tr height="20"><td>
			<font  style="color: red;font-size: 12px; margin-left: 100px;">
				<%
				if(submitFlag == null || "".equals(submitFlag) || "null".equals(submitFlag)){
					out.print("��δִ���������ݵĲ���");
				}else if(result.result == 0){//��ѯ�������ݳɹ�
					out.print("�����������ݳɹ�");
				}else{
					out.print("������������ʧ�ܣ�ʧ��ԭ��" + result.remark);
				}
				%>
			</font>
			</td></tr></table><!--
			<%
				if(submitFlag != null && result.result == 0){
			%>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1200px" height="400">
				<tHead>
					<tr height="25" align=center>
						<td class="panel_tHead_LB">&nbsp;</td>
						<td class="panel_tHead_MB" align="center">��������</td>
						<td class="panel_tHead_MB" align="center">����ϵͳ</td>
						<td class="panel_tHead_MB" align="center">�����̴���</td>
						<td class="panel_tHead_MB" align="center">���տ���</td>
						<td class="panel_tHead_MB" align="center">���տ���</td>
						<td class="panel_tHead_MB" align="center">����Ȩ��</td>
						<td class="panel_tHead_MB" align="center">����Ȩ��</td>
						<td class="panel_tHead_MB" align="center">������</td>
						<td class="panel_tHead_RB">&nbsp;</td>
					</tr>
				</tHead>
				<tBody>

				<%
				for(int i=0;i<obj.getCurNum();i++)
				{
					RZQS money = (RZQS)obj.get(i);
				%>
					<tr height="22" align=center onclick="selectTr();">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="underLine" align="center"><%=Tool.fmtDate(money.date)%>&nbsp;</td>
						<td class="underLine" align="left"><%=sysMap.get(money.systemID) == null ? "�����쳣" : sysMap.get(money.systemID).systemName%>&nbsp;</td>
						<td class="underLine" align="left"><%=money.firmID%>&nbsp;</td>
						<td class="underLine" align=right><%=Tool.fmtMoney(money.balance)%>&nbsp;</td>
						<td class="underLine" align=right><%=Tool.fmtMoney(money.lastBalance)%>&nbsp;</td>
						<td class="underLine" align=right><%=Tool.fmtMoney(money.rights)%>&nbsp;</td>
						<td class="underLine" align=right><%=Tool.fmtMoney(money.lastRights)%>&nbsp;</td>
						<td class="underLine" align=right><%=Tool.fmtMoney(money.fee)%>&nbsp;</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>	
					<%
						}
					%>
				</tBody>
				<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td colspan="8">&nbsp;</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="pager" colspan="8" align=right>
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
						<td class="panel_tFoot_MB" colspan="8"></td>
						<td class="panel_tFoot_RB">&nbsp;</td>
					</tr>
				</tFoot>
			</table>
			<%	
				}
			%>-->
		</fieldset>
    </form>
  </body>
</html>

<script type="text/javascript"> 
		
		
	function doCompare(n)
	{
		if(frm.systemID.value == -1 && n == 1){
			alert("��ѡ����ϵͳ");
			return;
		}else if(frm.tradeDate.value == null){
			alert("��ѡ������");
			return;
		}
		if(n == 1){
			frm.submitFlag.value = "one";
			frm.saveOne.disabled = 'disabled';
		}else if(n == 2){
			frm.submitFlag.value = "all";
			frm.saveAll.disabled = 'disabled';
		}
		frm.submit();
			
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
</script>