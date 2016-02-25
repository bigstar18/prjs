<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>

<%
	String compareTime = Tool.getCompareTime();
	Calendar sysCalendar = Calendar.getInstance();
	Calendar calendar = Calendar.getInstance();
	String[] time = compareTime.split(":");
	CapitalProcessorRMI cp = null;
	try{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
	calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
	calendar.set(Calendar.SECOND,Integer.parseInt(time[2]));
	
	boolean disableBtn = true;
	if(sysCalendar.before(calendar))
	{
		disableBtn = true;
	}
	else
	{
		disableBtn = false;	
	}
	
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	int fileType = Tool.strToInt((String)request.getParameter("fileType"));
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals(""))
	{
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	ObjSet obj =null;
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String result = Tool.delNull((String)request.getParameter("result"));
	Vector compareResultList =null;
	//手动对账
	if("turnPage".equals(request.getParameter("opt")) || request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		//lv.setIp(computerIP);
		boolean marketStatus = true;//cp.getTraderStatus();
		if(!marketStatus)
		{
			result = "系统尚未结算。银行代码："+bankID+"时间："+new java.util.Date();
		}
		else
		{
			if(!noAdapterBank(bankID)){
				compareResultList = cp.checkMoney(bankID,Tool.strToDate(s_time));
			}
			if(compareResultList == null)
			{
				result = "银行数据尚未就绪 银行代码："+bankID+"时间："+new java.util.Date();
			}
			else if(compareResultList.size() == 0)
			{
				result = "对账成功 银行代码："+bankID+"时间："+new java.util.Date();
			}
			else 
			{
				result = "对账不成功 银行代码："+bankID+"时间："+new java.util.Date();
				obj = ObjSet.getInstance(compareResultList, pageSize, pageIndex);
			}
		}
		lv.setLogcontent("手动对账:"+result);
		dao.log(lv);
	}

int maxpage = 0;
if(compareResultList!=null){
	maxpage = compareResultList.size()%pageSize==0 ? compareResultList.size()/pageSize : compareResultList.size()/pageSize+1;
}
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
	obj = ObjSet.getInstance(compareResultList, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>凭证列表</title>
  </head>
  
  <body onload="changeBank();">
  	<form id="frm" action="result2.jsp" method="post">
		<fieldset width="100%">
			<legend>银行对账结果查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1200px" height="35">
				<tr height="35">	
				<td align="right">银行：&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" onchange="changeBank();" style="width:120px">
							<OPTION value="-1">请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bv = (BankValue)bankList.get(i);
								if(!noAdapterBank(bv.bankID)){
								%>
								<option value="<%=bv.bankID%>" <%=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
								<%
								}
							}
							%>
						</select>
						<script type="text/javascript">
							frm.bankID.value='<%="".equals(bankID)?-1:bankID %>';
						</script>
					</td>
					</tr>
				<tr height="35">
					<td align="right">对账日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="../skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">文件类型：&nbsp;</td>
					<td align="left">
						<select name="fileType" class="normal" style="width:120px">
							<OPTION value="-1" <%=fileType==-1 ? "selected" : "" %> >请选择</OPTION>
							<OPTION value="1" <%=fileType==1 ? "selected" : "" %> >清算文件</OPTION>
							<!-- <OPTION value="3" <%//=fileType==3 ? "selected" : "" %> >对账文件</OPTION> -->
							<OPTION value="4" <%=fileType==4 ? "selected" : "" %> >转账流水文件</OPTION>
							<OPTION value="5" <%=fileType==5 ? "selected" : "" %> >会员开销户文件</OPTION>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">对账结果：&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" id="compareBtn1" class="mdlbtn" <%if(s_time.equals(new Date()+"")){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="取银行数据">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" id="compareBtn2" class="mdlbtn" <%if(disableBtn&&(s_time.equals(new Date()+""))){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="对流水明细">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn3"id="compareBtn3"  class="mdlbtn" onclick="doCompare(3)" value="发送清算文件">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn4"id="compareBtn4"  class="mdlbtn" onclick="doCompare(4)" value="查询文件状态">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn5"id="compareBtn5"  class="mdlbtn" onclick="doCompare(5)" value="资金核对">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(obj!=null && obj.size()!=0)
		{
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">银行流水号</td>
				<td class="panel_tHead_MB">市场流水号</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">银行代码</td>
				<td class="panel_tHead_MB">银行帐号</td>
				<td class="panel_tHead_MB">银行类型</td>
				<td class="panel_tHead_MB">市场类型</td>
				<td class="panel_tHead_MB">银行金额</td>
				<td class="panel_tHead_MB">市场金额</td>
				<td class="panel_tHead_MB">出错类型</td>
				<td class="panel_tHead_MB">对账日期</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				CompareResult money = (CompareResult)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=money.id%>&nbsp;</td>
					<td class="underLine"><%=money.m_Id<=0?"--":money.m_Id%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(money.firmID)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(money.bankID)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(money.account)%>&nbsp;</td>
					<td class="underLine">
					<%
						if(money.errorType==3){
							out.println("--");
						}else{
							if(money.type == 0)
							{
								out.println("入金");
							}
							else if(money.type == 1)
							{
								out.println("出金");
							}
						}
					%>
					&nbsp;</td>
					<td class="underLine">
					<%
						if(money.errorType==2){
							out.println("--");
						}else{
							if(money.m_type == 0)
							{
								out.println("入金");
							}
							else if(money.m_type == 1)
							{
								out.println("出金");
							}
						}
					%>
					&nbsp;</td>
					<td class="underLine" align=right>
					<%
						if(money.money != money.m_money)
						{
							%>
							<font color=red><%=Tool.fmtDouble2(money.money)%></font>	
							<%
						}
						else
						{
							%>
							<%=Tool.fmtDouble2(money.money)%>
							<%
						}
					%>						
						&nbsp;</td>
					<td class="underLine" align=right>
					<%
						if(money.money != money.m_money)
						{
							%>
							<font color=red><%=Tool.fmtDouble2(money.m_money)%></font>	
							<%
						}
						else
						{
							%>
							<%=Tool.fmtDouble2(money.m_money)%>
							<%
						}
					%>		
						&nbsp;</td>
					<td class="underLine">
					<font color=blue>
					<%
					   String errorType="--";
					   switch(money.errorType) 
					   {
					   		case 0:
								errorType="账面不平";
							break;
							case 1:
								errorType="转账类型不匹配";
							break;
							case 2:
								errorType="市场无流水";
							break;
							case 3:
								errorType="银行无流水";
							break;
							default:
							break;
					   }
					   out.print(errorType);
					%></font>	&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDate(money.compareDate)%>&nbsp;</td>
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
	<%}%>
	
				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				<input name=opt type=hidden>
	</from>
  </body>
</html>
<script type="text/javascript">  
	function changeBank(){
		//var bankID=frm.bankID.value;
		//if(bankID==17){
		//	document.getElementById("compareBtn3").style.display="";
		//	document.getElementById("compareBtn4").style.display="none";
		//	document.getElementById("compareBtn5").style.display="";
		//}else if(bankID==13){
		//	document.getElementById("compareBtn3").style.display="";
		//	document.getElementById("compareBtn4").style.display="";
		//	document.getElementById("compareBtn5").style.display="none";
		//}else if(bankID==10){
		//	document.getElementById("compareBtn3").style.display="";
		//	document.getElementById("compareBtn4").style.display="none";
		//	document.getElementById("compareBtn5").style.display="none";
		//}else{
		//	document.getElementById("compareBtn3").style.display="none";
		//	document.getElementById("compareBtn4").style.display="none";
		//	document.getElementById("compareBtn5").style.display="none";
		//}
	}
	function doCompare(v)
	{
		frm.pageIndex.value = 1;
		if(frm.bankID.value == -1)
		{
			alert("请选择银行！");
		}
		else if(frm.s_time.value == "")
		{
			alert("请选择对账日期！");
		}
		else
		{
			var now1 = new Date()+"";
			if(CompareDate((frm.s_time.value),now1))
			{
				if(v==2)
				{
					frm.submitFlag.value = "do";
					frm.submit();
				}
				else if(v==1)
				{
					frm.submitFlag.value = "getData";
					frm.submit();	
				}
				else if(v==3)
				{
					frm.submitFlag.value = "sentQS";
					frm.submit();
				}
				else if(v==4)
				{
					if(frm.fileType.value==-1)
					{
						alert("请先选择文件");
					}else{
						frm.submitFlag.value = "fileType";
						frm.submit();
					}
				}
				else if(v==5)
				{
					frm.submitFlag.value = "sentDZ";
					frm.submit();
				}
			}
			else
			{
				alert("选择的对账日期不合法!");
				frm.s_time.value="";
			}
		}
	}
	
	function pgTurn(i)
	{
		frm.submitFlag.value = "getData";
		frm.opt.value="turnPage";
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
			<%if(obj!=null){ %> frm.pageIndex.value = <%=obj.getPageCount()%><%}%>;
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
				frm.submitFlag.value = "getData";
				frm.pageIndex.value = frm.pageJumpIdx.value;
				frm.submit();
			}
		}	
	}
	function doQuery()
	{
		var selectDay = frm.s_time.value;
		if(frm.bankID.value == -1)
		{
			alert("请选择银行！");
		}	
		else
		{
			window.location = "list.jsp?bankID="+frm.bankID.value;
		}
	}
	function CompareDate(d1,d2)
 	{
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>