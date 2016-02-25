<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	ReturnValue result = new ReturnValue();
	PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	Date now = new Date();
	RequestMsg req = new RequestMsg();
	req.setBankID("");
	req.setMethodName("checkCapitalInfoPT");
	req.setParams(new Object[]{now});
	try {
		result = cp.doWork(req);
	} catch (RemoteException e) {
		Tool.log("调用平台异常"+result);
		Tool.getExceptionTrace(e);
		result.remark="系统异常";
		result.result=-1;
	}
	//检查账面不平的流水
	Vector<CapitalValueMoney> vecError = null;
	//检查平台到银行有，平台到交易系统没有的流水
	Vector<CapitalValue> vecSysNo = null;
	//检查平台到交易系统有，平台到银行没有的流水
	Vector<CapitalValue> vecPlatNo = null;
	if(result != null && result.result == 0){
		vecError = (Vector<CapitalValueMoney>) result.msg[0];
		vecSysNo = (Vector<CapitalValue>) result.msg[1];
		vecPlatNo = (Vector<CapitalValue>) result.msg[2];
	}else{
		vecError = new Vector<CapitalValueMoney>();
		vecSysNo = new Vector<CapitalValue>();
		vecPlatNo = new Vector<CapitalValue>();
		if(result == null){
			result = new ReturnValue();
			result.remark = "核对流水，系统异常";
		}else if(result.remark == null || "".equals(result.remark)){
			result.remark = "核对流水操作失败";
		}
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>对账结果</title>
  </head>
  
  <body>
	<fieldset width="100%"  style="margin-left:10px;">
		<legend>流水核对结果</legend>
		<table border="0" cellspacing="0" cellpadding="0" width="1040px" align="center">
			<tr>
				<td>
					<font color="red">
					<%
						if(result.result == 0 && vecError.size()==0 && vecSysNo.size()==0 && vecPlatNo.size()==0){
							out.println("核对流水操作成功，资金流水核对成功");
						}else{
							out.println("流水核对失败[" + result.remark + "]。不平流水如下列表：");
						}
					%>
					</font>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
		<fieldset width="100%" style="margin-left:10px;">
			<legend>账面不平流水</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">发生时间</td>
				<td class="panel_tHead_MB">平台账号</td>
				<td class="panel_tHead_MB">交易系统</td>
				<td class="panel_tHead_MB">银行</td>
				<td class="panel_tHead_MB">到系统流水号</td>
				<td class="panel_tHead_MB">到银行流水号</td>
				<td class="panel_tHead_MB">到系统转账类型</td>
				<td class="panel_tHead_MB">到银行转账类型</td>
				<td class="panel_tHead_MB">到系统发生额</td>
				<td class="panel_tHead_MB">到银行发生额</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<%for(CapitalValueMoney cvm : vecError){%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=cvm.createDate%>&nbsp;</td>
					<td class="underLine"><%=cvm.firmID%>&nbsp;</td>
					<td class="underLine"><%=(systemsMap.get(cvm.systemID) == null ? "未知交易系统" : systemsMap.get(cvm.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cvm.bankID) == null ? "--" : banksMap.get(cvm.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cvm.sysActionID%>&nbsp;</td>
					<td class="underLine"><%=cvm.platformActionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cvm.sysType == 0){
							out.println("入金");
						}else if(cvm.sysType == 1)	{
							out.println("出金");
						}else if(cvm.sysType == 2){
							out.println("出入金手续费");
						}else if(cvm.sysType == 3){
							out.println("其他资金划转");
						}
					%>&nbsp;
					</td>
					<td class="underLine">
					<%
						if(cvm.platformType == 0){
							out.println("入金");
						}else if(cvm.platformType == 1)	{
							out.println("出金");
						}else if(cvm.platformType == 2){
							out.println("出入金手续费");
						}else if(cvm.platformType == 3){
							out.println("其他资金划转");
						}
					%>&nbsp;
					</td>
					<td class="underLine"><%=cvm.sysMoney%>&nbsp;</td>
					<td class="underLine"><%=cvm.money%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>
			<%}%>
	  	</tBody>
	  	<tFoot>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</fieldset>
	<br>
	<fieldset width="100%"  style="margin-left:10px;">
			<legend>平台到银行无流水</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">发生时间</td>
				<td class="panel_tHead_MB">平台账号</td>
				<td class="panel_tHead_MB">交易商</td>
				<td class="panel_tHead_MB">交易系统</td>
				<td class="panel_tHead_MB">银行</td>
				<td class="panel_tHead_MB">平台流水号</td>
				<td class="panel_tHead_MB">系统流水号</td>
				<td class="panel_tHead_MB">转账类型</td>
				<td class="panel_tHead_MB">发生金额</td>
				<td class="panel_tHead_MB">流水状态</td>
				<td class="panel_tHead_MB">流水备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<%for(CapitalValue cv : vecPlatNo){%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=cv.createtime%>&nbsp;</td>
					<td class="underLine"><%=cv.firmID%>&nbsp;</td>
					<td class="underLine"><%=cv.sysFirmID%>&nbsp;</td>
					<td class="underLine"><%=(systemsMap.get(cv.systemID) == null ? "未知交易系统" : systemsMap.get(cv.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cv.bankID) == null ? "--" : banksMap.get(cv.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cv.funID%>&nbsp;</td>
					<td class="underLine"><%=cv.actionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cv.type == 0){
							out.println("入金");
						}else if(cv.type == 1)	{
							out.println("出金");
						}else if(cv.type == 2){
							out.println("出入金手续费");
						}else if(cv.type == 3){
							out.println("其他资金划转");
						}
					%>&nbsp;
					</td>
					<td class="underLine"><%=cv.money%>&nbsp;</td>
					<td class="underLine"><%=cv.status%>&nbsp;
					<%
						if(cv.status == 0){
							out.println("成功");
						}else if(cv.status == 1){
							out.println("<font color=red>失败</font>");
						}else if(cv.status == 2){
							out.println("处理中");
						}else if(cv.status == 3 || cv.status == 13){
							out.println("待审核");
						}else if(cv.status == 4){
							out.println("待二次审核");
						}
					%>
					</td>
					<td class="underLine"><%=cv.note%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
			<%}%>
	  	</tBody>
	  	<tFoot>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</fieldset>
	<br>
	<fieldset width="100%"  style="margin-left:10px;">
			<legend>平台到系统无流水</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">发生时间</td>
				<td class="panel_tHead_MB">平台账号</td>
				<td class="panel_tHead_MB">交易商</td>
				<td class="panel_tHead_MB">交易系统</td>
				<td class="panel_tHead_MB">银行</td>
				<td class="panel_tHead_MB">平台流水号</td>
				<td class="panel_tHead_MB">系统流水号</td>
				<td class="panel_tHead_MB">转账类型</td>
				<td class="panel_tHead_MB">发生金额</td>
				<td class="panel_tHead_MB">流水状态</td>
				<td class="panel_tHead_MB">流水备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<%for(CapitalValue cv : vecSysNo){%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=cv.createtime%>&nbsp;</td>
					<td class="underLine"><%=cv.firmID%>&nbsp;</td>
					<td class="underLine"><%=cv.sysFirmID%>&nbsp;</td>
					<td class="underLine"><%=(systemsMap.get(cv.systemID) == null ? "未知交易系统" : systemsMap.get(cv.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cv.bankID) == null ? "--" : banksMap.get(cv.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cv.funID%>&nbsp;</td>
					<td class="underLine"><%=cv.actionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cv.type == 0){
							out.println("入金");
						}else if(cv.type == 1)	{
							out.println("出金");
						}else if(cv.type == 2){
							out.println("出入金手续费");
						}else if(cv.type == 3){
							out.println("其他资金划转");
						}
					%>&nbsp;
					</td>
					<td class="underLine"><%=cv.money%>&nbsp;</td>
					<td class="underLine"><%=cv.status%>&nbsp;
					<%
						if(cv.status == 0){
							out.println("成功");
						}else if(cv.status == 1){
							out.println("<font color=red>失败</font>");
						}else if(cv.status == 2){
							out.println("处理中");
						}else if(cv.status == 3 || cv.status == 13){
							out.println("待审核");
						}else if(cv.status == 4){
							out.println("待二次审核");
						}
					%>
					</td>
					<td class="underLine"><%=cv.note%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>
			<%}%>
	  	</tBody>
	  	<tFoot>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</fieldset>
  </body>
</html>
<script type="text/javascript">  
	
</SCRIPT>