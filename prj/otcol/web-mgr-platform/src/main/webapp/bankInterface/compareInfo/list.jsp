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
String filter = " where 1=1 ";

String firmID = Tool.delNull(request.getParameter("firmID"));
if(!firmID.trim().equals(""))
{
	filter += " and firmid='"+ firmID +"'";
}

String bankID = Tool.delNull(request.getParameter("bankID"));
if(!bankID.trim().equals(""))
{
	filter += " and bankID='"+ bankID +"'";
}

int type = Tool.strToInt(request.getParameter("type"));
if(type >= 0)
{
	filter += " and type="+ type;
}

int status = Tool.strToInt(request.getParameter("status"));
if(status >= 0)
{
	filter += " and status="+ status;
}

String s_time = Tool.delNull(request.getParameter("s_time"));
if(!s_time.trim().equals(""))
{
	filter += " and createtime>=to_date('"+s_time+" 00:00:00','yyyy-mm-dd hh24:mi:ss')";
}

String e_time = Tool.delNull(request.getParameter("e_time"));
if(!e_time.trim().equals(""))
{
	filter += " and createtime<=to_date('"+e_time+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
}

filter += " order by createtime desc,id desc ";

Vector moneyList = dao.getCapitalInfoList(filter);
double allMoney = 0;
for(int i = 0 ; i <moneyList.size();i++){
	CapitalValue money = (CapitalValue)moneyList.get(i);
	allMoney += money.money;
}
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
    <title>凭证列表</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>资金流水查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">交易商代码&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="right">银行代码&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="right">类型&nbsp;</td>
					<td align="left">
						<select name="type" class="normal" style="width: 100px">
							<OPTION value="-1">全部</OPTION>
							<option value="0" <%=type==0?"selected":""%>>入金</option>
              				<option value="1" <%=type==1?"selected":""%>>出金</option>
              				<option value="2" <%=type==2?"selected":""%>>出入金手续费</option>
							<option value="3" <%=type==3?"selected":""%>>其他资金划转</option>
						</select>
					</td>
					<td align="right">状态&nbsp;</td>
					<td align="left">
						<select name="status" class="normal" style="width: 100px">
							<OPTION value="-1">全部</OPTION>
							<option value="0" <%=status==0?"selected":""%>>成功</option>
              				<option value="1" <%=status==1?"selected":""%>>失败</option>
              				<option value="2" <%=status==2?"selected":""%>>处理中</option>
							<option value="3" <%=status==3?"selected":""%>>待审核</option>
							<%if(secondAudit) {%>
							<option value="4" <%=status==4?"selected":""%>>待二次审核</option>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;</td>
					<td align="left" colspan="3">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
						&nbsp;至&nbsp;
						<MEBS:calendar eltName="e_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=e_time%>"/>
					</td>
					<td colspan="4" align="right">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">记录流水号</td>
				<td class="panel_tHead_MB" align="left">交易商代码</td>
				<td class="panel_tHead_MB" align="left">银行代码</td>
				<td class="panel_tHead_MB" align="left">类型</td>
				<td class="panel_tHead_MB" align="right">金额&nbsp;</td>
				<td class="panel_tHead_MB" align="left">&nbsp;创建时间</td>
				<td class="panel_tHead_MB" align="left">状态</td>
				<td class="panel_tHead_MB" align="left">备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			double sumMoney = 0 ;
			double sumOutMoney = 0 ;
			double sumInMoney = 0 ;
			double sumFee = 0 ;
			for(int i=0;i<obj.getCurNum();i++)
			{
				CapitalValue money = (CapitalValue)obj.get(i);
				sumMoney += Tool.strToDouble(Tool.fmtDouble2(money.money));
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=money.iD%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.firmID%>&nbsp;</td>
					<td class="underLine" align="left"><%=money.bankID%>&nbsp;</td>
					<td class="underLine" align="left">
					<%
						if(money.type == 0)
						{
							sumInMoney += Tool.strToDouble(Tool.fmtDouble2(money.money));
							out.println("入金");
						}
						else if(money.type == 1)
						{
							sumOutMoney += Tool.strToDouble(Tool.fmtDouble2(money.money));
							out.println("出金");
						}
						else if(money.type == 2)
						{
							sumFee += Tool.strToDouble(Tool.fmtDouble2(money.money));
							out.println("出入金手续费");
						}
						else if(money.type == 3)
						{
							out.println("其他资金划转");
						}
					%>
					&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(money.money)%>&nbsp;</td>
					<td class="underLine" align="left">&nbsp;<%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align="left">
						<%
						if(money.status == 0)
						{
							out.println("成功");
						}
						else if(money.status == 1)
						{
							out.println("<font color=red>失败</font>");
						}
						else if(money.status == 2)
						{
							out.println("处理中");
						}
						else if(money.status == 3 || money.status == 13)
						{
							out.println("待审核");
						}
						else if(money.status == 4)
						{
							out.println("待二次审核");
						}
					%>
						&nbsp;</td>
					<td class="underLine" align="left">
						<%//=Tool.delNull(money.note)
						String queryNote = Tool.delNull(money.note);
							if(queryNote.indexOf("market_in")>=0)
							{
								out.print("市场入金");	
							}
							else 
							if(queryNote.indexOf("market_out")>=0)
							{
								out.print("市场出金");	
							}
							else if(queryNote.indexOf("bank_out")>=0)
							{
								out.print("银行出金");
							}
							else
							{
								out.print("银行入金");
							}
							%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
			<tr height="22" align=center>
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center">合计：</td>
					<td class="underLine" align="right">入金合计:&nbsp;</td>
					<td class="underLine" align="left"><%=Tool.fmtMoney(sumInMoney)%>&nbsp;</td>
					<td class="underLine" align="right">出金合计:&nbsp;</td>
					<td class="underLine" align=left><%=Tool.fmtMoney(sumOutMoney)%>&nbsp;</td>
					<td class="underLine" align="right">手续费合计:&nbsp;</td>
					<td class="underLine" align="left"><%=Tool.fmtMoney(sumFee)%>&nbsp;</td>
					<td class="underLine" align="left">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" align=center>资金合计：<%=Tool.formatToMoney(allMoney)%></td>
				<td class="pager" colspan="7" align=right>
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
				<td class="panel_tFoot_MB" colspan="8"></td>
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