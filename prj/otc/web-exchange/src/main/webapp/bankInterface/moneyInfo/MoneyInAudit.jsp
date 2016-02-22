<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<style>
.table_tdcss{
     font-size: 12px;
	 line-height: 40px;
	 color: #000000;
	 height: 40px; 
	 float:right;
	 }
</style>
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
BankDAO dao = BankDAOFactory.getDAO();
Vector bankList = dao.getBankList(" and validFlag=0 ");
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
pageinfo[2]=pageSize;
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
pageinfo[1]=pageIndex;
String firmID = Tool.delNull(request.getParameter("firmID"));
String bankID = Tool.delNull(request.getParameter("bankID"));
String s_time = Tool.delNull(request.getParameter("s_time"));
String e_time = Tool.delNull(request.getParameter("e_time"));
String filter = " and (type=0 or type=1) and fbc.status not in ("+ProcConstants.statusSuccess+","+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+")";
if(firmID != null && firmID.trim().length()>0){
	filter += " and fbc.contact like '%"+firmID.trim()+"%' ";
}
if(bankID != null && bankID.trim().length()>0 && !bankID.trim().equalsIgnoreCase("-2")){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(s_time != null && s_time.trim().length()>0){
	//filter += " and trunc(createtime)>=to_date('"+s_time+"','yyyy-MM-dd') ";
	filter += " and createtime>=to_date('"+s_time+"','yyyy-MM-dd') ";
}
if(e_time != null && e_time.trim().length()>0){
	//filter += " and trunc(createtime)<=to_date('"+e_time+"','yyyy-MM-dd') ";
	filter += " and createtime<to_date('"+e_time+"','yyyy-MM-dd')+1 ";
}
filter += " order by createtime";
System.out.println(filter);
Vector moneyList = dao.getCapitalInfoList2(filter,pageinfo);
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
    <title>单边账审核列表</title>
  </head>
  
  <body>
		<form id="frm" action="MoneyInAudit2.jsp" method="post">
		<input type="hidden" name="submitFlag">
		<input type="hidden" name="funID">
		<div id="main_body">
			<table border="0" cellspacing="0" class="table1_style" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
													<tr>
														<td class="table3_td_1" align="left">
															<%=CONTACTTITLE%>:&nbsp;
															<label>
																<input type="text"
																	class="input_text" maxlength="15" name="firmID" value="<%//=firmID%>" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															银行名称:&nbsp;
															<label>
																<select name="bankID" class="normal" style="width:120px">
																	<OPTION value="">全部</OPTION>
															   <%
																for(int i=0;i<bankList.size();i++) {
																	BankValue bv = (BankValue)bankList.get(i);
																	%>
																	<option value="<%=bv.bankID%>" <%//=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
																	<%
																}
																%>
																</select>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="doQuery();">
																查询
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">
																重置
															</button>
														</td>
														
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															
															<label>
															<input type="text" style="width:120px" class="wdate" readonly name="s_time" value="<%//=s_time%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															<label>
														</td>
														<td class="table3_td_1" align="left">
														      结束日期:&nbsp;
															<label>
																<input type="text" style="width:120px" class="wdate" readonly name="e_time" value="<%//=e_time%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
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
						<div class="div_tj">
							<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
								<tr>
									<td class="table3_td" align="left">&nbsp;&nbsp;
										<button id="yesBtn" class="anniu_btnmax" onclick="handleData(frm,tableList,'ck','yes')">
											通过
										</button>&nbsp;&nbsp;
										<button id="noBtn" class="anniu_btnmax" onclick="handleData(frm,tableList,'ck','no')">
											拒绝
										</button>&nbsp;&nbsp;
										<button id="noBtn" class="anniu_btnmax" onclick="addInfo()">
											手工流水
										</button>
									</td>
								</tr>
							</table>
						</div>
					<%}%>
						<div class="div_list">
							 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1000" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">序号</td>
				<td class="panel_tHead_MB" align="center">操作时间</td>
				<td class="panel_tHead_MB" align="center">交易所流水号</td>
				<td class="panel_tHead_MB" align="center"><%=CONTACTTITLE%></td>
				<td class="panel_tHead_MB" align="center">银行</td>
				<td class="panel_tHead_MB" align="center">银行账号</td>
				<td class="panel_tHead_MB" align="center">转账金额</td>
				<td class="panel_tHead_MB" align="center">转账类型</td>
				<td class="panel_tHead_MB" align="center">当前状态</td>
				<td class="panel_tHead_MB_last" align="center">备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<moneyList.size();i++){
				CapitalValue money = (CapitalValue)moneyList.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><input name="ck" type="radio" value='<%=money.actionID%>'></td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.actionID%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.account)%>&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.type==ProcConstants.inMoneyType){
							out.print("入金");
						}else if(money.type==ProcConstants.outMoneyType){
							out.print("出金");
						}else if(money.type==ProcConstants.inMoneyBlunt){
							out.print("入金冲正");
						}else if(money.type==ProcConstants.outMoneyBlunt){
							out.print("出金冲正");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.status==0){
							out.print("成功");
						}else if(money.status==1 || money.status==-1){
							out.print("失败");
						}else{
							out.print("待处理");
						}
					%>&nbsp;</td>
					<td class="underLine_last" align="center"><%=replaceNull(money.note)%>&nbsp;</td>
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
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
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
				到<input class="text" type="text" maxlength="4" value="<%=pageinfo[1]%>" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
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
						</div>
					</td>
				</tr>
			</table>
		</div>
		
							</form>
  </body>
</html>
<script>
	frm.firmID.value="<%=firmID%>";
	frm.bankID.value="<%=bankID%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function myreset(){
	frm.reset();
	doQuery();
}
function handleData(v1,v2,v3,v4) {
	hiddenBtn(true);
	var ck = null;
	var radios = document.getElementsByName("ck");
	for(var i=0;i<radios.length;i++){
		if(radios[i].checked == true){
			ck = radios[i].value;
		}
	}
	if(ck != null){
		if(v4=='no'){
			var str = '请确认拒绝流水['+ck+']';
			if(confirm(str)){
				frm.submitFlag.value=v4;
				frm.submit();
			}else{
				hiddenBtn(false);
			}
		}else{
			var funID = window.showModalDialog("addfunID.jsp","","dialogWidth=480px; dialogHeight=130px; status=yes;help=no;");
			if(funID != null){
				frm.funID.value=funID;
				var str = '请确认流水['+ck+']审核通过，流水号['+funID+']';
				if(confirm(str)){
					frm.submitFlag.value=v4;
					frm.submit();
				}else{
					hiddenBtn(false);
				}
			}else{
				hiddenBtn(false);
			}
		}
	}else{
		alert("请选择要审核的数据");
		hiddenBtn(false);
	}
}
function hiddenBtn(flag) {
		document.getElementById("yesBtn").disabled=flag;
		document.getElementById("noBtn").disabled=flag;
}
function addInfo(){
	var result = window.showModalDialog("addInfo.jsp","","dialogWidth=500px; dialogHeight=350px; status=yes;scroll=yes;help=no;");	
}
function pgTurn(i) {
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
	var firmID = frm.firmID.value;
	var s_time = frm.s_time.value;
	var e_time = frm.e_time.value;
	var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
	if(!isStr(firmID.trim(),false,null)){
		alert("交易商信息非法字符");
		frm.firmID.focus();
	} else if(s_time != "" && !CompareDate(s_time,now1)){
		alert("起始日期格式错误或起始日期超过今天");
		frm.s_time.value="";
	} else if(e_time != "" && !CompareDate(e_time,now1)){
		alert("结束日期格式错误或结束日期超过今天");
		frm.e_time.value="";
	} else if(s_time != "" && e_time != "" && !CompareDate(s_time,e_time)){
		alert("输入的开始日期不能超过结束日期");
		frm.s_time.value="";
		frm.e_time.value="";
	} else{
		frm.pageIndex.value = 1;
		frm.submitFlag.value="do";
		frm.submit();
	}
}
function pgJumpChk() {
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("请输入正确数字！");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submitFlag.value="do";
			frm.submit();
		}
	}	
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
//-->
</SCRIPT>