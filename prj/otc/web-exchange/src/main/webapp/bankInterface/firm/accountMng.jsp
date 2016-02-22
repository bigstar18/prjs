<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
boolean modright = false;
if(session.getAttribute("rightMap") != null){
	Map<String,Boolean> rightMap = (Map<String,Boolean>) session.getAttribute("rightMap");
	if(rightMap.get("update") != null){
		if(rightMap.get("update")==true){
			modright = true;
		}
	}
}
int[] pageinfo = new int[4];
boolean medoBank = false;
BankDAO dao = BankDAOFactory.getDAO();
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt((String)request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
pageinfo[2]=pageSize;
Vector bankList = dao.getBankList("");
int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
pageinfo[1]=pageIndex;
String filter = " ";
String bankID = request.getParameter("bankID");
String isOpen = request.getParameter("isOpen");
String status = request.getParameter("status");
String firmType = request.getParameter("firmType");
String cardType = request.getParameter("cardType");
String frozenFuns = request.getParameter("frozenFuns");
String belevemember = request.getParameter("belevemember");
String contact = request.getParameter("contact");
String account = request.getParameter("account");
String accountName = request.getParameter("accountName");
if(bankID != null && bankID.length()>0){
	filter += " and fbf.bankID='"+bankID+"' ";
}
if(isOpen != null && isOpen.length()>0){
	filter += " and fbf.isOpen="+isOpen+" ";
}
if(status != null && status.length()>0){
	filter += " and fbf.status="+status+" ";
}
if(firmType != null && firmType.length()>0){
	filter += " and mf.firmType='"+firmType+"' ";
}
if(cardType != null && cardType.length()>0){
	if("other".equals(cardType)){
		filter += " and fbf.cardType not in ('1','8','9') ";
	}else{
		filter += " and fbf.cardType='"+cardType+"' ";
	}
}
if(frozenFuns != null && frozenFuns.length()>0){
	if("1".equals(frozenFuns)){
		filter += " and fbf.frozenFuns<>0";
	}else{
		filter += " and fbf.frozenFuns=0";
	}
}
if(belevemember != null && belevemember.trim().length()>0){
	if("M".equalsIgnoreCase(firmType) || "S".equalsIgnoreCase(firmType)){
		filter += " and fbf.firmID='"+belevemember.trim()+"' ";
	}else{
		filter += " and mc.memberno='"+belevemember.trim()+"' ";
	}
}
if(contact != null && contact.trim().length()>0){
	filter += " and fbf.contact='"+contact.trim()+"' ";
}
if(account != null && account.trim().length()>0){
	filter += " and fbf.account='"+account.trim()+"' ";
}
if(accountName != null && accountName.trim().length()>0){
	filter += " and fbf.accountName like '"+accountName.trim()+"%' ";
}
filter += " order by fbf.firmID,fbf.bankid ";
Vector dicList = dao.getCorrespondList2(filter,pageinfo);
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title><%=CONTACTTITLE%>����</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" id="submitFlag" name="submitFlag">
		<input type="hidden" id="thispage" name="thispage">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
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
												   <td width="240" align="right">
														<%=CONTACTTITLE%>��
														<input type="text" maxlength="15" class="input_text"  name="contact" value=""/>
													</td>
													<td width="200" align="left">
														&nbsp;��;�ʽ�&nbsp;
														<select name="frozenFuns" style="width:80px">
															<option value="">ȫ��</option>
															<option value="1">��</option>
															<option value="2">��</option>
														</select>
													</td>
													<td width="160" align="right">
														ǩԼ״̬��
														<select name="isOpen" style="width:80px">
															<option value="">ȫ��</option>
															<option value="1">��ǩԼ</option>
															<option value="0">δǩԼ</option>
															<option value="2">�ѽ�Լ</option>
														</select>&nbsp;&nbsp;
													</td>
													<td width="160" align="left" >
														�˺����ͣ�&nbsp;
														<select name="firmType" style="width:80px">
															<option value="">ȫ��</option>
															<option value="C">�ͻ�</option>
															<option value="M">��Ա</option>
															<option value="S">�ر��Ա</option>
														</select>
													</td>
												</tr>
												<tr>
												    <td align="right">
														<%=ACCOUNTNAMETITLE%>��
														<input type="text" maxlength="64" class="input_text"  name="accountName" value=""/>&nbsp;
													</td>
												   <td align="left">
														&nbsp;֤�����ͣ�&nbsp;
														<select name="cardType" style="width:80px;">
															<option value="">ȫ��</option>
															<option value="1">���֤</option>
															<option value="8">��������</option>
															<option value="9">Ӫҵִ��</option>
															<option value="other">����</option>
														</select>
													</td>
													<td align="right">
														���У�
														<label>
															<select name="bankID" class="normal" style="width:80px">
																<OPTION value="">ȫ��</OPTION>
																<%
																for(int i=0;i<bankList.size();i++){
																	BankValue bank = (BankValue)bankList.get(i);
																	if(bank.bankID.startsWith("1") && !bank.bankName.equals("��������")){
																		medoBank = true;
																	}
																	%>
																	<option value="<%=bank.bankID%>"><%=bank.bankName%></option>	
																	<%
																}
																%>
															</select>
														</label>&nbsp;&nbsp;
													</td>
													<td align="left">
														�Ƿ���ã�&nbsp;
														<select name="status" style="width:80px">
															<option value="">ȫ��</option>
															<option value="0">����</option>
															<option value="1">������</option>
														</select>&nbsp;
													</td>
												</tr>
												<tr>
												   <td align="right">
														������Ա��
														<input type="text" class="input_text" maxlength="3" name="belevemember" value="" />&nbsp;
													</td>
													 <td align="left">
														&nbsp;�����˺ţ�&nbsp;
														<input type="text" maxlength="32" class="input_text" name="account" value=""/>
													</td>
													<td align="right" colspan="2">
														<button class="btn_sec" onclick="doQuery();">
															��ѯ
														</button>&nbsp;
														<button class="btn_sec" onclick="output()">
															����
														</button>&nbsp;
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
				<%
					if(modright){
				%>
				<tr id="buttontr">
					<td>
						 <div class="div_gn">
							<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
								<tr style="hidden">
									<td class="table3_td" align="left">&nbsp;&nbsp;
									<%if(medoBank){%>
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','openAccountdemo');">
											ģ������ǩԼ
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','delAccountdemo');">
											ģ�����н�Լ
										</button>&nbsp;&nbsp;
									<%}%>
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','01_synchroCorr');">
											����ԤǩԼ
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','02_openAccount');">
											����ǩԼ
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','03_delAccount');">
											�����Լ
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','disAccount');">
											����
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','recAccount');">
											����
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','recPassword');">
											��ʼ���ʽ�����
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','modAccount');">
											ǿ���޸�
										</button>&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<%}%>
				<tr>
					<td>
						 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1200" height="400">
							<tHead>
								<tr align=center>
									<td class="panel_tHead_LB">&nbsp;</td>
									<td  class="panel_tHead_MB" >&nbsp;</td>
									<td class="panel_tHead_MB">���</td>
									<td  class="panel_tHead_MB" align="center"><%=CONTACTTITLE%></td>
									<td  class="panel_tHead_MB" align="center">������Ա</td>
									<td  class="panel_tHead_MB" align="center">����</td>
									<td class="panel_tHead_MB" align="center">�˻�����</td>
									<td  class="panel_tHead_MB" align="center">�����˺�</td>
									<td class="panel_tHead_MB" align="center"><%=ACCOUNTNAMETITLE%></td>
									<td  class="panel_tHead_MB" align="center">�˺�����</td>
									<td  class="panel_tHead_MB" align="center">֤������</td>
									<td  class="panel_tHead_MB" align="center">ǩԼ״̬</td>
									<td  class="panel_tHead_MB" align="center">�Ƿ����</td>
									<td  class="panel_tHead_MB_last" align="center">��;�ʽ�</td>
									<td class="panel_tHead_RB">&nbsp;</td>
								</tr>
							</tHead>
							<tBody>
	  		<%
			for(int i=0;i<dicList.size();i++) {
				CorrespondValue corr = (CorrespondValue)dicList.get(i);
				%>
				<tr align="center"  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="left"><input name="ck" type="radio" value='<%=corr.bankID%>,<%=corr.firmID%>,<%=corr.contact%>,<%=corr.isOpen%>,<%=corr.status%>,<%=corr.id%>'></td>
					<td class="underLine" align="center"><%=rownum++ %></td>
					<td class="underLine" align="center"><%=replaceNull(corr.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(corr.belevemember)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(corr.name)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(corr.bankID==null || corr.bankID.trim().length()<=0){
							out.print("-");
						}else if(corr.bankID.trim().equals(corr.mainBank)){
							out.print("���˻�");
						}else{
							out.print("���˻�");
						}
					%></td>
					<td class="underLine" align="center"><%
						if(checkAccount(corr.bankID)){
							out.print(replaceNull(corr.account1));
						}else{
							out.print(replaceNull(corr.account));
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(corr.accountName)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if("C".equals(corr.firmType)){
							out.print("�ͻ�");
						}else if("M".equals(corr.firmType)){
							out.print("��Ա");
						}else if("S".equals(corr.firmType)){
							out.print("�ر��Ա");
						}
					%>
					<td class="underLine" align="center"><%=replaceNull(corr.getCardType())%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(1==corr.isOpen){
							out.print("��ǩԼ");
						}else if(0==corr.isOpen){
							out.print("<font color='#FF0000'>δǩԼ</font>");
						}else if(2==corr.isOpen){
							out.print("<font color='#FF0000'>�ѽ�Լ</font>");
						}else{
							out.print("<font color='#FF0000'>״̬����</font>");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(0==corr.status){
							out.print("����");
						}else if(1==corr.status){
							out.print("<font color='#FF0000'>������</font>");
						}else{
							out.print("<font color='#FF0000'>״̬δ֪</font>");
						}
					%></td>
					<td class="underLine_last" align=right><%=Tool.fmtDouble2(corr.frozenFuns)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="13">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="13" align=right>
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
				��<input class="text" type="text" maxlength="4" style="width:25px;" name="pageJumpIdx" value="<%=pageinfo[1]%>" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="13"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
						
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="MbankID" name="MbankID">
		<input type="hidden" id="MfirmID" name="MfirmID">
		<input type="hidden" id="Mcontact" name="Mcontact">
		<input type="hidden" id="MisOpen" name="MisOpen">
		<input type="hidden" id="Maccount" name="Maccount">
		<input type="hidden" id="MbankCardPassword" name="MbankCardPassword">
	</form>
  </body>
</html>
<script>
frm.bankID.value = '<%=Tool.delNull(bankID)%>';
frm.isOpen.value = '<%=Tool.delNull(isOpen)%>';
frm.status.value = '<%=Tool.delNull(status)%>';
frm.firmType.value = '<%=Tool.delNull(firmType)%>';
frm.cardType.value = '<%=Tool.delNull(cardType)%>';
frm.frozenFuns.value = '<%=Tool.delNull(frozenFuns)%>';
frm.belevemember.value = '<%=Tool.delNull(belevemember)%>';
frm.contact.value = '<%=Tool.delNull(contact)%>';
frm.account.value = '<%=Tool.delNull(account)%>';
frm.accountName.value = '<%=Tool.delNull(accountName)%>';
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function myreset(){
	frm.action="";
	myReset();
}
function doQuery(){
	var belevemember = frm.belevemember;
	var contact = frm.contact;
	var account = frm.account;
	var accountName = frm.accountName;
	if(!isStr(belevemember.value.trim(),false,null)){
		alert("��Ա��ŷǷ�");
		belevemember.focus();
	}else if(!isStr(contact.value.trim(),false,null)){
		alert("<%=CONTACTTITLE%>�Ƿ�");
		contact.focus();
	}else if(!isStr(account.value.trim(),false,null)){
		alert("�����˺ŷǷ�");
		account.focus();
	}else if(!isStr(accountName.value.trim(),true,new Array("��"))){
		alert("<%=ACCOUNTNAMETITLE%>�Ƿ�");
		accountName.focus();
	}else {
		frm.pageIndex.value=1;
		frm.action="accountMng.jsp";
		frm.submit();
	}
}

function reloadmainFrame(v1){
	window.top.workspace.mainFrame.location.replace(v1);
}

function gotoAccountMng2(v1,v2,v3,v4){
	var ck = null;
	var radios = document.getElementsByName("ck");
	for(var i=0;i<radios.length;i++){
		if(radios[i].checked == true){
			ck = radios[i].value;
		}
	}
	if('01_synchroCorr'==v4){//����Ͳ�ѯԤǩԼ
		if(ck == null){
			var result = window.showModalDialog("../ext_connector/01_synchroCorr.jsp?firmID="+"",""
					,"dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
			var resultsplit =null;
			resultsplit = result.split(',');
			if(resultsplit[0]=="1"){
				reloadmainFrame(resultsplit[3]+'?firmID='+resultsplit[2]+'&bankID='+resultsplit[1]+'&contact='+resultsplit[4]);
			}
		}else{
			var vec = ck.split(',');
			objmsg('submitFlag',v4);
			objmsg('MbankID',""+vec[0]);
			objmsg('MfirmID',""+vec[1]);
			objmsg('Mcontact',""+vec[2]);
			objmsg('MisOpen',""+vec[3]);
			var result = window.showModalDialog("../ext_connector/01_synchroCorr.jsp?firmID="+vec[1],""
					,"dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
			var resultsplit =null;
			resultsplit = result.split(',');
			if(resultsplit[0]=="1"){
				reloadmainFrame(resultsplit[3]+'?firmID='+resultsplit[2]+'&bankID='+resultsplit[1]+'&contact='+resultsplit[4]);
			}
		}
	}else{
		if(ck == null){
			alert("��ѡ��Ҫ����������");
		}else{
			var vec = ck.split(',');
			objmsg('submitFlag',v4);
			objmsg('MbankID',""+vec[0]);
			objmsg('MfirmID',""+vec[1]);
			objmsg('Mcontact',""+vec[2]);
			objmsg('MisOpen',""+vec[3]);
			if('openAccountdemo'==v4){//ǩԼ
				var result = window.showModalDialog("openAccountmedo.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&oldbankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
				if(result){
					frm.action="accountMng.jsp";
					frm.submit();
				}
			}else if('delAccountdemo'==v4){//��Լ
				if(vec[0]==null || vec[0]=="null"){
					alert("û�����в��ܽ�Լ");
				}else if(!delAccountBank(vec[0])){
					alert("���в�֧�ֽ�Լ����");
				}else if(vec[0].substr(0,1)!= 1){
					alert("��ģ�����пͻ����ܴ�ģ�����н�Լ");
				}else{
					var result = window.showModalDialog("delAccountmedo.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&bankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('02_openAccount'==v4){//����ǩԼ
				var result = window.showModalDialog("../ext_connector/02_openAccount.jsp?firmID="+vec[1],"","dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
				if(result=="1"){
					frm.action="accountMng.jsp";
					frm.submit();
				}
			}else if('03_delAccount'==v4){//�����Լ
				if(vec[0]==null || vec[0]=="null"){
					alert("�ÿͻ���ǩԼ���У����ܽ�Լ");
				}else if(!delAccountBank(vec[0])){
					alert("���в�֧�ֽ�Լ����");
				//}else if("005" != vec[0]){
				//	alert("��ũ�пͻ����ܴ�ũ�н�Լ");
				}else if(vec[0].substr(0,1)== 1){
					alert("ģ����������ģ�����н�Լ");
				}else{
					var result = window.showModalDialog("../ext_connector/03_delAccount.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&bankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result=="1"){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('disAccount'==v4){//����
				if(vec[3] != 1){
					alert("��ǩԼ״̬����Ҫ����");
				}else if(vec[4] != 0){
					alert("�Ѿ��ǽ���״̬");
				}else{
					var msg = "��ȷ��Ҫ���ã�\n";
					msg += "      <%=CONTACTTITLE%>��["+vec[2]+"]\n";
					if(confirm(msg)){
						frm.action="accountMng2.jsp";
						frm.submit();
					}
				}
			}else if('recAccount'==v4){//����
				if(vec[3] != 1){
					alert("��ǩԼ״̬��������");
				}else if(vec[4] == 0){
					alert("�Ѿ��ǿ���״̬");
				}else{
					var msg = "��ȷ��Ҫ���ã�\n";
					msg += "      <%=CONTACTTITLE%>��["+vec[2]+"]\n";
					if(confirm(msg)){
						frm.action="accountMng2.jsp";
						frm.submit();
					}
				}
			}else if('recPassword'==v4){//��ʼ������
				var msg = "��ȷ��Ҫ��ʼ���ʽ����룺\n";
				msg += "      <%=CONTACTTITLE%>��["+vec[2]+"]\n";
				if(confirm(msg)){
					frm.action="accountMng2.jsp";
					frm.submit();
				}
			}else if('modAccount'==v4){//�޸�
				var result = window.showModalDialog("modCorr.jsp?ID="+vec[5],"","dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
				if(result){
					frm.action="accountMng.jsp";
					frm.submit();
				}
			}else if('synchroAccount'==v4){//���˻�ͬ��
				var firmid=vec[1];
				var ck = document.getElementsByName("ck");
				var flag = false;
				
				for(var i=0;i<ck.length;i++){
					strs = ck[i].value.split(",");
					if(strs[0]=='006' && strs[1] == firmid){
						flag = true;
					}
				}
				
				if(flag){
					alert("�Ѿ��뻪������ǩԼ");
				}else if(vec[0]== "006" && vec[3]=="1"){
					alert("�Ѿ��뻪������ǩԼ");
				}else if(vec[0]== "006" && vec[3]=="2"){
					alert("���˻��Ѿ��뻪�����н�Լ������ִ�д˲���");
				}else{
				var result = window.showModalDialog("./ext_hxb/synchroCorr.jsp?ID="+vec[5],""
				,"dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
				//var msg = "��ȷ��Ҫͬ�����˻���\n";
					//msg += "      <%=CONTACTTITLE%>��["+vec[2]+"]\n";
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('delAccountHX'==v4){//�������н�Լ
				if(vec[0]==null || vec[0]=="null"){
					alert("�ÿͻ���ǩԼ���У����ܽ�Լ");
				}else if("006" != vec[0]){
					alert("�ǻ������пͻ����ܴӻ������н�Լ");
				}else{
					var result = window.showModalDialog("./ext_hxb/delAccounthx.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&bankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else{
				alert("������ʶ��");
			}
		}
	}
}
function objmsg(userID,msg){
	document.getElementById(userID).value=""+msg;
}
function pgTurn(i){
	frm.action="";
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

function pgJumpChk() {
	frm.action="";
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("��������ȷ���֣�");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function output(){
	var url = "accountExcle.jsp";
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
