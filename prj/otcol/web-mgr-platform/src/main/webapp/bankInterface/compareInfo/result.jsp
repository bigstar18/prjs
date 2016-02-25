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
	//�ֶ�����
	if("turnPage".equals(request.getParameter("opt")) || request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		//lv.setIp(computerIP);
		boolean marketStatus = true;//cp.getTraderStatus();
		if(!marketStatus)
		{
			result = "ϵͳ��δ���㡣���д��룺"+bankID+"ʱ�䣺"+new java.util.Date();
		}
		else
		{
			if(!noAdapterBank(bankID)){
				compareResultList = cp.checkMoney(bankID,Tool.strToDate(s_time));
			}
			if(compareResultList == null)
			{
				result = "����������δ���� ���д��룺"+bankID+"ʱ�䣺"+new java.util.Date();
			}
			else if(compareResultList.size() == 0)
			{
				result = "���˳ɹ� ���д��룺"+bankID+"ʱ�䣺"+new java.util.Date();
			}
			else 
			{
				result = "���˲��ɹ� ���д��룺"+bankID+"ʱ�䣺"+new java.util.Date();
				obj = ObjSet.getInstance(compareResultList, pageSize, pageIndex);
			}
		}
		lv.setLogcontent("�ֶ�����:"+result);
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
    <title>ƾ֤�б�</title>
  </head>
  
  <body onload="changeBank();">
  	<form id="frm" action="result2.jsp" method="post">
		<fieldset width="100%">
			<legend>���ж��˽����ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1200px" height="35">
				<tr height="35">	
				<td align="right">���У�&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" onchange="changeBank();" style="width:120px">
							<OPTION value="-1">��ѡ��</OPTION>
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
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="../skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ļ����ͣ�&nbsp;</td>
					<td align="left">
						<select name="fileType" class="normal" style="width:120px">
							<OPTION value="-1" <%=fileType==-1 ? "selected" : "" %> >��ѡ��</OPTION>
							<OPTION value="1" <%=fileType==1 ? "selected" : "" %> >�����ļ�</OPTION>
							<!-- <OPTION value="3" <%//=fileType==3 ? "selected" : "" %> >�����ļ�</OPTION> -->
							<OPTION value="4" <%=fileType==4 ? "selected" : "" %> >ת����ˮ�ļ�</OPTION>
							<OPTION value="5" <%=fileType==5 ? "selected" : "" %> >��Ա�������ļ�</OPTION>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���˽����&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" id="compareBtn1" class="mdlbtn" <%if(s_time.equals(new Date()+"")){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="ȡ��������">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" id="compareBtn2" class="mdlbtn" <%if(disableBtn&&(s_time.equals(new Date()+""))){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="����ˮ��ϸ">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn3"id="compareBtn3"  class="mdlbtn" onclick="doCompare(3)" value="���������ļ�">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn4"id="compareBtn4"  class="mdlbtn" onclick="doCompare(4)" value="��ѯ�ļ�״̬">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn5"id="compareBtn5"  class="mdlbtn" onclick="doCompare(5)" value="�ʽ�˶�">
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
				<td class="panel_tHead_MB">������ˮ��</td>
				<td class="panel_tHead_MB">�г���ˮ��</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class="panel_tHead_MB">���д���</td>
				<td class="panel_tHead_MB">�����ʺ�</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">�г�����</td>
				<td class="panel_tHead_MB">���н��</td>
				<td class="panel_tHead_MB">�г����</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">��������</td>
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
								out.println("���");
							}
							else if(money.type == 1)
							{
								out.println("����");
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
								out.println("���");
							}
							else if(money.m_type == 1)
							{
								out.println("����");
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
								errorType="���治ƽ";
							break;
							case 1:
								errorType="ת�����Ͳ�ƥ��";
							break;
							case 2:
								errorType="�г�����ˮ";
							break;
							case 3:
								errorType="��������ˮ";
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
			alert("��ѡ�����У�");
		}
		else if(frm.s_time.value == "")
		{
			alert("��ѡ��������ڣ�");
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
						alert("����ѡ���ļ�");
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
				alert("ѡ��Ķ������ڲ��Ϸ�!");
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
				alert("������1 - <%=obj.getPageCount()%>������֣�");			
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
			alert("��ѡ�����У�");
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