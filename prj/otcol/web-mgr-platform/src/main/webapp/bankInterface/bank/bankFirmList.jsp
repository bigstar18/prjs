<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.DecimalFormat"/>
<jsp:directive.page import="java.text.NumberFormat"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	String bankID = Tool.delNull(request.getParameter("bankID"));//银行编号
	String firmID = Tool.delNull(request.getParameter("firmID"));//交易商编号
	String cardType = Tool.delNull(request.getParameter("cardType"));//对公对私(1对私,8对公)
	String isOpen = Tool.delNull(request.getParameter("isOpen"));//是否签约(1已签约,0未签约)
	String status = Tool.delNull(request.getParameter("status"));//是否可用(0可用,1不可用)
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
    <title>按条件查询交易商条数</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset>
			<legend>银行<%=bankID%>的交易商信息</legend>
				
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
					<tr height="35">
						<td align="right">银行代码&nbsp;</td>
						<td align="left"><input name="bankID" value="<%=bankID==null ? "" : bankID%>" type=text  class="text" maxlength="10" style="width: 100px"></td>
						<td align="right">交易商代码&nbsp;</td>
						<td align="left"><input name="firmID" value="<%=firmID==null ? "" : firmID%>" type=text  class="text" maxlength="10" style="width: 100px"></td>
						<td align="right">交易商类型：&nbsp;</td>
						<td align="left">
							<select name="cardType">
								<option value="">请选择</option>
								<option value="1" <%="1".equals(cardType) ? "selected" : "" %>>个人</option>
								<option value="8" <%="8".equals(cardType) ? "selected" : "" %>>企业</option>
							</select>
						</td>
					</tr>
					<tr height="35">
						<td align="right">是否已签约：&nbsp;</td>
						<td align="left">
							<select name="isOpen">
								<option value="">请选择</option>
								<option value="1" <%="1".equals(isOpen) ? "selected" : "" %>>已签约</option>
								<option value="0" <%="0".equals(isOpen) ? "selected" : "" %>>未签约</option>
								<option value="2" <%="2".equals(isOpen) ? "selected" : "" %>>预签约成功</option>
							</select>
						</td>
						<td align="right">是否可用：&nbsp;</td>
						<td align="left">
							<select name="status">
								<option value="">请选择</option>
								<option value="0" <%="0".equals(status) ? "selected" : "" %>>可用</option>
								<option value="1" <%="1".equals(status) ? "selected" : "" %>>冻结</option>
							</select>&nbsp;
						</td>
						<td align="right">&nbsp;<input type="button" class="smlbtn" value="查询" onclick="doQuery()">
						&nbsp;
						</td>
						<td align="left">
						&nbsp;
						<input type="button" class="smlbtn" value="重置" onclick="frm.reset();"></td>
					</tr>
				</table>
		</fieldset>	 <br>
				<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="350" align="center">
				<tHead>
					<tr height="25">
						<td class="panel_tHead_LB">&nbsp;</td>
						<!--<td class="panel_tHead_MB">银行代码</td>-->
						<td class="panel_tHead_MB">交易商代码</td>
						<td class="panel_tHead_MB">帐户名称</td>
						<td class="panel_tHead_MB">银行名称</td>
						<td class="panel_tHead_MB">是否已签约</td>
						<td class="panel_tHead_MB">是否可用</td>
						<td class="panel_tHead_MB">交易商类型</td>
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
							out.println("已签约");
						}else if(cv.isOpen==2){
							out.println("预签约成功");
						}else{
							out.println("未签约");
						}
						%>
						</td>
						<td class="underLine"><%=cv.status==0 ? "可用状态" : "不可用"%></td>
						<td class="underLine"><%="8".equals(cv.cardType) ? "对公账户" : "个人账户" %></td>
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
							第<%=pageIndex%>/<%=obj.getPageCount()%>页 &nbsp;&nbsp;共<%=obj.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
							<%
								if(pageIndex != 1)
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
								if(pageIndex != obj.getPageCount())
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
							到<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页
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
			alert("银行代码信息非法字符");
			frm.bankID.focus();
		}else if(!calibration("str",firmID)){
			alert("交易商代码信息非法字符");
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
				alert("请输入1 - <%=obj.getPageCount()%>间的数字！");			
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