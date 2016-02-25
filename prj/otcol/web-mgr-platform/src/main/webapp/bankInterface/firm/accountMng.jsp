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
		out.println("无效的交易商代码！");
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
    <title>交易商和银行绑定关系列表</title>
  </head>
  
  <body>
  	<form id="frm" action="accountMng2.jsp" method="post" name="frm">
		<font style="font-size: 10pt;font-weight: bold;">交易商&nbsp;<%=firmID%>&nbsp;银行帐号管理</font>
		<input type="hidden" name="firmID" value="<%=firmID%>">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'ck')"></td>
				<td class="panel_tHead_MB">银行代码</td>
				<td class="panel_tHead_MB" align=left>银行帐号</td>
				<td class="panel_tHead_MB" align=left>银行内部帐号</td>
				<td class="panel_tHead_MB" align=left>账户名</td>
				<td class="panel_tHead_MB" align=left>开户行名称</td>
				<td class="panel_tHead_MB" align=left>开户行省份</td>
				<td class="panel_tHead_MB" align=left>开户行所在市</td>
				<td class="panel_tHead_MB" align=left>签约状态</td>
				<td class="panel_tHead_MB" align=left>帐号状态</td>
				<td class="panel_tHead_MB" align=left>冻结资金</td>
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
							out.println("未签约");
						}
						else if(corr.isOpen == 1)
						{
							out.println("成功");
						}
						else if(corr.isOpen == 2)
						{
							out.println("预签约成功");
						}
						else if(corr.isOpen == 3)
						{
							out.println("已解约");
						}
					%>
					&nbsp;</td>
					<td class="underLine">
					<%
						if(corr.status == 0)
						{
							out.println("可用");
						}
						else if(corr.status == 1)
						{
							out.println("不可用");
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
				第<%=pageIndex%>/<%=obj.getPageCount()%>页 &nbsp;&nbsp;共<%=obj.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageIndex != 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(pageIndex != obj.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

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
		<!--button type="button" class="smlbtn" onclick="addCorr();">注册帐号</button-->&nbsp;
		<%
		} else {
			if("79".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','synAccount');">预签约</button-->&nbsp;
		<%
			}
			if("17".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','synchro');">同步账号</button-->&nbsp;
		<!--button type="button" class="smlbtn" onclick="bankFlag();">他行签约</button-->&nbsp;
		<%
			}
			if(openAccountBank(bankID) && !"79".equals(bankID)){
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','open');">签约账号</button-->&nbsp;
		<%
			}
		%>
		<!--button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','del');">注销帐号</button-->&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','forbid');">禁用帐号</button>&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','recover');">恢复帐号</button>&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','resetsmmy');">密码重置</button>&nbsp;
		<button type="button" class="smlbtn" onclick="forceMod();">强制修改</button>&nbsp;
		<%}%>
		<button type="button" class="smlbtn" onclick="window.location='list.jsp';">返回</button>&nbsp;
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function checkNum(){//复选项选择的数量
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
function forceMod(){//强制修改
	var bankID="";
	var account="";
	var reStr=checkNum().split(",");
	var chestr=reStr[0];
	var ch=reStr[1].split("-");
	if(chestr<=0){
		alert ( "请选择需要操作的数据！" );
		return false;
	}else if(chestr>1){
		alert ( "只能选择一项数据!" );
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
function bankFlag(){//他行签约
	var bankID="";
	var account="";
	var reStr=checkNum().split(",");
	var chestr=reStr[0];
	var ch=reStr[1].split("-");
	if(chestr<=0)
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}else if(chestr>1)
	{
		alert ( "只能选择一项数据!" );
		return false;
	}else{
		var str=document.getElementsByName("ck");
		var info=str[ch[1]].value.split(",");
		bankID=info[0];
		account=info[2];
		if(bankID!=17&&bankID!=31){
			alert ( "您选择的银行"+bankID+",不能进行他行签约!" );
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
			alert("请输入1 - <%=obj.getPageCount()%>间的数字！");			
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