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
String filter = " and mf.firmType='C' ";
String ORGANIZATIONID = (String)session.getAttribute("ORGANIZATIONID");
if(ORGANIZATIONID==null || ORGANIZATIONID.trim().length()<=0){
	filter += " and mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' ";
	//filter += " and exists (select customerno from m_customerinfo mc where mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}else{
	filter += " and mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' ";
	//filter += " and exists (select customerno from v_customerrelateorganization mc where mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' and fbf.firmID=mc.customerno) ";
}
String bankID = request.getParameter("bankID");
String isOpen = request.getParameter("isOpen");
String status = request.getParameter("status");
String cardType = request.getParameter("cardType");
String frozenFuns = request.getParameter("frozenFuns");
String firmID = request.getParameter("firmID");
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
System.out.println(filter);
Vector dicList = dao.getCorrespondList2(filter,pageinfo);
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>客户管理</title>
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
								class="table2_style" style="table-layout:fixed;height:70px;" >
								<tr>
									<td style="height:40px;width: 790px;">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0" style="table-layout:fixed;">
												<tr>
												   <td width="100" align="right">
														<%=FIRMTITLE%>:
													</td>
													<td width="140">
														<input type="text" class="input_text" name="contact" value=""/>
													</td>
													<td align="left" width=200>
														&nbsp;&nbsp;&nbsp;&nbsp;<%=BANKTITLE%>：
														<label>
															<select name="bankID" class="normal" style="width: 90px">
																<OPTION value="">全部</OPTION>
																<%
																for(int i=0;i<bankList.size();i++){
																	BankValue bank = (BankValue)bankList.get(i);
																	if(bank.bankID.startsWith("1")){
																		medoBank = true;
																	}
																	%>
																	<option value="<%=bank.bankID%>"><%=bank.bankName%></option>	
																	<%
																}
																%>
															</select>
														</label>
													</td>
													<td align="left">
													是否可用:
														<select name="status" style="width:70px;">
															<option value="">全部</option>
															<option value="0">可用</option>
															<option value="1">不可用</option>
														</select>&nbsp;
													</td>
												   <td align="left" style="width:180px;">
														证件类型：&nbsp;
														<select name="cardType" style="width:70px;">
															<option value="">全部</option>
															<option value="1">身份证</option>
															<option value="8">机构代码</option>
															<option value="9">营业执照</option>
															<option value="other">其他</option>
														</select>
													</td>
												</tr>
												<tr>
												    <td align="right">
														<%=ACCOUNTNAMETITLE%>:
													</td>
													<td>
														<input type="text" class="input_text" name="accountName" value=""/>
													</td>
													
													<td width="200" align="left">
														<%=ACCOUNTTITLE%>：
														<input type="text" class="input_text" name="account" value=""/>
													</td>
													<td  align="left" >
														在途资金:
														<select name="frozenFuns" style="width:70px;">
															<option value="">全部</option>
															<option value="1">有</option>
															<option value="2">无</option>
														</select>
													</td>
													<td align="left">
														签约状态：&nbsp;
														<select name="isOpen" style="width: 70px">
															<option value="">全部</option>
															<option value="1">已签约</option>
															<option value="0">未签约</option>
															<option value="2">已解约</option>
														</select>
													</td>
												</tr>
												<tr>
												   <td align="right">
														
													</td>
													<td>
														
													</td>
													 <td align="left">
														
													</td>
													<td align="left" colspan="2">
														<button class="btn_sec" onclick="doQuery();">
															查询
														</button>&nbsp;
														<button class="btn_sec" onclick="output()">
															导出
														</button>&nbsp;
														<button class="btn_cz" onclick="myreset();">
															重置
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
											模拟银行签约
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','delAccountdemo');">
											模拟银行解约
										</button>&nbsp;&nbsp;
									<%}%>
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','01_synchroCorr');">
											发起预签约
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','02_openAccount');">
											发起签约
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','03_delAccount');">
											发起解约
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="gotoAccountMng2(frm,tableList,'ck','recPassword');">
											初始化资金密码
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
						<div class="div_list">
							 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1100" height="400">
								<tHead>
									<tr align=center>
										<td class="panel_tHead_LB">&nbsp;</td>
										<td class="panel_tHead_MB">&nbsp;</td>
										<td class="panel_tHead_MB"><%=SEQUNCE%></td>
										<td class="panel_tHead_MB" align="center"><%=FIRMTITLE%></td>
										<td class="panel_tHead_MB" align="center"><%=BANKTITLE%></td>
										<td class="panel_tHead_MB" align="center">账户属性</td>
										<td class="panel_tHead_MB" align="center"><%=ACCOUNTTITLE%></td>
										<td class="panel_tHead_MB" align="center"><%=ACCOUNTNAMETITLE%></td>
										<td class="panel_tHead_MB" align="center">证件类型</td>
										<td class="panel_tHead_MB" align="center">签约状态</td>
										<td class="panel_tHead_MB" align="center">是否可用</td>
										<td class="panel_tHead_MB_last" align="center">在途资金</td>
										<td class="panel_tHead_RB">&nbsp;</td>
									</tr>
								</tHead>
								<tBody>
	  		<%
			for(int i=0;i<dicList.size();i++) {
				CorrespondValue corr = (CorrespondValue)dicList.get(i);
				%>
				<tr align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="left"><input name="ck" type="radio" value='<%=corr.bankID%>,<%=corr.firmID%>,<%=corr.contact%>,<%=corr.isOpen%>,<%=corr.status%>,<%=corr.id%>'></td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=replaceNull(corr.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(corr.name)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(corr.bankID==null || corr.bankID.trim().length()<=0){
							out.print("-");
						}else if(corr.bankID.trim().equals(corr.mainBank)){
							out.print("主账户");
						}else{
							out.print("次账户");
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
					<td class="underLine" align="center"><%=replaceNull(corr.getCardType())%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(1==corr.isOpen){
							out.print("已签约");
						}else if(0==corr.isOpen){
							out.print("<font color='#FF0000'>未签约</font>");
						}else if(2==corr.isOpen){
							out.print("<font color='#FF0000'>已解约</font>");
						}else{
							out.print("<font color='#FF0000'>状态不明</font>");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(0==corr.status){
							out.print("可用");
						}else if(1==corr.status){
							out.print("<font color='#FF0000'>不可用</font>");
						}else{
							out.print("<font color='#FF0000'>状态未知</font>");
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
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11" align=right>
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(pageinfo[1] < pageinfo[3])
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" maxlength="4" value="<%=pageinfo[1]%>" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

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
frm.cardType.value = '<%=Tool.delNull(cardType)%>';
frm.frozenFuns.value = '<%=Tool.delNull(frozenFuns)%>';
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
	var contact = frm.contact;
	var account = frm.account;
	var accountName = frm.accountName;
	if(!is_Str(contact.value.trim(),false,null)){
		alert("签约号非法");
		contact.focus();
	}else if(!is_Str(account.value.trim(),false,null)){
		alert("银行账号非法");
		account.focus();
	}else if(!is_Str(accountName.value.trim(),true,new Array("·"))){
		alert("账户名非法");
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
	if('01_synchroCorr'==v4){//发起和查询预签约
		if(ck == null){
			var result = window.showModalDialog("../ext_connector/01_synchroCorr.jsp?firmID="+"",""
					,"dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
			var resultsplit =null;
			resultsplit = result.split(',');
			if(resultsplit[0]=="1"){
				reloadmainFrame(resultsplit[3]+'?firmID='+resultsplit[2]+'&bankID='+resultsplit[1]);
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
				reloadmainFrame(resultsplit[3]+'?firmID='+resultsplit[2]+'&bankID='+resultsplit[1]);
			}
		}
	}else{
		if(ck == null){
			alert("请选择要操作的数据");
		}else{
			var vec = ck.split(',');
			objmsg('submitFlag',v4);
			objmsg('MbankID',""+vec[0]);
			objmsg('MfirmID',""+vec[1]);
			objmsg('Mcontact',""+vec[2]);
			objmsg('MisOpen',""+vec[3]);
			if('openAccountdemo'==v4){//签约
				var result = window.showModalDialog("openAccountmedo.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&oldbankID="+vec[0],"","dialogWidth=500px; dialogHeight=340px; status=yes;scroll=yes;help=no;");
				if(result){
					frm.action="accountMng.jsp";
					frm.submit();
				}
			}else if('delAccountdemo'==v4){//解约
				if(vec[0]==null || vec[0]=="null"){
					alert("该客户未签约，不能解约");
				}else if(!delAccountBank(vec[0])){
					alert("该银行不支持解约功能");
				}else if(vec[0].substr(0,1)!=1){
					alert("非模拟银行客户不能从模拟银行解约");
				}else{
					var result = window.showModalDialog("delAccountmedo.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&oldbankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('02_openAccount'==v4){//发起签约
				var result = window.showModalDialog("../ext_connector/02_openAccount.jsp?firmID="+vec[1],"","dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
				if(result=="1"){
					frm.action="accountMng.jsp";
					frm.submit();
				}
			}else if('03_delAccount'==v4){//发起解约
				if(vec[0]==null || vec[0]=="null"){
					alert("该客户无签约银行，不能解约");
				}else if(!delAccountBank(vec[0])){
					alert("银行不支持解约功能");
				//}else if("005" != vec[0]){
				//	alert("非农行客户不能从农行解约");
				}else if(vec[0].substr(0,1)== 1){
					alert("模拟银行请点击模拟银行解约");
				}else{
					var result = window.showModalDialog("../ext_connector/03_delAccount.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&bankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result=="1"){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('recPassword'==v4){//初始化密码
				var msg = "您确认要初始化资金密码：\n";
				msg += "      交易账号：["+vec[2]+"]\n";
				if(confirm(msg)){
					frm.action="accountMng2.jsp";
					frm.submit();
				}
			}else if('synchroCorr'==v4){//预签约
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
					alert("已经与华夏银行签约");
				}else if(vec[0]== "006" && vec[3]=="1"){
					alert("已经与华夏银行签约");
				}else if(vec[0]== "006" && vec[3]=="2"){
					alert("该账户已经与华夏银行解约，不能执行此操作");
				}else{
				var result = window.showModalDialog("./ext_hxb/synchroCorr.jsp?ID="+vec[5],""
				,"dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
				//var msg = "您确认要同步子账户：\n";
					//msg += "      <%=CONTACTTITLE%>：["+vec[2]+"]\n";
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else if('delAccountHX'==v4){//华夏银行解约
				if(vec[0]==null || vec[0]=="null"){
					alert("该客户无签约银行，不能解约");
				}else if("006" != vec[0]){
					alert("非华夏银行客户不能从华夏银行解约");
				}else{
					var result = window.showModalDialog("./ext_hxb/delAccounthx.jsp?firmID="+vec[1]+"&contact="+vec[2]+"&oldbankID="+vec[0],"","dialogWidth=500px; dialogHeight=300px; status=yes;scroll=yes;help=no;");
					if(result){
						frm.action="accountMng.jsp";
						frm.submit();
					}
				}
			}else{
				alert("操作不识别");
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
			alert("请输入正确数字！");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function isEmpty(s){
	if(s.trim().length<=0){
		return true;
	}
	return false;
}
function is_Str(s,ch,vec){
	if(isEmpty(s)){
		return true;
	}
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
function output(){
	var url = "accountExcle.jsp";
	if(confirm("导出全部数据吗?\n\n(取消为导出当前页)")){
		frm.thispage.value="0";
	}else{
		frm.thispage.value="1";
	}
	frm.action=url;
	frm.submit();
}
//-->
</SCRIPT>
