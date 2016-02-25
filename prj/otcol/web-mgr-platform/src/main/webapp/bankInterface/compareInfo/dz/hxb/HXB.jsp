<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.io.File"/>
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
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String s_time = Tool.delNull(request.getParameter("s_time"));
	if(s_time.equals("")) {
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	int cibpageSizezfph = BANKPAGESIZE;//�ܷ�ƽ��ҳ������
	int cibpageSizeffhd = BANKPAGESIZE;//�ַֺ˶�ҳ������
	int cibpageIndexzfph = 0;//�ܷ�ƽ��ҳ���
	int cibpageIndexffhd = 0;//�ַֺ˶�ҳ���
	ObjSet hxbqsxx=null;//������Ϣ
	Vector hxbgetqsxx =null;//�ܷ�ƽ��
	String result = "---";
	if("hxbQS".equals(request.getParameter("submitFlag"))) {//����������Ϣ
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.hxSentQS(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
		dao.log(lv);
	}else if("hxbDZ".equals(request.getParameter("submitFlag"))) {//���Ͷ�����Ϣ
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		lv.setIp(computerIP);
		ReturnValue rv = cp.hxSentDZ(bankID,Tool.strToDate(s_time));
		result = rv.remark;
		lv.setLogcontent(result+";����ID"+bankID+"ʱ�䣺"+new java.util.Date());
		dao.log(lv);
	}else if("hxbGetQS".equals(request.getParameter("submitFlag"))) {//�鿴������Ϣ
		try{
			hxbgetqsxx = dao.getHXQSMsg(bankID,null,Tool.strToDate(s_time));
		}catch(Exception e){
			
		}
		int size = Tool.strToInt((String)request.getParameter("cibpageSizezfph"));
		if(size>0){
			cibpageSizezfph = size;
		}
		cibpageIndexzfph = Tool.strToInt((String)request.getParameter("cibpageIndexzfph"));
		if(cibpageIndexzfph <= 0) {
			cibpageIndexzfph = 1;
		}
		int maxpage = hxbgetqsxx.size()%cibpageSizezfph==0 ? hxbgetqsxx.size()/cibpageSizezfph : hxbgetqsxx.size()/cibpageSizezfph+1;
		if(cibpageIndexzfph>maxpage){
			cibpageIndexzfph=maxpage;
		}
		if(hxbgetqsxx.size()<=0){
			result = "δ��ѯ������["+bankID+"]�ܷ�����";
		}else{
			result = "����["+bankID+"]�ܷ���������";
		}
	}
	hxbqsxx = ObjSet.getInstance(hxbgetqsxx, cibpageSizezfph, cibpageIndexzfph);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
    <title>������������ҳ����Ϣ</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
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
					<td align="right">���������&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(1);" value="����������Ϣ">
						&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(2);" value="���Ͷ�����Ϣ">
						&nbsp;&nbsp;&nbsp;
						<!-- 
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(getdisableBtn()){out.print("disabled='disabled'");} %> onclick="cibbuttonclick(3);" value="�鿴������Ϣ">
						&nbsp;&nbsp;&nbsp;
						-->
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>	  
		<%
		if(hxbqsxx!=null && hxbqsxx.size()!=0) {
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">���д���</td>
				<td class="panel_tHead_MB">��ϸ�˻�������</td>
				<td class="panel_tHead_MB">�����˺Ž��</td>
				<td class="panel_tHead_MB">�����ʶ</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">��������</td>
				<td class="panel_tHead_MB">���˽��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		</table>
		<%
		}
		%>
	<input name=cibpageIndexzfph type=hidden value="<%=cibpageIndexzfph%>">
	<input name=cibpageIndexffhd type=hidden value="<%=cibpageIndexffhd%>">
	</form>
  </body>
</html>
<script type="text/javascript">  
	function cibbuttonclick(v) {
		var now1 = new Date()+"";
		if(frm.s_time.value == "") {
			alert("��ѡ��������ڣ�");
		}else if(CompareDate(now1,(frm.s_time.value))){
			alert("ѡ��Ķ������ڲ��Ϸ�!");
			frm.s_time.value="";
		}else {
			if(v==1) {//������������
				frm.submitFlag.value = "hxbQS";
				frm.submit();	
			} else if(v==2) {//ȡ�ܷ�ƽ��
				frm.submitFlag.value = "hxbDZ";
				frm.submit();
			} else if(v==3) {//ȡ�ַֺ˶�
				frm.submitFlag.value = "hzxGetQS";
				frm.submit();	
			} 
		}
	}
	function cibpgTurnzfph(i) {
		frm.submitFlag.value = "cibGETZFPH";
		if(i == 0) {
			frm.cibpageIndexzfph.value = 1;
			frm.submit();
		} else if(i == 1) {		
			frm.cibpageIndexzfph.value = frm.cibpageIndexzfph.value * 1 + 1;	
			frm.submit();
		} else if(i == 2) {
			<%if(hxbqsxx!=null){ %> frm.cibpageIndexzfph.value = <%=hxbqsxx.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.cibpageIndexzfph.value = frm.cibpageIndexzfph.value - 1;
			frm.submit();
		}
	}
	function cibpgTurnffhd(i) {
		frm.submitFlag.value = "cibGETFFHD";
		if(i == 0) {
			frm.cibpageIndexffhd.value = 1;
			frm.submit();
		} else if(i == 1) {
			frm.cibpageIndexffhd.value = frm.cibpageIndexffhd.value * 1 + 1;	
			frm.submit();
		} else if(i == 2) {
			<%if(hxbqsxx!=null){ %> frm.cibpageIndexffhd.value = <%=hxbqsxx.getPageCount()%><%}%>;
			frm.submit();
		} else if(i == -1) {
			frm.cibpageIndexffhd.value = frm.cibpageIndexffhd.value - 1;
			frm.submit();
		}
	}
	function cibpgJumpChkzfph() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxzfph.value) || frm.cibpageJumpIdxzfph.value < 1 || frm.cibpageJumpIdxzfph.value > <%=hxbqsxx.getPageCount()%>) && (isNaN(frm.cibpageSizezfph.value) || frm.cibpageSizezfph.value < 1 )) {
				alert("������1 - <%=hxbqsxx.getPageCount()%>������֣�");			
			} else {
				frm.submitFlag.value = "cibGETZFPH";
				frm.cibpageIndexzfph.value = frm.cibpageJumpIdxzfph.value;
				frm.submit();
			}
		}
	}
	function cibpgJumpChkffhd() {
		if(event.keyCode == 13) {
			if((isNaN(frm.cibpageJumpIdxffhd.value) || frm.cibpageJumpIdxffhd.value < 1 || frm.cibpageJumpIdxffhd.value > <%=hxbqsxx.getPageCount()%>) && (isNaN(frm.cibpageSizeffhd.value) || frm.cibpageSizeffhd.value < 1 )) {
				alert("������1 - <%=hxbqsxx.getPageCount()%>������֣�");			
			} else {
				frm.submitFlag.value = "cibGETFFHD";
				frm.cibpageIndexffhd.value = frm.cibpageJumpIdxffhd.value;
				frm.submit();
			}
		}
	}
	function CompareDate(d1,d2) {
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>