<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>

<%
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	int ccbpageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("ccbpageSize"));
	if(size>0){
		ccbpageSize = size;
	}
	int ccbpageIndex= Tool.strToInt((String)request.getParameter("ccbpageIndex"));
	if(ccbpageIndex < 0)  ccbpageIndex = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals(""))
	{
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	ObjSet ccbqsbp =null;
	Vector bankList = dao.getBankList(" where validFlag=0");
	String result = "";
	Vector ccbCustDzFail =null;
	if("ccbsendQS".equalsIgnoreCase(request.getParameter("submitFlag"))){//发送清算数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.sentFirmBalance(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("ccbqsResult".equalsIgnoreCase(request.getParameter("submitFlag"))){//查看清算状态
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ReturnValue rv = cp.saveQSResult(bankID,s_time);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "返回信息为空";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("取银行清算信息信息:"+result);
		dao.log(lv);
	}else if("ccbsaveqsbp".equalsIgnoreCase(request.getParameter("submitFlag"))){//取银行数据
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ReturnValue rv = cp.saveQSResult(bankID,s_time);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "返回信息为空";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("取银行清算信息信息:"+result);
		dao.log(lv);
	}else if("ccbgetqsbp".equalsIgnoreCase(request.getParameter("submitFlag"))){//展示清算结果
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ccbCustDzFail = dao.getQSList(bankID,null,-1,Tool.strToDate(s_time));
		if(ccbCustDzFail==null) {
			result = "查询银行对账不平信息异常，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else if(ccbCustDzFail.size() == 0) {
			result = "银行对账成功，银行代码："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
		} else {
			result = "银行对账不成功："+bankID+"，对账日期："+s_time+"时间："+Tool.fmtTime(new java.util.Date());
			int maxpage = 0;
			if(ccbCustDzFail!=null){
				maxpage = ccbCustDzFail.size()%ccbpageSize==0 ? ccbCustDzFail.size()/ccbpageSize : ccbCustDzFail.size()/ccbpageSize+1;
			}
			if(ccbpageIndex>maxpage){
				ccbpageIndex=maxpage;
			}
		}
		lv.setLogcontent("对银行和市场的签解约信息:"+result);
		dao.log(lv);
	}
	ccbqsbp = ObjSet.getInstance(ccbCustDzFail, ccbpageSize, ccbpageIndex);
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>建设银行清算页面</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>建设银行清算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
				<td align="right">银行：&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1">请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++) {
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
				<tr height="35">
					<td align="right">操作结果结果：&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn0" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="发送清算数据">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- 
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="查看清算状态">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						-->
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(3);" value="取银行信息">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn3" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(4);" value="比对银行信息">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(ccbqsbp!=null && ccbqsbp.size()!=0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class-"panel_tHead_MB">银行</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class-"panel_tHead_MB">清算结果</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<ccbqsbp.getCurNum();i++) {
				QSRresult ocb = (QSRresult)ccbqsbp.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.bankID)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.firmID)%>&nbsp;</td>
					<td class="underLine">
						<%
							if(0==ocb.status){
								out.println("清算成功");
							}else if(1==ocb.status){
								out.println("失败");
							}else if(2==ocb.status){
								out.println("可用余额不等");
							}else if(3==ocb.status){
								out.println("冻结余额不等");
							}else if(4==ocb.status){
								out.println("总余额不等");
							}else if(6==ocb.status){
								out.println("账号资金异常");
							}
						%>
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			  }
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="3">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="3" align=right>
				第<%=ccbpageIndex%>/<%=ccbqsbp.getPageCount()%>页 &nbsp;&nbsp;共<%=ccbqsbp.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="ccbpageSize" class="text" type="text" style="width:25px;" value="<%=ccbpageSize%>" onkeydown="return ccbpgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(ccbpageIndex != 1) {
					%>
					<span style="cursor:hand" onclick="ccbpgTurn(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="ccbpgTurn(-1)">上页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(ccbpageIndex != ccbqsbp.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="ccbpgTurn(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="ccbpgTurn(2)">尾页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}
				%>
				到<input class="text" type="text" style="width:25px;" name="ccbpageJumpIdx" onkeydown="return ccbpgJumpChk()">页

				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="3"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		
	</table>
	<%}%>
	<input name=ccbpageIndex type=hidden value="<%=ccbpageIndex%>">
	</form>
  </body>
</html>
<script type="text/javascript">  
	function doCompare(v) {
		frm.ccbpageIndex.value = 1;
		if(frm.bankID.value == -1) {
			alert("请选择银行！");
		} else if(frm.s_time.value == "") {
			alert("请选择对账日期！");
		} else {
			var now1 = new Date()+"";
			if(CompareDate((frm.s_time.value),now1)) {
				if(v==1){//发送清算数据
					frm.submitFlag.value = "ccbsendQS";
					frm.submit();
				}else if(v==2){//查看文件状态
					frm.submitFlag.value = "ccbqsResult";
					frm.submit();
				}else if(v==3){//取得银行清算数据
					frm.submitFlag.value = "ccbsaveqsbp";
					frm.submit();
				} else if(v==4) {//展示清算结果
					frm.submitFlag.value = "ccbgetqsbp";
					frm.submit();	
				}
			} else {
				alert("选择的对账日期不合法!");
				frm.s_time.value="";
			}
		}
	}
	function ccbpgTurn(i) {
		frm.submitFlag.value = "getData";
		if(i == 0) {
			frm.ccbpageIndex.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.ccbpageIndex.value = frm.ccbpageIndex.value * 1 + 1;	
			frm.submit();
		} else if(i == 2) {
			<%if(ccbqsbp!=null){ %> frm.ccbpageIndex.value = <%=ccbqsbp.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.ccbpageIndex.value = frm.ccbpageIndex.value - 1;
			frm.submit();
		}
	}
	function ccbpgJumpChk() {
		if(event.keyCode == 13) {
			if((isNaN(frm.ccbpageJumpIdx.value) || frm.ccbpageJumpIdx.value < 1 || frm.ccbpageJumpIdx.value > <%=ccbqsbp.getPageCount()%>) && (isNaN(frm.ccbpageSize.value) || frm.ccbpageSize.value < 1 )) {
				alert("请输入1 - <%=ccbqsbp.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "getData";
				frm.ccbpageIndex.value = frm.ccbpageJumpIdx.value;
				frm.submit();
			}
		}	
	}
	function CompareDate(d1,d2)
 	{
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>