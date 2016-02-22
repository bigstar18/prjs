<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
int pageSize = BANKPAGESIZE;
String today = Tool.fmtDate(new java.util.Date());
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
String firmID = Tool.delNull(request.getParameter("firmID"));
String bankID = Tool.delNull(request.getParameter("bankID"));
String firmType = Tool.delNull(request.getParameter("firmType"));
String capitalType = Tool.delNull(request.getParameter("capitalType"));
String capitalStatus = Tool.delNull(request.getParameter("capitalStatus"));
String s_time = Tool.delNull(request.getParameter("s_time"));
String e_time = Tool.delNull(request.getParameter("e_time"));
String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
if(submitFlag == null || submitFlag.trim().length()<=0){
	if(s_time == null || s_time.trim().length()<=0){
		s_time=today;
	}
	if(e_time == null || e_time.trim().length()<=0){
		e_time=today;
	}
}
String filter = "";
if(firmID != null && firmID.trim().length()>0){
	filter += " and fbc.firmID like '%"+firmID.trim()+"%' ";
}
if(bankID != null && bankID.trim().length()>0 && !bankID.trim().equalsIgnoreCase("-2")){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(firmType != null && firmType.trim().length()>0 && !firmType.trim().equalsIgnoreCase("-2")){
	filter += " and mf.firmtype='"+firmType.trim()+"' ";
}
if(capitalType != null && capitalType.trim().length()>0 && !"-2".equals(capitalType)){
	filter += " and fbc.type="+capitalType;
}
if(capitalStatus != null && capitalStatus.trim().length()>0 && !"-2".equals(capitalStatus)){
	if(capitalStatus.equals("2")){
		filter += " and fbc.status not in ("+ProcConstants.statusSuccess+","+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") ";
	}else{
		filter += " and fbc.status="+capitalStatus;
	}
}
if(s_time != null && s_time.trim().length()>0){
	filter += " and trunc(createtime)>=to_date('"+s_time+"','yyyy-MM-dd') ";
}
if(e_time != null && e_time.trim().length()>0){
	filter += " and trunc(createtime)<=to_date('"+e_time+"','yyyy-MM-dd') ";
}
filter += " order by createtime,id";
System.out.println(filter);
Vector moneyList = dao.getCapitalInfoList2(filter);
//System.out.println(moneyList.size());
double allMoney = 0;
for(int i = 0 ; i <moneyList.size();i++){
	CapitalValue money = (CapitalValue)moneyList.get(i);
	allMoney += money.money;
}
int maxpage = moneyList.size()%pageSize==0 ? moneyList.size()/pageSize : moneyList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);
Vector bankList = dao.getBankList(" and validFlag=0 ");
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/tools.js"></script>
	<!-- <script language="javascript" src="<%//=request.getContextPath()%>/bankInterface/lib/validate.js"></script> -->
	<script language="javascript" src="<%=request.getContextPath()%>/bankInterface/lib/My97DatePicker/WdatePicker.js"></script>
    <title>凭证列表</title>
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
										<td class="table2_td_widthcdmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed;">
													<tr>
														<td class="table3_td_1" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text"
																	class="input_text" name="firmID" value="<%//=firmID%>" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															银行名称:&nbsp;
															<label>
																<select name="bankID" class="normal" style="width:120px">
																	<OPTION value="">请选择</OPTION>
															   <%
																for(int i=0;i<bankList.size();i++) {
																	BankValue bv = (BankValue)bankList.get(i);
																	%>
																	<option value="<%=bv.bankID%>" <%//=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
																	<%
																}
																%>
																</select>
															</label>
														</td>
                                                        <td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态:&nbsp;
															<label>
																<select name="capitalStatus" class="normal" style="width: 100px">
																	<OPTION value="">全部</OPTION>
																	<option value="<%=ProcConstants.statusSuccess%>" <%//=(""+ProcConstants.statusSuccess).equals(capitalStatus)?"selected":""%>>成功</option>
																	<option value="<%=ProcConstants.statusFailure%>" <%//=(""+ProcConstants.statusFailure).equals(capitalStatus)?"selected":""%>>失败</option>
																	<option value="<%=ProcConstants.statusBankProcessing%>" <%//=(""+ProcConstants.statusBankProcessing).equals(capitalStatus)?"selected":""%>>处理中</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															转账类型:&nbsp;
															<label>
																<select name="capitalType" class="normal" style="width: 100px">
							                                     <OPTION value="">全部</OPTION>
							                                     <option value="<%=ProcConstants.inMoneyType%>" <%//=(""+ProcConstants.inMoneyType).equals(capitalType)?"selected":""%>>入金</option>
              				                                     <option value="<%=ProcConstants.outMoneyType%>" <%//=(""+ProcConstants.outMoneyType).equals(capitalType)?"selected":""%>>出金</option>
						                                       </select>
															</label>
														</td>
													</tr>
													<tr>
									                    
														
														 <td class="table3_td_1mid" align="left">
															交易账号类型:&nbsp;
															<label>
																<select name="firmType" class="normal" style="width: 100px">
							                                     <OPTION value="">全部</OPTION>
							                                     <option value="C" <%//=firmType.equals("C")?"selected":""%>>客户</option>
              				                                     <option value="M" <%//=firmType.equals("M")?"selected":""%>>综合会员</option>
              				                                     <option value="S" <%//=firmType.equals("S")?"selected":""%>>特别会员</option>
						                                       </select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width:120px" class="wdate" maxlength="10" name="s_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1" align="left">
														         终止日期:&nbsp;
																<input type="text" style="width:100px" class="wdate" maxlength="10" name="e_time" value="<%=today%>" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1mid" align="left">
															<button class="btn_sec" onclick="doQuery();">
																查询
															</button>&nbsp;
															<button class="btn_sec" onclick="ConvertToExcel('tableList',0,1)">
																导出
															</button>
														     &nbsp;
															<button class="btn_cz" onclick="frm.reset();">
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
						<div class="div_list" style="overflow-x:scroll;">
				<table style="table-layout:fixed;" id="tableList" border="0" cellspacing="0" cellpadding="0" width="150%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">转账时间</td>
				<td class="panel_tHead_MB" align="center">交易所流水号</td>
				<td class="panel_tHead_MB" align="center">银行流水号</td>
				<td class="panel_tHead_MB" align="center">银行名称</td>
				<td class="panel_tHead_MB" align="center">客户或会员编号</td>
				<td class="panel_tHead_MB" align="center">账号类型</td>
				<td class="panel_tHead_MB" align="center">银行账户名称</td>
				<td class="panel_tHead_MB" align="center">交易资金账号</td>
				<td class="panel_tHead_MB" align="center">转账金额</td>
				<td class="panel_tHead_MB" align="center">转账类型</td>
				<td class="panel_tHead_MB" align="center">处理状态</td>
				<td class="panel_tHead_MB_last" align="center">备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
			double ins = 0;//入金成功
			int insn = 0;
			double ini = 0;//入金处理中
			int inin = 0;
			double inf = 0;//入金失败
			int infn = 0;
			double ous = 0;//出金成功
			int ousn = 0;
			double oui = 0;//出金处理中
			int ouin = 0;
			double ouf = 0;//出金失败
			int oufn = 0;
			for(int i=0;i<obj.getCurNum();i++){
				CapitalValue money = (CapitalValue)obj.get(i);
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
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.actionID%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.funID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if("C".equals(money.firmType)) {
							out.print("客户");
						}else if("M".equals(money.firmType)){
							out.print("会员");
						}else if("S".equals(money.firmType)){
							out.print("特别会员");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.accountName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(money.account)%>&nbsp;</td>
					<td class="underLine" align="right"><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.type==ProcConstants.inMoneyType){
							out.print("入金");
						}else if(money.type==ProcConstants.outMoneyType){
							out.print("出金");
						}else if(money.type==ProcConstants.inMoneyBlunt){
							out.print("入金冲正");
						}else if(money.type==ProcConstants.outMoneyBlunt){
							out.print("出金冲正");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(money.status==ProcConstants.statusSuccess){
							out.print("成功");
						}else if(money.status==ProcConstants.statusFailure || money.status==ProcConstants.statusBlunt){
							out.print("失败");
						}else{
							out.print("待处理");
						}
					%>&nbsp;</td>
					<td class="underLine" title="<%=Tool.delNull(money.note)%>" align="center"><%=Tool.delNull(money.note).length()>5 ? Tool.delNull(money.note).substring(0,4):Tool.delNull(money.note)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">当前结果：&nbsp;</td>
				<td class="underLine" align="left">成功合计共<%=insn+ousn%>笔</td>
				<td class="underLine" align="left" colspan="3">入金共计：<%=Tool.fmtDouble2(ins)%>(元)&nbsp;<%=insn%>笔</td>
				<td class="underLine" align="left" colspan="3">出金共计：<%=Tool.fmtDouble2(ous)%>(元)&nbsp;<%=ousn%>笔</td>
				<td class="underLine" align="left" colspan="3">出入金差值：<%=Tool.fmtDouble2(ins-ous)%>(元)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine" align="left">失败合计共<%=infn+oufn%>笔</td>
				<td class="underLine" align="left" colspan="3">入金共计：<%=Tool.fmtDouble2(inf)%>(元)&nbsp;<%=infn%>笔</td>
				<td class="underLine" align="left" colspan="3">出金共计：<%=Tool.fmtDouble2(ouf)%>(元)&nbsp;<%=oufn%>笔</td>
				<td class="underLine" align="left" colspan="3">出入金差值：<%=Tool.fmtDouble2(inf-ouf)%>(元)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22" align=center>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align="right">&nbsp;</td>
				<td class="underLine" align="left">处理中合计共<%=inin+ouin%>笔</td>
				<td class="underLine" align="left" colspan="3">入金共计：<%=Tool.fmtDouble2(ini)%>(元)&nbsp;<%=inin%>笔</td>
				<td class="underLine" align="left" colspan="3">出金共计：<%=Tool.fmtDouble2(oui)%>(元)&nbsp;<%=ouin%>笔</td>
				<td class="underLine" align="left" colspan="3">出入金差值：<%=Tool.fmtDouble2(ini-oui)%>(元)</td>
				<td class="underLine" align="left">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="12">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="12" align=right>
				第<%=pageIndex%>/<%=obj.getPageCount()%>页 &nbsp;&nbsp;共<%=obj.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageIndex != 1) {
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">上页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}
				if(pageIndex != obj.getPageCount()) {
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">尾页</span> &nbsp;&nbsp;	
					<%
				} else {
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="12"></td>
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
	frm.firmID.value="<%=firmID%>";
	frm.bankID.value="<%=bankID%>";
	frm.firmType.value="<%=firmType%>";
	frm.capitalType.value="<%=capitalType%>";
	frm.s_time.value="<%=s_time%>";
	frm.e_time.value="<%=e_time%>";
	frm.capitalStatus.value="<%=capitalStatus%>";
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function pgTurn(i) {
	frm.submitFlag.value="do";
	if(i == 0) {
		frm.pageIndex.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex.value = <%=obj.getPageCount()%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function doQuery()
{
	frm.submitFlag.value="do";
	var firmID = frm.firmID.value;
	var s_time = frm.s_time.value;
	var e_time = frm.e_time.value;
	var now1 = new Date()+"";
	if(!ismyStr(firmID)){
		alert("交易商信息非法字符");
		frm.firmID.focus();
	} else if(s_time != "" && !CompareDate(s_time,now1)){
		alert("起始日期不合法，请输入(yyyy-MM-dd)格式日期");
		frm.s_time.value="";
	} else if(e_time != "" && !CompareDate(e_time,now1)){
		alert("结束日期不合法，请输入(yyyy-MM-dd)格式日期");
		frm.s_time.value="";
	} else if(e_time != "" && !CompareDate(s_time,e_time)){
		alert("输入的开始日期不能超过结束日期");
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
	frm.submitFlag.value="do";
	if(event.keyCode == 13)
	{
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
		{
			alert("请输入1 - <%=obj.getPageCount()%>间的数字！");			
		}
		else
		{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
/**
 * 将table中的数据放置到Excel表格中去
 * tableID 表格的ID号
 * rowStart 从第几行开始
 * colStart 从第几列开始
 */
function ConvertToExcel(tableID, rowStart ,colStart){
	var xlApp,myWorkbook,myWordsheet,myHTMLTableCell,myExcelCell,myExcelCell2;
	var myCellColSpan,myCellRowSpan;
	try{
		xlApp=new ActiveXObject("Excel.Application");//创建Excel对象
	}catch(e){
		alert("无法启动Excel!\n\n"+e.message+"\n\n如果您确信您的电脑中已经安装了Excel，那么请调整IE的安全级别。");
		return false;
	}
	xlApp.Visible=true;//设置Excel对象可见
	myWorkbook=xlApp.Workbooks.Add();//获取WorkBook对象
	xlApp.DisplayAlerts=false;//当Excel打开后马上关闭
	myWorkbook.Worksheets(3).Delete();
	myWorkbook.Worksheets(2).Delete();
	xlApp.DisplayAlerts=true;//当Excel打开后不关闭
	myWorksheet=myWorkbook.ActiveSheet;
	var obj = document.all.tags("table");//获取当前页中所有的表格
	var RealColCount;  //实际列数（通过HTML表格的第二行得到）
	RealColCount=0;
	for(x=0;x<obj.length;x++) {//遍历取得的这些表格
		if(obj[x].id==tableID) {//如果表格的ID号等于传入的ID号
			myWorksheet.Cells.NumberFormatLocal="@";//设置单元格格式为文本
			for (i=rowStart; i<obj[x].rows.length-4; i++) {//遍历这个表格每一行
				for (j=colStart; j<obj[x].rows(i).cells.length-1; j++){//遍历表格的每一列
					myExcelCell=myWorksheet.Cells(i+1-rowStart,j+1-colStart);//获取这个Excel表格的十字交叉的位置表格
					myHTMLTableCell=obj[x].rows(i).cells(j);//取得页面table表格中十字交叉列的<td>
					if(i==rowStart){
						myExcelCell.Borders.Weight = 1;//设置首行边框
						myExcelCell.Interior.ColorIndex = 6;//设置首行为底色为黄色
					}
					myExcelCell.HorizontalAlignment = 3;//设置单元格居中显示
					myExcelCell.Value = myHTMLTableCell.innerText;//将表格十字交叉位置<td>的值放到Excel表格的十字交叉位置
					myCellColSpan = myHTMLTableCell.colSpan;//取得当前单元格占用了多少列
					myCellRowSpan = myHTMLTableCell.rowSpan;//取得当前单元格占用了多少行
					//不是第一行，且有合并单元格的情况
					if((i>=rowStart)&&(myCellColSpan*myCellRowSpan>1)){
						myExcelCell2=myWorksheet.Cells(i+1-rowStart+(myCellRowSpan-1),j+1-colStart+(myCellColSpan-1));
						myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
					}
					if(i==rowStart+1){
						RealColCount=RealColCount+myCellColSpan;
					}
				}
			}
			//如果有标题栏（第一个有效行的单元格数量为1），则处理标题栏的合并情况
			if((obj[x].rows(rowStart).cells.length==1)&&(RealColCount>0)){
				myExcelCell=myWorksheet.Cells(1,1);
				myExcelCell2=myWorksheet.Cells(1,RealColCount);
				myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
			}
		}
	}
	myWorksheet.Columns("A:IV").AutoFit;
}
//-->
</SCRIPT>