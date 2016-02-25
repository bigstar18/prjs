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
		Tool.log("����ƽ̨�쳣"+result);
		Tool.getExceptionTrace(e);
		result.remark="ϵͳ�쳣";
		result.result=-1;
	}
	//������治ƽ����ˮ
	Vector<CapitalValueMoney> vecError = null;
	//���ƽ̨�������У�ƽ̨������ϵͳû�е���ˮ
	Vector<CapitalValue> vecSysNo = null;
	//���ƽ̨������ϵͳ�У�ƽ̨������û�е���ˮ
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
			result.remark = "�˶���ˮ��ϵͳ�쳣";
		}else if(result.remark == null || "".equals(result.remark)){
			result.remark = "�˶���ˮ����ʧ��";
		}
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>���˽��</title>
  </head>
  
  <body>
	<fieldset width="100%"  style="margin-left:10px;">
		<legend>��ˮ�˶Խ��</legend>
		<table border="0" cellspacing="0" cellpadding="0" width="1040px" align="center">
			<tr>
				<td>
					<font color="red">
					<%
						if(result.result == 0 && vecError.size()==0 && vecSysNo.size()==0 && vecPlatNo.size()==0){
							out.println("�˶���ˮ�����ɹ����ʽ���ˮ�˶Գɹ�");
						}else{
							out.println("��ˮ�˶�ʧ��[" + result.remark + "]����ƽ��ˮ�����б�");
						}
					%>
					</font>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
		<fieldset width="100%" style="margin-left:10px;">
			<legend>���治ƽ��ˮ</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">����ʱ��</td>
				<td class="panel_tHead_MB">ƽ̨�˺�</td>
				<td class="panel_tHead_MB">����ϵͳ</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">��ϵͳ��ˮ��</td>
				<td class="panel_tHead_MB">��������ˮ��</td>
				<td class="panel_tHead_MB">��ϵͳת������</td>
				<td class="panel_tHead_MB">������ת������</td>
				<td class="panel_tHead_MB">��ϵͳ������</td>
				<td class="panel_tHead_MB">�����з�����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
			<%for(CapitalValueMoney cvm : vecError){%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=cvm.createDate%>&nbsp;</td>
					<td class="underLine"><%=cvm.firmID%>&nbsp;</td>
					<td class="underLine"><%=(systemsMap.get(cvm.systemID) == null ? "δ֪����ϵͳ" : systemsMap.get(cvm.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cvm.bankID) == null ? "--" : banksMap.get(cvm.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cvm.sysActionID%>&nbsp;</td>
					<td class="underLine"><%=cvm.platformActionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cvm.sysType == 0){
							out.println("���");
						}else if(cvm.sysType == 1)	{
							out.println("����");
						}else if(cvm.sysType == 2){
							out.println("�����������");
						}else if(cvm.sysType == 3){
							out.println("�����ʽ�ת");
						}
					%>&nbsp;
					</td>
					<td class="underLine">
					<%
						if(cvm.platformType == 0){
							out.println("���");
						}else if(cvm.platformType == 1)	{
							out.println("����");
						}else if(cvm.platformType == 2){
							out.println("�����������");
						}else if(cvm.platformType == 3){
							out.println("�����ʽ�ת");
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
			<legend>ƽ̨����������ˮ</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">����ʱ��</td>
				<td class="panel_tHead_MB">ƽ̨�˺�</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">����ϵͳ</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">ƽ̨��ˮ��</td>
				<td class="panel_tHead_MB">ϵͳ��ˮ��</td>
				<td class="panel_tHead_MB">ת������</td>
				<td class="panel_tHead_MB">�������</td>
				<td class="panel_tHead_MB">��ˮ״̬</td>
				<td class="panel_tHead_MB">��ˮ��ע</td>
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
					<td class="underLine"><%=(systemsMap.get(cv.systemID) == null ? "δ֪����ϵͳ" : systemsMap.get(cv.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cv.bankID) == null ? "--" : banksMap.get(cv.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cv.funID%>&nbsp;</td>
					<td class="underLine"><%=cv.actionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cv.type == 0){
							out.println("���");
						}else if(cv.type == 1)	{
							out.println("����");
						}else if(cv.type == 2){
							out.println("�����������");
						}else if(cv.type == 3){
							out.println("�����ʽ�ת");
						}
					%>&nbsp;
					</td>
					<td class="underLine"><%=cv.money%>&nbsp;</td>
					<td class="underLine"><%=cv.status%>&nbsp;
					<%
						if(cv.status == 0){
							out.println("�ɹ�");
						}else if(cv.status == 1){
							out.println("<font color=red>ʧ��</font>");
						}else if(cv.status == 2){
							out.println("������");
						}else if(cv.status == 3 || cv.status == 13){
							out.println("�����");
						}else if(cv.status == 4){
							out.println("���������");
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
			<legend>ƽ̨��ϵͳ����ˮ</legend>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1040px">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">����ʱ��</td>
				<td class="panel_tHead_MB">ƽ̨�˺�</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">����ϵͳ</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">ƽ̨��ˮ��</td>
				<td class="panel_tHead_MB">ϵͳ��ˮ��</td>
				<td class="panel_tHead_MB">ת������</td>
				<td class="panel_tHead_MB">�������</td>
				<td class="panel_tHead_MB">��ˮ״̬</td>
				<td class="panel_tHead_MB">��ˮ��ע</td>
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
					<td class="underLine"><%=(systemsMap.get(cv.systemID) == null ? "δ֪����ϵͳ" : systemsMap.get(cv.systemID).systemName)%>&nbsp;</td>
					<td class="underLine"><%=(banksMap.get(cv.bankID) == null ? "--" : banksMap.get(cv.bankID).bankName)%>&nbsp;</td>
					<td class="underLine"><%=cv.funID%>&nbsp;</td>
					<td class="underLine"><%=cv.actionID%>&nbsp;</td>
					<td class="underLine">
					<%
						if(cv.type == 0){
							out.println("���");
						}else if(cv.type == 1)	{
							out.println("����");
						}else if(cv.type == 2){
							out.println("�����������");
						}else if(cv.type == 3){
							out.println("�����ʽ�ת");
						}
					%>&nbsp;
					</td>
					<td class="underLine"><%=cv.money%>&nbsp;</td>
					<td class="underLine"><%=cv.status%>&nbsp;
					<%
						if(cv.status == 0){
							out.println("�ɹ�");
						}else if(cv.status == 1){
							out.println("<font color=red>ʧ��</font>");
						}else if(cv.status == 2){
							out.println("������");
						}else if(cv.status == 3 || cv.status == 13){
							out.println("�����");
						}else if(cv.status == 4){
							out.println("���������");
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