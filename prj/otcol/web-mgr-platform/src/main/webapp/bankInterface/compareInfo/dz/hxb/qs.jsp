<%@ page contentType="text/html;charset=GB2312"%>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="gnnt.trade.bank.hx.*"/>
<%@ include file="../globalDef.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	String bankID = Tool.delNull(request.getParameter("bankID"));
	String date = Tool.delNull(request.getParameter("date"));
	ExchangeData ed = new ExchangeDataImpl();
	List<QS> qsList = new ArrayList<QS>();
	if("do".equals(request.getParameter("opt"))){
		Connection conn = null;
		try
		{
			conn = dao.getConnection();
			qsList = ed.getQS(bankID,date,conn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
%>

<html xmlns:MEBS>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<IMPORT namespace="MEBS" implementation="../../../lib/calendar.htc">
<script language="javascript" src="../../../lib/excelTools.js"></script>
<script language="javascript" src="../../../lib/frameCtrl.js"></script>
		<title>子账户查询</title>
	</head>

	<body>
		<form id="frm" name="frm" action="" method="post">
			<fieldset width="95%">
				<legend><%=bankID%>子账户信息查询
				</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
						<td align="right">
							银行代码&nbsp;
						</td>
						<td align="left">
							<%
								Vector bankList = dao.getBankList(" where validFlag=0 ");
							%>
							<select name="bankID" class="normal" style="width: 140px">
								<OPTION value="-1">请选择</OPTION>
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
							日期选择&nbsp;
						</td>
						<td align="left">
							<MEBS:calendar eltName="date" eltStyle="width:80px"
								eltImgPath="../../../skin/default/images/" eltValue="<%=date%>" />
						</td>
						<td align="left">
							<button type="button" class="smlbtn" onclick="doQuery();">
								查询
							</button>
							&nbsp;
							<button type="button" class="smlbtn" onclick="frm.reset();">
								重置
							</button>&nbsp;
							<button type="button" class="smlbtn" onclick="exportTxt();">
								导出数据
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
						<!-- <td class="panel_tHead_LB">
							&nbsp;
						</td> -->
						<td class="panel_tHead_MB">
							标志位
						</td>
						<td class="panel_tHead_MB">
							子账号
						</td>
						<td class="panel_tHead_MB">
							借贷标志
						</td>
						<td class="panel_tHead_MB">
							金额
						</td>
						<td class="panel_tHead_MB">
							摘要
						</td>
						<!-- <td class="panel_tHead_RB">
							&nbsp;
						</td> -->
					</tr>
				</tHead>
			<%if(qsList.size()>0){ %>
				<tBody>
					<%
							for (int i = 0; i < qsList.size(); i++) {
							QS qs = (QS) qsList.get(i);
					%>
					<tr height="22" align=center>
						<!-- <td class="panel_tBody_LB">&nbsp;</td> -->
						<td class="underLine"><%=qs.flag%></td>
						<td class="underLine"><%=qs.firmId%></td>
						<td class="underLine"><%=qs.direction%></td>
						<td class="underLine"><%=(Tool.fmtDouble2(qs.amt)==null || Tool.fmtDouble2(qs.amt).trim().length()==0) ? "0.00" : Tool.fmtDouble2(qs.amt)%></td>
						<td class="underLine"><%=qs.abstct==null ? "" : qs.abstct%></td>
						<!-- <td class="panel_tBody_RB">&nbsp;</td> -->
					</tr>
					<%
					}
					%>
				</tBody>				
			<%
				}//核对失败显示明细
			%>
			<!-- <tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">
							&nbsp;
						</td>
						<td colspan="5">
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
						<td class="panel_tFoot_MB" colspan="5"><br></td>
						<td class="panel_tFoot_RB">
							&nbsp;
						</td>
					</tr>
				</tFoot> -->
			</table>
			<input type="hidden" name="opt">
			</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery()
	{
		if(frm.bankID.value==-1){
			alert("请选择银行");
			frm.bankID.focus();
		}else if (frm.date.value == ""){
			alert("请选择日期");
			frm.date.focus();
		}else{
			frm.opt.value="do";
			frm.submit();
		}
	}
	function exportTxt()
	{
		if(frm.bankID.value==-1){
			alert("请选择银行");
			frm.bankID.focus();
		}else if (frm.date.value == ""){
			alert("请选择日期");
			frm.date.focus();
		}else{
			var tablePrint = document.getElementById("tableList");
			var path="d:\\duizhang\\qs_100008_"+frm.date.value.substr(0,4)+frm.date.value.substr(5,2)+frm.date.value.substr(8,2)+".txt";
			try{
				if(tablePrint)
					exportToTxt(tablePrint,1,0,path);
				else
					alert('此导出功能只用于结果列表，主窗口中未发现结果列表！');
			}catch(e){
				alert("请先在D盘建立'duizhang'文件夹");
			}
		}
	}
//-->
</SCRIPT>
