<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
Vector bankList = dao.getBankList(" ");
int[] pageinfo = new int[4];
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
String bankID = Tool.delNull(request.getParameter("bankID"));
String capitalType = Tool.delNull(request.getParameter("capitalType"));
String capitalStatus = Tool.delNull(request.getParameter("capitalStatus"));
String firmType = Tool.delNull(request.getParameter("firmType"));
String contact = Tool.delNull(request.getParameter("contact"));
String accountName = Tool.delNull(request.getParameter("accountName"));
String s_time = Tool.delNull(request.getParameter("s_time"));
String e_time = Tool.delNull(request.getParameter("e_time"));
String trader = Tool.delNull(request.getParameter("trader"));
String launcher = Tool.delNull(request.getParameter("launcher"));
String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
if(submitFlag == null || submitFlag.trim().length()<=0){
	s_time=today;
	e_time=today;
}
String filter = " and mf.firmType='C'  and (type=0 or type=1) ";
String ORGANIZATIONID = (String)session.getAttribute("ORGANIZATIONID");
System.out.println("ORGANIZATIONID="+ORGANIZATIONID);
if(ORGANIZATIONID==null || ORGANIZATIONID.trim().length()<=0){
	filter += " and mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' ";
	//filter += " and exists (select customerno from m_customerinfo mc where mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}else{
	filter += " and mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' ";
	//filter += " and exists (select customerno from v_customerrelateorganization mc where mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' and fbf.firmID=mc.customerno) ";
}
if(bankID != null && bankID.trim().length()>0){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(capitalType != null && capitalType.trim().length()>0){
	filter += " and fbc.type="+capitalType;
}
if(launcher != null && launcher.trim().length()>0){
	filter += " and fbc.launcher="+launcher+" ";
}
if(capitalStatus != null && capitalStatus.trim().length()>0){
	if(capitalStatus.equals(ProcConstants.statusBankProcessing+"")){
		filter += " and fbc.status not in ("+ProcConstants.statusSuccess+","+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") ";
	}else if(capitalStatus.equals(ProcConstants.statusSuccess+"")){
		filter += " and fbc.status="+capitalStatus;
	}else if(capitalStatus.equals(ProcConstants.statusFailure+"")){
		filter += " and fbc.status in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+")";
	}
}
if(firmType != null && firmType.trim().length()>0){
	filter += " and mf.firmtype='"+firmType.trim()+"' ";
}
if(contact != null && contact.trim().length()>0){
	filter += " and fbc.contact='"+contact.trim()+"'";
}
if(accountName != null && accountName.trim().length()>0){
	filter += " and fbf.accountName like '"+accountName.trim()+"%'";
}
if(s_time != null && s_time.trim().length()>0){
	//filter += " and trunc(createtime)>=to_date('"+s_time+"','yyyy-MM-dd') ";
	filter += " and fbc.createtime>=to_date('"+s_time+"','yyyy-MM-dd') ";
}
if(e_time != null && e_time.trim().length()>0){
	//filter += " and trunc(createtime)<=to_date('"+e_time+"','yyyy-MM-dd') ";
	filter += " and fbc.createtime<to_date('"+e_time+"','yyyy-MM-dd')+1 ";
}
if(trader != null && trader.trim().length()>0){
	filter += " and fbc.trader like '%"+trader+"%' ";
}
Vector capitalMoney = dao.getCapitalInfoMoney(filter);
filter += " order by fbc.createtime,fbc.id";
Vector moneyList = dao.getCapitalInfoList2(filter,pageinfo);
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
    <title>�ͻ�������ѯ</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag">
		<input type="hidden" id="thispage" name="thispage">
		<div id="main_body">
			<table border="0" cellspacing="0" class="table1_style"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthcdmax">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0" style="table-layout:fixed;">
												<tr>
												    <td align="left" width="200">
														<%=CONTACTTITLE%>��&nbsp;
														<input type="text" class="input_text" maxlength="15" name="contact" value="">
													</td>
													<td width="180" align="right">
														ת�����ͣ�&nbsp;
														<label>
															<select name="capitalType" class="normal" style="width: 80px">
															 <OPTION value="">ȫ��</OPTION>
															 <option value="<%=ProcConstants.inMoneyType%>">���</option>
															 <option value="<%=ProcConstants.outMoneyType%>">����</option>
														   </select>
														</label>&nbsp;&nbsp;
													</td>
													<td>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=BANKTITLE%>��
														<label>
															<select name="bankID" class="normal" style="width:100px">
																<OPTION value="">��ѡ��</OPTION>
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
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td align="left">
														<%=ACCOUNTNAMETITLE%>��&nbsp;
														<input type="text" class="input_text" maxlength="10" name="accountName" value="">
													</td>
													<td align="right">
													���𷽣�
														<label>
															<select name="launcher" class="normal" style="width: 80px">
															 <OPTION value="">ȫ��</OPTION>
															 <option value="0">�г�</option>
															 <option value="1">����</option>
														   </select>
														</label>&nbsp;&nbsp;
													</td>
													<td colspan="2">
														&nbsp;&nbsp;����״̬��
														<label>
															<select name="capitalStatus" class="normal" style="width: 60px">
																<OPTION value="">ȫ��</OPTION>
																<option value="<%=ProcConstants.statusSuccess%>">�ɹ�</option>
																<option value="<%=ProcConstants.statusFailure%>">ʧ��</option>
																<option value="<%=ProcConstants.statusBankProcessing%>">������</option>
															</select>
														</label>
														����������
														<label>
															<select name="trader" class="normal" style="width: 80px">
																<OPTION value="">��</OPTION>
																<option value="manual">�ռ䴦��</option>
																<option value="endofday">���մ���</option>
															</select>
														</label>
													</td>
												</tr>
												<tr>
													<td align="left" colspan=2>
														ת�����ڣ�
														<label>
															��<input type="text" readonly style="width:100px" class="wdate" maxlength="10" name="s_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</label>
														<label>
															&nbsp;��&nbsp;<input type="text" readonly style="width:100px" class="wdate" maxlength="10" name="e_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</label>
													</td>
													<td colspan="2" align="left">
														<button class="btn_sec" onclick="doQuery();">
															��ѯ
														</button>&nbsp;
														<button class="btn_sec" onclick="output()">
															����
														</button>
														 &nbsp;
														<button class="btn_cz" onclick="myreset();">
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
				</td>
			</tr>
			<tr>
				<td>
				<table  id="tableList" border="0" cellspacing="0" cellpadding="0" width="1100" height="400">
  		<tHead>
  			<tr align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center"><%=SEQUNCE%></td>
				<td class="panel_tHead_MB" align="center">ʱ��</td>
				<td class="panel_tHead_MB" align="center" >��������ˮ��</td>
				<td class="panel_tHead_MB" align="center">������ˮ��</td>
				<td class="panel_tHead_MB" align="center"  ><%=BANKTITLE%></td>
				<td class="panel_tHead_MB" align="center" ><%=ACCOUNTNAMETITLE%></td>
				<td class="panel_tHead_MB" align="center" ><%=CONTACTTITLE%></td>
				<td class="panel_tHead_MB" align="center">ת�˽��</td>
				<td class="panel_tHead_MB" align="center" >ת������</td>
				<td class="panel_tHead_MB" align="center" >����״̬</td>
				<td class="panel_tHead_MB_last" align="center">��ע</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			double ins = 0;//���ɹ�
			int insn = 0;
			double ini = 0;//�������
			int inin = 0;
			double inf = 0;//���ʧ��
			int infn = 0;
			double ous = 0;//����ɹ�
			int ousn = 0;
			double oui = 0;//��������
			int ouin = 0;
			double ouf = 0;//����ʧ��
			int oufn = 0;
			for(int i=0;i<moneyList.size();i++){
				CapitalValue money = (CapitalValue)moneyList.get(i);
				if(money.type==ProcConstants.inMoneyType){
					if(money.status==ProcConstants.statusSuccess){
						ins+=money.money;
						insn++;
					}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusFailure){
						inf+=money.money;
						infn++;
					}else{
						ini+=money.money;
						inin++;
					}
				}else if(money.type==ProcConstants.outMoneyType){
					if(money.status==ProcConstants.statusSuccess){
						ous += money.money;
						ousn++;
					}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusFailure){
						ouf += money.money;
						oufn++;
					}else{
						oui += money.money;
						ouin++;
					}
				}
				%>
				<tr  align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.actionID%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.funID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.accountName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(money.contact)%>&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.type==ProcConstants.inMoneyType){
							out.print("���");
						}else if(money.type==ProcConstants.outMoneyType){
							out.print("����");
						}else if(money.type==ProcConstants.inMoneyBlunt){
							out.print("������");
						}else if(money.type==ProcConstants.outMoneyBlunt){
							out.print("�������");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.status==ProcConstants.statusSuccess){
							out.print("�ɹ�");
						}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusBlunt){
							out.print("ʧ��");
						}else{
							out.print("������");
						}
					%>&nbsp;</td>
					<td width="20%" class="underLine_last" align="left"><%=replaceNull(money.note)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%
			if(capitalMoney != null && capitalMoney.size()==6){
				CapitalMoneyVO cm11 = (CapitalMoneyVO) capitalMoney.get(0);
				CapitalMoneyVO cm12 = (CapitalMoneyVO) capitalMoney.get(1);
				CapitalMoneyVO cm21 = (CapitalMoneyVO) capitalMoney.get(2);
				CapitalMoneyVO cm22 = (CapitalMoneyVO) capitalMoney.get(3);
				CapitalMoneyVO cm31 = (CapitalMoneyVO) capitalMoney.get(4);
				CapitalMoneyVO cm32 = (CapitalMoneyVO) capitalMoney.get(5);
			%>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="center" colspan="2">�ϼƣ�</td>
				<td class="underLine" align="left" colspan="2">�ɹ��ϼƹ�<%=cm11.rowcount+cm12.rowcount%>��&nbsp;</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtMoney(cm11.money)%>(Ԫ)&nbsp;<%=cm11.rowcount%>��</td>
				<td class="underLine" align="left" colspan="2">���𹲼ƣ�<%=Tool.fmtMoney(cm12.money)%>(Ԫ)&nbsp;<%=cm12.rowcount%>��</td>
				<td class="underLine_last" align="left" colspan="2">������ֵ��<%=Tool.fmtMoney(cm11.money-cm12.money)%>(Ԫ)</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right" colspan="2">&nbsp;</td>
				<td class="underLine" align="left" colspan="2">ʧ�ܺϼƹ�<%=cm21.rowcount+cm22.rowcount%>��&nbsp;</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtMoney(cm21.money)%>(Ԫ)&nbsp;<%=cm21.rowcount%>��</td>
				<td class="underLine" align="left" colspan="2">���𹲼ƣ�<%=Tool.fmtMoney(cm22.money)%>(Ԫ)&nbsp;<%=cm22.rowcount%>��</td>
				<td class="underLine_last" align="left" colspan="2">������ֵ��<%=Tool.fmtMoney(cm21.money-cm22.money)%>(Ԫ)</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right" colspan="2">&nbsp;</td>
				<td class="underLine" align="left" colspan="2">�����кϼƹ�<%=cm31.rowcount+cm32.rowcount%>��&nbsp;</td>
				<td class="underLine" align="left" colspan="3">��𹲼ƣ�<%=Tool.fmtMoney(cm31.money)%>(Ԫ)&nbsp;<%=cm31.rowcount%>��</td>
				<td class="underLine" align="left" colspan="2">���𹲼ƣ�<%=Tool.fmtMoney(cm32.money)%>(Ԫ)&nbsp;<%=cm32.rowcount%>��</td>
				<td class="underLine_last" align="left" colspan="2">������ֵ��<%=Tool.fmtMoney(cm31.money-cm32.money)%>(Ԫ)</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%}%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11" align=right>
				��<%=pageinfo[1]%>/<%=pageinfo[3]%>ҳ &nbsp;&nbsp;��<%=pageinfo[0]%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" maxlength="4" class="text" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1) {
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}
				if(pageinfo[1] < pageinfo[3]) {
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[1]%>" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="11"></td>
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
	frm.bankID.value="<%=bankID%>";
	frm.capitalType.value="<%=capitalType%>";
	frm.capitalStatus.value="<%=capitalStatus%>";
	frm.contact.value="<%=contact%>";
	frm.accountName.value="<%=accountName%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
	frm.trader.value="<%=trader%>";
	frm.launcher.value="<%=launcher%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function myreset(){
	frm.action="";
	myReset();
}
function pgTurn(i) {
	frm.action="";
	frm.submitFlag.value="do";
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

function doQuery()
{
	frm.action="";
	frm.submitFlag.value="do";
	var contact = frm.contact.value;
	var accountName = frm.accountName.value;
	var s_time = frm.s_time.value;
	var e_time = frm.e_time.value;
	var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
	if(contact.trim() != "" && !isStr(contact.trim(),false,null)){
		alert("�����<%=CONTACTTITLE%>���Ϸ�");
		frm.contact.value="";
	} else if(accountName.trim() != "" && !isStr(accountName.trim(),true,new Array("��"))){
		alert("�����<%=ACCOUNTNAMETITLE%>���Ϸ�");
		frm.accountName.value="";
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
function pgJumpChk(){
	frm.action="";
	frm.submitFlag.value="do";
	if(event.keyCode == 13){
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )){
			alert("��������ȷ���֣�");			
		}else{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
function output(){
	var url = "capitalExcle.jsp";
	if(confirm("����ȫ��������?\n\n(ȡ��Ϊ������ǰҳ)")){
		frm.thispage.value="0";
	}else{
		frm.thispage.value="1";
	}
	frm.action=url;
	frm.submit();
}
//-->
</SCRIPT>
