<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%
	String compareTime = Tool.getCompareTime();
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	String result = "";//显示结果
	Vector icbczfphList = null;//总分平衡
	Vector icbcffhdList = null;//分分核对
	ObjSet icbcffhd = null;//分分核对数据
	ObjSet icbczfph = null;//总分平衡显示数据
	int icbcpageSizezf = BANKPAGESIZE;//总分每页大小
	int icbcpageSizeff = BANKPAGESIZE;//分分每页大小
	int icbcpageIndexzf = Tool.strToInt((String)request.getParameter("icbcpageIndexzf"));//总分页码
	if(icbcpageIndexzf <= 0)  icbcpageIndexzf = 1;
	int icbcpageIndexff = Tool.strToInt((String)request.getParameter("icbcpageIndexff"));//分分页码
	if(icbcpageIndexff <= 0)  icbcpageIndexff = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	String submitFlag = request.getParameter("submitFlag");
	Vector bankList = dao.getBankList(" where validFlag=0");
	if("icbcsendQS".equalsIgnoreCase(submitFlag)){//工行发送清算数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.sendGHQS(bankID,null,Tool.strToDate(s_time));//发送工行交易商清算信息
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
	}else if("icbcsavezfph".equalsIgnoreCase(submitFlag)){//工行取总分平衡数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getProperBalanceValue(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
	}else if("icbcsaveffhd".equalsIgnoreCase(submitFlag)){//工行取分分核对数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getBankFirmRightValue(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
	}else if("icbcgetzfph".equalsIgnoreCase(submitFlag)){//工行查询总分平衡
		ProperBalanceValue pbv = new ProperBalanceValue();
		pbv.bankId = bankID;
		if(!s_time.trim().equals("")){
			pbv.bdate = new java.sql.Timestamp(Tool.getDateTime(s_time).getTime());
		}
		icbczfphList = new Vector(dao.getProperBalance(pbv));
		if(icbczfphList == null){
			result = "查询总分平衡异常";
		} else {
			int size = Tool.strToInt((String)request.getParameter("icbcpageSizezf"));
			if(size>0){
				icbcpageSizezf = size;
			}
			int maxpage = icbczfphList.size()%icbcpageSizezf==0 ? icbczfphList.size()/icbcpageSizezf : icbczfphList.size()/icbcpageSizezf+1;
			if(icbcpageIndexzf>maxpage) icbcpageIndexzf=maxpage;
			if(icbczfphList.size()<=0){
				result = "没有查询到当日的总分平衡信息";
			} else {
				result = "查询到总分平衡信息如下";
			}
		}
	}else if("icbcgetffhd".equalsIgnoreCase(submitFlag)){//工行查询分分核对
		BankFirmRightValue bfr = new BankFirmRightValue();
		bfr.bankId = bankID;
		if(!s_time.trim().equals("")) {
			bfr.bdate = new java.sql.Timestamp(Tool.getDateTime(s_time).getTime());
		}
		icbcffhdList = dao.getBankCapital(bfr);
		if(icbcffhdList == null){
			result = "查询分分核对数据异常";
		}else {
			int size = Tool.strToInt((String)request.getParameter("icbcpageSizeff"));
			if(size > 0){
				icbcpageSizeff = size;
			}
			int maxpage = icbcffhdList.size()%icbcpageSizeff==0 ? icbcffhdList.size()/icbcpageSizeff : icbcffhdList.size()/icbcpageSizeff+1;
			if(icbcpageIndexff>maxpage){
				icbcpageIndexff=maxpage;
			}
			if(icbcffhdList.size()<=0){
				result = "当日分分核对平";
			}else{
				result = "当日分分不平信息记录如下";
			}
		}
	}
	icbczfph = ObjSet.getInstance(icbczfphList, icbcpageSizezf, icbcpageIndexzf);
	icbcffhd = ObjSet.getInstance(icbcffhdList, icbcpageSizeff, icbcpageIndexff);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>工商银行清算界面</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%">
			<legend>工商银行清算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">银行&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1" >请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++) {
								BankValue bv = (BankValue)bankList.get(i);
								if(sendQSBank(bv.bankID) != 0){
							%>
									<option value="<%=bv.bankID%>" <%if(bankID.equals(bv.bankID))out.print("selected");%>><%=bv.bankName%></option>
							<%
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">清算日期&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">清算结果&nbsp;</td>
					<td align="left"><font color=red><%=result%>&nbsp;</font></td>
				</tr>
				<tr height="35">
					<td align="center" colspan="2">
						<input name=submitFlag type=hidden value="">
						<input type="button"   class="mdlbtn" value="发送清算" onclick="icbcbuttonclick(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="取总分平衡" onclick="icbcbuttonclick(2);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="取分分核对" onclick="icbcbuttonclick(3);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="显示总分平衡" onclick="icbcbuttonclick(4);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="lgrbtn" value="显示分分核对" onclick="icbcbuttonclick(5);">
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
	<%
		if(icbczfph != null && icbczfph.size()>0){
	%>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" align="center">银行编号</td>
				<td class="panel_tHead_MB" align=center>日期</td>
				<td class="panel_tHead_MB" align=right>汇总金额</td>
				<td class="panel_tHead_MB" align=right>工行汇总账户金额</td>
				<td class="panel_tHead_MB" align=right>他行汇总账户金额</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			for(int i=0;i<icbczfph.getCurNum();i++)
			{
				ProperBalanceValue pbv2 = (ProperBalanceValue)icbczfph.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=pbv2.bankId%></td>
					<td class="underLine" align=center><%=Tool.fmtDate(pbv2.bdate)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.allMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.gongMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.otherMoney)%>&nbsp;</td>
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
				第<%=icbcpageIndexzf%>/<%=icbczfph.getPageCount()%>页 &nbsp;&nbsp;共<%=icbczfph.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="icbcpageSizezf" class="text" type="text" style="width:25px;" value="<%=icbcpageSizezf%>" onkeydown="return icbcpgJumpChkzf()">条 &nbsp;&nbsp;
				<%
				if(icbcpageIndexzf != 1)
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnzf(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnzf(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(icbcpageIndexzf != icbczfph.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnzf(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnzf(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="icbcpageJumpIdxzf" onkeydown="return icbcpgJumpChkzf()">页

				<input name=icbcpageIndexzf type=hidden value="<%=icbcpageIndexzf%>">
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
	<%}%>
	<%
		if(icbcffhd != null && icbcffhd.size()>0){
	%>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" align="center">银行编号</td>
				<td class="panel_tHead_MB" align="center">日期</td>
				<td class="panel_tHead_MB" align="center">交易商编号</td>
				<td class="panel_tHead_MB" align=right>银行端权益&nbsp;</td>
				<td class="panel_tHead_MB" align=right>市场端权益&nbsp;</td>
				<td class="panel_tHead_MB" align="center">不平原因</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<icbcffhd.getCurNum();i++)
			{
				BankFirmRightValue bfr2 = (BankFirmRightValue)icbcffhd.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=bfr2.bankId%></td>
					<td class="underLine" align=center><%=Tool.fmtDate(bfr2.bdate)%>&nbsp;</td>
					<td class="underLine" align=center>&nbsp;<%=bfr2.firmId%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.bankRight)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.maketRight)%>&nbsp;</td>
					<td class="underLine" align=center><%
					if(bfr2.reason == 0){
						out.print("金额不平");
					}else if(bfr2.reason == 1){
						out.print("银行端账户未建立");
					}else if(bfr2.reason == 2){
						out.print("市场端账户未建立");
					}
					%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6" align=right>
				第<%=icbcpageIndexff%>/<%=icbcffhd.getPageCount()%>页 &nbsp;&nbsp;共<%=icbcffhd.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="icbcpageSizeff" class="text" type="text" style="width:25px;" value="<%=icbcpageSizeff%>" onkeydown="return icbcpgJumpChkff()">条 &nbsp;&nbsp;
				<%
				if(icbcpageIndexff != 1)
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnff(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnff(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(icbcpageIndexff != icbcffhd.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnff(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnff(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="icbcpageJumpIdxff" onkeydown="return icbcpgJumpChkff()">页

				<input name=icbcpageIndexff type=hidden value="<%=icbcpageIndexff%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<%}%>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function icbcbuttonclick(i){
	if(i == 1){
		frm.submitFlag.value = "icbcsendQS";
		frm.submit();
	} else if (i == 2){
		frm.submitFlag.value = "icbcsavezfph";
		frm.submit();
	} else if (i == 3){
		frm.submitFlag.value = "icbcsaveffhd";
		frm.submit();
	} else if (i == 4){
		frm.submitFlag.value = "icbcgetzfph";
		frm.submit();
	} else if (i == 5){
		frm.submitFlag.value = "icbcgetffhd";
		frm.submit();
	}
}
function icbcpgTurnzf(i) {
	frm.submitFlag.value = "icbcgetzfph";
	if(i == 0) {
		frm.icbcpageIndexzf.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.icbcpageIndexzf.value = frm.icbcpageIndexzf.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.icbcpageIndexzf.value = <%=icbczfph.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.icbcpageIndexzf.value = frm.icbcpageIndexzf.value - 1;
		frm.submit();
	}
}
function icbcpgTurnff(i) {
	frm.submitFlag.value = "icbcgetffhd";
	if(i == 0) {
		frm.icbcpageIndexff.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.icbcpageIndexff.value = frm.icbcpageIndexff.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.icbcpageIndexff.value = <%=icbcffhd.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.icbcpageIndexff.value = frm.icbcpageIndexff.value - 1;
		frm.submit();
	}
}
function icbcpgJumpChkzf() {
	if(event.keyCode == 13) {
		if((isNaN(frm.icbcpageJumpIdxzf.value) || frm.icbcpageJumpIdxzf.value < 1 || frm.icbcpageJumpIdxzf.value > <%=icbczfph.getPageCount()%>) && (isNaN(frm.icbcpageSizezf.value) || frm.icbcpageSizezf.value < 1 )) {
			alert("请输入1 - <%=icbczfph.getPageCount()%>间的数字！");			
		} else {
			frm.submitFlag.value = "icbcgetzfph";
			frm.icbcpageIndexzf.value = frm.icbcpageJumpIdxzf.value;
			frm.submit();
		}
	}
}
function icbcpgJumpChkff() {
	if(event.keyCode == 13) {
		if((isNaN(frm.icbcpageJumpIdxff.value) || frm.icbcpageJumpIdxff.value < 1 || frm.icbcpageJumpIdxff.value > <%=icbcffhd.getPageCount()%>) && (isNaN(frm.icbcpageSizeff.value) || frm.icbcpageSizeff.value < 1 )) {
			alert("请输入1 - <%=icbcffhd.getPageCount()%>间的数字！");			
		} else {
			frm.submitFlag.value = "icbcgetffhd";
			frm.icbcpageIndexff.value = frm.icbcpageJumpIdxff.value;
			frm.submit();
		}
	}
}
//-->
</SCRIPT>