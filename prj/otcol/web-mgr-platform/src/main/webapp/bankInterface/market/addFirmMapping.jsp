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
			result = "��ȡ������ʧ��";
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
			ReturnValue rv = cp.doWork(req);//ִ�����
			if(rv == null){
				result = "����ʧ��";
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
    <title>���ƽ̨�˺źͽ����̶�Ӧ��ϵ</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="POST">
		<fieldset width="100%">
		<legend>��Ӧ��ϵά��</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
				<tr height="35">
					<td width="45%" align="right">ƽ̨�˺�:&nbsp;&nbsp;</td>
					<td align="left">
						<input id="firmID" name="firmID" class="text" type="text" value=""/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">����ϵͳ:&nbsp;&nbsp;</td>
					<td align="left">
						<select id="systemID" name="systemID">
							<option value="-1">��ѡ��</option>
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
					<td align="right">�����̴���:&nbsp;&nbsp;</td>
					<td align="left">
						<input id="sysFirmID" name="sysFirmID" class="text" type="text" value=""/>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan="2">
						<button type="button" class="smlbtn" onclick="addMapping();" name="add" id="add">ȷ��</button>&nbsp;
						<button type="button" class="smlbtn" onclick="reSet();">����</button>&nbsp;
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
			alert("������ƽ̨�˺�");
			frm.firmID.focus();
			return;
		}else if(frm.systemID.value == -1){
			alert("��ѡ����ϵͳ");
			frm.systemID.focus();
			return;
		}else if(frm.sysFirmID.value == ""){
			alert("�����뽻���̴���");
			frm.sysFirmID.focus();
			return;
		}
		var rmsg = window.confirm("�����Ϣ���£�\nƽ̨�˺ţ�" + frm.firmID.value + "\n����ϵͳ��" + frm.systemID.value + "\n�����̴��룺" + frm.sysFirmID.value + "\n\nȷ����ӣ�");
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