<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>

<%
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	boolean disableBtn = false;
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals(""))
	{
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	ObjSet obj =null;
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String result = "";
	Vector BatFailResult =null;
	
	if("turnPage".equals(request.getParameter("opt")) || request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{//��ѯ���ж��˲�ƽ��Ϣ
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			BatFailResult = dao.getFirmBalanceError(null,bankID,Tool.strToDate(s_time));
		}
		if(BatFailResult==null)
		{
			result = "��ѯ���ж��˲�ƽ��Ϣ�쳣�����д��룺"+bankID+"������ʱ�䣺"+s_time+"ʱ�䣺"+new java.util.Date();
		}
		else if(BatFailResult.size() == 0)
		{
			result = "���ж��˳ɹ������д��룺"+bankID+"������ʱ�䣺"+s_time+"ʱ�䣺"+new java.util.Date();
		}
		else 
		{
			result = "���ж��˲��ɹ���"+bankID+"������ʱ�䣺"+s_time+"ʱ�䣺"+new java.util.Date();
			obj = ObjSet.getInstance(BatFailResult, pageSize, pageIndex);
		}
		lv.setLogcontent("�����к��г���ǩ��Լ��Ϣ:"+result);
		dao.log(lv);
	}
	else if("getData".equals(request.getParameter("submitFlag")))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.getFirmBalanceError(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "������ϢΪ��";
			}else{
				result = rv.remark;
			}
			
		}
		lv.setLogcontent("ȡ���к��г���ǩ��Լ��Ϣ:"+result);
		dao.log(lv);
	}

int maxpage = 0;
if(BatFailResult!=null){
	maxpage = BatFailResult.size()%pageSize==0 ? BatFailResult.size()/pageSize : BatFailResult.size()/pageSize+1;
}
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
	obj = ObjSet.getInstance(BatFailResult, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>������ǩԼ��Ϣ��Ӧ�б�</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>�ȶԽ�����ǩԼ��Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
				<td align="right">���У�&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px">
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
					<td align="right">�ȶ����ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="../skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�ȶԽ����&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="ȡ������Ϣ">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="�ȶ�������Ϣ">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(obj!=null && obj.size()!=0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class="panel_tHead_MB">�����ܶ�����</td>
				<td class="panel_tHead_MB">�����ܽⶳ���</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">�򷽻���</td>
				<td class="panel_tHead_MB">ӯ�����</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">�г��ʽ�</td>
				<td class="panel_tHead_MB">�г�����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				BatFailResultChild bfr = (BatFailResultChild)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(bfr.ThirdCustId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.FreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.UnfreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.AddTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.CutTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.ProfitAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.LossAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.TranHandFee)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewFreezeAmount)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			  }
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10" align=right>
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
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		
	</table>
	<%}%>
	
				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				<input name=opt type=hidden>
	</form>
  </body>
</html>
<script type="text/javascript">  
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