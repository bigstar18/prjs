<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.common.model.*,java.util.Date" %>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
BankFeeAndOffsetDao dao = new BankFeeAndOffsetDaoImpl();
User user = (User)session.getAttribute("CURRENUSER");

int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " ";

String bankID = Tool.delNull(request.getParameter("bankID"));
System.out.println(bankID);
if(!bankID.trim().equals("")&&!bankID.trim().equals("-1"))
{
	filter += " and BankCode='"+ bankID +"'";
}

String s_time = Tool.delNull(request.getParameter("s_time"));
if(!s_time.trim().equals(""))
{
	filter += " and trunc(TransferTime) = to_date('"+s_time+"','yyyy-mm-dd')";
}
String operatorID = Tool.delNull(request.getParameter("operatorID")); 
if(!operatorID.trim().equals("")&&!operatorID.trim().equals("-1"))
{
	filter += " and OperatorID='"+ operatorID +"'";
}

filter += " order by ID asc";

Set<List<Object>> set = dao.TransferRightsQuery(null,filter);

int maxpage = set.size()%pageSize==0 ? set.size()/pageSize : set.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
//ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�������г��鿴��ת��¼</title>
  </head>
  
  <body>
  	<form id="frm" action="MarketFeeOnBankLU.jsp" method="post">
		<fieldset width="95%">
			<legend>�������г��鿴��ת��¼</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
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
						<input type="hidden" id="bankIDH" name="bankIDH" value="<%=request.getParameter("bankIDH") %>"/>
					</td>
					<td align="right">����Ա</td>
					<td align="left">
					<select name="operatorID" class="normal" style="width: 100px">
						<OPTION value="-1">ȫ��</OPTION>
						<%
							for( List<Object> li : setUser )
							{
								%><option value="<%=li.get(0)%>" <%=li.get(0).equals(operatorID)?"selected":""%>><%=li.get(0)%></option><%
							}
						%>
					</select>
					<td align="right">��ת����&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="_reset();">����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="javascript:_goto('../reportMarketFeeOnBank.jsp');">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">��ת���</td>
				<td class="panel_tHead_MB" align="center">���д���</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_MB" align="center">��ת���</td>
				<td class="panel_tHead_MB" align="center">����Ա</td>
				<td class="panel_tHead_MB" align="center">��תʱ��</td>
				<td class="panel_tHead_MB" align="center">��ע</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
	  		int cou = 0;
			for( List<Object> li : set )
			{
				cou++;
				if(cou>((pageIndex-1)*pageSize)&&cou<(pageIndex*pageSize+1))
				{
				%>
				<tr height="22" align=center >
					<td class="panel_tBody_LB">&nbsp;</td>
				<%
				List<Object> list = (List<Object>)li;
				for(int i=0;i<list.size();i++)
				{
					if(i==3)
					{
						%>
							<td class="underLine" align="center"><fmt:formatNumber value="<%=list.get(i) %>" pattern="#,##0.00"/>&nbsp;</td>
						<%
					}
					else if((i+1)==list.size())
					{
						%>
						<td class="underLine" align="center" title="<%=list.get(i)%>">
						<c:set var="remark" value="<%=list.get(i)%>"/>
						<c:choose>  
						    <c:when test="${fn:length(remark) > 20}">  
						        <c:out value="${fn:substring(remark, 0, 20)}..." />  
						    </c:when>  
						   <c:otherwise>  
						      <c:out value="${remark}" />  
						    </c:otherwise>  
						</c:choose>  
						&nbsp;</td>
						<%
					}
					else {
						%>
							<td class="underLine" align="center"><%=list.get(i)%>&nbsp;</td>
						<%
					}
				}
				%>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
				}
			}
			%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7" align=right>
				��<%=pageIndex%>/<%=maxpage%>ҳ &nbsp;&nbsp;��<%=set.size()%>�� &nbsp;&nbsp;ÿҳ
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

				if(pageIndex != maxpage)
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
				<td class="panel_tFoot_MB" colspan="7"></td>
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
		frm.pageIndex.value = <%=maxpage%>;
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
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=maxpage%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
		{
			alert("������1 - <%=maxpage%>������֣�");
		}
		else
		{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}

function _reset()
{
	_goto("MarketFeeOnBankLU.jsp?bankID="+document.getElementById("bankIDH").value+"&bankIDH="+document.getElementById("bankIDH").value+"&");
}

function _goto(pathRrl,title)
{
  	parent.parent.workspace.mainFrame.location.href=pathRrl;
	//parent.workspace.topFrame1.document.getElementById("loc").innerHTML=title;
}
//-->
</SCRIPT>