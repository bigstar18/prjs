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
	if("ccbsendQS".equalsIgnoreCase(request.getParameter("submitFlag"))){//������������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.sentFirmBalance(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
		dao.log(lv);
	}else if("ccbqsResult".equalsIgnoreCase(request.getParameter("submitFlag"))){//�鿴����״̬
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ReturnValue rv = cp.saveQSResult(bankID,s_time);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "������ϢΪ��";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("ȡ����������Ϣ��Ϣ:"+result);
		dao.log(lv);
	}else if("ccbsaveqsbp".equalsIgnoreCase(request.getParameter("submitFlag"))){//ȡ��������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ReturnValue rv = cp.saveQSResult(bankID,s_time);
		if(rv.remark==null || rv.remark.trim().length()<=0){
			result = "������ϢΪ��";
		}else{
			result = rv.remark;
		}
		lv.setLogcontent("ȡ����������Ϣ��Ϣ:"+result);
		dao.log(lv);
	}else if("ccbgetqsbp".equalsIgnoreCase(request.getParameter("submitFlag"))){//չʾ������
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		ccbCustDzFail = dao.getQSList(bankID,null,-1,Tool.strToDate(s_time));
		if(ccbCustDzFail==null) {
			result = "��ѯ���ж��˲�ƽ��Ϣ�쳣�����д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else if(ccbCustDzFail.size() == 0) {
			result = "���ж��˳ɹ������д��룺"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
		} else {
			result = "���ж��˲��ɹ���"+bankID+"���������ڣ�"+s_time+"ʱ�䣺"+Tool.fmtTime(new java.util.Date());
			int maxpage = 0;
			if(ccbCustDzFail!=null){
				maxpage = ccbCustDzFail.size()%ccbpageSize==0 ? ccbCustDzFail.size()/ccbpageSize : ccbCustDzFail.size()/ccbpageSize+1;
			}
			if(ccbpageIndex>maxpage){
				ccbpageIndex=maxpage;
			}
		}
		lv.setLogcontent("�����к��г���ǩ��Լ��Ϣ:"+result);
		dao.log(lv);
	}
	ccbqsbp = ObjSet.getInstance(ccbCustDzFail, ccbpageSize, ccbpageIndex);
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>������������ҳ��</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>������������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
				<td align="right">���У�&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
							<OPTION value="-1">��ѡ��</OPTION>
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
					<td align="right">�������ڣ�&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">������������&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn0" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="������������">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!-- 
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="�鿴����״̬">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						-->
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(3);" value="ȡ������Ϣ">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn3" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="doCompare(4);" value="�ȶ�������Ϣ">
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
				<td class-"panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">�����̴���</td>
				<td class-"panel_tHead_MB">������</td>
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
								out.println("����ɹ�");
							}else if(1==ocb.status){
								out.println("ʧ��");
							}else if(2==ocb.status){
								out.println("��������");
							}else if(3==ocb.status){
								out.println("��������");
							}else if(4==ocb.status){
								out.println("������");
							}else if(6==ocb.status){
								out.println("�˺��ʽ��쳣");
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
				��<%=ccbpageIndex%>/<%=ccbqsbp.getPageCount()%>ҳ &nbsp;&nbsp;��<%=ccbqsbp.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="ccbpageSize" class="text" type="text" style="width:25px;" value="<%=ccbpageSize%>" onkeydown="return ccbpgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(ccbpageIndex != 1) {
					%>
					<span style="cursor:hand" onclick="ccbpgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="ccbpgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(ccbpageIndex != ccbqsbp.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="ccbpgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="ccbpgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}
				%>
				��<input class="text" type="text" style="width:25px;" name="ccbpageJumpIdx" onkeydown="return ccbpgJumpChk()">ҳ

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
			alert("��ѡ�����У�");
		} else if(frm.s_time.value == "") {
			alert("��ѡ��������ڣ�");
		} else {
			var now1 = new Date()+"";
			if(CompareDate((frm.s_time.value),now1)) {
				if(v==1){//������������
					frm.submitFlag.value = "ccbsendQS";
					frm.submit();
				}else if(v==2){//�鿴�ļ�״̬
					frm.submitFlag.value = "ccbqsResult";
					frm.submit();
				}else if(v==3){//ȡ��������������
					frm.submitFlag.value = "ccbsaveqsbp";
					frm.submit();
				} else if(v==4) {//չʾ������
					frm.submitFlag.value = "ccbgetqsbp";
					frm.submit();	
				}
			} else {
				alert("ѡ��Ķ������ڲ��Ϸ�!");
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
				alert("������1 - <%=ccbqsbp.getPageCount()%>������֣�");			
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