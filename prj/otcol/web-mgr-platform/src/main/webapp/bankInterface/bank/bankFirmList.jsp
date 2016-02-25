<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.DecimalFormat"/>
<jsp:directive.page import="java.text.NumberFormat"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	String bankID = Tool.delNull(request.getParameter("bankID"));//���б��
	String firmID = Tool.delNull(request.getParameter("firmID"));//�����̱��
	String cardType = Tool.delNull(request.getParameter("cardType"));//�Թ���˽(1��˽,8�Թ�)
	String isOpen = Tool.delNull(request.getParameter("isOpen"));//�Ƿ�ǩԼ(1��ǩԼ,0δǩԼ)
	String status = Tool.delNull(request.getParameter("status"));//�Ƿ����(0����,1������)
	BankDAO dao = BankDAOFactory.getDAO();
	Vector bankFirms = dao.getCorrespondList(bankID,firmID,cardType,isOpen,status);
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	int maxpage = bankFirms.size()%pageSize==0 ? bankFirms.size()/pageSize : bankFirms.size()/pageSize+1;
	if(pageIndex>maxpage){
		pageIndex=maxpage;
	}
	ObjSet obj = ObjSet.getInstance(bankFirms, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<script language="javascript" src="../lib/validate.js"></script>
    <title>��������ѯ����������</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset>
			<legend>����<%=bankID%>�Ľ�������Ϣ</legend>
				
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
					<tr height="35">
						<td align="right">���д���&nbsp;</td>
						<td align="left"><input name="bankID" value="<%=bankID==null ? "" : bankID%>" type=text  class="text" maxlength="10" style="width: 100px"></td>
						<td align="right">�����̴���&nbsp;</td>
						<td align="left"><input name="firmID" value="<%=firmID==null ? "" : firmID%>" type=text  class="text" maxlength="10" style="width: 100px"></td>
						<td align="right">���������ͣ�&nbsp;</td>
						<td align="left">
							<select name="cardType">
								<option value="">��ѡ��</option>
								<option value="1" <%="1".equals(cardType) ? "selected" : "" %>>����</option>
								<option value="8" <%="8".equals(cardType) ? "selected" : "" %>>��ҵ</option>
							</select>
						</td>
					</tr>
					<tr height="35">
						<td align="right">�Ƿ���ǩԼ��&nbsp;</td>
						<td align="left">
							<select name="isOpen">
								<option value="">��ѡ��</option>
								<option value="1" <%="1".equals(isOpen) ? "selected" : "" %>>��ǩԼ</option>
								<option value="0" <%="0".equals(isOpen) ? "selected" : "" %>>δǩԼ</option>
								<option value="2" <%="2".equals(isOpen) ? "selected" : "" %>>ԤǩԼ�ɹ�</option>
							</select>
						</td>
						<td align="right">�Ƿ���ã�&nbsp;</td>
						<td align="left">
							<select name="status">
								<option value="">��ѡ��</option>
								<option value="0" <%="0".equals(status) ? "selected" : "" %>>����</option>
								<option value="1" <%="1".equals(status) ? "selected" : "" %>>����</option>
							</select>&nbsp;
						</td>
						<td align="right">&nbsp;<input type="button" class="smlbtn" value="��ѯ" onclick="doQuery()">
						&nbsp;
						</td>
						<td align="left">
						&nbsp;
						<input type="button" class="smlbtn" value="����" onclick="frm.reset();"></td>
					</tr>
				</table>
		</fieldset>	 <br>
				<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="350" align="center">
				<tHead>
					<tr height="25">
						<td class="panel_tHead_LB">&nbsp;</td>
						<!--<td class="panel_tHead_MB">���д���</td>-->
						<td class="panel_tHead_MB">�����̴���</td>
						<td class="panel_tHead_MB">�ʻ�����</td>
						<td class="panel_tHead_MB">��������</td>
						<td class="panel_tHead_MB">�Ƿ���ǩԼ</td>
						<td class="panel_tHead_MB">�Ƿ����</td>
						<td class="panel_tHead_MB">����������</td>
						<td class="panel_tHead_RB">&nbsp;</td>
					</tr>
				</tHead>
				<tBody>
				<% 
					for(int i=0;i<obj.getCurNum();i++){
					CorrespondValue cv = (CorrespondValue)obj.get(i);
				%>
					<tr onclick="selectTr();">
						<td class="panel_tBody_LB">&nbsp;</td>
						<!--<td class="underLine"><%//=cv.bankID%></td>-->
						<td class="underLine"><%=cv.firmID%></td>
						<td class="underLine"><%=(cv.accountName==null || "null".equals(cv.accountName)) ? "" : cv.accountName%>&nbsp;</td>
						<td class="underLine" align=left>
					<%
						if(cv.bankID == null || "0".equals(cv.bankID) || "".equals(cv.bankID) || "null".equals(cv.bankID)){
							out.print("-");
						}else{
							if(banksMap.get(cv.bankID) == null){
								out.print("--");
							}else{
								out.print(banksMap.get(cv.bankID).bankName);
							}
						}
					%>&nbsp;
					</td>
						<td class="underLine">
						<%
						if(cv.isOpen==1){
							out.println("��ǩԼ");
						}else if(cv.isOpen==2){
							out.println("ԤǩԼ�ɹ�");
						}else{
							out.println("δǩԼ");
						}
						%>
						</td>
						<td class="underLine"><%=cv.status==0 ? "����״̬" : "������"%></td>
						<td class="underLine"><%="8".equals(cv.cardType) ? "�Թ��˻�" : "�����˻�" %></td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
				<%}%>
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
							<input name="pageIndex" type="hidden" value="<%=pageIndex%>">
						</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">&nbsp;</td>
						<td class="panel_tFoot_MB" colspan="6"><br></td>
						<td class="panel_tFoot_RB">&nbsp;</td>
					</tr>
				</tFoot>
			</table> 
	</form>
  </body>
</html>
<script>
	function doQuery(){
		var bankID = frm.bankID.value;
		var firmID = frm.firmID.value;
		if(!calibration("str",bankID)){
			alert("���д�����Ϣ�Ƿ��ַ�");
			frm.bankID.focus();
		}else if(!calibration("str",firmID)){
			alert("�����̴�����Ϣ�Ƿ��ַ�");
			frm.firmID.focus();
		}else{
			frm.pageIndex.value = 1;
			frm.action="bankFirmList.jsp";
			frm.submit();
		}
	}
	function pgJumpChk(){
		if(event.keyCode == 13){
			if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )){
				alert("������1 - <%=obj.getPageCount()%>������֣�");			
			}else{
				frm.pageIndex.value = frm.pageJumpIdx.value;
				frm.submit();
			}
		}	
	}

	function pgTurn(i)
{
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
</script>