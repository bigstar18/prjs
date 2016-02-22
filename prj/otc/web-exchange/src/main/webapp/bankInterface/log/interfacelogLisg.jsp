<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	int[] pageinfo=new int[4];
	BankDAO dao = BankDAOFactory.getDAO();
	Vector bankList = dao.getBankList(" ");

	int pageSize = BANKPAGESIZE;
	String today = Tool.fmtDate(new java.util.Date());
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	pageinfo[2]=pageSize;
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	if(pageIndex <= 0)  pageIndex = 1;
	pageinfo[1]=pageIndex;
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
		filter += " and fbi.contact='"+contact.trim()+"'";
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
		//filter += " and trunc(fbi.CREATETIME)>=to_date('"+ s_time +"','yyyy-MM-dd')";
		filter += " and fbi.CREATETIME>=to_date('"+ s_time +"','yyyy-MM-dd') ";
	}
	if(e_time != null && e_time.trim().length()>0) {
		//filter += " and trunc(fbi.CREATETIME)<=to_date('"+ e_time +"','yyyy-MM-dd')";
		filter += " and fbi.CREATETIME<to_date('"+ e_time +"','yyyy-MM-dd')+1 ";
	}
	filter += " order by fbi.logid desc ";
	Vector logList = dao.interfaceLogList(filter,pageinfo);
	int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�ӿ�ͨ����־</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag">
		<div id="main_body">
			<table border="0" cellspacing="0" class="table1_style"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmax">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0" style="table-layout:fixed;">
												<tr>
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
<%--																<OPTION value="11">�г������Ǽ�����</OPTION>--%>
																<OPTION value="12">ԤǩԼ</OPTION>
																<OPTION value="13">�г��ظ���������</OPTION>
<%--																<OPTION value="14">���ж˷���ǩԼ</OPTION>--%>
																<OPTION value="15">���з����������</OPTION>
<%--																<OPTION value="16">���ж����</OPTION>--%>
<%--																<OPTION value="17">��ѯ���˻����</OPTION>--%>
																<OPTION value="18">����</OPTION>
																<OPTION value="19">����</OPTION>
																<OPTION value="20">�������ϸ�˶�</OPTION>
																<OPTION value="21">�����Զ�����</OPTION>
																<OPTION value="22">��������</OPTION>
																<OPTION value="23">�����ѯ</OPTION>
																<OPTION value="24">���ɶ����ļ�����</OPTION>
																<OPTION value="25">��ѯ�����ļ�����״̬</OPTION>
																<OPTION value="26">��ѯ����������ˮ״̬</OPTION>
																<OPTION value="27">Ԥ��Լ</OPTION>
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
															<input type="text" class="input_text" name="account">
														</label>
													</td>
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
												</tr>
												<tr>
													<td class="table3_td_1" aling="left">
														<%=CONTACTTITLE%>:
														<input type="text" class="input_text" name="contact">
													</td>
													<td class="table3_td_1" align="left">
														&nbsp;&nbsp;��ʼ����:&nbsp;
														<label>
															<input type="text" style="width:92px" class="wdate" readonly name="s_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</label>
													</td>
													<td class="table3_td_1" align="left">
													��ֹ����:&nbsp;
														<label>
															<input type="text" style="width:92px" class="wdate" readonly name="e_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</label>
													</td>
													<td class="table3_td_anniu" align="left" colspan="2">
														<button class="btn_sec" onclick="doQuery();">
															��ѯ
														</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset();">
															����
														</button>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<div class="div_list">
							 <table id="tableList"  border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">���</td>
  				<td class="panel_tHead_MB" align="center" width="10%">��¼ʱ��</td>
  				<td class="panel_tHead_MB" align="center">����</td>
				<td class="panel_tHead_MB" align="center" width="">��������</td>
				<td class="panel_tHead_MB" align="center">����</td>
				<td class="panel_tHead_MB" align="center"><%=CONTACTTITLE%></td>
				<td class="panel_tHead_MB" align="center">�����˺�</td>
				<td class="panel_tHead_MB" align="center">�������</td>
				<td class="panel_tHead_MB_last" align="center" width="30%">�����Ϣ</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<logList.size();i++){
				InterfaceLog log = (InterfaceLog)logList.get(i);
				//out.print(log.bankName);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++%></td>
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
						}else if(log.type==12){
							out.print("ԤǩԼ");
						}else if(log.type==13){
							out.print("�г��ظ���������");
						}else if(log.type==15){
							out.print("���з����������");
						}else if(log.type==18){
							out.print("����");
						}else if(log.type==19){
							out.print("����");
						}else if(log.type==20){
							out.print("�������ϸ�˶�");
						}else if(log.type==21){
							out.print("�����Զ�����");
						}else if(log.type==22){
							out.print("��������");
						}else if(log.type==23){
							out.print("�����ѯ");
						}else if(log.type==24){
							out.print("���ɶ����ļ�����");
						}else if(log.type==25){
							out.print("��ѯ�����ļ�����״̬");
						}else if(log.type==26){
							out.print("��ѯ����������ˮ״̬");
						}else if(log.type==27){
							out.print("Ԥ��Լ");
						}else{
							out.print("����");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(log.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(log.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(log.account)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.result==0){
							out.print("�ɹ�");
						}else{
							out.print("ʧ��");
						}
					%>&nbsp;</td>
					<td class="underLine_last" align="left"><%=replaceNull(log.endMsg)%>&nbsp;</td>
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
				��<%=pageinfo[1]%>/<%=pageinfo[3]%>ҳ &nbsp;&nbsp;��<%=pageinfo[0]%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
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

				if(pageinfo[1] < pageinfo[3])
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
				��<input class="text" maxlength="4" value="<%=pageinfo[1]%>" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
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
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
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
<!--
function myreset(){
	frm.reset();
	doQuery();
}
function pgTurn(i) {
	frm.submitFlag.value = "doQuery";
	if(i == 0) {
		frm.pageIndex.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex.value = <%=pageinfo[3]%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function doQuery() {
	frm.pageIndex.value=1;
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
		frm.pageIndex.value = 1;
	}
}
function ismyStr(str){
	var patrn=/^\w*$/;
	if (patrn.exec(str)) {
		return true ;
	}
	return false ;
}
function pgJumpChk()
{
	if(event.keyCode == 13)
	{
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
		{
			alert("������ȷ���֣�");			
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

//-->
</SCRIPT>
