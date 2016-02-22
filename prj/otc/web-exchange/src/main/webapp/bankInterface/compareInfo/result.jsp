<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
boolean modright = false;
if(session.getAttribute("rightMap") != null){
	Map<String,Boolean> rightMap = (Map<String,Boolean>) session.getAttribute("rightMap");
	if(rightMap.get("update") != null){
		if(rightMap.get("update")==true){
			modright = true;
		}
	}
}
int[] pageinfo = new int[4];
int[] pageinfo2 = new int[4];
	String compareTime = Tool.getConfig("CompareTime");
	//Calendar sysCalendar = Calendar.getInstance();
	//Calendar calendar = Calendar.getInstance();
	//String[] time = compareTime.split(":");
	//calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
	//calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
	//calendar.set(Calendar.SECOND,Integer.parseInt(time[2]));
	
	boolean disableBtn = false;
	//if(sysCalendar.before(calendar)) {
	//	disableBtn = true;
	//}
	//else {
	//	disableBtn = false;	
	//}
	
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	pageinfo[2]=pageSize;
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex <= 0)  pageIndex = 1;
	pageinfo[1]=pageIndex;
	int pageSize2 = BANKPAGESIZE;
	int size2 = Tool.strToInt((String)request.getParameter("pageSize2"));
	if(size2>0){
		pageSize2 = size2;
	}
	pageinfo2[2]=pageSize2;
	int pageIndex2= Tool.strToInt((String)request.getParameter("pageIndex2"));
	if(pageIndex2 <= 0)  pageIndex2 = 1;
	pageinfo2[1]=pageIndex2;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	Vector bankList = dao.getBankList(" and validFlag=0 ");
	Map<String,String> mapBank = new HashMap<String,String>();
	String result = Tool.delNull((String)request.getParameter("result"));
	Vector getBankNoInfo =null;
	Vector getMarketNoInfo =null;
	Vector sumCompareMoney = null;
	//手动对账
	if("do".equals(request.getParameter("submitFlag"))) {
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new java.util.Date());
		lv.setIp(computerIP);
		lv.setLogtype("2150");
		lv.setLogoprtype("E");
		lv.setResult(1);
		getBankNoInfo = dao.getBankNoInfo(bankID,Tool.strToDate(s_time),pageinfo);
		getMarketNoInfo = dao.getMarketNoInfo(bankID,Tool.strToDate(s_time),pageinfo2);
		sumCompareMoney = dao.sumCompareMoney(bankID,Tool.strToDate(s_time));
		if(getBankNoInfo == null) {
			result += "，系统异常";
		} else if(getMarketNoInfo != null && getMarketNoInfo.size()>0){
			result += "，对账失败";
		} else if(getBankNoInfo.size() == 0) {
			result += "，对账成功";
			lv.setResult(0);
		} else {
			result += "，对账失败";
		}
		lv.setLogcontent("银行对账信息-对账流水明细-手动对账:"+result+" 银行代码："+bankID);
		dao.log(lv);
	}
	if(getBankNoInfo == null) getBankNoInfo = new Vector();
	if(getMarketNoInfo == null) getMarketNoInfo = new Vector();
	if(sumCompareMoney == null) sumCompareMoney = new Vector();
	int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
	int rownum2 = (pageinfo2[1]-1)*pageinfo2[2]+1;
	if(result != null && result.trim().length()>0){
	%>
	<script>
		alert('<%=result%>');
	</script>
	<%
	}
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" src="../lib/tools.js"></script>
    <title>银行对账信息</title>
  </head>
  
  <body>
	<form id="frm" action="result2.jsp" method="post">
		<input type="hidden" name="submitFlag">
		<input type="hidden" id="thispage" name="thispage">
		<div id="main_body">
			<table border="0" cellspacing="0" class="table1_style"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmid">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
												<tr>
													<td class="table3_td_1" align="left">
														银行名称:&nbsp;
														<label>
															<select name="bankID" class="normal" style="width:120px" onchange="reconciliation()">
																<OPTION value="-1">请选择</OPTION>
														   <%
															for(int i=0;i<bankList.size();i++) {
																BankValue bv = (BankValue)bankList.get(i);
																mapBank.put(bv.bankID,bv.bankName);
																%>
																<option value="<%=bv.bankID%>" <%=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
																<%
															}
															%>
															</select>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														对账日期:&nbsp;
														<label>
															<input type="text" style="width:100px" class="wdate" maxlength="10" name="s_time" value="<%=s_time%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</label>
													</td>
													<!-- <td class="table3_td_1" align="right">
														<button class="anniu_btnmax" <%//if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(1);">
															读取银行数据
														</button>&nbsp;&nbsp;
													</td> -->
													<td class="table3_td_1" align="left">
														<button class="anniu_btnmax" id='Compare2' <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(2);">
															对流水明细
														</button>
													</td>
												</tr>
												<tr>
													<td colspan="4" align="left">
														操作结果:&nbsp;
													<label>
														<font color=red><%=result%></font>
													</label>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
				<%
					if(modright){
				%>
					<div>
						<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
							<tr>
								<td class="table3_td" align="left">&nbsp;&nbsp;
									
									<button class="anniu_btnmax" id='Compare3' <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(3);">
										自动调整
									</button>
								</td>
							</tr>
						</table>
					</div>
				<%}%>
					<div class="div_list">
		<table width="100%" border="0">
			<tr height="25" align="center">
				<td><font align="center">对账结果</font></td>
			</tr>
		</table>
		<table id="sumCompareMoney" border="0" cellspacing="0" cellpadding="0" width="100%" height="100" >
		<thead>
			<tr height="25" align=center>
				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">序号</td>
				<td class="panel_tHead_MB">类别</td>
				<td class="panel_tHead_MB">入金笔数</td>
				<td class="panel_tHead_MB">入金金额</td>
				<td class="panel_tHead_MB">出金笔数</td>
				<td class="panel_tHead_MB">出金金额</td>
				<td class="panel_tHead_MB">出入金总额</td>
				<td class="panel_tHead_MB_last">出入金差额</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</thead>
		<tbody>
			<%
				if(sumCompareMoney != null && sumCompareMoney.size()>0){
					for(int i=0;i<sumCompareMoney.size();i++){
						CompareSumMoney cms = (CompareSumMoney)sumCompareMoney.get(i);
						if(cms != null && cms.bankName != null){
			%>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="center"><%=i+1%></td>
				<td class="underLine" align="center"><%
				if(cms.mb==1){
					out.print(Tool.delNull(cms.bankName));
				}else{
					out.print("交易所");
				}
				%>&nbsp;</td>
				<td class="underLine" align="center"><%=cms.inMoneyCount%></td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(cms.inMoney)%>&nbsp;</td>
				<td class="underLine" align="center"><%=cms.outMoneyCount%></td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(cms.outMoney)%>&nbsp;</td>
				<td class="underLine" align="right"><%=Tool.fmtMoney(cms.inMoney+cms.outMoney)%>&nbsp;</td>
				<td class="underLine_last" align="right"><%=Tool.fmtMoney(cms.inMoney-cms.outMoney)%>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%
						}
					}
				}
			%>
		</tbody>
		<tfoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tfoot>
		</table>
		<%
		//if(obj!=null && obj.size()!=0) {
		%>
		<table width="100%" border="0">
			<tr height="25" align=center>
				<td width="55%" align="right">交易所单边流水信息</td>
				<td align="right">
				<button class="btn_sec" onclick="doCompare(4)">导出</button>
				</td>
			</tr>
		</table>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="200" >
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">序号</td>
				<td class="panel_tHead_MB">银行名称</td>
				<td class="panel_tHead_MB">银行流水号</td>
				<td class="panel_tHead_MB">交易所流水号</td>
				<td class="panel_tHead_MB"><%=CONTACTTITLE%></td>
				<td class="panel_tHead_MB">银行账号</td>
				<td  class="panel_tHead_MB">转账类型</td>
				<td class="panel_tHead_MB">转账金额</td>
				<td  class="panel_tHead_MB">转账状态</td>
				<td class="panel_tHead_MB_last">对账结果</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			for(int i=0;i<getBankNoInfo.size();i++){
				CompareResult money = (CompareResult)getBankNoInfo.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=replaceNull(mapBank.get(money.bankID))%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.funID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.actionID+"")%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%
					//Tool.delNull(money.account).equals(Tool.getConfig("DefaultAccount")) ? "-" : Tool.delNull(money.account)
						if("005".equals(money.bankID)){
							out.print(replaceNull(money.account1));
						}else{
							out.print(replaceNull(money.account));
						}
					%>&nbsp;</td>
					<td class="underLine" align="center">
					<%
						if(money.type == 0){
							out.println("入金");
						}else if(money.type == 1){
							out.println("出金");
						}
					%>
					&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.status == 0){
							out.print("<font color='#FF0000'>成功</font>");
						}else{
							out.print("处理中");
						}
					%>&nbsp;</td>
					<td class="underLine_last" align="left">银行无流水&nbsp;</td>
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
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text"  maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
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

				if(pageinfo[1] < pageinfo[3])
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
				到<input class="text" type="text"  maxlength="4"  value="<%=pageinfo[1]%>"  style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

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
	<%//}%>
	</div>
	<div class="div_list">
		<%
		//if(obj2!=null && obj2.size()!=0) {
		%>
		<table width="100%" border="0">
			<tr height="25" align=center>
				<td width="53%" align="right">银行流水不平记录</td>
				<td align="right">
				<button class="btn_sec" onclick="doCompare(5)">导出</button>
				</td>
			</tr>
		</table>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="200">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">序号</td>
				<td class="panel_tHead_MB">银行名称</td>
				<td class="panel_tHead_MB"><%=CONTACTTITLE%></td>
				<td class="panel_tHead_MB">银行账号</td>
				<td class="panel_tHead_MB">银行流水号</td>
				<td class="panel_tHead_MB">转账类型</td>
				<td class="panel_tHead_MB">转账金额</td>
				<td class="panel_tHead_MB_last">对账结果</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<%
		for(int i=0;i<getMarketNoInfo.size();i++){
			CompareResult money2 = (CompareResult) getMarketNoInfo.get(i);
		%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=Tool.delNull(mapBank.get(money2.bankID))%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money2.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if("005".equals(money2.bankID)){
							out.print(replaceNull(money2.account1));
						}else{
							out.print(replaceNull(money2.account));
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money2.funID)%>&nbsp;</td>
					<td class="underLine" align="center">
					<%
						if(money2.type == 0){
							out.println("入金");
						}else if(money2.type == 1){
							out.println("出金");
						}
					%>
					&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money2.money)%>&nbsp;</td>
					<td class="underLine_last" align="center">市场无流水&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
		<%}%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8" align=right>
				第<%=pageinfo2[1]%>/<%=pageinfo2[3]%>页 &nbsp;&nbsp;共<%=pageinfo2[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize2" class="text" type="text" style="width:25px;" value="<%=pageinfo2[2]%>" onkeydown="return pgJumpChk2()">条 &nbsp;&nbsp;
				<%
				if(pageinfo2[1] != 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn2(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn2(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;
					<%
				}

				if(pageinfo2[1] < pageinfo2[3])
				{
					%>
					<span style="cursor:hand" onclick="pgTurn2(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn2(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" maxlength="4" value="<%=pageinfo2[1]%>" style="width:25px;" name="pageJumpIdx2" onkeydown="return pgJumpChk2()">页

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
	<%//}%>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
		<input name=pageIndex2 type=hidden value="<%=pageinfo2[1]%>">
		</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function doCompare(v) {
	frm.pageIndex.value = 1;
	frm.pageIndex2.value = 1;
	if(frm.bankID.value == -1) {
		alert("请选择银行！");
	}
	else if(frm.s_time.value == "") {
		alert("请选择对账日期！");
	}
	else {
		var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
		if(CompareDate((frm.s_time.value),now1)) {
			if(v==3){
				if(<%=(getBankNoInfo != null && getBankNoInfo.size()>0) || (getMarketNoInfo != null && getMarketNoInfo.size()>0)%>){
					if(confirm("确认现在要同步银行流水吗？")){
						document.getElementById('Compare2').disabled='disabled';
						document.getElementById('Compare3').disabled='disabled';
						frm.action="result2.jsp";
						frm.submitFlag.value = "roughInfo";
						frm.submit();
					}
				}else{
					alert("还未查询到不平数据，不能调平！");
				}
			} else if(v==2) {
				if(confirm("您确认取银行数据吗？")){
					document.getElementById('Compare2').disabled='disabled';
					if(document.getElementById('Compare3') != null){
						document.getElementById('Compare3').disabled='disabled';
					}
					frm.action="result2.jsp";
					frm.submitFlag.value = "getData";
					frm.submit();
				}
			} else if(v==4 || v==5) {//4 导出市场单边账信息,5 导出银行单边账信息
				var url = "BankNoCapitalExcle.jsp";
				if(v==5){
					url = "MarketNoCapitalExcle.jsp";
				}
				if(confirm("导出全部数据吗?\n\n(取消为导出当前页)")){
					frm.thispage.value="0";
				}else{
					frm.thispage.value="1";
				}
				frm.action=url;
				frm.submit();
			}
		} else {
			alert("对账日期格式错误或对账日期超过今天!");
			frm.s_time.value="";
		}
	}
}
function pgJumpChk() {
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("请输入正确数字！");			
		} else {
			frm.action="result2.jsp";
			frm.submitFlag.value = "do";
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}

function pgTurn(i) {
	frm.action="result2.jsp";
	frm.submitFlag.value = "do";
	if(i == 0) {
		frm.pageIndex.value = 1;
		frm.submit();
	} else if(i == 1) {
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex.value = <%=pageinfo[3]%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function doQuery() {
	frm.submitFlag.value = "do";
	var firmID = frm.firmID.value;
	var bankID = frm.bankID.value;
	if(!calibration("str",firmID)){
		alert("交易商信息非法字符");
		frm.firmID.focus();
	}else if(!calibration("str",bankID)){
		alert("银行编号信息非法字符");
		frm.bankID.focus();
	}else{
		frm.action="result2.jsp";
		frm.submit();
		frm.pageIndex.value = 1;
	}
}

function pgTurn2(i) {
	frm.action="result2.jsp";
	frm.submitFlag.value = "do";
	if(i == 0) {
		frm.pageIndex2.value = 1;
		frm.submit();
	} else if(i == 1) {
		frm.pageIndex2.value = frm.pageIndex2.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex2.value = <%=pageinfo2[3]%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex2.value = frm.pageIndex2.value - 1;
		frm.submit();
	}
}
function pgJumpChk2() {
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx2.value) || frm.pageJumpIdx2.value < 1 || frm.pageJumpIdx2.value > <%=pageinfo2[3]%>) || (isNaN(frm.pageSize2.value) || frm.pageSize2.value < 1 )) {
			alert("请输入正确数字！");			
		} else {
			frm.action="result2.jsp";
			frm.submitFlag.value = "do";
			frm.pageIndex2.value = frm.pageJumpIdx2.value;
			frm.submit();
		}
	}	
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
//-->
function reconciliation(){
	var compareTime=frm.compareTime.value;
	var selectbank=frm.bankID.value;
	var newtime=new Date();
	var settime=new Date();
	var bankTimeList=compareTime.split(";");
	document.getElementById('Compare2').disabled='disabled';
	document.getElementById('Compare3').disabled='disabled';
	for(var i=0;i<bankTimeList.length;i++){
		if(bankTimeList[i].split("=")[0]==selectbank){
			var time=bankTimeList[i].split("=")[1].split(":");
			settime.setHours(time[0],time[1],time[2]);

				if(settime>newtime){
					document.getElementById('Compare2').disabled='disabled';
					document.getElementById('Compare3').disabled='disabled';
				}else{
					document.getElementById('Compare2').disabled='';
					document.getElementById('Compare3').disabled='';
				}
		}		
	}
}
</SCRIPT>