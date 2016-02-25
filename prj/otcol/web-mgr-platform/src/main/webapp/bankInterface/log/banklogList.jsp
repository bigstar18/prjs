<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	BankDAO dao = BankDAOFactory.getDAO();
	
	String today = Tool.fmtDate(new java.util.Date());
	
	Vector bankList = dao.getBankList(" ");
	
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	
	String filter = " ";
	String type = Tool.delNull(request.getParameter("type"));
	if(type != null && type.trim().length()>0){
		filter += " and fbi.type="+type;
	}
	
	String bankID = Tool.delNull(request.getParameter("bankID"));
	if(bankID != null && bankID.trim().length()>0){
		filter += " and fbi.bankID='"+bankID+"'";
	}
	
	String launcher = Tool.delNull(request.getParameter("launcher"));
	if(launcher != null && launcher.trim().length()>0){
		filter += " and fbi.launcher='"+launcher.trim()+"'";
	}
	
	String firmID = Tool.delNull(request.getParameter("firmID"));
	if(firmID != null && firmID.trim().length()>0){
		filter += " and fbf.firmID='"+firmID.trim()+"'";
	}
	
	String contact = Tool.delNull(request.getParameter("contact"));
	if(contact != null && contact.trim().length()>0){
		filter += " and fbi.firmID='"+contact.trim()+"'";
	}
	
	String account = Tool.delNull(request.getParameter("account"));
	if(account != null && account.trim().length()>0){
		filter += " and fbi.account='"+account.trim()+"'";
	}
	
	String result = Tool.delNull(request.getParameter("result"));
	if(result != null && result.trim().length()>0){
		filter += " and fbi.result='"+result+"'";
	}
	
	String s_time = Tool.delNull(request.getParameter("s_time"));
	String e_time = Tool.delNull(request.getParameter("e_time"));
	if(request.getParameter("submitFlag")==null || request.getParameter("submitFlag").trim().length()<=0){
		s_time = today;
		e_time = today;
	}
	if(s_time != null && s_time.trim().length()>0) {
		filter += " and fbi.CREATETIME>=to_date('"+ s_time +"','yyyy-MM-dd') ";
	}
	if(e_time != null && e_time.trim().length()>0) {
		filter += " and fbi.CREATETIME<to_date('"+ e_time +"','yyyy-MM-dd')+1 ";
	}
	filter += " order by fbi.logid desc ";

	Vector logList = dao.interfaceLogList(filter);
	ObjSet obj = ObjSet.getInstance(logList, pageSize, pageIndex);
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" src="../lib/validate.js"></script>
    <title>接口通信日志</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
	    <input type="hidden" name="submitFlag">
		<fieldset width="100%">
			<legend>接口通信日志</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td class="table3_td_1" align="left">
						交易类型:&nbsp;
						<label>
							<select name="type" class="normal" style="width:120px">
								<OPTION value="">全部</OPTION>
								<OPTION value="1">签到</OPTION>
								<OPTION value="2">签退</OPTION>
								<OPTION value="3">签约</OPTION>
								<OPTION value="4">解约</OPTION>
								<OPTION value="9">改约</OPTION>
								<OPTION value="5">查询余额</OPTION>
								<OPTION value="6">出金</OPTION>
								<OPTION value="7">入金</OPTION>
								<OPTION value="8">冲正</OPTION>
								<OPTION value="10">密钥同步</OPTION>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						银行:&nbsp;
						<label>
							<select name="bankID" class="normal" style="width:120px">
								<OPTION value="">全部</OPTION>
								<%
								  for(int i=0;i<bankList.size();i++) {
									BankValue bv = (BankValue)bankList.get(i);
									%>
									   <option value="<%=bv.bankID%>"><%=bv.bankName%></option>		
									<%
								  }
								%>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						发起方&nbsp;
						<label>
							<select name="launcher" class="normal" style="width:120px">
								<option value="">全部</option>
								<option value="0">市场</option>
								<option value="1">银行</option>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						银行账号:&nbsp;
						<label>
							<input type="text" class="text" name="account">
						</label>
					</td>
					
				</tr>
				<tr>
					<td class="table3_td_1" align="left">
						操作结果:&nbsp;
						<label>
							<select name="result" class="normal" style="width:120px">
								<option value="">全部</option>
								<option value="0">成功</option>
								<option value="1">失败</option>
							</select>
						</label>
						<input type="hidden"  class="input_text" name="firmID">
					</td>
					<td class="table3_td_1" aling="left">
						交易账号:&nbsp;
						<input type="text" class="text" name="contact" style="width:120px">
					</td>
					<td class="table3_td_1" align="left">
						开始日期:&nbsp;
						<label>
							<!--<input type="text" style="width:100px" class="wdate" value="<%=today%>" readonly name="s_time" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">-->
							<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
						</label>
					</td>
					<td class="table3_td_1" align="left">
					    截止日期:&nbsp;
						<label>
							<!--<input type="text" style="width:102px" class="wdate" value="<%=today%>" readonly name="e_time" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">-->
							<MEBS:calendar eltName="e_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=e_time%>"/>
						</label>
					</td>
					<td align="left">
						<button type="button" onclick="doQuery();">查询</button>&nbsp;&nbsp;&nbsp;
						<button type="button" onclick="myreset();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">序号</td>
				<td class="panel_tHead_MB">记录时间</td>
				<td class="panel_tHead_MB">发起方</td>
				<td class="panel_tHead_MB">交易类型</td>
				<td class="panel_tHead_MB">银行</td>
				<td class="panel_tHead_MB">交易账号</td>
				<td class="panel_tHead_MB">银行账号</td>
				<td class="panel_tHead_MB">操作结果</td>
				<td class="panel_tHead_MB">结果信息</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			for(int i=0;i<obj.getCurNum();i++){
				InterfaceLog log = (InterfaceLog)logList.get(i);
				//out.print(log.bankName);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=log.logID%></td>
					<td class="underLine" align="center"><%=Tool.fmtTime(log.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.launcher==0){
							out.print("市场");
						}else if(log.launcher==1){
							out.print("银行");
						}else{
							out.print("其他");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.type==1){
							out.print("签到");
						}else if(log.type==2){
							out.print("签退");
						}else if(log.type==3){
							out.print("签约");
						}else if(log.type==4){
							out.print("解约");
						}else if(log.type==5){
							out.print("查询余额");
						}else if(log.type==6){
							out.print("出金");
						}else if(log.type==7){
							out.print("入金");
						}else if(log.type==8){
							out.print("冲正");
						}else if(log.type==9){
							out.print("改约");
						}else if(log.type==10){
							out.print("密钥同步");
						}else{
							out.print("其他");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.account)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.result==0){
							out.print("成功");
						}else{
							out.print("失败");
						}
					%>&nbsp;</td>
					<td class="underLine_last" align="left"><%=Tool.delNull(log.endMsg)%>&nbsp;</td>
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
				<td class="panel_tFoot_MB" colspan="9"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
  </body>
</html>
<script>
	frm.type.value="<%=type%>";
	frm.bankID.value="<%=bankID%>";
	frm.launcher.value="<%=launcher%>";
	frm.firmID.value="<%=firmID%>";
	frm.contact.value="<%=contact%>";
	frm.account.value="<%=account%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
	frm.result.value="<%=result%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
	function pgTurn(i)
	{
	    frm.submitFlag.value = "doQuery";
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
	
	function doQuery(){
		frm.submitFlag.value = "doQuery";
		var firmID = frm.firmID.value;
		var contact = frm.contact.value;
		var account = frm.account.value;
		var s_time = frm.s_time.value;
		var e_time = frm.e_time.value;
		var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
		if(!isStr(firmID.trim(),false,null)){
			alert("会员或客户编号不合法");
			frm.firmID.focus();
		} else if(!isStr(contact.trim(),false,null)){
			alert("签约号不合法");
			frm.contact.focus();
		} else if(!isStr(account.trim(),false,null)){
			alert("银行账号不合法");
			frm.account.focus();
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
			frm.submit();
		}
	}
	
	function myreset(){
		frm.reset();
		//doQuery();
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
			    frm.submitFlag.value = "doQuery";
				frm.pageIndex.value = frm.pageJumpIdx.value;
				frm.submit();
			}
		}	
	}
	
	function CompareDate(d1,d2) {
		return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>