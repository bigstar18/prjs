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
	ObjSet hxbqsxx=null;//清算信息
	Vector hxbgetqsxx =null;//总分平衡
	String result = "---";
	if("hxbQS".equals(request.getParameter("submitFlag"))) {//发送清算信息
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.hxSentQS(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("hxbDZ".equals(request.getParameter("submitFlag"))) {//发送对账信息
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.hxSentDZ(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";银行ID"+bankID+"时间："+new java.util.Date());
		dao.log(lv);
	}else if("hxbGetQS".equals(request.getParameter("submitFlag"))) {//查看清算信息
		try{
			hxbgetqsxx = dao.getHXQSMsg(bankID,null,Tool.strToDate(s_time));
		}catch(Exception e){
			
		}
		int size = Tool.strToInt((String)request.getParameter("cibpageSizezfph"));
		if(size>0){
			cibpageSizezfph = size;
		}
		cibpageIndexzfph = Tool.strToInt((String)request.getParameter("cibpageIndexzfph"));
		if(cibpageIndexzfph <= 0) {
			cibpageIndexzfph = 1;
		}
		int maxpage = hxbgetqsxx.size()%cibpageSizezfph==0 ? hxbgetqsxx.size()/cibpageSizezfph : hxbgetqsxx.size()/cibpageSizezfph+1;
		if(cibpageIndexzfph>maxpage){
			cibpageIndexzfph=maxpage;
		}
		if(hxbgetqsxx.size()<=0){
			result = "未查询到银行["+bankID+"]总分数据";
		}else{
			result = "银行["+bankID+"]总分数据如下";
		}
	}
	hxbqsxx = ObjSet.getInstance(hxbgetqsxx, cibpageSizezfph, cibpageIndexzfph);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>华夏银行清算页面信息</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%">
			<legend>华夏银行清算</legend>
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
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(1);" value="发送清算信息">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(2);" value="发送对账信息">
						&nbsp;&nbsp;&nbsp;
						<!-- 
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(3);" value="查看清算信息">
						&nbsp;&nbsp;&nbsp;
						-->
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>	  
		<%
		if(hxbqsxx!=null && hxbqsxx.size()!=0) {
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
		</table>
		<%
		}
		%>
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
				frm.submitFlag.value = "hxbQS";
				frm.submit();	
			} else if(v==2) {//取总分平衡
				frm.submitFlag.value = "hxbDZ";
				frm.submit();
			} else if(v==3) {//取分分核对
				frm.submitFlag.value = "hzxGetQS";
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
			<%if(hxbqsxx!=null){ %> frm.cibpageIndexzfph.value = <%=hxbqsxx.getPageCount()%><%}%>;
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
			<%if(hxbqsxx!=null){ %> frm.cibpageIndexffhd.value = <%=hxbqsxx.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.cibpageIndexffhd.value = frm.cibpageIndexffhd.value - 1;
			frm.submit();
		}
	}
	function cibpgJumpChkzfph() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxzfph.value) || frm.cibpageJumpIdxzfph.value < 1 || frm.cibpageJumpIdxzfph.value > <%=hxbqsxx.getPageCount()%>) && (isNaN(frm.cibpageSizezfph.value) || frm.cibpageSizezfph.value < 1 )) {
				alert("请输入1 - <%=hxbqsxx.getPageCount()%>间的数字！");			
			} else {
				frm.submitFlag.value = "cibGETZFPH";
				frm.cibpageIndexzfph.value = frm.cibpageJumpIdxzfph.value;
				frm.submit();
			}
		}
	}
	function cibpgJumpChkffhd() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxffhd.value) || frm.cibpageJumpIdxffhd.value < 1 || frm.cibpageJumpIdxffhd.value > <%=hxbqsxx.getPageCount()%>) && (isNaN(frm.cibpageSizeffhd.value) || frm.cibpageSizeffhd.value < 1 )) {
				alert("请输入1 - <%=hxbqsxx.getPageCount()%>间的数字！");			
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