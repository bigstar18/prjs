<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	BankDAO dao = BankDAOFactory.getDAO();
	
	String today = Tool.fmtDate(new java.util.Date());
	
	Vector bankList = dao.getBankList(" ");
	
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	
	String filter = " ";
	String type = Tool.delNull(request.getParameter("type"));
	if(type != null && type.trim().length()>0){
		filter += " and fbi.type="+type;
	}
	
	String bankID = Tool.delNull(request.getParameter("bankID"));
	if(bankID != null && bankID.trim().length()>0){
		filter += " and fbi.bankID='"+bankID+"'";
	}
	
	String launcher = Tool.delNull(request.getParameter("launcher"));
	if(launcher != null && launcher.trim().length()>0){
		filter += " and fbi.launcher='"+launcher.trim()+"'";
	}
	
	String firmID = Tool.delNull(request.getParameter("firmID"));
	if(firmID != null && firmID.trim().length()>0){
		filter += " and fbf.firmID='"+firmID.trim()+"'";
	}
	
	String contact = Tool.delNull(request.getParameter("contact"));
	if(contact != null && contact.trim().length()>0){
		filter += " and fbi.firmID='"+contact.trim()+"'";
	}
	
	String account = Tool.delNull(request.getParameter("account"));
	if(account != null && account.trim().length()>0){
		filter += " and fbi.account='"+account.trim()+"'";
	}
	
	String result = Tool.delNull(request.getParameter("result"));
	if(result != null && result.trim().length()>0){
		filter += " and fbi.result='"+result+"'";
	}
	
	String s_time = Tool.delNull(request.getParameter("s_time"));
	String e_time = Tool.delNull(request.getParameter("e_time"));
	if(request.getParameter("submitFlag")==null || request.getParameter("submitFlag").trim().length()<=0){
		s_time = today;
		e_time = today;
	}
	if(s_time != null && s_time.trim().length()>0) {
		filter += " and fbi.CREATETIME>=to_date('"+ s_time +"','yyyy-MM-dd') ";
	}
	if(e_time != null && e_time.trim().length()>0) {
		filter += " and fbi.CREATETIME<to_date('"+ e_time +"','yyyy-MM-dd')+1 ";
	}
	filter += " order by fbi.logid desc ";

	Vector logList = dao.interfaceLogList(filter);
	ObjSet obj = ObjSet.getInstance(logList, pageSize, pageIndex);
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" src="../lib/validate.js"></script>
    <title>�ӿ�ͨ����־</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
	    <input type="hidden" name="submitFlag">
		<fieldset width="100%">
			<legend>�ӿ�ͨ����־</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td class="table3_td_1" align="left">
						��������:&nbsp;
						<label>
							<select name="type" class="normal" style="width:120px">
								<OPTION value="">ȫ��</OPTION>
								<OPTION value="1">ǩ��</OPTION>
								<OPTION value="2">ǩ��</OPTION>
								<OPTION value="3">ǩԼ</OPTION>
								<OPTION value="4">��Լ</OPTION>
								<OPTION value="9">��Լ</OPTION>
								<OPTION value="5">��ѯ���</OPTION>
								<OPTION value="6">����</OPTION>
								<OPTION value="7">���</OPTION>
								<OPTION value="8">����</OPTION>
								<OPTION value="10">��Կͬ��</OPTION>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						����:&nbsp;
						<label>
							<select name="bankID" class="normal" style="width:120px">
								<OPTION value="">ȫ��</OPTION>
								<%
								  for(int i=0;i<bankList.size();i++) {
									BankValue bv = (BankValue)bankList.get(i);
									%>
									   <option value="<%=bv.bankID%>"><%=bv.bankName%></option>		
									<%
								  }
								%>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						����&nbsp;
						<label>
							<select name="launcher" class="normal" style="width:120px">
								<option value="">ȫ��</option>
								<option value="0">�г�</option>
								<option value="1">����</option>
							</select>
						</label>
					</td>
					<td class="table3_td_1" align="left">
						�����˺�:&nbsp;
						<label>
							<input type="text" class="text" name="account">
						</label>
					</td>
					
				</tr>
				<tr>
					<td class="table3_td_1" align="left">
						�������:&nbsp;
						<label>
							<select name="result" class="normal" style="width:120px">
								<option value="">ȫ��</option>
								<option value="0">�ɹ�</option>
								<option value="1">ʧ��</option>
							</select>
						</label>
						<input type="hidden"  class="input_text" name="firmID">
					</td>
					<td class="table3_td_1" aling="left">
						�����˺�:&nbsp;
						<input type="text" class="text" name="contact" style="width:120px">
					</td>
					<td class="table3_td_1" align="left">
						��ʼ����:&nbsp;
						<label>
							<!--<input type="text" style="width:100px" class="wdate" value="<%=today%>" readonly name="s_time" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">-->
							<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
						</label>
					</td>
					<td class="table3_td_1" align="left">
					    ��ֹ����:&nbsp;
						<label>
							<!--<input type="text" style="width:102px" class="wdate" value="<%=today%>" readonly name="e_time" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">-->
							<MEBS:calendar eltName="e_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=e_time%>"/>
						</label>
					</td>
					<td align="left">
						<button type="button" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;&nbsp;
						<button type="button" onclick="myreset();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">���</td>
				<td class="panel_tHead_MB">��¼ʱ��</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">����</td>
				<td class="panel_tHead_MB">�����˺�</td>
				<td class="panel_tHead_MB">�����˺�</td>
				<td class="panel_tHead_MB">�������</td>
				<td class="panel_tHead_MB">�����Ϣ</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			for(int i=0;i<obj.getCurNum();i++){
				InterfaceLog log = (InterfaceLog)logList.get(i);
				//out.print(log.bankName);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=log.logID%></td>
					<td class="underLine" align="center"><%=Tool.fmtTime(log.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.launcher==0){
							out.print("�г�");
						}else if(log.launcher==1){
							out.print("����");
						}else{
							out.print("����");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.type==1){
							out.print("ǩ��");
						}else if(log.type==2){
							out.print("ǩ��");
						}else if(log.type==3){
							out.print("ǩԼ");
						}else if(log.type==4){
							out.print("��Լ");
						}else if(log.type==5){
							out.print("��ѯ���");
						}else if(log.type==6){
							out.print("����");
						}else if(log.type==7){
							out.print("���");
						}else if(log.type==8){
							out.print("����");
						}else if(log.type==9){
							out.print("��Լ");
						}else if(log.type==10){
							out.print("��Կͬ��");
						}else{
							out.print("����");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.account)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.result==0){
							out.print("�ɹ�");
						}else{
							out.print("ʧ��");
						}
					%>&nbsp;</td>
					<td class="underLine_last" align="left"><%=Tool.delNull(log.endMsg)%>&nbsp;</td>
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
				��<%=pageIndex%>/<%=obj.getPageCount()%>ҳ &nbsp;&nbsp;��<%=obj.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageIndex != 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(pageIndex != obj.getPageCount())
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageIndex%>">
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
	</from>
  </body>
</html>
<script>
	frm.type.value="<%=type%>";
	frm.bankID.value="<%=bankID%>";
	frm.launcher.value="<%=launcher%>";
	frm.firmID.value="<%=firmID%>";
	frm.contact.value="<%=contact%>";
	frm.account.value="<%=account%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
	frm.result.value="<%=result%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
	function pgTurn(i)
	{
	    frm.submitFlag.value = "doQuery";
		if(i == 0)
		{
			frm.pageIndex.value = 1;
			frm.submit();
		}
		else if(i == 1)
		{		
			frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
			frm.submit();
		}
		else if(i == 2)
		{
			frm.pageIndex.value = <%=obj.getPageCount()%>;
			frm.submit();
		}
		else if(i == -1)
		{
			frm.pageIndex.value = frm.pageIndex.value - 1;
			frm.submit();
		}
	}
	
	function doQuery(){
		frm.submitFlag.value = "doQuery";
		var firmID = frm.firmID.value;
		var contact = frm.contact.value;
		var account = frm.account.value;
		var s_time = frm.s_time.value;
		var e_time = frm.e_time.value;
		var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
		if(!isStr(firmID.trim(),false,null)){
			alert("��Ա��ͻ���Ų��Ϸ�");
			frm.firmID.focus();
		} else if(!isStr(contact.trim(),false,null)){
			alert("ǩԼ�Ų��Ϸ�");
			frm.contact.focus();
		} else if(!isStr(account.trim(),false,null)){
			alert("�����˺Ų��Ϸ�");
			frm.account.focus();
		} else if(s_time != "" && !CompareDate(s_time,now1)){
			alert("��ʼ���ڸ�ʽ�������ʼ���ڳ�������");
			frm.s_time.value="";
		} else if(e_time != "" && !CompareDate(e_time,now1)){
			alert("�������ڸ�ʽ�����������ڳ�������");
			frm.e_time.value="";
		} else if(s_time != "" && e_time != "" && !CompareDate(s_time,e_time)){
			alert("����Ŀ�ʼ���ڲ��ܳ�����������");
			frm.s_time.value="";
			frm.e_time.value="";
		} else{
			frm.submit();
		}
	}
	
	function myreset(){
		frm.reset();
		//doQuery();
	}
	
	function pgJumpChk()
	{
		if(event.keyCode == 13)
		{
			if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
			{
				alert("������1 - <%=obj.getPageCount()%>������֣�");
			}
			else
			{
			    frm.submitFlag.value = "doQuery";
				frm.pageIndex.value = frm.pageJumpIdx.value;
				frm.submit();
			}
		}	
	}
	
	function CompareDate(d1,d2) {
		return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>