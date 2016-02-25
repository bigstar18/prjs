<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.io.File"/>
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
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	int cibpageSizezfph = BANKPAGESIZE;//总分平衡页面条数
	int cibpageSizeffhd = BANKPAGESIZE;//分分核对页面条数
	int cibpageIndexzfph = 0;//总分平衡页编号
	int cibpageIndexffhd = 0;//分分核对页编号
	ObjSet cibzfph =null;//总分平衡
	ObjSet cibffhd =null;//分分核对
	Vector cibgetzfph =null;//总分平衡
	Vector cibgetffhd =null;//分分核对
	String result = "---";
	if("cibQSDZ".equals(request.getParameter("submitFlag"))) {//发送对账信息
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.sendXYQSValue(bankID,null,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("cibZFPH".equals(request.getParameter("submitFlag"))) {//取银行总分平衡
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getZFPH(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("cibFFHD".equals(request.getParameter("submitFlag"))) {//取银行分分核对
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getFFHD(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("cibGETZFPH".equals(request.getParameter("submitFlag"))) {//查看总分平衡信息
		try{
			cibgetzfph = dao.getZFPH(bankID,Tool.strToDate(s_time),-1);
		}catch(Exception e){
			e.printStackTrace();
		}
		int size = Tool.strToInt((String)request.getParameter("cibpageSizezfph"));
		if(size>0){
			cibpageSizezfph = size;
		}
		cibpageIndexzfph = Tool.strToInt((String)request.getParameter("cibpageIndexzfph"));
		if(cibpageIndexzfph <= 0) {
			cibpageIndexzfph = 1;
		}
		int maxpage = cibgetzfph.size()%cibpageSizezfph==0 ? cibgetzfph.size()/cibpageSizezfph : cibgetzfph.size()/cibpageSizezfph+1;
		if(cibpageIndexzfph>maxpage){
			cibpageIndexzfph=maxpage;
		}
		if(cibgetzfph.size()<=0){
			result = "未查询到银行["+bankID+"]总分数据";
		}else{
			result = "银行["+bankID+"]总分数据如下";
		}
	}else if("cibGETFFHD".equals(request.getParameter("submitFlag"))) {//查看分分核对信息
		cibgetffhd = dao.getFFHD("",bankID,Tool.strToDate(s_time));
		int size = Tool.strToInt((String)request.getParameter("cibpageSizeffhd"));
		if(size>0){
			cibpageSizeffhd = size;
		}
		cibpageIndexffhd = Tool.strToInt((String)request.getParameter("cibpageIndexffhd"));
		if(cibpageIndexffhd <= 0) {
			cibpageIndexffhd = 1;
		}
		int maxpage = cibgetffhd.size()%cibpageSizeffhd==0 ? cibgetffhd.size()/cibpageSizeffhd : cibgetffhd.size()/cibpageSizeffhd+1;
		if(cibpageIndexffhd>maxpage){
			cibpageIndexffhd=maxpage;
		}
		if(cibgetffhd.size()<=0){
			result = "分分核对结果平";
		}else{
			result = "分分核对不平数据如下";
		}
	}
	cibzfph = ObjSet.getInstance(cibgetzfph, cibpageSizezfph, cibpageIndexzfph);
	cibffhd = ObjSet.getInstance(cibgetffhd, cibpageSizeffhd, cibpageIndexffhd);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>兴业银行清算页面信息</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%">
			<legend>兴业银行清算页面信息</legend>
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
					<td align="right">操作结果：&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(1);" value="发送对账信息">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(2);" value="取总分平衡">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(3);" value="取分分核对">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(4);" value="显示总分平衡">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(5);" value="显示分分核对不平">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>	  
		<%
		if(cibzfph!=null && cibzfph.size()!=0) {
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">银行代码</td>
				<td class="panel_tHead_MB">明细账户余额汇总</td>
				<td class="panel_tHead_MB">汇总账号金额</td>
				<td class="panel_tHead_MB">钞汇标识</td>
				<td class="panel_tHead_MB">币种类型</td>
				<td class="panel_tHead_MB">操作日期</td>
				<td class="panel_tHead_MB">对账结果</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%		
			for(int i=0;i<cibzfph.getCurNum();i++) {
				ZFPHValue zfph = (ZFPHValue)cibzfph.get(i);
		%>
		<tr height="22" align=center>
			<td class="panel_tBody_LB">&nbsp;</td>
			<td class="underLine"><%=Tool.delNull(zfph.bankID)%></td>
			<td class="underLine"><%=Tool.fmtDouble2(zfph.lastAccountBalance.doubleValue())%></td>
			<td class="underLine"><%=Tool.fmtDouble2(zfph.accountBalance.doubleValue())%></td>
			<td class="underLine"><%=zfph.type==0 ? "钞" : "汇"%></td>
			<td class="underLine">
			<%
			if("013".equals(zfph.currency)){
				out.print("港币");
			}else if("014".equals(zfph.currency)){
				out.print("美元");
			}else {
				out.print("人民币");
			}
			%>
			</td>
			<td class="underLine"><%=Tool.fmtDate(zfph.tradeDate)%></td>
			<td class="underLine">
			<%
				if(zfph.result==0){
					out.print("平");
				}else{
					out.print("不平");
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
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7" align=right>
				第<%=cibpageIndexzfph%>/<%=cibzfph.getPageCount()%>页 &nbsp;&nbsp;共<%=cibzfph.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="cibpageSizezfph" class="text" type="text" style="width:25px;" value="<%=cibpageSizezfph%>" onkeydown="return cibpgJumpChkzfph()">条 &nbsp;&nbsp;
				<%
				if(cibpageIndexzfph != 1){
					%>
					<span style="cursor:hand" onclick="cibpgTurnzfph(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="cibpgTurnzfph(-1)">上页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				} if(cibpageIndexzfph != cibzfph.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="cibpgTurnzfph(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="cibpgTurnzfph(2)">尾页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}
				%>
				到<input class="text" type="text" style="width:25px;" name="cibpageJumpIdxzfph" onkeydown="return cibpgJumpChkzfph()">页
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
		<%
		}else if(cibffhd != null && cibffhd.size()>0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">银行代码</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">市场权益</td>
				<td class="panel_tHead_MB">市场可用资金</td>
				<td class="panel_tHead_MB">银行权益</td>
				<td class="panel_tHead_MB">银行可用资金</td>
				<td class="panel_tHead_MB">不平原因</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
		<%	
			for(int i=0;i<cibffhd.getCurNum();i++) {
				FirmDateValue fd = (FirmDateValue)cibffhd.get(i);
		%>
		<tr height="22" align=center>
			<td class="panel_tBody_LB">&nbsp;</td>
			<td class="underLine"><%=Tool.delNull(fd.bankID)%></td>
			<td class="underLine"><%=Tool.delNull(fd.firmID)%></td>
			<td class="underLine"><%=Tool.fmtDouble2(fd.balanceM)%></td>
			<td class="underLine"><%=Tool.fmtDouble2(fd.useBalanceM)%></td>
			<td class="underLine"><%=Tool.fmtDouble2(fd.balanceB)%></td>
			<td class="underLine"><%=Tool.fmtDouble2(fd.useBalanceB)%></td>
			<td class="underLine">
			<%
				if(fd.reason==0){
					out.print("金额不平");
				}else if(fd.reason==1){
					out.print("银行端资金存管账户未建立");
				}else{
					out.print("市场端交易商代码不存在");
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
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7" align=right>
				第<%=cibpageIndexffhd%>/<%=cibffhd.getPageCount()%>页 &nbsp;&nbsp;共<%=cibffhd.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="cibpageSizeffhd" class="text" type="text" style="width:25px;" value="<%=cibpageSizeffhd%>" onkeydown="return cibpgJumpChkffhd()">条 &nbsp;&nbsp;
				<%
				if(cibpageIndexffhd != 1) {
					%>
					<span style="cursor:hand" onclick="cibpgTurnffhd(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="cibpgTurnffhd(-1)">上页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}
				if(cibpageIndexffhd != cibffhd.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="cibpgTurnffhd(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="cibpgTurnffhd(2)">尾页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="cibpageJumpIdxffhd" onkeydown="return cibpgJumpChkffhd()">页
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
	<%}%>
	<input name=cibpageIndexzfph type=hidden value="<%=cibpageIndexzfph%>">
	<input name=cibpageIndexffhd type=hidden value="<%=cibpageIndexffhd%>">
	</form>
  </body>
</html>
<script type="text/javascript">  
	function cibbuttonclick(v) {
		var now1 = new Date()+"";
		if(frm.s_time.value == "") {
			alert("请选择对账日期！");
		}else if(CompareDate(now1,(frm.s_time.value))){
			alert("选择的对账日期不合法!");
			frm.s_time.value="";
		}else {
			if(v==1) {//发送清算数据
				frm.submitFlag.value = "cibQSDZ";
				frm.submit();	
			} else if(v==2) {//取总分平衡
				frm.submitFlag.value = "cibZFPH";
				frm.submit();
			} else if(v==3) {//取分分核对
				frm.submitFlag.value = "cibFFHD";
				frm.submit();	
			} else if(v==4) {//显示总分平衡
				frm.submitFlag.value = "cibGETZFPH";
				frm.submit();	
			} else if(v==5) {//显示分分核对
				frm.submitFlag.value = "cibGETFFHD";
				frm.submit();	
			}
		}
	}
	function cibpgTurnzfph(i) {
		frm.submitFlag.value = "cibGETZFPH";
		if(i == 0) {
			frm.cibpageIndexzfph.value = 1;
			frm.submit();
		} else if(i == 1) {		
			frm.cibpageIndexzfph.value = frm.cibpageIndexzfph.value * 1 + 1;	
			frm.submit();
		} else if(i == 2) {
			<%if(cibzfph!=null){ %> frm.cibpageIndexzfph.value = <%=cibzfph.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.cibpageIndexzfph.value = frm.cibpageIndexzfph.value - 1;
			frm.submit();
		}
	}
	function cibpgTurnffhd(i) {
		frm.submitFlag.value = "cibGETFFHD";
		if(i == 0) {
			frm.cibpageIndexffhd.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.cibpageIndexffhd.value = frm.cibpageIndexffhd.value * 1 + 1;	
			frm.submit();
		} else if(i == 2) {
			<%if(cibffhd!=null){ %> frm.cibpageIndexffhd.value = <%=cibffhd.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.cibpageIndexffhd.value = frm.cibpageIndexffhd.value - 1;
			frm.submit();
		}
	}
	function cibpgJumpChkzfph() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxzfph.value) || frm.cibpageJumpIdxzfph.value < 1 || frm.cibpageJumpIdxzfph.value > <%=cibzfph.getPageCount()%>) && (isNaN(frm.cibpageSizezfph.value) || frm.cibpageSizezfph.value < 1 )) {
				alert("请输入1 - <%=cibzfph.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "cibGETZFPH";
				frm.cibpageIndexzfph.value = frm.cibpageJumpIdxzfph.value;
				frm.submit();
			}
		}
	}
	function cibpgJumpChkffhd() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxffhd.value) || frm.cibpageJumpIdxffhd.value < 1 || frm.cibpageJumpIdxffhd.value > <%=cibffhd.getPageCount()%>) && (isNaN(frm.cibpageSizeffhd.value) || frm.cibpageSizeffhd.value < 1 )) {
				alert("请输入1 - <%=cibffhd.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "cibGETFFHD";
				frm.cibpageIndexffhd.value = frm.cibpageJumpIdxffhd.value;
				frm.submit();
			}
		}
	}
	function CompareDate(d1,d2) {
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>