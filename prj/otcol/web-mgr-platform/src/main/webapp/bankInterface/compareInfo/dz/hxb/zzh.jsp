<%@ page contentType="text/html;charset=GB2312"%>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="gnnt.trade.bank.hx.*"/>
<%@ include file="../../../globalDef.jsp"%>
<%@ include file="../../../session.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String date = Tool.delNull(request.getParameter("date"));
	ExchangeData ed = new ExchangeDataImpl();
	List<ZZH> zzhList = null;
	Connection conn = null;
	try{
		conn = dao.getConnection();
		zzhList = ed.getZZH(bankID,date,conn);
	}catch(Exception e){
	}
	if(zzhList == null){
		zzhList = new ArrayList<ZZH>();
	}
%>

<html xmlns:MEBS>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<IMPORT namespace="MEBS" implementation="../../../lib/calendar.htc">
<script language="javascript" src="../../../lib/excelTools.js"></script>
<script language="javascript" src="../../../lib/frameCtrl.js"></script>
		<title>���˻���ѯ</title>
	</head>

	<body>
		<form id="frm" name="frm" action="" method="post">
			<fieldset width="95%">
				<legend><%=bankID%>���˻���Ϣ��ѯ
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							���д���&nbsp;
						</td>
						<td align="left">
							<%
								Vector bankList = dao.getBankList(" where validFlag=0 ");
							%>
							<select name="bankID" class="normal" style="width: 140px">
								<OPTION value="-1">��ѡ��</OPTION>
								<%
								for(int i=0;i<bankList.size();i++)
								{
									BankValue bank = (BankValue)bankList.get(i);
								%>
									<option value="<%=bank.bankID%>" <% if(bank.bankID.equals(bankID)){out.print("selected='selected'");} %>><%=bank.bankName%></option>	
								<%
								}
								%>
							</select>
						</td>
						<td align="right">
							����ѡ��&nbsp;
						</td>
						<td align="left">
							<MEBS:calendar eltName="date" eltStyle="width:80px"
								eltImgPath="../../../skin/default/images/" eltValue="<%=date%>" />
						</td>
						<td align="left">
							<button type="button" class="smlbtn" onclick="doQuery();">
								��ѯ
							</button>
							&nbsp;
							<button type="button" class="smlbtn" onclick="frm.reset();">
								����
							</button>&nbsp;
							<button type="button" class="smlbtn" onclick="exportTxt();">
								��������
							</button>
							&nbsp;
						</td>
					</tr>
				</table>
			</fieldset>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="400">
				<tHead>
					<tr height="25" align=center>
						<!--<td class="panel_tHead_LB">
							&nbsp;
						</td>-->
						<td class="panel_tHead_MB">
							���˻���
						</td>
						<td class="panel_tHead_MB">
							����������
						</td>
						<td class="panel_tHead_MB">
							���˻����
						</td>
						<td class="panel_tHead_MB">
							�˻�����
						</td>
						<!--<td class="panel_tHead_RB">
							&nbsp;
						</td>-->
					</tr>
				</tHead>
			<%if(zzhList.size()>0){ %>
				<tBody>
					<%
							for (int i = 0; i < zzhList.size(); i++) {
							ZZH zzh = (ZZH) zzhList.get(i);
					%>
					<tr height="22" align=center>
						<!--<td class="panel_tBody_LB">
							&nbsp;
						</td>-->
						<td class="underLine"><%=zzh.firmId%></td>
						<td class="underLine"><%=zzh.firmName%></td>
						<td class="underLine"><%=Tool.fmtDouble2(zzh.amt)%></td>
						<td class="underLine"><%=zzh.type%></td>
						<!--<td class="panel_tBody_RB">
							&nbsp;
						</td>-->
					</tr>
					<%
					}
					%>
				</tBody>				
			<%
				}//�˶�ʧ����ʾ��ϸ
			%>
			<!--<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td colspan="4">
							&nbsp;
						</td>
						<td class="panel_tBody_RB">
							&nbsp;
						</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">
							&nbsp;
						</td>
						<td class="panel_tFoot_MB" colspan="4"><br></td>
						<td class="panel_tFoot_RB">
							&nbsp;
						</td>
					</tr>
				</tFoot>-->
			</table>
			<input type="hidden" name="tDate" >
			</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery()
	{
		if(frm.bankID.value==-1){
			alert("��ѡ������");
			frm.bankID.focus();
		}else if (frm.date.value==""){
			alert("��ѡ��ʱ��");
			frm.date.focus();
		}else{
			frm.submit();
		}
	}
	function exportTxt()
	{
		if(frm.bankID.value==-1){
			alert("��ѡ������");
			frm.bankID.focus();
		}else if (frm.date.value==""){
			alert("��ѡ��ʱ��");
			frm.date.focus();
		}else{
			var tablePrint = document.getElementById("tableList");
			var path="d:/duizhang/zzh_100008_"+frm.date.value.substr(0,4)+frm.date.value.substr(5,2)+frm.date.value.substr(8,2)+".txt";
			try{
				if(tablePrint)
					exportToTxt(tablePrint,1,0,path);
				else
					alert('�˵�������ֻ���ڽ���б���������δ���ֽ���б�');
			}catch(e){
				alert("������D�̽���'duizhang'�ļ���"+e);
			}
		}
	}
//-->
</SCRIPT>
