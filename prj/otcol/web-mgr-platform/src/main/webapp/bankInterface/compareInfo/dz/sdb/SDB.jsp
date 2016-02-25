<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ include file="../globalDef.jsp"%>

<%
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	String bankID = Tool.delNull((String)request.getParameter("bankID"));//���б��
	String s_time = Tool.delNull((String)request.getParameter("s_time"));//��������
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	int sdbfileType = Tool.strToInt((String)request.getParameter("sdbfileType"));
	int sdbpageSizefirm = BANKPAGESIZE;//������ǩ��Լҳ������
	int sdbpageIndexfirm = 0;//������ǩ��Լҳ����
	int sdbpageSizeqsbp = BANKPAGESIZE;//���㲻ƽҳ������
	int sdbpageIndexqsbp = 0;//���㲻ƽҳ����
	int sdbpageSizeqssb = BANKPAGESIZE;//����ʧ��ҳ������
	int sdbpageIndexqssb = 0;//����ʧ��ҳ����
	ObjSet sdbfirm =null;//������ǩ��Լҳ����Ϣ
	ObjSet sdbqsbp =null;//�������㲻ƽҳ����Ϣ
	ObjSet sdbqssb =null;//��������ʧ��ҳ����Ϣ
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String result = "";
	Vector sdbfirmbank =null;//������ǩ��Լ�ȶԽ��
	Vector sdbqsbpbank =null;//���д������㲻ƽ����
	Vector sdbqssbbank =null;//���д�������ʧ������
	String submitFlag = request.getParameter("submitFlag");
	if("sdbsendQS".equals(submitFlag)){//�����չ��������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.sentMaketQS(Tool.strToDate(s_time),bankID);
		result = rv.remark;
		lv.setLogcontent(result);
		dao.log(lv);
	}else if("sdbqsResult".equals(submitFlag)){//�鿴������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.getBankFileStatus(Tool.strToDate(s_time),1,bankID);//�鿴�������ļ�
		result = rv.remark;
		lv.setLogcontent(result);
		dao.log(lv);
	}else if("sdbsavefirm".equals(submitFlag)) {//��ǩ��Լ��Ϣ
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.saveFirmKXH(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "������ϢΪ��";
			}else{
				result = rv.remark;
			}
		}
		lv.setLogcontent("ȡ����ǩ��Լ��Ϣ:"+result);
		dao.log(lv);
	}else if("sdbsaveqsbp".equals(submitFlag)){//��ȡ�������㲻ƽ����
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		ReturnValue rv = cp.getBatCustDz(Tool.strToDate(s_time),bankID);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "������ϢΪ��";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("ȡ�������㲻ƽ��Ϣ:"+result);
		dao.log(lv);
	}else if("sdbsaveqssb".equals(submitFlag)){//��ȡ��������ʧ������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.getFirmBalanceError(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "������ϢΪ��";
			}else{
				result = rv.remark;
			}
		}
		lv.setLogcontent("ȡ��������ʧ����Ϣ:"+result);
		dao.log(lv);
	}else if("sdbsavefiles".equals(submitFlag)){//��ȡ������������
		
	}else if("sdbgetDatafirm".equals(submitFlag)) {//ȡ���չ���еĽ�����ǩ��Լ����
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbfirmbank = dao.getFirmBank(bankID,Tool.strToDate(s_time));
		if(sdbfirmbank==null) {
			result = "ƥ��ǩԼ��Ϣ�쳣�����д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else if(sdbfirmbank.size() == 0) {
			result = "ǩԼ��Ϣƥ�䣬���д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizefirm"));
			if(size>0){
				sdbpageSizefirm = size;
			}
			sdbpageIndexfirm = Tool.strToInt((String)request.getParameter("sdbpageIndexfirm"));
			if(sdbpageIndexfirm <= 0) {
				sdbpageIndexfirm = 1;
			}
			int maxpage = sdbfirmbank.size()%sdbpageSizefirm==0 ? sdbfirmbank.size()/sdbpageSizefirm : sdbfirmbank.size()/sdbpageSizefirm+1;
			if(sdbpageIndexfirm>maxpage){
				sdbpageIndexfirm=maxpage;
			}
			result = "������Ϣ��ƥ�䣺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("�����к��г���ǩ��Լ��Ϣ:"+result);
		dao.log(lv);
	}else if("sdbgetDataqsbp".equals(submitFlag)){//�鿴�������㲻ƽ����
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbqsbpbank = dao.getBatCustDz(Tool.strToDate(s_time),bankID);
		if(sdbqsbpbank==null) {
			result = "��ѯ���ж��˲�ƽ��Ϣ�쳣�����д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else if(sdbqsbpbank.size() == 0) {
			result = "���ж��˳ɹ������д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizeqsbp"));
			if(size>0){
				sdbpageSizeqsbp = size;
			}
			sdbpageIndexqsbp = Tool.strToInt((String)request.getParameter("sdbpageIndexqsbp"));
			if(sdbpageIndexqsbp <= 0) {
				sdbpageIndexqsbp = 1;
			}
			int maxpage = sdbqsbpbank.size()%sdbpageSizeqsbp==0 ? sdbqsbpbank.size()/sdbpageSizeqsbp : sdbqsbpbank.size()/sdbpageSizeqsbp+1;
			if(sdbpageIndexqsbp>maxpage){
				sdbpageIndexqsbp=maxpage;
			}
			result = "���ж��˲��ɹ���"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("�鿴���㲻ƽ��Ϣ:"+result);
		dao.log(lv);
	}else if("sdbgetDataqssb".equals(submitFlag)){//�鿴��������ʧ������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setIp(computerIP);
		lv.setLogdate(new Date());
		sdbqssbbank = dao.getFirmBalanceError(null,bankID,Tool.strToDate(s_time));
		if(sdbqssbbank==null) {
			result = "��ѯ���ж��˲�ƽ��Ϣ�쳣�����д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else if(sdbqssbbank.size() == 0) {
			result = "���ж��˳ɹ������д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else {
			int size = Tool.strToInt((String)request.getParameter("sdbpageSizeqssb"));
			if(size>0){
				sdbpageSizeqssb = size;
			}
			sdbpageIndexqssb = Tool.strToInt((String)request.getParameter("sdbpageIndexqssb"));
			if(sdbpageIndexqssb <= 0) {
				sdbpageIndexqssb = 1;
			}
			int maxpage = sdbqssbbank.size()%sdbpageSizeqssb==0 ? sdbqssbbank.size()/sdbpageSizeqssb : sdbqssbbank.size()/sdbpageSizeqssb+1;
			if(sdbpageIndexqssb>maxpage){
				sdbpageIndexqssb=maxpage;
			}
			result = "���ж��˲��ɹ���"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		}
		lv.setLogcontent("�鿴����ʧ����Ϣ:"+result);
		dao.log(lv);
	}
sdbfirm = ObjSet.getInstance(sdbfirmbank, sdbpageSizefirm, sdbpageIndexfirm);
sdbqsbp = ObjSet.getInstance(sdbqsbpbank, sdbpageSizeqsbp, sdbpageIndexqsbp);
sdbqssb = ObjSet.getInstance(sdbqssbbank, sdbpageSizeqssb, sdbpageIndexqssb);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>�չ����������ҳ��</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>�չ��������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
					<td align="right">���У�&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++){
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
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<!--
				<tr height="35">
					<td align="right">�ļ����ͣ�&nbsp;</td>
					<td align="left">
						<select name="sdbfileType" class="normal" style="width:120px">
							<OPTION value="-1" <%=sdbfileType==-1 ? "selected" : "" %> >��ѡ��</OPTION>
							<OPTION value="1" <%=sdbfileType==1 ? "selected" : "" %> >�������ļ�</OPTION>
							<OPTION value="4" <%=sdbfileType==4 ? "selected" : "" %> >ת����ˮ�ļ�</OPTION>
							<OPTION value="5" <%=sdbfileType==5 ? "selected" : "" %> >��Ա�������ļ�</OPTION>
						</select>
					</td>
				</tr>
				-->
				<tr height="35">
					<td align="right">ִ�н����&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(1);" value="������������">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(2);" value="�鿴������">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(3);" value="ȡ�ý������">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(7);" value="�ȶ�ǩ��Լ">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(8);" value="�鿴����ʧ��">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="sdbbuttonclick(9);" value="�鿴���㲻ƽ">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(sdbfirm!=null && sdbfirm.size()!=0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class="panel_tHead_MB">ǩ��Լ</td>
				<td class="panel_tHead_MB">ǩ��Լ����</td>
				<td class="panel_tHead_MB">��ϸ��Ϣ</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbfirm.getCurNum();i++)
			{
				FirmOpenCloseBank ocb = (FirmOpenCloseBank)sdbfirm.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=ocb.firmID%>&nbsp;</td>
					<td class="underLine">
					<%
					if(ocb.type==1){
						out.print("ǩԼ");
					}else if(ocb.type==2){
						out.print("��Լ");
					}
					%>&nbsp;
					</td>
					<td class="underLine"><%=Tool.fmtDate(ocb.tradeDate)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.note)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			  }
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4" align=right>
				��<%=sdbpageIndexfirm%>/<%=sdbfirm.getPageCount()%>ҳ &nbsp;&nbsp;��<%=sdbfirm.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="sdbpageSizefirm" class="text" type="text" style="width:25px;" value="<%=sdbpageSizefirm%>" onkeydown="return sdbpgJumpChkfirm()">�� &nbsp;&nbsp;
				<%
				if(sdbpageIndexfirm != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnfirm(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnfirm(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexfirm != sdbfirm.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnfirm(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnfirm(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxfirm" onkeydown="return sdbpgJumpChkfirm()">ҳ

				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="4"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
		
	</table>
	<%}%>
	<%
		if(sdbqsbp!=null && sdbqsbp.size()!=0){
	%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class="panel_tHead_MB">���˺�</td>
				<td class="panel_tHead_MB">�˻���</td>
				<td class="panel_tHead_MB">���п������</td>
				<td class="panel_tHead_MB">���ж������</td>
				<td class="panel_tHead_MB">�г��������</td>
				<td class="panel_tHead_MB">�г��������</td>
				<td class="panel_tHead_MB">�����������</td>
				<td class="panel_tHead_MB">�����������</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbqsbp.getCurNum();i++)
			{
				BatCustDzFailChild ocb = (BatCustDzFailChild)sdbqsbp.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.ThirdCustId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.CustAcctId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(ocb.CustName)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BankBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BankFrozen)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.MaketBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.MaketFrozen)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.BalanceError)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(ocb.FrozenError)%>&nbsp;</td>
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
				��<%=sdbpageIndexqsbp%>/<%=sdbqsbp.getPageCount()%>ҳ &nbsp;&nbsp;��<%=sdbqsbp.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="sdbpageSizeqsbp" class="text" type="text" style="width:25px;" value="<%=sdbpageSizeqsbp%>" onkeydown="return sdbpgJumpChkqsbp()">�� &nbsp;&nbsp;
				<%
				if(sdbpageIndexqsbp != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqsbp(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqsbp(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexqsbp != sdbqsbp.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqsbp(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqsbp(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxqsbp" onkeydown="return sdbpgJumpChkqsbp()">ҳ

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
	<%}%>
	<%
		if(sdbqssb!=null && sdbqssb.size()!=0){
	%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class="panel_tHead_MB">�����ܶ�����</td>
				<td class="panel_tHead_MB">�����ܽⶳ���</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">�򷽻���</td>
				<td class="panel_tHead_MB">ӯ�����</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">������</td>
				<td class="panel_tHead_MB">�г��ʽ�</td>
				<td class="panel_tHead_MB">�г�����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<sdbqssb.getCurNum();i++)
			{
				BatFailResultChild bfr = (BatFailResultChild)sdbqssb.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(bfr.ThirdCustId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.FreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.UnfreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.AddTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.CutTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.ProfitAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.LossAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.TranHandFee)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewFreezeAmount)%>&nbsp;</td>
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
				��<%=sdbpageIndexqssb%>/<%=sdbqssb.getPageCount()%>ҳ &nbsp;&nbsp;��<%=sdbqssb.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="sdbpageSizeqssb" class="text" type="text" style="width:25px;" value="<%=sdbpageSizeqssb%>" onkeydown="return sdbpgJumpChkqssb()">�� &nbsp;&nbsp;
				<%
				if(sdbpageIndexqssb != 1)
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqssb(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqssb(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(sdbpageIndexqssb != sdbqssb.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="sdbpgTurnqssb(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="sdbpgTurnqssb(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="sdbpageJumpIdxqssb" onkeydown="return sdbpgJumpChkqssb()">ҳ

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
	<%}%>
	<input name=sdbpageIndexfirm type=hidden value="<%=sdbpageIndexfirm%>">
	<input name=sdbpageIndexqsbp type=hidden value="<%=sdbpageIndexqsbp%>">
	<input name=sdbpageIndexqssb type=hidden value="<%=sdbpageIndexqssb%>">
	</form>
  </body>
</html>
<script type="text/javascript">  
	function sdbbuttonclick(v) {
		frm.sdbpageIndexfirm.value = 1;
		if(v == 1){//������������
			frm.submitFlag.value = "sdbsendQS";
			frm.submit();
		}else {
			var now1 = new Date()+"";
			if (frm.s_time.value == ""){
				alert("��ѡ��������ڣ�");
			}else if(CompareDate(now1,(frm.s_time.value))){
				alert("ѡ��Ķ������ڲ��Ϸ�!");
				frm.s_time.value="";
			}else if(v == 2){//�鿴������
				/*if(frm.sdbfileType.value==-1){
					alert("��ѡ���ļ�����");
				}else{
					frm.submitFlag.value = "sdbqsResult";
					frm.submit();
				}*/
				frm.submitFlag.value = "sdbqsResult";
				frm.submit();
			}else if(v == 3){//ȡ�����н�����ǩ��Լ����
				frm.submitFlag.value = "sdbsavefirm";
				frm.submit();
			}else if(v == 4){//ȡ���������㲻ƽ����
				frm.submitFlag.value = "sdbsaveqsbp";
				frm.submit();
			}else if(v == 5){//ȡ����������ʧ������
				frm.submitFlag.value = "sdbsaveqssb";
				frm.submit();
			}else if(v == 6){//ȡ�����з�����������
				frm.submitFlag.value = "sdbsavefiles";
				frm.submit();
			}else if(v == 7){//�ȶ�ǩ��Լ���
				frm.submitFlag.value = "sdbgetDatafirm";
				frm.submit();
			}else if(v == 8){//�鿴���㲻ƽ����
				frm.submitFlag.value = "sdbgetDataqsbp";
				frm.submit();
			}else if(v == 9){//�鿴����ʧ������
				frm.submitFlag.value = "sdbgetDataqssb";
				frm.submit();
			}
		}
	}
	function sdbpgTurnfirm(i) {
		frm.submitFlag.value = "sdbgetDatafirm";
		if(i == 0) {
			frm.sdbpageIndexfirm.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexfirm.value = frm.sdbpageIndexfirm.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbfirm!=null){ %> frm.sdbpageIndexfirm.value = <%=sdbfirm.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexfirm.value = frm.sdbpageIndexfirm.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkfirm() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxfirm.value) || frm.sdbpageJumpIdxfirm.value < 1 || frm.sdbpageJumpIdxfirm.value > <%=sdbfirm.getPageCount()%>) && (isNaN(frm.sdbpageSizefirm.value) || frm.sdbpageSizefirm.value < 1 )) {
				alert("������1 - <%=sdbfirm.getPageCount()%>������֣�");			
			} else {
				frm.submitFlag.value = "sdbgetDatafirm";
				frm.sdbpageIndexfirm.value = frm.sdbpageJumpIdxfirm.value;
				frm.submit();
			}
		}	
	}
	function sdbpgTurnqsbp(i) {
		frm.submitFlag.value = "sdbgetDataqsbp";
		if(i == 0) {
			frm.sdbpageIndexqsbp.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexqsbp.value = frm.sdbpageIndexqsbp.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbqsbp!=null){ %> frm.sdbpageIndexqsbp.value = <%=sdbqsbp.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexqsbp.value = frm.sdbpageIndexqsbp.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkqsbp() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxqsbp.value) || frm.sdbpageJumpIdxqsbp.value < 1 || frm.sdbpageJumpIdxqsbp.value > <%=sdbqsbp.getPageCount()%>) && (isNaN(frm.sdbpageSizeqsbp.value) || frm.sdbpageSizeqsbp.value < 1 )) {
				alert("������1 - <%=sdbqsbp.getPageCount()%>������֣�");			
			} else {
				frm.submitFlag.value = "sdbgetDataqsbp";
				frm.sdbpageIndexqsbp.value = frm.sdbpageJumpIdxqsbp.value;
				frm.submit();
			}
		}	
	}
	function sdbpgTurnqssb(i) {
		frm.submitFlag.value = "sdbgetDataqssb";
		if(i == 0) {
			frm.sdbpageIndexqssb.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.sdbpageIndexqssb.value = frm.sdbpageIndexqssb.value * 1 + 1;
			frm.submit();
		} else if(i == 2) {
			<%if(sdbqssb!=null){ %> frm.sdbpageIndexqssb.value = <%=sdbqssb.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.sdbpageIndexqssb.value = frm.sdbpageIndexqssb.value - 1;
			frm.submit();
		}
	}
	function sdbpgJumpChkqssb() {
		if(event.keyCode == 13) {
			if((isNaN(frm.sdbpageJumpIdxqssb.value) || frm.sdbpageJumpIdxqssb.value < 1 || frm.sdbpageJumpIdxqssb.value > <%=sdbqssb.getPageCount()%>) && (isNaN(frm.sdbpageSizeqssb.value) || frm.sdbpageSizeqssb.value < 1 )) {
				alert("������1 - <%=sdbqssb.getPageCount()%>������֣�");			
			} else {
				frm.submitFlag.value = "sdbgetDataqssb";
				frm.sdbpageIndexqssb.value = frm.sdbpageJumpIdxqssb.value;
				frm.submit();
			}
		}	
	}
	function CompareDate(d1,d2) {
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>