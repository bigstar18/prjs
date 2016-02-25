<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
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
	String result = "";//��ʾ���
	Vector icbczfphList = null;//�ܷ�ƽ��
	Vector icbcffhdList = null;//�ַֺ˶�
	ObjSet icbcffhd = null;//�ַֺ˶�����
	ObjSet icbczfph = null;//�ܷ�ƽ����ʾ����
	int icbcpageSizezf = BANKPAGESIZE;//�ܷ�ÿҳ��С
	int icbcpageSizeff = BANKPAGESIZE;//�ַ�ÿҳ��С
	int icbcpageIndexzf = Tool.strToInt((String)request.getParameter("icbcpageIndexzf"));//�ܷ�ҳ��
	if(icbcpageIndexzf <= 0)  icbcpageIndexzf = 1;
	int icbcpageIndexff = Tool.strToInt((String)request.getParameter("icbcpageIndexff"));//�ַ�ҳ��
	if(icbcpageIndexff <= 0)  icbcpageIndexff = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	String submitFlag = request.getParameter("submitFlag");
	Vector bankList = dao.getBankList(" where validFlag=0");
	if("icbcsendQS".equalsIgnoreCase(submitFlag)){//���з�����������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.sendGHQS(bankID,null,Tool.strToDate(s_time));//���͹��н�����������Ϣ
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
	}else if("icbcsavezfph".equalsIgnoreCase(submitFlag)){//����ȡ�ܷ�ƽ������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getProperBalanceValue(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
	}else if("icbcsaveffhd".equalsIgnoreCase(submitFlag)){//����ȡ�ַֺ˶�����
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.getBankFirmRightValue(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
	}else if("icbcgetzfph".equalsIgnoreCase(submitFlag)){//���в�ѯ�ܷ�ƽ��
		ProperBalanceValue pbv = new ProperBalanceValue();
		pbv.bankId = bankID;
		if(!s_time.trim().equals("")){
			pbv.bdate = new java.sql.Timestamp(Tool.getDateTime(s_time).getTime());
		}
		icbczfphList = new Vector(dao.getProperBalance(pbv));
		if(icbczfphList == null){
			result = "��ѯ�ܷ�ƽ���쳣";
		} else {
			int size = Tool.strToInt((String)request.getParameter("icbcpageSizezf"));
			if(size>0){
				icbcpageSizezf = size;
			}
			int maxpage = icbczfphList.size()%icbcpageSizezf==0 ? icbczfphList.size()/icbcpageSizezf : icbczfphList.size()/icbcpageSizezf+1;
			if(icbcpageIndexzf>maxpage) icbcpageIndexzf=maxpage;
			if(icbczfphList.size()<=0){
				result = "û�в�ѯ�����յ��ܷ�ƽ����Ϣ";
			} else {
				result = "��ѯ���ܷ�ƽ����Ϣ����";
			}
		}
	}else if("icbcgetffhd".equalsIgnoreCase(submitFlag)){//���в�ѯ�ַֺ˶�
		BankFirmRightValue bfr = new BankFirmRightValue();
		bfr.bankId = bankID;
		if(!s_time.trim().equals("")) {
			bfr.bdate = new java.sql.Timestamp(Tool.getDateTime(s_time).getTime());
		}
		icbcffhdList = dao.getBankCapital(bfr);
		if(icbcffhdList == null){
			result = "��ѯ�ַֺ˶������쳣";
		}else {
			int size = Tool.strToInt((String)request.getParameter("icbcpageSizeff"));
			if(size > 0){
				icbcpageSizeff = size;
			}
			int maxpage = icbcffhdList.size()%icbcpageSizeff==0 ? icbcffhdList.size()/icbcpageSizeff : icbcffhdList.size()/icbcpageSizeff+1;
			if(icbcpageIndexff>maxpage){
				icbcpageIndexff=maxpage;
			}
			if(icbcffhdList.size()<=0){
				result = "���շַֺ˶�ƽ";
			}else{
				result = "���շֲַ�ƽ��Ϣ��¼����";
			}
		}
	}
	icbczfph = ObjSet.getInstance(icbczfphList, icbcpageSizezf, icbcpageIndexzf);
	icbcffhd = ObjSet.getInstance(icbcffhdList, icbcpageSizeff, icbcpageIndexff);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>���������������</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%">
			<legend>������������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">����&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1" >��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++) {
								BankValue bv = (BankValue)bankList.get(i);
								if(sendQSBank(bv.bankID) != 0){
							%>
									<option value="<%=bv.bankID%>" <%if(bankID.equals(bv.bankID))out.print("selected");%>><%=bv.bankName%></option>
							<%
								}
							}
							%>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">��������&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������&nbsp;</td>
					<td align="left"><font color=red><%=result%>&nbsp;</font></td>
				</tr>
				<tr height="35">
					<td align="center" colspan="2">
						<input name=submitFlag type=hidden value="">
						<input type="button"   class="mdlbtn" value="��������" onclick="icbcbuttonclick(1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="ȡ�ܷ�ƽ��" onclick="icbcbuttonclick(2);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="ȡ�ַֺ˶�" onclick="icbcbuttonclick(3);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="mdlbtn" value="��ʾ�ܷ�ƽ��" onclick="icbcbuttonclick(4);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="lgrbtn" value="��ʾ�ַֺ˶�" onclick="icbcbuttonclick(5);">
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
	<%
		if(icbczfph != null && icbczfph.size()>0){
	%>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" align="center">���б��</td>
				<td class="panel_tHead_MB" align=center>����</td>
				<td class="panel_tHead_MB" align=right>���ܽ��</td>
				<td class="panel_tHead_MB" align=right>���л����˻����</td>
				<td class="panel_tHead_MB" align=right>���л����˻����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			for(int i=0;i<icbczfph.getCurNum();i++)
			{
				ProperBalanceValue pbv2 = (ProperBalanceValue)icbczfph.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=pbv2.bankId%></td>
					<td class="underLine" align=center><%=Tool.fmtDate(pbv2.bdate)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.allMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.gongMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(pbv2.otherMoney)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="5" align=right>
				��<%=icbcpageIndexzf%>/<%=icbczfph.getPageCount()%>ҳ &nbsp;&nbsp;��<%=icbczfph.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="icbcpageSizezf" class="text" type="text" style="width:25px;" value="<%=icbcpageSizezf%>" onkeydown="return icbcpgJumpChkzf()">�� &nbsp;&nbsp;
				<%
				if(icbcpageIndexzf != 1)
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnzf(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnzf(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(icbcpageIndexzf != icbczfph.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnzf(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnzf(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="icbcpageJumpIdxzf" onkeydown="return icbcpgJumpChkzf()">ҳ

				<input name=icbcpageIndexzf type=hidden value="<%=icbcpageIndexzf%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="5"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<%}%>
	<%
		if(icbcffhd != null && icbcffhd.size()>0){
	%>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB" align="center">���б��</td>
				<td class="panel_tHead_MB" align="center">����</td>
				<td class="panel_tHead_MB" align="center">�����̱��</td>
				<td class="panel_tHead_MB" align=right>���ж�Ȩ��&nbsp;</td>
				<td class="panel_tHead_MB" align=right>�г���Ȩ��&nbsp;</td>
				<td class="panel_tHead_MB" align="center">��ƽԭ��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<icbcffhd.getCurNum();i++)
			{
				BankFirmRightValue bfr2 = (BankFirmRightValue)icbcffhd.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=bfr2.bankId%></td>
					<td class="underLine" align=center><%=Tool.fmtDate(bfr2.bdate)%>&nbsp;</td>
					<td class="underLine" align=center>&nbsp;<%=bfr2.firmId%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.bankRight)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.maketRight)%>&nbsp;</td>
					<td class="underLine" align=center><%
					if(bfr2.reason == 0){
						out.print("��ƽ");
					}else if(bfr2.reason == 1){
						out.print("���ж��˻�δ����");
					}else if(bfr2.reason == 2){
						out.print("�г����˻�δ����");
					}
					%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6" align=right>
				��<%=icbcpageIndexff%>/<%=icbcffhd.getPageCount()%>ҳ &nbsp;&nbsp;��<%=icbcffhd.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="icbcpageSizeff" class="text" type="text" style="width:25px;" value="<%=icbcpageSizeff%>" onkeydown="return icbcpgJumpChkff()">�� &nbsp;&nbsp;
				<%
				if(icbcpageIndexff != 1)
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnff(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnff(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(icbcpageIndexff != icbcffhd.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="icbcpgTurnff(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="icbcpgTurnff(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="icbcpageJumpIdxff" onkeydown="return icbcpgJumpChkff()">ҳ

				<input name=icbcpageIndexff type=hidden value="<%=icbcpageIndexff%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<%}%>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function icbcbuttonclick(i){
	if(i == 1){
		frm.submitFlag.value = "icbcsendQS";
		frm.submit();
	} else if (i == 2){
		frm.submitFlag.value = "icbcsavezfph";
		frm.submit();
	} else if (i == 3){
		frm.submitFlag.value = "icbcsaveffhd";
		frm.submit();
	} else if (i == 4){
		frm.submitFlag.value = "icbcgetzfph";
		frm.submit();
	} else if (i == 5){
		frm.submitFlag.value = "icbcgetffhd";
		frm.submit();
	}
}
function icbcpgTurnzf(i) {
	frm.submitFlag.value = "icbcgetzfph";
	if(i == 0) {
		frm.icbcpageIndexzf.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.icbcpageIndexzf.value = frm.icbcpageIndexzf.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.icbcpageIndexzf.value = <%=icbczfph.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.icbcpageIndexzf.value = frm.icbcpageIndexzf.value - 1;
		frm.submit();
	}
}
function icbcpgTurnff(i) {
	frm.submitFlag.value = "icbcgetffhd";
	if(i == 0) {
		frm.icbcpageIndexff.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.icbcpageIndexff.value = frm.icbcpageIndexff.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.icbcpageIndexff.value = <%=icbcffhd.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.icbcpageIndexff.value = frm.icbcpageIndexff.value - 1;
		frm.submit();
	}
}
function icbcpgJumpChkzf() {
	if(event.keyCode == 13) {
		if((isNaN(frm.icbcpageJumpIdxzf.value) || frm.icbcpageJumpIdxzf.value < 1 || frm.icbcpageJumpIdxzf.value > <%=icbczfph.getPageCount()%>) && (isNaN(frm.icbcpageSizezf.value) || frm.icbcpageSizezf.value < 1 )) {
			alert("������1 - <%=icbczfph.getPageCount()%>������֣�");			
		} else {
			frm.submitFlag.value = "icbcgetzfph";
			frm.icbcpageIndexzf.value = frm.icbcpageJumpIdxzf.value;
			frm.submit();
		}
	}
}
function icbcpgJumpChkff() {
	if(event.keyCode == 13) {
		if((isNaN(frm.icbcpageJumpIdxff.value) || frm.icbcpageJumpIdxff.value < 1 || frm.icbcpageJumpIdxff.value > <%=icbcffhd.getPageCount()%>) && (isNaN(frm.icbcpageSizeff.value) || frm.icbcpageSizeff.value < 1 )) {
			alert("������1 - <%=icbcffhd.getPageCount()%>������֣�");			
		} else {
			frm.submitFlag.value = "icbcgetffhd";
			frm.icbcpageIndexff.value = frm.icbcpageJumpIdxff.value;
			frm.submit();
		}
	}
}
//-->
</SCRIPT>