<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
BankDAO dao = BankDAOFactory.getDAO();
String firmID = request.getParameter("firmID");
if(firmID == null || firmID.trim().length()<=0){
	//firmID = Tool.delNull((String)request.getAttribute("firmID"));
	//if(firmID == null || firmID.trim().length()<=0){
		out.println("��Ч�Ľ����̴��룡");
		return;
	//}
}
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt((String)request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " where firmid='"+ firmID +"' order by bankid ";


Vector dicList = dao.getCorrespondList(filter);

String bankID = "";
if(dicList != null && dicList.size() > 0){
	bankID = ((CorrespondValue)dicList.get(0)).bankID;
}

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
    <title>�����̺����а󶨹�ϵ�б�</title>
  </head>
  
  <body>
  	<form id="frm" action="accountMng2.jsp" method="post" name="frm">
		<font style="font-size: 10pt;font-weight: bold;">������&nbsp;<%=firmID%>&nbsp;�����ʺŹ���</font>
		<input type="hidden" name="firmID" value="<%=firmID%>">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'ck')"></td>
				<td class="panel_tHead_MB">���д���</td>
				<td class="panel_tHead_MB" align=left>�����ʺ�</td>
				<td class="panel_tHead_MB" align=left>�����ڲ��ʺ�</td>
				<td class="panel_tHead_MB" align=left>�˻���</td>
				<td class="panel_tHead_MB" align=left>����������</td>
				<td class="panel_tHead_MB" align=left>������ʡ��</td>
				<td class="panel_tHead_MB" align=left>������������</td>
				<td class="panel_tHead_MB" align=left>ǩԼ״̬</td>
				<td class="panel_tHead_MB" align=left>�ʺ�״̬</td>
				<td class="panel_tHead_MB" align=left>�����ʽ�</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				CorrespondValue corr = (CorrespondValue)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><input name="ck" type="checkbox" value='<%=corr.bankID%>,<%=corr.firmID%>,<%=corr.account%>,<%=corr.account1%>,<%=corr.card%>,<%=corr.cardType%>,<%=corr.isOpen%>'></td>
					<td class="underLine"><%=corr.bankID%>&nbsp;</td>
					<td class="underLine" align=left>
					<% 
						Tool tool = new Tool();
						String DefaultAccount=tool.getConfig("DefaultAccount");
					if(!replaceNull(corr.account).equals(DefaultAccount)){ 
					%>
					<%=replaceNull(corr.account)%>
					<%}%>&nbsp;
					</td>
					<td class="underLine" align=left><%=replaceNull(corr.account1)%>&nbsp;</td>
					<td class="underLine" align=left><%=replaceNull(corr.accountName)%>&nbsp;</td>
					<td class="underLine" align=left>
					<%
						if(corr.bankName == null || "0".equals(corr.bankName) || "".equals(corr.bankName) || "null".equals(corr.bankName)){
							out.print("-");
						}else{
							if(BankCode.codeForBank.get(corr.bankName) == null){
								out.print(corr.bankName);
							}else{
								out.print(BankCode.codeForBank.get(corr.bankName));
							}
						}
					%>&nbsp;
					</td>
					<td class="underLine" align=left>
					<%
						if(corr.bankProvince == null || "0".equals(corr.bankProvince) || "".equals(corr.bankProvince) || "null".equals(corr.bankProvince)){
							out.print("-");
						}else{
							if(map.get(corr.bankProvince) == null){
								out.print(corr.bankProvince);
							}else{
								out.print(map.get(corr.bankProvince).getCityName());
							}
						}
					%>&nbsp;
					</td>
					<td class="underLine" align=left>
					<%
						if(corr.bankCity == null || "0".equals(corr.bankCity) || "".equals(corr.bankCity) || "null".equals(corr.bankCity)){
							out.print("-");
						}else{
							if(map.get(corr.bankCity) == null){
								out.print(corr.bankCity);
							}else{
								out.print(map.get(corr.bankCity).getCityName());
							}
						}
					%>&nbsp;
					</td>
					
					
					<td class="underLine" align=left>
					<%
						if(corr.isOpen == 0)//||corr.isOpen == 2
						{
							out.println("δǩԼ");
						}
						else if(corr.isOpen == 1)
						{
							out.println("�ɹ�");
						}
						else if(corr.isOpen == 2)
						{
							out.println("ԤǩԼ�ɹ�");
						}
						else if(corr.isOpen == 3)
						{
							out.println("�ѽ�Լ");
						}
					%>
					&nbsp;</td>
					<td class="underLine">
					<%
						if(corr.status == 0)
						{
							out.println("����");
						}
						else if(corr.status == 1)
						{
							out.println("������");
						}
					%>
					&nbsp;</td>
					<td class="underLine" align=left><%=Tool.fmtDouble2(corr.frozenFuns)%>&nbsp;</td>
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
	<TABLE width=100%>
	<TR align=center>
		<TD>
		<%
		if(dicList == null || dicList.size() <= 0){
		%>
		<!--button type="button" class="smlbtn" onclick="addCorr();">ע���ʺ�</button-->&nbsp;
		<%
		} else {
			if("79".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','synAccount');">ԤǩԼ</button-->&nbsp;
		<%
			}
			if("17".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','synchro');">ͬ���˺�</button-->&nbsp;
		<!--button type="button" class="smlbtn" onclick="bankFlag();">����ǩԼ</button-->&nbsp;
		<%
			}
			if(openAccountBank(bankID) && !"79".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','open');">ǩԼ�˺�</button-->&nbsp;
		<%
			}
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','del');">ע���ʺ�</button-->&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','forbid');">�����ʺ�</button>&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','recover');">�ָ��ʺ�</button>&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','resetsmmy');">��������</button>&nbsp;
		<button type="button" class="smlbtn" onclick="forceMod();">ǿ���޸�</button>&nbsp;
		<%}%>
		<button type="button" class="smlbtn" onclick="window.location='list.jsp';">����</button>&nbsp;
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function checkNum(){//��ѡ��ѡ�������
	var str=document.getElementsByName("ck");
	var objarray=str.length;
	var chestr=0;
	var ch="";
	for (i=0;i<objarray;i++)
	{
	  if(str[i].checked == true)
	  {
	   chestr=chestr+1;
	   ch=ch+"-"+i;
	  }
	}
	var reStr=chestr+","+ch;
	return reStr;
}
function forceMod(){//ǿ���޸�
	var bankID="";
	var account="";
	var reStr=checkNum().split(",");
	var chestr=reStr[0];
	var ch=reStr[1].split("-");
	if(chestr<=0){
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}else if(chestr>1){
		alert ( "ֻ��ѡ��һ������!" );
		return false;
	}else{
		var str=document.getElementsByName("ck");
		var info=str[ch[1]].value.split(",");
		bankID=info[0];
		account=info[2];
	}
	var result = window.showModalDialog("forceMod.jsp?firmID=<%=firmID%>&bankID="+bankID+"&account="+account,"","dialogWidth=420px; dialogHeight=440px; status=no;scroll=no;help=no;");	
	if(result){
		window.location.reload();
	}
}
function bankFlag(){//����ǩԼ
	var bankID="";
	var account="";
	var reStr=checkNum().split(",");
	var chestr=reStr[0];
	var ch=reStr[1].split("-");
	if(chestr<=0)
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}else if(chestr>1)
	{
		alert ( "ֻ��ѡ��һ������!" );
		return false;
	}else{
		var str=document.getElementsByName("ck");
		var info=str[ch[1]].value.split(",");
		bankID=info[0];
		account=info[2];
		if(bankID!=17&&bankID!=31){
			alert ( "��ѡ�������"+bankID+",���ܽ�������ǩԼ!" );
			return false;
		}
	}
	var result = window.showModalDialog("addCorr_hx.jsp?firmID=<%=firmID%>&bankID="+bankID+"&account="+account,"","dialogWidth=500px; dialogHeight=500px; status=yes;scroll=yes;help=no;");	
	if(result){
		window.location.reload();
	}
}
function addCorr(){
	var result = window.showModalDialog("addCorr.jsp?firmID=<%=firmID%>","","dialogWidth=500px; dialogHeight=500px; status=yes;scroll=yes;help=no;");	
	if(result){
		//window.location.reload();
		window.location = "accountMng2.jsp?firmID=" + '<%=firmID%>';
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