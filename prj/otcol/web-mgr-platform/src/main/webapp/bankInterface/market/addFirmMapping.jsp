<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	String result = "";
	String firmID = request.getParameter("firmID");
	String systemID = request.getParameter("systemID");
	String sysFirmID = request.getParameter("sysFirmID");
	if( "do".equals(request.getParameter("doSub"))){
		PlatformProcessorRMI cp = null;
		try{
			cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(cp == null){
			result = "获取处理器失败";
		}else{
			FirmID2SysFirmID f2f = new FirmID2SysFirmID();
			f2f.firmID = firmID;
			f2f.sysFirmID = sysFirmID;
			f2f.systemID = systemID;
			f2f.bankID = "";
			RequestMsg req = new RequestMsg();
			req.setBankID("");
			req.setMethodName("addFirmMapping");
			req.setParams(new Object[]{f2f});
			ReturnValue rv = cp.doWork(req);//执行添加
			if(rv == null){
				result = "操作失败";
			}else{
				result = rv.remark;
			}
		}
		%><script>alert("<%=result%>")</script><%
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>添加平台账号和交易商对应关系</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="POST">
		<fieldset width="100%">
		<legend>对应关系维护</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
				<tr height="35">
					<td width="45%" align="right">平台账号:&nbsp;&nbsp;</td>
					<td align="left">
						<input id="firmID" name="firmID" class="text" type="text" value=""/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">交易系统:&nbsp;&nbsp;</td>
					<td align="left">
						<select id="systemID" name="systemID">
							<option value="-1">请选择</option>
							<%
								for(SystemMessage sm : vecSystemList){
							%>
							<option value="<%=sm.systemID%>"><%=sm.systemName%></option>
							<%
								}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">交易商代码:&nbsp;&nbsp;</td>
					<td align="left">
						<input id="sysFirmID" name="sysFirmID" class="text" type="text" value=""/>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan="2">
						<button type="button" class="smlbtn" onclick="addMapping();" name="add" id="add">确定</button>&nbsp;
						<button type="button" class="smlbtn" onclick="reSet();">重置</button>&nbsp;
						<input type="hidden" name="doSub" id="doSub" value=""/>
					</td>
				</tr>
          </table>
		</fieldset>
    </form>
  </body>
</html>

<script type="text/javascript"> 
	function addMapping(){
		if(frm.firmID.value == ""){
			alert("请输入平台账号");
			frm.firmID.focus();
			return;
		}else if(frm.systemID.value == -1){
			alert("请选择交易系统");
			frm.systemID.focus();
			return;
		}else if(frm.sysFirmID.value == ""){
			alert("请输入交易商代码");
			frm.sysFirmID.focus();
			return;
		}
		var rmsg = window.confirm("添加信息如下：\n平台账号：" + frm.firmID.value + "\n交易系统：" + frm.systemID.value + "\n交易商代码：" + frm.sysFirmID.value + "\n\n确定添加？");
		if(rmsg){
			frm.doSub.value = "do";
			frm.add.disabled = 'disabled';
			frm.submit();
		}else{
			return;
		}
	}
	function reSet(){
		frm.firmID.value = "";
		frm.systemID.value = -1;
		frm.sysFirmID.value = "";
	}
</script>