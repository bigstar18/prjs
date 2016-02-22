<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	CEBBankDao dao = BankDAOFactory.getCEBDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String bankID = request.getParameter("bankID");
	Vector bankList = dao.getBankList(" and validflag=0 and bankID='"+bankID+"'");
	String strs = "";
	for(int i=0;i<bankList.size();i++){
		BankValue bank = (BankValue)bankList.get(i);
		out.print("<input type='hidden' id='"+bank.bankID+"' value='"+bank.bankName+"'/>");
	}
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankid='"+bankID+"'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
		corr.status = 0;
		
		new ErrorCode().load();
	}
	if(corr == null){
		out.print("δ��Ѱ���ͻ���Ϣ");
		return ;
	}
	if("do".equals(request.getParameter("submitFlag"))){
		
		long bak = -1;
		String str="�����˺Ź���-��Լ��������˺�["+firmID+"]";
		
		if(bankList.size()==0){
			strs = "�����˺�["+firmID+"]��Լʧ�ܣ����нӿڽ���";
		}else{
			
				try {
					//�ദ����·��
					CapitalProcessorRMI cp = null;
					try{
						cp = getBankUrl(corr.bankID);
					}catch(Exception e){
						e.printStackTrace();
					}
					//corr.otherBank = Integer.parseInt(request.getParameter("InOutStart"));
					ReturnValue result=cp.delAccountMaket(corr);
					if(result.result<0){
						if(result.remark != null && result.remark.trim().length()>0){
							strs = result.remark;
						}else if(ErrorCode.error.get(result.result) != null){
							strs = ErrorCode.error.get(result.result);
						}else{
							strs = "������ ["+ result.result+"]";
						}
					}else{
						bak = 0;
						strs = "�����˺�["+corr.firmID+"]��Լ�ɹ�";
					}
				} catch(Exception e) {
					strs = "�����˺�["+corr.firmID+"]��Լϵͳ�쳣";
					e.printStackTrace();
				}
				
			
		}
	%>
		<script>
			alert('<%=strs%>');
			<%
				if(bak == 0){
			%>
				window.returnValue="1";
				window.close();
			<%
				}
			%>
		</script>
	<%
	}
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../../lib/jquery/style/validator.css"></link>
    <title>�г��˽�Լ�ͻ��˺�</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag" id="submitFlag">
	<div style="overflow:auto;height:150;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title">&nbsp;(�������)�����������Լ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="20">
					<td align="right" width="25%">&nbsp;</td>
					<td align="left" width="30%">
						<input name="firmID"  value="<%=firmID%>" readonly type="hidden"  class="input_text" style="width: 140px">
						<input type="hidden" value="<%=firmID%>" name="firmID">
                         <input type="hidden" value="<%=bankID%>" name="bankID">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" readonly value="<%=contact%>" type=text  class="input_text" style="width: 140px">
					</td>
				</tr>
				<!--<tr height="35" >
						<td width="30%" align="right">�Ƿ����&nbsp;</td>
						<td align="left"><p>
						<label>
							<input type="radio" name="InOutStart" value="1"  checked = "checked">
							��</label>
						&nbsp;
						<label>
							<input type="radio" name="InOutStart" value="2" >
							��</label>
						&nbsp;
						
						</td>
					</tr>-->
				<tr height="20">
					<td align="right">&nbsp;</td>
					<td align="left">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">
					<td align="right">&nbsp;</td>
					<td align="left">
						&nbsp;&nbsp;
					</td>
				</tr>
			<tr height="35">					
				<td align="center" colspan=2>
					<input id="sub_btn" class="btn_sec" onclick="delAccount();"value="��Լ"></input>&nbsp;
					<input id="bak_btn" class="btn_cz" onclick="window.history.back();"value="����"></input>&nbsp;
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function delAccount(){
	if('<%=corr.isOpen%>'=='1'){
		var msg = "���Ƿ�Ҫ��Լ�˻���";
			msg += "\n<%=CONTACTTITLE%>["+idobj('contact').value+"]";
			//msg += "\nũ�пͻ����["+idobj('account1').value+"]";
			//msg += "\n����["+idobj('<%=bankID%>').value+"]";
		if(confirm(msg)){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
			idobj("submitFlag").value="do";
			frm.submit();
		}
	}else{
		if(confirm("�����˺Ų���ǩԼ״̬���Ƿ����Լ?")){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
			idobj("submitFlag").value="do";
			frm.submit();
		}
	}
}
function idobj(userID){
	return document.getElementById(userID);
}
//-->
</SCRIPT>
