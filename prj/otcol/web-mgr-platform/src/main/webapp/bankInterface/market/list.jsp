<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
String result = "";
BankDAO dao = BankDAOFactory.getDAO();
String submitFlag = request.getParameter("submitFlag");
String checkSystemID = request.getParameter("checkSystemID");
if(checkSystemID == null){
	result = "δѡ����ϵͳ";
}
if(submitFlag != null && checkSystemID != null){
	Vector<SystemMessage>  sysVec = dao.getSystemMessages(" where systemID='" + checkSystemID + "'");
	if(sysVec == null || sysVec.size() <= 0){
		result = "δ�ҵ���Ӧ����ϵͳ��Ϣ";
	}else{
		SystemMessage sysMsg = sysVec.get(0);
		if("2".equals(submitFlag)){//������ϸ�ļ�
			java.util.Date now = new java.util.Date();
			if(Tool.time2Today(sysMsg.startTime).after(now) || Tool.time2Today(sysMsg.endTime).before(now)){
					result = "�ý���ϵͳ����ת��ʱ����";
			}else{
				CapitalProcessorRMI cp = null;
				try{
					cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
					if(cp == null){
						result = "��ȡƽ̨������ʧ��";
					}else{
						ReturnValue rev = cp.getSystemDetailsFile(sysMsg.systemID);
						result = rev.remark;
					}
				}catch(Exception e){
					result = "��ȡƽ̨�������쳣";
				}
			}
		}else{
			result = "ѡ������";
		}
	}
}

int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " ";

Vector dicList = dao.getSystemMessages(filter);
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
    <title>�г��б�</title>
  </head>
  
  <body>
  	<form name="frm" id="frm" action="" method="post">
		<font style="font-size: 10pt;font-weight: bold;"></font>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1200px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">����ϵͳ���</td>
				<td class="panel_tHead_MB" align="center">����ϵͳ����</td>
				<td class="panel_tHead_MB" align="center">���׿�ʼʱ��</td>
				<td class="panel_tHead_MB" align="center">���׽���ʱ��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				SystemMessage bank = (SystemMessage)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><input name="ck" type="radio" value='<%=bank.systemID%>'></td>
					<td class="underLine" align="center"><%=bank.systemID%>&nbsp;</td>
					<td class="underLine" align="center"><%=bank.systemName%>&nbsp;</td>
					<td class="underLine" align="center"><%=(bank.startTime == null ? "--" : bank.startTime)%>&nbsp;</td>
					<td class="underLine" align="center"><%=(bank.endTime == null ? "--" : bank.endTime)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="5" align=right>
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
				<td class="panel_tFoot_MB" colspan="5"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<TABLE width=100%>
	<TR align=center>
		<TD>
		<!--button type="button" class="smlbtn" onclick="deleteRec('2');">������ϸ</button-->&nbsp;
		<input type=hidden name=submitFlag value="">
		<input type=hidden name=checkSystemID value="">
		</TD>
	</TR>
	</TABLE>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<%if(submitFlag != null){%>
	alert('<%=result%>');
<%}%>
<!--
function deleteRec(flag){
	var ck = null;
	var radios = document.getElementsByName("ck");
	for(var i=0;i<radios.length;i++){
		if(radios[i].checked == true){
			ck = radios[i].value;
		}
	}
	if(ck == null){
		alert("����ѡ��һ����Ϣ");
	}else{
		frm.submitFlag.value = flag;
		frm.checkSystemID.value = ck;
		frm.submit();
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