<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ include file="../globalDef.jsp"%>

<%
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	String bankID = Tool.delNull((String)request.getParameter("bankID"));//银行编号
	String s_time = Tool.delNull((String)request.getParameter("s_time"));//交易日期
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	int sdbfileType = Tool.strToInt((String)request.getParameter("sdbfileType"));
	int sdbpageSizefirm = BANKPAGESIZE;//交易商签解约页面条数
	int sdbpageIndexfirm = 0;//交易商签解约页面编号
	int sdbpageSizeqsbp = BANKPAGESIZE;//清算不平页面条数
	int sdbpageIndexqsbp = 0;//清算不平页面编号
	int sdbpageSizeqssb = BANKPAGESIZE;//清算失败页面条数
	int sdbpageIndexqssb = 0;//清算失败页面编号
	ObjSet sdbfirm =null;//交易商签解约页面信息
	ObjSet sdbqsbp =null;//银行清算不平页面信息
	ObjSet sdbqssb =null;//银行清算失败页面信息
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String result = "";
	Vector sdbfirmbank =null;//交易商签解约比对结果
	Vector sdbqsbpbank =null;//银行传来清算不平数据
	Vector sdbqssbbank =null;//银行传来清算失败数据
	String submitFlag = request.getParameter("submitFlag");
	if("sdbsendQS".equals(submitFlag)){//发送深发展清算数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.sentMaketQS(Tool.strToDate(s_time),bankID);
		result = rv.remark;
		lv.setLogcontent(result);
		dao.log(lv);
	}else if("sdbqsResult".equals(submitFlag)){//查看清算结果
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.getBankFileStatus(Tool.strToDate(s_time),1,bankID);//查看清算结果文件
		result = rv.remark;
		lv.setLogcontent(result);
		dao.log(lv);
	}else if("sdbsavefirm".equals(submitFlag)) {//对签解约信息
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.saveFirmKXH(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "返回信息为空";
			}else{
				result = rv.remark;
			}
		}
		lv.setLogcontent("取银行签解约信息:"+result);
		dao.log(lv);
	}else if("sdbsaveqsbp".equals(submitFlag)){//获取银行清算不平数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.getBatCustDz(Tool.strToDate(s_time),bankID);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "返回信息为空";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("取银行清算不平信息:"+result);
		dao.log(lv);
	}else if("sdbsaveqssb".equals(submitFlag)){//获取银行清算失败数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.getFirmBalanceError(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "返回信息为空";
			}else{
				result = rv.remark;
			}
		}
		lv.setLogcontent("取银行清算失败信息:"+result);
		dao.log(lv);
	}else if("sdbsavefiles".equals(submitFlag)){//获取银行清算数据
		
	}else if("sdbgetDatafirm".equals(submitFlag)) {//取得深发展银行的交易商签解约数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbfirmbank = dao.getFirmBank(bankID,Tool.strToDate(s_time));
		if(sdbfirmbank==null) {
			result = "匹配签约信息异常，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else if(sdbfirmbank.size() == 0) {
			result = "签约信息匹配，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizefirm"));
			if(size>0){
				sdbpageSizefirm = size;
			}
			sdbpageIndexfirm = Tool.strToInt((String)request.getParameter("sdbpageIndexfirm"));
			if(sdbpageIndexfirm <= 0) {
				sdbpageIndexfirm = 1;
			}
			int maxpage = sdbfirmbank.size()%sdbpageSizefirm==0 ? sdbfirmbank.size()/sdbpageSizefirm : sdbfirmbank.size()/sdbpageSizefirm+1;
			if(sdbpageIndexfirm>maxpage){
				sdbpageIndexfirm=maxpage;
			}
			result = "银行信息不匹配："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("对银行和市场的签解约信息:"+result);
		dao.log(lv);
	}else if("sdbgetDataqsbp".equals(submitFlag)){//查看银行清算不平数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbqsbpbank = dao.getBatCustDz(Tool.strToDate(s_time),bankID);
		if(sdbqsbpbank==null) {
			result = "查询银行对账不平信息异常，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else if(sdbqsbpbank.size() == 0) {
			result = "银行对账成功，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizeqsbp"));
			if(size>0){
				sdbpageSizeqsbp = size;
			}
			sdbpageIndexqsbp = Tool.strToInt((String)request.getParameter("sdbpageIndexqsbp"));
			if(sdbpageIndexqsbp <= 0) {
				sdbpageIndexqsbp = 1;
			}
			int maxpage = sdbqsbpbank.size()%sdbpageSizeqsbp==0 ? sdbqsbpbank.size()/sdbpageSizeqsbp : sdbqsbpbank.size()/sdbpageSizeqsbp+1;
			if(sdbpageIndexqsbp>maxpage){
				sdbpageIndexqsbp=maxpage;
			}
			result = "银行对账不成功："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("查看清算不平信息:"+result);
		dao.log(lv);
	}else if("sdbgetDataqssb".equals(submitFlag)){//查看银行清算失败数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbqssbbank = dao.getFirmBalanceError(null,bankID,Tool.strToDate(s_time));
		if(sdbqssbbank==null) {
			result = "查询银行对账不平信息异常，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else if(sdbqssbbank.size() == 0) {
			result = "银行对账成功，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizeqssb"));
			if(size>0){
				sdbpageSizeqssb = size;
			}
			sdbpageIndexqssb = Tool.strToInt((String)request.getParameter("sdbpageIndexqssb"));
			if(sdbpageIndexqssb <= 0) {
				sdbpageIndexqssb = 1;
			}
			int maxpage = sdbqssbbank.size()%sdbpageSizeqssb==0 ? sdbqssbbank.size()/sdbpageSizeqssb : sdbqssbbank.size()/sdbpageSizeqssb+1;
			if(sdbpageIndexqssb>maxpage){
				sdbpageIndexqssb=maxpage;
			}
			result = "银行对账不成功："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("查看清算失败信息:"+result);
		dao.log(lv);
	}
sdbfirm = ObjSet.getInstance(sdbfirmbank, sdbpageSizefirm, sdbpageIndexfirm);
sdbqsbp = ObjSet.getInstance(sdbqsbpbank, sdbpageSizeqsbp, sdbpageIndexqsbp);
sdbqssb = ObjSet.getInstance(sdbqssbbank, sdbpageSizeqssb, sdbpageIndexqssb);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>深发展银行清算结果页面</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>深发展银行清算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
					<td align="right">银行：&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1">请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bv = (BankValue)bankList.get(i);
								if(sendQSBank(bv.bankID) != 0){
								%>
								<option value="<%=bv.bankID%>" <%=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>
								<%
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">交易日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<!--
				<tr height="35">
					<td align="right">文件类型：&nbsp;</td>
					<td align="left">
						<select name="sdbfileType" class="normal" style="width:120px">
							<OPTION value="-1" <%=sdbfileType==-1 ? "selected" : "" %> >请选择</OPTION>
							<OPTION value="1" <%=sdbfileType==1 ? "selected" : "" %> >清算结果文件</OPTION>
							<OPTION value="4" <%=sdbfileType==4 ? "selected" : "" %> >转账流水文件</OPTION>
							<OPTION value="5" <%=sdbfileType==5 ? "selected" : "" %> >会员开销户文件</OPTION>
						</select>
					</td>
				</tr>
				-->
				<tr height="35">
					<td align="right">执行结果：&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(1);" value="发送清算数据">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(2);" value="查看清算结果">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(3);" value="取得结果数据">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(7);" value="比对签解约">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(8);" value="查看清算失败">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(9);" value="查看清算不平">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(sdbfirm!=null && sdbfirm.size()!=0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">签解约</td>
				<td class="panel_tHead_MB">签解约日期</td>
				<td class="panel_tHead_MB">详细信息</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbfirm.getCurNum();i++)
			{
				FirmOpenCloseBank ocb = (FirmOpenCloseBank)sdbfirm.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=ocb.firmID%>&nbsp;</td>
					<td class="underLine">
					<%
					if(ocb.type==1){
						out.print("签约");
					}else if(ocb.type==2){
						out.print("解约");
					}
					%>&nbsp;
					</td>
					<td class="underLine"><%=Tool.fmtDate(ocb.tradeDate)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.note)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			  }
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4" align=right>
				第<%=sdbpageIndexfirm%>/<%=sdbfirm.getPageCount()%>页 &nbsp;&nbsp;共<%=sdbfirm.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="sdbpageSizefirm" class="text" type="text" style="width:25px;" value="<%=sdbpageSizefirm%>" onkeydown="return sdbpgJumpChkfirm()">条 &nbsp;&nbsp;
				<%
				if(sdbpageIndexfirm != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnfirm(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnfirm(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexfirm != sdbfirm.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnfirm(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnfirm(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxfirm" onkeydown="return sdbpgJumpChkfirm()">页

				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		
	</table>
	<%}%>
	<%
		if(sdbqsbp!=null && sdbqsbp.size()!=0){
	%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">子账号</td>
				<td class="panel_tHead_MB">账户名</td>
				<td class="panel_tHead_MB">银行可用余额</td>
				<td class="panel_tHead_MB">银行冻结余额</td>
				<td class="panel_tHead_MB">市场可用余额</td>
				<td class="panel_tHead_MB">市场冻结余额</td>
				<td class="panel_tHead_MB">可用余额扎差</td>
				<td class="panel_tHead_MB">冻结余额扎差</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbqsbp.getCurNum();i++)
			{
				BatCustDzFailChild ocb = (BatCustDzFailChild)sdbqsbp.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.ThirdCustId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.CustAcctId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.CustName)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BankBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BankFrozen)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.MaketBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.MaketFrozen)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BalanceError)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.FrozenError)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			  }
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="9">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="9" align=right>
				第<%=sdbpageIndexqsbp%>/<%=sdbqsbp.getPageCount()%>页 &nbsp;&nbsp;共<%=sdbqsbp.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="sdbpageSizeqsbp" class="text" type="text" style="width:25px;" value="<%=sdbpageSizeqsbp%>" onkeydown="return sdbpgJumpChkqsbp()">条 &nbsp;&nbsp;
				<%
				if(sdbpageIndexqsbp != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqsbp(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqsbp(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexqsbp != sdbqsbp.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqsbp(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqsbp(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxqsbp" onkeydown="return sdbpgJumpChkqsbp()">页

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
	<%}%>
	<%
		if(sdbqssb!=null && sdbqssb.size()!=0){
	%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">当天总冻结金额</td>
				<td class="panel_tHead_MB">当天总解冻余额</td>
				<td class="panel_tHead_MB">卖方货款</td>
				<td class="panel_tHead_MB">买方货款</td>
				<td class="panel_tHead_MB">盈利金额</td>
				<td class="panel_tHead_MB">亏损金额</td>
				<td class="panel_tHead_MB">手续费</td>
				<td class="panel_tHead_MB">市场资金</td>
				<td class="panel_tHead_MB">市场冻结</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbqssb.getCurNum();i++)
			{
				BatFailResultChild bfr = (BatFailResultChild)sdbqssb.get(i);
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
				第<%=sdbpageIndexqssb%>/<%=sdbqssb.getPageCount()%>页 &nbsp;&nbsp;共<%=sdbqssb.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="sdbpageSizeqssb" class="text" type="text" style="width:25px;" value="<%=sdbpageSizeqssb%>" onkeydown="return sdbpgJumpChkqssb()">条 &nbsp;&nbsp;
				<%
				if(sdbpageIndexqssb != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqssb(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqssb(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexqssb != sdbqssb.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqssb(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqssb(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxqssb" onkeydown="return sdbpgJumpChkqssb()">页

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
	<input name=sdbpageIndexfirm type=hidden value="<%=sdbpageIndexfirm%>">
	<input name=sdbpageIndexqsbp type=hidden value="<%=sdbpageIndexqsbp%>">
	<input name=sdbpageIndexqssb type=hidden value="<%=sdbpageIndexqssb%>">
	</form>
  </body>
</html>
<script type="text/javascript">  
	function sdbbuttonclick(v) {
		frm.sdbpageIndexfirm.value = 1;
		if(v == 1){//发送清算数据
			frm.submitFlag.value = "sdbsendQS";
			frm.submit();
		}else {
			var now1 = new Date()+"";
			if (frm.s_time.value == ""){
				alert("请选择对账日期！");
			}else if(CompareDate(now1,(frm.s_time.value))){
				alert("选择的对账日期不合法!");
				frm.s_time.value="";
			}else if(v == 2){//查看清算结果
				/*if(frm.sdbfileType.value==-1){
					alert("请选择文件类型");
				}else{
					frm.submitFlag.value = "sdbqsResult";
					frm.submit();
				}*/
				frm.submitFlag.value = "sdbqsResult";
				frm.submit();
			}else if(v == 3){//取得银行交易商签解约数据
				frm.submitFlag.value = "sdbsavefirm";
				frm.submit();
			}else if(v == 4){//取得银行清算不平数据
				frm.submitFlag.value = "sdbsaveqsbp";
				frm.submit();
			}else if(v == 5){//取得银行清算失败数据
				frm.submitFlag.value = "sdbsaveqssb";
				frm.submit();
			}else if(v == 6){//取得银行发送来的数据
				frm.submitFlag.value = "sdbsavefiles";
				frm.submit();
			}else if(v == 7){//比对签解约结果
				frm.submitFlag.value = "sdbgetDatafirm";
				frm.submit();
			}else if(v == 8){//查看清算不平数据
				frm.submitFlag.value = "sdbgetDataqsbp";
				frm.submit();
			}else if(v == 9){//查看清算失败数据
				frm.submitFlag.value = "sdbgetDataqssb";
				frm.submit();
			}
		}
	}
	function sdbpgTurnfirm(i) {
		frm.submitFlag.value = "sdbgetDatafirm";
		if(i == 0) {
			frm.sdbpageIndexfirm.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexfirm.value = frm.sdbpageIndexfirm.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbfirm!=null){ %> frm.sdbpageIndexfirm.value = <%=sdbfirm.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexfirm.value = frm.sdbpageIndexfirm.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkfirm() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxfirm.value) || frm.sdbpageJumpIdxfirm.value < 1 || frm.sdbpageJumpIdxfirm.value > <%=sdbfirm.getPageCount()%>) && (isNaN(frm.sdbpageSizefirm.value) || frm.sdbpageSizefirm.value < 1 )) {
				alert("请输入1 - <%=sdbfirm.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "sdbgetDatafirm";
				frm.sdbpageIndexfirm.value = frm.sdbpageJumpIdxfirm.value;
				frm.submit();
			}
		}	
	}
	function sdbpgTurnqsbp(i) {
		frm.submitFlag.value = "sdbgetDataqsbp";
		if(i == 0) {
			frm.sdbpageIndexqsbp.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexqsbp.value = frm.sdbpageIndexqsbp.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbqsbp!=null){ %> frm.sdbpageIndexqsbp.value = <%=sdbqsbp.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexqsbp.value = frm.sdbpageIndexqsbp.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkqsbp() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxqsbp.value) || frm.sdbpageJumpIdxqsbp.value < 1 || frm.sdbpageJumpIdxqsbp.value > <%=sdbqsbp.getPageCount()%>) && (isNaN(frm.sdbpageSizeqsbp.value) || frm.sdbpageSizeqsbp.value < 1 )) {
				alert("请输入1 - <%=sdbqsbp.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "sdbgetDataqsbp";
				frm.sdbpageIndexqsbp.value = frm.sdbpageJumpIdxqsbp.value;
				frm.submit();
			}
		}	
	}
	function sdbpgTurnqssb(i) {
		frm.submitFlag.value = "sdbgetDataqssb";
		if(i == 0) {
			frm.sdbpageIndexqssb.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexqssb.value = frm.sdbpageIndexqssb.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbqssb!=null){ %> frm.sdbpageIndexqssb.value = <%=sdbqssb.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexqssb.value = frm.sdbpageIndexqssb.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkqssb() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxqssb.value) || frm.sdbpageJumpIdxqssb.value < 1 || frm.sdbpageJumpIdxqssb.value > <%=sdbqssb.getPageCount()%>) && (isNaN(frm.sdbpageSizeqssb.value) || frm.sdbpageSizeqssb.value < 1 )) {
				alert("请输入1 - <%=sdbqssb.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "sdbgetDataqssb";
				frm.sdbpageIndexqssb.value = frm.sdbpageJumpIdxqssb.value;
				frm.submit();
			}
		}	
	}
	function CompareDate(d1,d2) {
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>